package com.irontrack.irontrack.service;


import com.irontrack.irontrack.entity.User;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.lang.reflect.Field;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;



@Service
public class TokenJwtService {
    /**
     * Chave secreta usada para ASSINAR e VALIDAR o token JWT.
     *
     * IMPORTANTE:
     * - Vem do application.properties ou variável de ambiente
     * - Deve estar em Base64
     * - Nunca deve ficar hardcoded no código
     */
    private final String secretKey;

    public TokenJwtService(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Método genérico para extrair QUALQUER informação (claim) do token.
     *
     * @param token JWT recebido na requisição
     * @param claimsResolver Função que diz qual informação queremos extrair
     * @return Valor da claim desejada
     *
     * Exemplo de uso:
     * - Extrair username → Claims::getSubject
     * - Extrair expiração → Claims::getExpiration
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrai o username (subject) do token JWT.
     *
     * No JWT, o "subject" normalmente representa o usuário.
     */
    public String extractUsername(String token){return extractClaim(token, Claims::getSubject);}

    /**
     * Gera um token JWT para o usuário autenticado.
     *
     * @param extraClaims Informações extras que podem ser adicionadas ao token
     * @param userDetails Usuário autenticado pelo Spring Security
     * @return Token JWT assinado
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims) // Claims adicionais (roles, permissões, etc.)
                .setSubject(userDetails.getUsername())// Identificação do usuário
                .setIssuedAt(new Date(System.currentTimeMillis()))// Data de criação
                .setExpiration(new Date(System.currentTimeMillis() + 1000* 60 * 24)) // Expiração do token
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)// Assinatura
                .compact(); // Gera o token final
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User user){
            claims.put("userId", user.getId());
        }
        return generateToken(claims, userDetails);
    }

    /**
     * Verifica se o token é válido para um usuário específico.
     *
     * Regras:
     * - O username do token deve ser igual ao do usuário
     * - O token não pode estar expirado
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) { return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Lê TODAS as claims do token JWT.
     *
     * Aqui ocorre:
     * - Validação da assinatura
     * - Verificação da integridade do token
     *
     * Se o token for inválido, uma exceção é lançada.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Integer extractUserId(String token){
        return extractClaim(token, claims -> claims.get("userId", Integer.class));
    }

    private Key getSingInKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

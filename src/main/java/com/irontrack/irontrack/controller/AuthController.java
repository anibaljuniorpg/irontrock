package com.irontrack.irontrack.controller;

import com.irontrack.irontrack.dto.AuthDTO;
import com.irontrack.irontrack.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDTO request){
        log.info("Register Info: {}", request.username());
        try {
            String jwt = authService.register(request);
            //Se o token JWT não tiver exatamente dois pontos (.), ele é considerado inválido e a execução é interrompida com erro.
            if (jwt.chars().filter(ch -> ch =='.').count() != 2){
                throw  new IllegalArgumentException("Invalid JWT token");
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwt);
            return ResponseEntity.ok().headers(headers).body(jwt);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO request){
        log.info("Login Info: {}", request.username());
        String jwt = authService.login(request);
        if (jwt.chars().filter(ch -> ch == '.').count() != 2){
            throw new IllegalArgumentException("Invalid JWT token");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        return ResponseEntity.ok().headers(headers).body(jwt);
    }
}

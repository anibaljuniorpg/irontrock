package com.irontrack.irontrack.service;

import com.irontrack.irontrack.dto.AuthDTO;
import com.irontrack.irontrack.entity.User;
import com.irontrack.irontrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenJwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(AuthDTO request){
        if (repository.findByUsername(request.username()).isPresent()){
            try {
                throw  new IllegalAccessException("Username exists");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        User user =User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        repository.save(user);
        return jwtService.generateToken(user);
    }

    public String login(AuthDTO request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = repository.findByUsername(request.username()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }

}

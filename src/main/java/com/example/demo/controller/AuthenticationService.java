package com.example.demo.controller;

import com.example.demo.config.JwtService;
import com.example.demo.model.entity.AuthenticationResponse;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {

        System.out.println(request.getPassword());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User test= userRepository.findByUsername(request.getUsername()).get();
         String s = passwordEncoder.encode(test.getPassword());

        boolean isMatch = passwordEncoder.matches(request.getPassword(),userRepository.findByUsername(request.getUsername()).get().getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken
                        (request.getUsername(), request.getPassword()));

        var user= userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken= jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}

package com.ibk.identityserver.service.impl;

import com.ibk.identityserver.dto.LoginResponse;
import com.ibk.identityserver.service.AuthService;
import com.ibk.identityserver.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String accessToken = jwtUtil.generateToken(username);
            return LoginResponse
                    .builder()
                    .accessToken(accessToken)
                    .tokenType("Bearer")
                    .expiresIn(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                    .build();
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}

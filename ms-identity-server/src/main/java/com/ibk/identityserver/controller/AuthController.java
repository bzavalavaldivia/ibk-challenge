package com.ibk.identityserver.controller;

import com.ibk.identityserver.dto.LoginRequest;
import com.ibk.identityserver.dto.LoginResponse;
import com.ibk.identityserver.entity.User;
import com.ibk.identityserver.service.AuthService;
import com.ibk.identityserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }
}

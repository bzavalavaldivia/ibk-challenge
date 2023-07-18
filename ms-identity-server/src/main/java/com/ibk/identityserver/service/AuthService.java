package com.ibk.identityserver.service;

import com.ibk.identityserver.dto.LoginResponse;

public interface AuthService {
    public LoginResponse login(String username, String password);
}

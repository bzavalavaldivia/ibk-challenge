package com.ibk.identityserver.service;

import com.ibk.identityserver.entity.User;

import java.util.Optional;

public interface UserService {
    public User createUser(User user);

    public Optional<User> findUserByUsername(String username);
}

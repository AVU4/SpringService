package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {

    User findOneByUsername(String username);
    User findByAuthToken(String token);
    User save(User user);
}
package com.huy.aristino.service;

import com.huy.aristino.model.User;

import java.util.Map;

public interface UserService {

    Boolean existsByUsername(String username);

    Boolean existsByPhone(String phone);

    Boolean existsByEmail(String email);

    User register(User user);

    Map<String, String> validateUser(User user);

    User login(User user);

}

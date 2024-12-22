package com.huy.aristino.service.impl;

import com.huy.aristino.model.User;
import com.huy.aristino.repository.UserRepository;
import com.huy.aristino.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }

    @Override
    public Map<String, String> validateUser(User user) {
        Map<String, String> errors = new HashMap<>();

        if (existsByUsername(user.getUsername())) {
            errors.put("username", "Tên người dùng đã tồn tại.");
        }

        if (existsByPhone(user.getPhone())) {
            errors.put("phone", "Số điện thoại đã tồn tại.");
        }

        if (existsByEmail(user.getEmail())) {
            errors.put("email", "Email đã tồn tại.");
        }

        return errors;
    }

    @Override
    public User login(User user) {
        User loginUser = userRepository.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        return loginUser;
    }
}

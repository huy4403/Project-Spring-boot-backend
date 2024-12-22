package com.huy.aristino.controller;

import com.huy.aristino.model.User;
import com.huy.aristino.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserController {
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Map<String, String> errors = userService.validateUser(user);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        User newUser = userService.register(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User userLogin = userService.login(user);
        if(userLogin != null) {
            return new ResponseEntity<>(userLogin, HttpStatus.OK);
        }else{
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Tài khoản hoặc mật khẩu không chính xác");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}

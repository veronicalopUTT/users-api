package com.chakray.test.users.controller;

import com.chakray.test.users.model.User;
import com.chakray.test.users.service.UserService;
import org.springframework.web.bind.annotation.*;

import com.chakray.test.users.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {

        return userService.login(
                request.getTaxId(),
                request.getPassword()
        );
    }
}

package com.example.postservice.controller;

import org.example.common.model.UserDTO;
import com.example.postservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public UserDTO getUserInfo(@RequestHeader("Authorization") String token){
        return userService.getUserByToken(token);
    }

}

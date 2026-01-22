package com.example.postservice.controller;

import com.example.postservice.DTO.UserDTO;
import com.example.postservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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

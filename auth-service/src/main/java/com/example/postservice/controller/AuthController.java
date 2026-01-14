package com.example.postservice.controller;

import com.example.postservice.DTO.TokenRequest;
import com.example.postservice.DTO.UserRegisterRequest;
import com.example.postservice.DTO.UserResponse;
import com.example.postservice.DTO.LoginResponse;
import com.example.postservice.exception.UsernameAlreadyExistException;
import com.example.postservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public TokenRequest login(@RequestBody LoginResponse jwtRequest) {
        return authService.login(new LoginResponse(jwtRequest.getLogin(), jwtRequest.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterRequest> register(@RequestBody UserResponse userRequest) throws UsernameAlreadyExistException {

        return ResponseEntity.ok(authService.register(userRequest));
    }

}

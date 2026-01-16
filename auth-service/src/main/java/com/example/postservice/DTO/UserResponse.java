package com.example.postservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserResponse {
    private String username;
    private String surname;
    private String password;
    private Set<String> roles;
}

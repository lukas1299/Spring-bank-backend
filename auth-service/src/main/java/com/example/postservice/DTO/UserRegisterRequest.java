package com.example.postservice.DTO;

import org.example.common.model.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class UserRegisterRequest {

    private String username;
    private String surname;
    private String password;
    private Set<String> roles;
    private AccountDTO account;
}

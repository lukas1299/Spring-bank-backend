package com.example.accountservice.controller;

import org.example.common.model.AccountDTO;
import com.example.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{userId}/create")
    public AccountDTO createAccount(@PathVariable("userId") UUID userId){
        return accountService.createAccount(userId);
    }

    @GetMapping("/info")
    public AccountDTO getAccountInfo(@RequestHeader("Authorization") String token){
        return accountService.getUserAccount(token);
    }

    @GetMapping("/{accountNumber}/info")
    public AccountDTO getAccountInfoByAccountNumber(@PathVariable(name = "accountNumber") String accountNumber){
        return accountService.getUserAccountByAccountNumber(accountNumber);
    }
}

package com.example.accountservice.controller;

import com.example.accountservice.model.AccountDTO;
import com.example.accountservice.model.AccountRequest;
import com.example.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/info")
    public AccountDTO getAccountInfo(@RequestHeader("Authorization") String token){
        return accountService.getUserAccount(token);
    }
    @GetMapping("/{accountNumber}/info")
    public AccountDTO getAccountInfoByAccountNumber(@PathVariable(name = "accountNumber") String accountNumber){
        return accountService.getUserAccountByAccountNumber(accountNumber);
    }
    @PostMapping("/{userId}/create")
    public AccountDTO createAccount(@PathVariable("userId") UUID userId){
        return accountService.createAccount(userId);
    }

    @PutMapping
    public AccountDTO updateAccount(@RequestParam String targetAccountNumber, @RequestParam BigDecimal amount, @RequestParam String operation) throws Exception {
        return accountService.updateAccount(targetAccountNumber, amount, operation);
    }
}

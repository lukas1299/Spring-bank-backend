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
@CrossOrigin
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{userId}")
    public AccountDTO getAccount(@PathVariable("userId") UUID userId){
        return accountService.getUserAccount(userId);
    }

    @PostMapping("/{userId}/create")
    public AccountDTO createAccount(@PathVariable("userId") UUID userId){
        return accountService.createAccount(userId);
    }

    @PostMapping("/{userId}/transaction")
    public ResponseEntity<String> realizeTransaction(@PathVariable("userId") String userId, @RequestBody AccountRequest accountRequest){
        return ResponseEntity.ok(accountService.realizeTransaction(userId, accountRequest));
    }

    @PatchMapping
    public AccountDTO updateAccount(@RequestParam String targetAccountNumber, @RequestParam BigDecimal amount, @RequestParam String operation){
        return accountService.updateAccount(targetAccountNumber, amount, operation);
    }
}

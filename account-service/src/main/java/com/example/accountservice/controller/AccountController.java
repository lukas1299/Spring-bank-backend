package com.example.accountservice.controller;

import com.example.accountservice.model.CreditCardRequest;
import com.example.accountservice.model.CreditCardResponse;
import org.example.common.model.AccountDTO;
import com.example.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
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

    @PostMapping("/{accountId}/creditCard")
    public ResponseEntity<CreditCardResponse> addCreditCardToAccount(@RequestBody CreditCardRequest creditCardRequest, @PathVariable(name = "accountId") UUID accountId, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(accountService.addCreditCardToAccount(creditCardRequest, accountId, token));
    }

    @GetMapping("/{accountId}/creditCard")
    public ResponseEntity<Set<CreditCardResponse>> getCreditCardsBelongingToAccount(@PathVariable(name = "accountId") UUID accountId, @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(accountService.getCreditCardBelongingToAccount(accountId, token));
    }


}

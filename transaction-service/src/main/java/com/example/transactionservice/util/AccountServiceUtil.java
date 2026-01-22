package com.example.transactionservice.util;

import com.example.accountservice.model.AccountRequest;
import com.example.transactionservice.model.AccountDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(value = "account-service")
public interface AccountServiceUtil {

    @PostMapping("/account/{userId}/transaction")
    AccountDTO createAccount(@PathVariable("userId") UUID userId, @RequestBody AccountRequest accountRequest);

    @PutMapping("/account")
    AccountDTO updateAccount(@RequestParam String targetAccountNumber, @RequestParam BigDecimal amount, @RequestParam String operation);

    @GetMapping("/account/{accountNumber}/info")
    AccountDTO getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber);
}





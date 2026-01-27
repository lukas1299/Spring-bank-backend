package com.example.transactionservice.util;

import com.example.transactionservice.model.AccountDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(value = "account-service")
public interface AccountServiceUtil {

    @PutMapping("/account")
    AccountDTO updateAccount(@RequestParam String targetAccountNumber, @RequestParam BigDecimal amount, @RequestParam String operation);

    @GetMapping("/account/{accountNumber}/info")
    AccountDTO getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber);
}





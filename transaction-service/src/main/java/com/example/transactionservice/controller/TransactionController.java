package com.example.transactionservice.controller;

import com.example.transactionservice.model.TransactionResponse;
import com.example.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.example.common.model.TransactionRequest;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public void createTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionService.createTransaction(transactionRequest);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getRealizedUserTransactions(
            @RequestParam("accountNumber") String accessNumber,
            @RequestParam(value = "status", required = false) Integer status ,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(transactionService.getRealizedUserTransactions(accessNumber, status, pageable));
    }
}

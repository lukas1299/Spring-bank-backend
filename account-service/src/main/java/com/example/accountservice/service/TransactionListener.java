package com.example.accountservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.model.TransactionRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionListener {

    private final AccountService accountService;

    @KafkaListener(topics = "transaction-subtract-commands")
    public void onTransactionSubtractCommand(TransactionRequest transactionRequest) throws ClassNotFoundException {
        accountService.realizeSubtractTransaction(transactionRequest);
    }

    @KafkaListener(topics = "transaction-add-commands")
    public void onTransactionAddCommand(TransactionRequest transactionRequest) throws ClassNotFoundException {
        accountService.realizeAddTransaction(transactionRequest);
    }
}

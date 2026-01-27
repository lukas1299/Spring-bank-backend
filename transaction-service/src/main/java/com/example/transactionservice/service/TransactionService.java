package com.example.transactionservice.service;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.util.TransactionOrchestratorUtil;
import lombok.RequiredArgsConstructor;
import org.example.common.model.TransactionRequest;
import org.example.common.model.TransactionStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionOrchestratorUtil transactionOrchestratorUtil;

    public void createTransaction(TransactionRequest transactionRequest) {
        UUID transactionId = UUID.randomUUID();
        transactionRepository.save(new Transaction(
                transactionId,
                transactionRequest.getFrom(),
                transactionRequest.getTargetAccountNumber(),
                new Date(System.currentTimeMillis()),
                transactionRequest.getAmount(),
                TransactionStatus.PENDING));
        transactionRequest.setId(transactionId);
        transactionRequest.setTransactionStatus(TransactionStatus.PENDING);
        transactionOrchestratorUtil.initializeTransaction(transactionRequest);
    }
}

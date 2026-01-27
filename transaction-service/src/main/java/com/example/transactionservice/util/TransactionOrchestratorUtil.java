package com.example.transactionservice.util;

import org.example.common.model.TransactionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "transaction-orchestrator")
public interface TransactionOrchestratorUtil {
    @PostMapping("/transaction/initialize")
    void initializeTransaction(TransactionRequest transactionRequest);
}

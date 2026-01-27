package org.example.service;

import lombok.RequiredArgsConstructor;

import org.example.common.model.TransactionRequest;
import org.example.common.model.TransactionStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final KafkaTemplate<String, TransactionRequest> kafkaTemplate;

    public void startTransactionSaga(TransactionRequest transactionRequest){
        kafkaTemplate.send("transaction-subtract-commands", transactionRequest);
    }

    @KafkaListener(topics = "transaction-subtract-events", groupId = "group")
    public void onTransactionSubtractEvents(TransactionRequest transactionRequest){
        if(transactionRequest.getTransactionStatus().equals(TransactionStatus.REALIZED)){
            transactionRequest.setTransactionStatus(TransactionStatus.PENDING);
            kafkaTemplate.send("transaction-add-commands", transactionRequest);
        } else {
            kafkaTemplate.send("transaction-finalize-commands", transactionRequest);
        }
    }

    @KafkaListener(topics = "transaction-add-events", groupId = "group")
    public void onTransactionAddEvents(TransactionRequest transactionRequest){
        kafkaTemplate.send("transaction-finalize-commands", transactionRequest);
    }
}

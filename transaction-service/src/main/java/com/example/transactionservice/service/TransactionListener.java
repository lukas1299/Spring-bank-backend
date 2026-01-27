package com.example.transactionservice.service;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.example.common.model.TransactionRequest;
import org.example.common.model.TransactionStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransactionListener {

    private final TransactionRepository transactionRepository;

    @KafkaListener(topics = "transaction-finalize-commands", groupId = "group")
    public void onTransactionFinalizeCommands(TransactionRequest transactionRequest) throws ClassNotFoundException {
        Transaction transaction = transactionRepository.findById(transactionRequest.getId()).orElseThrow(ClassNotFoundException::new);

        if(transactionRequest.getTransactionStatus().equals(TransactionStatus.CANCELED)){
            transaction.setStatus(TransactionStatus.CANCELED);
        } else {
            transaction.setStatus(TransactionStatus.REALIZED);
        }
        transactionRepository.save(transaction);
    }
}

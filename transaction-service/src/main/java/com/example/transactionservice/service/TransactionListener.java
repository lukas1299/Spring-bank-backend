package com.example.transactionservice.service;

import com.example.accountservice.model.AccountRequest;
import com.example.transactionservice.config.RabbitMQConfig;
import com.example.transactionservice.model.AccountDTO;
import com.example.transactionservice.model.Email;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.util.AccountServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionListener {

    private final AccountServiceUtil accountServiceUtil;
    private final RabbitTemplate rabbitTemplate;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    @RabbitListener(queues = "transaction")
    public void createTransaction(AccountRequest accountRequest) {

        Transaction transaction = transactionService.createTransaction(accountRequest);

        //accountServiceUtil.updateAccount(accountRequest.getTargetAccountNumber(), accountRequest.getAmount(), "ADD");

        //AccountDTO account = accountServiceUtil.getAccount(UUID.fromString(accountRequest.getFrom()));
        //accountServiceUtil.updateAccount(account.getAccountNumber(), accountRequest.getAmount(), "SUBTRACT");

        transactionRepository.save(transaction);
        sentEmail();
    }

    private void sentEmail() {
        Email mess = new Email("Mess", "INFO");
        rabbitTemplate.convertAndSend(RabbitMQConfig.queueName, mess);
    }
}

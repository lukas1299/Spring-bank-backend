package com.example.transactionservice.service;


import com.example.accountservice.model.AccountRequest;
import com.example.transactionservice.model.AccountDTO;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import com.example.transactionservice.util.AccountServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountServiceUtil accountServiceUtil;

    public Transaction createTransaction(AccountRequest accountRequest) {
        realizeTransaction(accountRequest);
        return transactionRepository.save(new Transaction(UUID.randomUUID(),
                accountRequest.getFrom(),
                accountRequest.getTargetAccountNumber(),
                new Date(System.currentTimeMillis()),
                accountRequest.getAmount()));
    }
    private void realizeTransaction(AccountRequest accountRequest){
        accountServiceUtil.updateAccount(accountRequest.getTargetAccountNumber(), accountRequest.getAmount(), "ADD");

        AccountDTO account = accountServiceUtil.getAccountByAccountNumber(accountRequest.getFrom());
        accountServiceUtil.updateAccount(account.getAccountNumber(), accountRequest.getAmount(), "SUBTRACT");

    }
}

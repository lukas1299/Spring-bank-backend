package com.example.transactionservice.mapper;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.TransactionResponse;

//public class TransactionMapperImpl implements TransactionMapper{
//    @Override
//    public TransactionResponse transactionToResponse(Transaction transaction) {
//        if(transaction == null){
//            return null;
//        }
//        TransactionResponse transactionResponse = new TransactionResponse();
//        transactionResponse.setId(transaction.getId());
//        transactionResponse.setFrom(transaction.getFromAccount());
//        transactionResponse.setTargetAccountNumber(transaction.getToAccount());
//        transactionResponse.setAmount(transaction.getAmount());
//        transactionResponse.setTransactionStatus(transaction.getStatus());
//        return transactionResponse;
//    }
//}

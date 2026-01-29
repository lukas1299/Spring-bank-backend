package com.example.transactionservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.common.model.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private UUID id;
    private String from;
    private String targetAccountNumber;
    private Date createDate;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
}

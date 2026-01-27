package com.example.transactionservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.common.model.TransactionStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String fromAccount;
    private String toAccount;
    private Date createDate;
    private BigDecimal amount;
    private TransactionStatus status;

}

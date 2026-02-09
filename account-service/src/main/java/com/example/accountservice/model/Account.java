package com.example.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "account_id", nullable = false)
    private UUID id;

    private String accountNumber;
    private UUID userId;
    private BigDecimal balance;

    @OneToMany(mappedBy = "account")
    private Set<CreditCard> cards;

}

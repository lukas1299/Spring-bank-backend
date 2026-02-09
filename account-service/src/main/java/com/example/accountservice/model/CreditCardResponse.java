package com.example.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.common.model.AccountDTO;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class CreditCardResponse {
    private UUID id;
    private String cardNumber;
    private BigDecimal cardLimit;
    private CardStatus cardStatus;
    private AccountDTO account;
}

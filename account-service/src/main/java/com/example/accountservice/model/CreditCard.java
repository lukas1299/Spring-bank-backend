package com.example.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "creditCards")
public class CreditCard {

    @Id
    @Column(name = "credit_card_id")
    private UUID id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_limit")
    private BigDecimal cardLimit;

    @Column(name = "status")
    private CardStatus cardStatus;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public static CreditCard fromDto(CreditCardRequest creditCardRequest, Account account){
        return new CreditCard(
                UUID.randomUUID(),
                cardNumberGenerator(),
                creditCardRequest.getCardLimit(),
                CardStatus.RELEASED,
                account
        );
    }

    private static String cardNumberGenerator(){
        StringBuilder stringBuilder = new StringBuilder();
        Stream.generate(() -> new Random().nextInt(10 - 1) + 1).limit(16).forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}

package com.example.accountservice.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreditCardRequest {
    public BigDecimal cardLimit;
}

package com.example.accountservice.controller;

import com.example.accountservice.model.CreditCard;
import com.example.accountservice.model.CreditCardRequest;
import com.example.accountservice.model.CreditCardResponse;
import com.example.accountservice.repository.CreditCardRepository;
import com.example.accountservice.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creditCard")
public class CreditCardController {

    private final CreditCardService creditCardService;

    @PostMapping("/{id}/suspend")
    public ResponseEntity<CreditCardResponse> cardSuspend(@PathVariable("id") UUID cardId){
        return ResponseEntity.ok(creditCardService.cardSuspend(cardId));
    }
}

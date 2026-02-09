package com.example.accountservice.service;

import com.example.accountservice.model.CardStatus;
import com.example.accountservice.model.CreditCard;
import com.example.accountservice.model.CreditCardResponse;
import com.example.accountservice.repository.CreditCardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;

    public CreditCardResponse cardSuspend(UUID cardId){
        CreditCard creditCard = creditCardRepository.findById(cardId).orElseThrow(EntityNotFoundException::new);

        creditCard.setCardStatus(CardStatus.SUSPENDED);
        creditCard = creditCardRepository.save(creditCard);

        return creditCardMapper(creditCard);
    }

    private CreditCardResponse creditCardMapper(CreditCard creditCard){
        return new CreditCardResponse(creditCard.getId(), creditCard.getCardNumber(), creditCard.getCardLimit(), creditCard.getCardStatus(), null);
    }
 }

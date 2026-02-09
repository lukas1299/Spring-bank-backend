package com.example.accountservice.service;

import com.example.accountservice.exception.CannotCreateCreditCardException;
import com.example.accountservice.model.*;
import com.example.accountservice.repository.CreditCardRepository;
import org.apache.catalina.User;
import org.aspectj.weaver.patterns.IToken;
import org.example.common.model.AccountDTO;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.util.AuthServiceUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.common.model.TransactionRequest;
import org.example.common.model.TransactionStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CreditCardRepository creditCardRepository;
    private final AuthServiceUtil authServiceUtil;
    private final KafkaTemplate<String, TransactionRequest> kafkaTemplate;

    public AccountDTO createAccount(UUID userId) {
        Account account = accountRepository.save(
                new Account(UUID.randomUUID(),
                        UUID.randomUUID().toString(),
                        userId,
                        BigDecimal.ZERO,
                        Collections.emptySet()));
        return convertAccountToDto(account, null);
    }

    public AccountDTO getUserAccount(String token) {
        UserDTO userDTO = authServiceUtil.getUserInfo(token);
        Account account = accountRepository.findByUserId(userDTO.getUserId()).orElseThrow(EntityNotFoundException::new);
        return convertUserDataToDto(account, userDTO);
    }

    public AccountDTO getUserAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(EntityNotFoundException::new);
        return new AccountDTO(account.getId(), account.getAccountNumber(), null, null, null, account.getBalance());
    }

    public void realizeSubtractTransaction(TransactionRequest transactionRequest) throws ClassNotFoundException {

        Account account = accountRepository.findByAccountNumber(transactionRequest.getFrom()).orElseThrow(ClassNotFoundException::new);
        BigDecimal balance = account.getBalance();

        if (balance.compareTo(transactionRequest.getAmount()) > 0) {
            account.setBalance(balance.subtract(transactionRequest.getAmount()));
            accountRepository.save(account);
            transactionRequest.setTransactionStatus(TransactionStatus.REALIZED);
            kafkaTemplate.send("transaction-subtract-events", transactionRequest);
        } else {
            transactionRequest.setTransactionStatus(TransactionStatus.CANCELED);
            kafkaTemplate.send("transaction-subtract-events", transactionRequest);
        }
    }

    public void realizeAddTransaction(TransactionRequest transactionRequest) throws ClassNotFoundException {
        Account account = accountRepository.findByAccountNumber(transactionRequest.getTargetAccountNumber()).orElseThrow(ClassNotFoundException::new);
        BigDecimal balance = account.getBalance();
        account.setBalance(balance.add(transactionRequest.getAmount()));
        accountRepository.save(account);
        transactionRequest.setTransactionStatus(TransactionStatus.REALIZED);
        kafkaTemplate.send("transaction-add-events", transactionRequest);

    }

    public CreditCardResponse addCreditCardToAccount(CreditCardRequest creditCardRequest, UUID accountId, String token) {
        Account account = accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
        UserDTO user = authServiceUtil.getUserInfo(token);

        Set<CreditCard> cards = account.getCards();

        if (cards.size() == 2) {
            throw new CannotCreateCreditCardException("The account can have at most 2 cards.");
        }

        CreditCard creditCard = CreditCard.fromDto(creditCardRequest, account);
        creditCard = creditCardRepository.save(creditCard);
        cards.add(creditCard);
        account.setCards(cards);
        accountRepository.save(account);

        return creditCardMapper(creditCard, user);
    }

    public Set<CreditCardResponse> getCreditCardBelongingToAccount(UUID accountId, String token) {
        Account account = accountRepository.findById(accountId).orElseThrow(EntityNotFoundException::new);
        UserDTO user = authServiceUtil.getUserInfo(token);
        return account.getCards().stream().map(e -> creditCardMapper(e, user)).collect(Collectors.toSet());
    }

    private CreditCardResponse creditCardMapper(CreditCard creditCard, UserDTO user) {
        return new CreditCardResponse(
                creditCard.getId(),
                creditCard.getCardNumber(),
                creditCard.getCardLimit(),
                creditCard.getCardStatus(),
                convertAccountToDto(creditCard.getAccount(), user)
        );
    }

    private AccountDTO convertAccountToDto(Account account, UserDTO user) {
        if (user == null) {
            return new AccountDTO(account.getId(), account.getAccountNumber(), null, null, null, account.getBalance());

        }
        return new AccountDTO(account.getId(), account.getAccountNumber(), user.getUsername(), user.getSurname(), user.getRoles(), account.getBalance());

    }

    private AccountDTO convertUserDataToDto(Account account, UserDTO userDTO) {
        return new AccountDTO(account.getId(), account.getAccountNumber(), userDTO.getUsername(), userDTO.getSurname(), userDTO.getRoles(), account.getBalance());
    }


}

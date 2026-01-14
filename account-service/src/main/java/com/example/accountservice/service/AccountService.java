package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.AccountDTO;
import com.example.accountservice.model.AccountRequest;
import com.example.accountservice.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RabbitTemplate rabbitTemplate;

    public AccountDTO createAccount(UUID userId) {

        Account account = accountRepository.save(
                new Account(UUID.randomUUID(),
                        UUID.randomUUID().toString().replace("-", ""),
                        userId,
                        BigDecimal.ZERO));
        return convertAccountToDto(account);
    }

    public AccountDTO getUserAccount(UUID userId) {

        Account account = accountRepository.findByUserId(userId).orElseThrow(EntityNotFoundException::new);
        return convertAccountToDto(account);
    }

    private AccountDTO convertAccountToDto(Account account) {
        return new AccountDTO(account.getId(),account.getAccountNumber(), account.getBalance());
    }

    public String realizeTransaction(String uuid, AccountRequest accountRequest) {

        accountRequest.setFrom(uuid);
        rabbitTemplate.convertAndSend("transaction", accountRequest);

        return accountRequest.toString();
    }

    public AccountDTO updateAccount(String targetAccountNumber, BigDecimal amount, String operation) {

        Account account = accountRepository.findByAccountNumber(targetAccountNumber).orElseThrow(EntityNotFoundException::new);
        return updateBalance(account, amount, operation);
    }

    private AccountDTO updateBalance(Account account, BigDecimal amount, String operation){
        BigDecimal accountBalance = account.getBalance();

        if(operation.equals("ADD")) {
            account.setBalance(accountBalance.add(amount));
        } else {
            account.setBalance(accountBalance.subtract(amount));
        }

        accountRepository.save(account);

        return new AccountDTO(account.getId(),account.getAccountNumber(), account.getBalance());
    }
}

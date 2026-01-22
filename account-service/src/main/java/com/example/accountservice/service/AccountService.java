package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.AccountDTO;
import com.example.accountservice.model.AccountRequest;
import com.example.accountservice.model.UserDTO;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.util.AuthServiceUtil;
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
    private final AuthServiceUtil authServiceUtil;

    public AccountDTO createAccount(UUID userId) {

        Account account = accountRepository.save(
                new Account(UUID.randomUUID(),
                        UUID.randomUUID().toString(),
                        userId,
                        BigDecimal.ZERO));
        return convertAccountToDto(account);
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

    private AccountDTO convertAccountToDto(Account account){
        return new AccountDTO(account.getId(),account.getAccountNumber(), null, null, null, account.getBalance());

    }
    private AccountDTO convertUserDataToDto(Account account, UserDTO userDTO) {
        return new AccountDTO(account.getId(),account.getAccountNumber(), userDTO.getUsername(), userDTO.getSurname(), userDTO.getRoles(),account.getBalance());
    }

    public String realizeTransaction(String uuid, AccountRequest accountRequest) {

        accountRequest.setFrom(uuid);
        rabbitTemplate.convertAndSend("transaction", accountRequest);

        return accountRequest.toString();
    }

    public AccountDTO updateAccount(String targetAccountNumber, BigDecimal amount, String operation) throws Exception {

        Account account = accountRepository.findByAccountNumber(targetAccountNumber).orElseThrow(EntityNotFoundException::new);
        return updateBalance(account, amount, operation);
    }

    private AccountDTO updateBalance(Account account, BigDecimal amount, String operation) throws Exception {
        BigDecimal accountBalance = account.getBalance();

        if(operation.equals("ADD")) {
            account.setBalance(accountBalance.add(amount));
        } else {
            account.setBalance(accountBalance.subtract(amount));
        }

        accountRepository.save(account);

        return new AccountDTO(account.getId(),account.getAccountNumber(),null, null,null,  account.getBalance());
    }


}

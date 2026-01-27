package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.AccountDTO;
import com.example.accountservice.model.UserDTO;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.util.AuthServiceUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.common.model.TransactionRequest;
import org.example.common.model.TransactionStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AuthServiceUtil authServiceUtil;
    private final KafkaTemplate<String, TransactionRequest> kafkaTemplate;

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

    public void realizeSubtractTransaction(TransactionRequest transactionRequest) throws ClassNotFoundException {

        Account account = accountRepository.findByAccountNumber(transactionRequest.getFrom()).orElseThrow(ClassNotFoundException::new);
        BigDecimal balance = account.getBalance();

        if (balance.compareTo(transactionRequest.getAmount()) > 0){
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

    private AccountDTO convertAccountToDto(Account account){
        return new AccountDTO(account.getId(),account.getAccountNumber(), null, null, null, account.getBalance());

    }
    private AccountDTO convertUserDataToDto(Account account, UserDTO userDTO) {
        return new AccountDTO(account.getId(),account.getAccountNumber(), userDTO.getUsername(), userDTO.getSurname(), userDTO.getRoles(),account.getBalance());
    }
}

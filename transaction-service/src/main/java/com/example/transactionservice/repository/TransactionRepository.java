package com.example.transactionservice.repository;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.model.TransactionResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query(value = "SELECT * FROM Transactions t WHERE to_account= :accessNumber AND (:status IS NULL OR status= :status)", nativeQuery = true)
    List<Transaction> findUserTransaction(@Param("accessNumber") String accessNumber,@Param("status") Integer status, Pageable pageable);
}

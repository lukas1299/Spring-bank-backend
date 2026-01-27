package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;

@SpringBootApplication
@FeignClient
public class TransactionOrchestrator {
    public static void main(String[] args) {
        SpringApplication.run(TransactionOrchestrator.class, args);

    }
}
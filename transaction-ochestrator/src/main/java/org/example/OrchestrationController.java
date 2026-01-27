package org.example;

import lombok.RequiredArgsConstructor;
import org.example.common.model.TransactionRequest;
import org.example.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class OrchestrationController {
    private final TransactionService transactionService;

    @PostMapping("/initialize")
    public void initializeTransaction(@RequestBody TransactionRequest transactionRequest){
        transactionService.startTransactionSaga(transactionRequest);
    }
}

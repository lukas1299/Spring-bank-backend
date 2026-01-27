package org.example.common.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class TransactionRequest implements Serializable {

    private UUID id;
    private String from;
    private String targetAccountNumber;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;

}

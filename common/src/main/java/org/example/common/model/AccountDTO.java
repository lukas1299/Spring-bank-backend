package org.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDTO {

    private UUID accountId;
    private String accountNumber;
    private String username;
    private String surname;
    private List<Role> roles;
    private BigDecimal balance;
}

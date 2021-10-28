package com.iteesoft.bankapp.dto;

import lombok.Data;

@Data
public class WithdrawalDto {
    String accountNumber;
    String password;
    Double amount;
}

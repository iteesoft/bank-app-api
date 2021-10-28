package com.iteesoft.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
        String name;
        String password;
        Double initialDeposit;
        String depositNarration;
}

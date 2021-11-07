package com.iteesoft.bankapp.dto;

import lombok.Data;

@Data
public class CrDrDto {
    private String accountNumber;
    private Double amount;
    private String narration;
}

package com.iteesoft.bankapp.dto;

import lombok.Data;

@Data
public class TransferDto {

    private String originAccountNumber;
    private String destinationAccountNumber;
    private String narration;
    private Double amount;
}
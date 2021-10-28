package com.iteesoft.bankapp.model;

import lombok.Data;

@Data
public class Account {

    String accountName;
    String accountNumber;
    String accountPassword;
    Double initialDeposit;
    Double balance;
}
package com.iteesoft.bankapp.model;

import com.iteesoft.bankapp.enums.TransactionType;
import lombok.Data;
import java.util.Date;

@Data
public class Transaction {

    String accountNumber;
    Date transactionDate;
    TransactionType transactionType;
    String narration;
    Double amount;
    Double balanceAfterTransaction;
}
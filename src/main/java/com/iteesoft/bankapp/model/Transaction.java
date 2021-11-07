package com.iteesoft.bankapp.model;

import com.iteesoft.bankapp.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDateTime transactionDate = LocalDateTime.now();
    private TransactionType transactionType;
    private String narration;
    private Double amount;
    private Double balanceAfterTransaction;
}
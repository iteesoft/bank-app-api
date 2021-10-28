package com.iteesoft.bankapp.model;

import com.iteesoft.bankapp.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class Response {

    private static final long serialVersionUID = 7702134516418120340L;

    private int statusCode;
    private Boolean success;
    private JwtResponse accessToken;
    private String message;
    private Account account;
    private LocalDateTime transactionDate;
    private TransactionType transactionType;
    private Double amount;
    private Double accountBalanceAfterTransaction;

    public Response(int statusCode, Boolean success, String message, Account account) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
        this.account = account;
    }
    public Response(Boolean success, JwtResponse accessToken) {
        this.success = success;
        this.accessToken = accessToken;
    }

    public Response(int statusCode, Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Response(int statusCode, Boolean success, String message, LocalDateTime transactionDate, TransactionType transactionType, Double accountBalanceAfterTransaction) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.accountBalanceAfterTransaction = accountBalanceAfterTransaction;
    }
}

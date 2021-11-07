package com.iteesoft.bankapp.enums;

public enum AccountType {
    SAVINGS ("Savings"),
    CURRENT ("Current"),
    BUSINESS ("Business");

    private final String code;

    private AccountType(String code) {
        this.code = code;
    }
}

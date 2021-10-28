package com.iteesoft.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String accountNumber;
    private String password;

    //default constructor for JSON Parsing

    public JwtRequest(String accountNumber, String password) {
        this.setAccountNumber(accountNumber);
        this.setPassword(password);
    }

}

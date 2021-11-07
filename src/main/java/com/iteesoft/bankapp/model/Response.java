package com.iteesoft.bankapp.model;

import com.iteesoft.bankapp.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Response {

    private static final long serialVersionUID = 7702134516418120340L;

    private int statusCode;
    private Boolean success;
    private String message;
    private String accountNo;
    private LocalDateTime date = LocalDateTime.now();

    public Response(int statusCode, Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
    }

    public Response(int statusCode, Boolean success, String message, String accountNo) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
        this.accountNo = accountNo;
    }

}

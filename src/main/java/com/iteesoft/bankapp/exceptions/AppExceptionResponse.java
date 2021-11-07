package com.iteesoft.bankapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class AppExceptionResponse {
    private String message;
    private String description;
    private Date date;
}

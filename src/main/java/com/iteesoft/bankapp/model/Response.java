package com.iteesoft.bankapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.Map;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@JsonInclude(NON_NULL)
public class Response {

    private static final long serialVersionUID = 7702134516418120340L;

    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected Boolean success;
    protected String message;
    protected Map<?,?> data;
}

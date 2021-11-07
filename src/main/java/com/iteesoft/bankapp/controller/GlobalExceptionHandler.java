package com.iteesoft.bankapp.controller;

import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.exceptions.AppExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> appExceptionHandler(Exception ex, WebRequest request) {
        AppExceptionResponse exception = new AppExceptionResponse(ex.getMessage(), request.getDescription(false), new Date());
        return new ResponseEntity<>(exception, NOT_FOUND);
    }
}

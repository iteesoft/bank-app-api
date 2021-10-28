package com.iteesoft.bankapp.controller;

import com.iteesoft.bankapp.dto.AccountDto;
import com.iteesoft.bankapp.dto.DepositDto;
import com.iteesoft.bankapp.dto.WithdrawalDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AccountController {

    private AccountService accountService;


    @PostMapping("/register_account")
    public ResponseEntity<?> newAccount(@RequestBody AccountDto newAccountInfo) throws AppException {
        return ResponseEntity.ok(accountService.createAccount(newAccountInfo));
    }

    @GetMapping("/account_info/{accountNumber}")
    public ResponseEntity<?> viewAccountInfo(@PathVariable String accountNumber) {
        Response response = accountService.viewAccountInfo(accountNumber);
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/account_statement/{accountNumber}")
    public ResponseEntity<?> viewAccountStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getTransactions(accountNumber));
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositToAccount(@RequestBody DepositDto depositInfo) throws AppException {
        Response response = accountService.deposit(depositInfo);
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawFromAccount(@RequestBody WithdrawalDto withdrawalInfo) throws AppException {
        Response response = accountService.withdraw(withdrawalInfo);
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);    }
}
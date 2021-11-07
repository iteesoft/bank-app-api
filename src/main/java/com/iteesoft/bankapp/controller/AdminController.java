package com.iteesoft.bankapp.controller;

import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.dto.CrDrDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("/register_account")
    public ResponseEntity<?> newAccount(@RequestBody RegistrationDto newAccountInfo) {
        return ResponseEntity.ok(adminService.createAccount(newAccountInfo));
    }

    @GetMapping("/account_info/{accountNumber}")
    public ResponseEntity<?> viewAccountInfo(@PathVariable String accountNumber) {
        Response response = adminService.viewAccountInfo(accountNumber);
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/account_statement/{accountNumber}")
    public ResponseEntity<?> viewAccountStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(adminService.getAccountStatement(accountNumber));
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositToAccount(@RequestBody CrDrDto depositInfo) throws AppException {
        Response response = adminService.deposit(depositInfo);
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawFromAccount(@RequestBody CrDrDto withdrawalInfo) throws AppException {
        Response response = adminService.withdraw(withdrawalInfo);
        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);    }
}
package com.iteesoft.bankapp.controller;

import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.dto.CrDrDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Account;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/register_account")
    public ResponseEntity<Response> newAccount(@RequestBody RegistrationDto newAccountInfo) {
        Account account = adminService.createAccount(newAccountInfo);
        return ResponseEntity.ok(Response.builder().timeStamp(now())
                .data(Map.of("account", account))
                .message("Account Retrieved").status(CREATED)
                .statusCode(CREATED.value()).build());
    }

    @GetMapping("/account_info/{accountNumber}")
    public ResponseEntity<Response> viewAccountInfo(@PathVariable String accountNumber) {
        return ResponseEntity.ok(Response.builder().timeStamp(now())
                .data(Map.of("account", adminService.viewAccountInfo(accountNumber)))
                .message("Account Retrieved").status(OK)
                .statusCode(OK.value()).build());
    }

    @GetMapping("/account_statement/{accountNumber}")
    public ResponseEntity<?> viewAccountStatement(@PathVariable String accountNumber) {
        return ResponseEntity.ok(Response.builder().timeStamp(now())
                .data(Map.of("transaction", adminService.getAccountStatement(accountNumber)))
                .message("Account Statement Retrieved").status(OK)
                .statusCode(OK.value()).build());
    }

    @PostMapping("/deposit")
    public ResponseEntity<Response> depositToAccount(@RequestBody CrDrDto depositInfo) throws AppException {
         return ResponseEntity.ok(Response.builder().timeStamp(now())
                .data(Map.of("account", adminService.deposit(depositInfo)))
                .message("Account Credited").status(OK)
                .statusCode(OK.value()).build());
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Response> withdrawFromAccount(@RequestBody CrDrDto withdrawalInfo) throws AppException {
        return ResponseEntity.ok(Response.builder().timeStamp(now())
                .data(Map.of("account", adminService.withdraw(withdrawalInfo)))
                .message("Account Debited").status(OK)
                .statusCode(OK.value()).build());
    }
}
package com.iteesoft.bankapp.controller;

import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.service.AdminService;
import com.iteesoft.bankapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register_account")
    public ResponseEntity<?> newAccount(@RequestBody RegistrationDto newAccountInfo) {
        Response response = userService.createAccount(newAccountInfo);
        HttpStatus status = response.getSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }
}

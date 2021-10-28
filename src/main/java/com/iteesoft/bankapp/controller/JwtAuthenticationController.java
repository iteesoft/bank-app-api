package com.iteesoft.bankapp.controller;

import com.iteesoft.bankapp.configuration.JwtTokenUtil;
import com.iteesoft.bankapp.dto.AccountDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.JwtRequest;
import com.iteesoft.bankapp.model.JwtResponse;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.service.AccountService;
import com.iteesoft.bankapp.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsServiceImpl userDetailsService;
    private AccountService accountService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (jwtRequest.getAccountNumber(), jwtRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getAccountNumber());
        final String jwtToken = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwtToken));
//        return new Response(true, new JwtResponse(jwtToken));
    }

    @PostMapping(value = "/create_account")
    public ResponseEntity<Response> registerNewAccount(@RequestBody AccountDto accountInfo) throws AppException {
        Response response = accountService.createAccount(accountInfo);
        HttpStatus status = response.getSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }
}

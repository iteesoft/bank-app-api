package com.iteesoft.bankapp.service.impl;


import com.iteesoft.bankapp.model.Account;
import com.iteesoft.bankapp.service.AccountService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.security.auth.login.AccountNotFoundException;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService service;


    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {

        User.UserBuilder userBuilder;
        Account account = service.getAccountInfo(accountNumber);

            if (account.getAccountNumber().equalsIgnoreCase(accountNumber)){
                userBuilder = User.withUsername(accountNumber);
                userBuilder.password(account.getAccountPassword());
            }else {
                throw new AccountNotFoundException("Account Not Found!");
            }
        return userBuilder.build();
    }
}

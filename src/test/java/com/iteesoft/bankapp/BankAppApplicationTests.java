package com.iteesoft.bankapp;

import com.iteesoft.bankapp.dto.AccountDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Account;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.model.Transaction;
import com.iteesoft.bankapp.service.AccountService;
import com.iteesoft.bankapp.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BankAppApplicationTests {

    AccountService service;
    Account account;
    Transaction transaction;
    AccountDto accountDetails;

    @BeforeEach
    void setUp() {
        service = new AccountServiceImpl();
        account = new Account();
        transaction = new Transaction();
        accountDetails = new AccountDto("John Smith","1234",1000.00,"Last cash");
    }

    @Test
    void shouldCreateAccount() throws AppException {
        //when
        Response newAccount = service.createAccount(accountDetails);
        //then
        assertThat(newAccount.getSuccess()).isEqualTo(true);
    }

    @Test
    void shouldNotCreateAccountWhenInitialDepositIsLessThan500() throws AppException {
        //given
        accountDetails.setInitialDeposit(300.00);
        //when
        Response newAccount = service.createAccount(accountDetails);
        //then
        assertThat(newAccount.getSuccess()).isEqualTo(false);
    }

    @Test
    void shouldGetAccountInformation() throws AppException {
//        //given
//        Response newAccount = service.createAccount(accountDetails);
//        //when
//        Response accountInfo = service.viewAccountInfo(newAccount.getAccount().getAccountNumber());
//        //then
//        assertThat(accountInfo.getSuccess()).isEqualTo(true);
    }

}

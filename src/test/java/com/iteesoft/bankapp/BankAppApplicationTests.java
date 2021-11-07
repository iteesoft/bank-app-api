package com.iteesoft.bankapp;

import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.model.Account;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.model.Transaction;
import com.iteesoft.bankapp.service.AdminService;
import com.iteesoft.bankapp.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class BankAppApplicationTests {

    AdminService service;
    Account account;
    Transaction transaction;
    RegistrationDto accountDetails;

    @BeforeEach
    void setUp() {
        service = new AdminServiceImpl();
        account = new Account();
        transaction = new Transaction();
        accountDetails = new RegistrationDto("John Smith","jsmith@aol.com","1234","savings");
    }

    @Test
    void shouldCreateAccount() {
        //when
        Response newAccount = service.createAccount(accountDetails);
        //then
        assertThat(newAccount.getSuccess()).isEqualTo(true);
    }

    @Test
    void shouldNotCreateAccountWhenInitialDepositIsLessThan500() {
        //given
//        accountDetails.setInitialDeposit(300.00);
        //when
        Response newAccount = service.createAccount(accountDetails);
        //then
        assertThat(newAccount.getSuccess()).isEqualTo(false);
    }

    @Test
    void shouldGetAccountInformation() {
//        //given
//        Response newAccount = service.createAccount(accountDetails);
//        //when
//        Response accountInfo = service.viewAccountInfo(newAccount.getAccount().getAccountNumber());
//        //then
//        assertThat(accountInfo.getSuccess()).isEqualTo(true);
    }

}

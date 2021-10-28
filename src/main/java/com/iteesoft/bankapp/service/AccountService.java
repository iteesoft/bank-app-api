package com.iteesoft.bankapp.service;

import com.iteesoft.bankapp.dto.AccountDto;
import com.iteesoft.bankapp.dto.DepositDto;
import com.iteesoft.bankapp.dto.WithdrawalDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Account;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.model.Transaction;
import java.util.List;

public interface AccountService {
    Response createAccount(AccountDto newAccountInfo) throws AppException;
    Response deposit(DepositDto creditInfo) throws AppException;
    Response withdraw(WithdrawalDto debitInfo);
    List<Transaction> getTransactions(String accountNumber);
    Response viewAccountInfo(String accountNumber);
    Account getAccountInfo(String accountNumber);
}
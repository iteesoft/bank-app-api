package com.iteesoft.bankapp.service;

import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.dto.TransferDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Account;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.model.Transaction;
import java.util.List;

public interface UserService {
    List<Transaction> getAccountStatement(String accountNumber, int id) throws AppException;
    Account getUserAccount(String accountNo, int id) throws AppException;
    Response createAccount(RegistrationDto accountInfo);
    Response transferFunds(TransferDto transferInfo, int id) throws AppException;
}

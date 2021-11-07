package com.iteesoft.bankapp.service;

import com.iteesoft.bankapp.dto.CrDrDto;
import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Account;
import com.iteesoft.bankapp.model.Transaction;
import java.util.List;

public interface AdminService {
    Account createAccount(RegistrationDto accountInfo);
    Account deposit(CrDrDto creditInfo) throws AppException;
    Account withdraw(CrDrDto debitInfo) throws AppException;

    List<Transaction> getAccountStatement(String accountNumber);
    Account viewAccountInfo(String accountNumber);
}
package com.iteesoft.bankapp.service;

import com.iteesoft.bankapp.dto.CrDrDto;
import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.model.Transaction;
import java.util.List;

public interface AdminService {
    Response createAccount(RegistrationDto accountInfo);
    Response deposit(CrDrDto creditInfo) throws AppException;
    Response withdraw(CrDrDto debitInfo) throws AppException;

    List<Transaction> getAccountStatement(String accountNumber);
    Response viewAccountInfo(String accountNumber);
}
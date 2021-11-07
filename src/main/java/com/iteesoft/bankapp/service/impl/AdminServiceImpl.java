package com.iteesoft.bankapp.service.impl;

import com.iteesoft.bankapp.dto.CrDrDto;
import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.enums.TransactionType;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.*;
import com.iteesoft.bankapp.repository.AccountRepository;
import com.iteesoft.bankapp.repository.AppUserRepository;
import com.iteesoft.bankapp.repository.TransactionRepository;
import com.iteesoft.bankapp.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;
import static com.iteesoft.bankapp.enums.TransactionType.DEPOSIT;
import static com.iteesoft.bankapp.enums.TransactionType.WITHDRAWAL;

@Slf4j
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    AccountRepository accountRepo;
    @Autowired
    AppUserRepository userRepo;
    @Autowired
    TransactionRepository transactionRepo;

    @Override
    public Account createAccount(RegistrationDto accountInfo) {
        return null;
    }

    @Override
    public Account deposit(CrDrDto creditInfo) {
        Account account = accountRepo.findByAccountNumber(creditInfo.getAccountNumber());

        if (account != null) {
            Double existingBalance = account.getBalance();
            Double newBalance = existingBalance + creditInfo.getAmount();

            Transaction transaction = createTransaction(creditInfo.getNarration(), creditInfo.getAmount(), newBalance, DEPOSIT);
            addTransactionToAccount(account, transaction);

        } else {
            log.error("Invalid Account: "+creditInfo.getAccountNumber());
        }
        return account;
    }

    @Override
    public Account withdraw(CrDrDto debitInfo) {
        Account account = accountRepo.findByAccountNumber(debitInfo.getAccountNumber());

        if (account != null) {
            Double existingBalance = account.getBalance();
            Double newBalance = existingBalance - debitInfo.getAmount();
            Transaction transaction = createTransaction(debitInfo.getNarration(), debitInfo.getAmount(), newBalance,WITHDRAWAL);

            addTransactionToAccount(account, transaction);
            log.info( debitInfo.getAmount() +" successfully debited from account: "+debitInfo.getAccountNumber());

        } else {
            log.error("Transaction Not Authorized on "+debitInfo.getAccountNumber());
        }
        return account;
    }

    @Override
    public List<Transaction> getAccountStatement(String accountNumber) {
        Account account = accountRepo.findByAccountNumber(accountNumber);
        return account.getTransactions();
    }

    @Override
    public Account viewAccountInfo(String accountNumber) {
        Account account = accountRepo.findByAccountNumber(accountNumber);
        if (account == null) {
            log.error("Invalid Account");
        }
        return account;
    }

    public Account getUserAccount(String accountNo, int id) throws AppException {
        AppUser user = userRepo.findById(id).orElseThrow(()-> new AppException(""));
        Account account = accountRepo.findByAccountNumber(accountNo);

        List<Account> userAccounts = user.getAccounts();

        if (!userAccounts.contains(account)) {
            throw new AppException("");
        }
        return account;
    }

    public void addTransactionToAccount(Account account, Transaction transaction) {
        List<Transaction> transactions = account.getTransactions();
        transactions.add(transaction);
        accountRepo.save(account);
    }

    public Transaction createTransaction(String narration, Double amount, Double newBalance, TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setNarration(narration);
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(newBalance);
        transactionRepo.save(transaction);

        return transaction;
    }
}

package com.iteesoft.bankapp.service.impl;

import com.iteesoft.bankapp.dto.AccountDto;
import com.iteesoft.bankapp.dto.DepositDto;
import com.iteesoft.bankapp.dto.WithdrawalDto;
import com.iteesoft.bankapp.enums.TransactionType;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.Account;
import com.iteesoft.bankapp.model.RandomNumber;
import com.iteesoft.bankapp.model.Response;
import com.iteesoft.bankapp.model.Transaction;
import com.iteesoft.bankapp.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.iteesoft.bankapp.enums.TransactionType.DEPOSIT;
import static com.iteesoft.bankapp.enums.TransactionType.WITHDRAWAL;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    Map<String, Account> accountRepo = new HashMap<>();
    List<Transaction> transactionRepo = new ArrayList<>();
    Response response = new Response();
    @Override
    public Response createAccount(AccountDto newAccountInfo) throws AppException {
        Account newAccount = new Account();
        Transaction transaction = new Transaction();
//        Random rand = new Random();

        if (newAccountInfo.getInitialDeposit() < 500) {
            return new Response(404, false, "Initial deposit must be above 500.00 naira");
        } else {
            newAccount.setAccountName(newAccountInfo.getName());
            newAccount.setAccountPassword(newAccountInfo.getPassword());
            newAccount.setInitialDeposit(newAccountInfo.getInitialDeposit());
            newAccount.setBalance(newAccount.getInitialDeposit());
//            newAccount.setAccountNumber(String.valueOf(rand.nextInt(100000000)));
            newAccount.setAccountNumber(RandomNumber.generate());
            accountRepo.put(newAccount.getAccountNumber(), newAccount);
            log.info("New Account no. is : "+newAccount.getAccountNumber());
            log.info("Account balance is: "+newAccount.getBalance());

            transaction.setTransactionType(DEPOSIT);
            transaction.setTransactionDate(new Date());
            transaction.setAccountNumber(newAccount.getAccountNumber());
            transaction.setNarration(newAccountInfo.getDepositNarration());
            transaction.setAmount(newAccountInfo.getInitialDeposit());
            transaction.setBalanceAfterTransaction(newAccount.getBalance());
            transactionRepo.add(transaction);
            response.setAccount(newAccount);

            return new Response(201, true, "Account Created");
        }
    }

    @Override
    public Response deposit(DepositDto creditInfo) throws AppException {
        Account existingAccount = accountRepo.get(creditInfo.getAccountNumber());
        Transaction transaction = new Transaction();
        Double existingBalance = existingAccount.getBalance();
        Double newBalance;


        if (existingAccount != null) {
            if (creditInfo.getAmount() > 1000000 || creditInfo.getAmount() < 1) {
                return new Response(404, false, "Transaction Not Authorized on "+creditInfo.getAccountNumber());
            } else {
                newBalance = existingBalance + creditInfo.getAmount();
                existingAccount.setBalance(newBalance);
                accountRepo.replace(creditInfo.getAccountNumber(), existingAccount);

                transaction.setAccountNumber(creditInfo.getAccountNumber());
                transaction.setTransactionDate(new Date());
                transaction.setTransactionType(DEPOSIT);
                transaction.setAmount(creditInfo.getAmount());
                transaction.setBalanceAfterTransaction(newBalance);
                transactionRepo.add(transaction);
            }
        } else {
            return new Response(404, false, creditInfo.getAccountNumber() +" does not exist");
        }
        return new Response(200, true, creditInfo.getAmount() +" successfully credited into account: "+creditInfo.getAccountNumber(),
                LocalDateTime.now(), DEPOSIT, newBalance);
    }

    @Override
    public Response withdraw(WithdrawalDto debitInfo) {

        Account existingAccount = accountRepo.get(debitInfo.getAccountNumber());
        Transaction transaction = new Transaction();
        Double existingBalance = existingAccount.getBalance();
        Double newBalance;

        if (debitInfo.getAmount() < existingBalance) {
            newBalance = existingBalance - debitInfo.getAmount();
            existingAccount.setBalance(newBalance);
            accountRepo.replace(debitInfo.getAccountNumber(), existingAccount);

            transaction.setAccountNumber(debitInfo.getAccountNumber());
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(TransactionType.WITHDRAWAL);
            transaction.setAmount(debitInfo.getAmount());
            transaction.setBalanceAfterTransaction(newBalance);
            transactionRepo.add(transaction);
        } else {
            return new Response(404, false, "Insufficient Balance in account: "+debitInfo.getAccountNumber());
        }
        return new Response(200, true, debitInfo.getAmount() +" debited from account: "+debitInfo.getAccountNumber(),
                LocalDateTime.now(), WITHDRAWAL, newBalance);
    }

    @Override
    public List<Transaction> getTransactions(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction t: transactionRepo) {
            if (t.getAccountNumber().equals(accountNumber)) {
                transactions.add(t);
            }
        }
        return transactions;
    }

    @Override
    public Response viewAccountInfo(String accountNumber) {
        Account account = accountRepo.get(accountNumber);
        if (account == null) {
            return new Response(404,false,"Invalid Account");
        }
        return new Response(200,true,"Account Fetched Successfully",account);
    }

    @Override
    public Account getAccountInfo(String accountNumber) {
        return accountRepo.get(accountNumber);
    }

    public Transaction creditTransaction(Transaction details) {

        Transaction transaction = new Transaction();

        transaction.setTransactionType(DEPOSIT);
        transaction.setTransactionDate(new Date());
        transaction.setAccountNumber(details.getAccountNumber());
        transaction.setNarration(details.getNarration());
        transaction.setAmount(details.getAmount());
        transactionRepo.add(transaction);

        return transaction;
    }

}

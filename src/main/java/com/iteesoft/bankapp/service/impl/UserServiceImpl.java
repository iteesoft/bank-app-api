package com.iteesoft.bankapp.service.impl;

import com.iteesoft.bankapp.dto.CrDrDto;
import com.iteesoft.bankapp.dto.RegistrationDto;
import com.iteesoft.bankapp.dto.TransferDto;
import com.iteesoft.bankapp.enums.AccountType;
import com.iteesoft.bankapp.enums.TransactionType;
import com.iteesoft.bankapp.exceptions.AppException;
import com.iteesoft.bankapp.model.*;
import com.iteesoft.bankapp.repository.AccountRepository;
import com.iteesoft.bankapp.repository.AppUserRepository;
import com.iteesoft.bankapp.repository.TransactionRepository;
import com.iteesoft.bankapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.iteesoft.bankapp.enums.TransactionType.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AccountRepository accountRepo;
    @Autowired
    AppUserRepository userRepo;
    @Autowired
    TransactionRepository transactionRepo;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    Response response = new Response();

    @Override
    public List<Transaction> getAccountStatement(String accountNumber, int id) throws AppException {
        Account account = getUserAccount(accountNumber, id);
        return account.getTransactions();
    }

    @Override
    public Account getUserAccount(String accountNo, int id) throws AppException {
        AppUser user = userRepo.findById(id).orElseThrow(()-> new AppException(""));
        Account account = accountRepo.findByAccountNumber(accountNo);

        List<Account> userAccounts = user.getAccounts();

        if (!userAccounts.contains(account)) {
            throw new AppException("");
        }
        return account;
    }

    public AppUser saveUser(RegistrationDto user) {
        AppUser newUser = new AppUser();
        newUser.setEmail(user.getUserEmail());
        newUser.setName(user.getName());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepo.save(newUser);
    }

    public Account generateAccount(String accountType) {
        Account newAccount = new Account();
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setAccountType(AccountType.valueOf(accountType));
        newAccount.setAccountNumber(RandomNumber.generate());
        accountRepo.save(newAccount);
        return newAccount;
    }

    @Override
    public Response createAccount(RegistrationDto accountInfo) {
        AppUser existingUser = userRepo.findByEmail(accountInfo.getUserEmail());

        if (existingUser != null) {
            response.setSuccess(false);
            response.setMessage("Account exist with the provided email");
        } else {
            Account newAccount = generateAccount(accountInfo.getAccountType());

            AppUser newUser = saveUser(accountInfo);
            newUser.getAccounts().add(newAccount);
            userRepo.save(newUser);

            log.info("New Account no. : "+newAccount.getAccountNumber()+ " created for "+newUser.getName());
            log.info("Account balance is: "+newAccount.getBalance());
            response.setSuccess(true);
            response.setAccountNo(newAccount.getAccountNumber());
            response.setMessage("Account Created Successfully");
        }
        return response;
    }
    public Response withdraw(CrDrDto debitInfo, int id) throws AppException {
        AppUser user = userRepo.findById(id).orElseThrow(()-> new AppException(""));
        Account account = getUserAccount(debitInfo.getAccountNumber(), id);

        if (user != null && account != null) {
            performDebitTransaction(debitInfo.getAccountNumber(), debitInfo.getNarration(), debitInfo.getAmount(), WITHDRAWAL);

            response.setSuccess(true);
            response.setMessage(debitInfo.getAmount() +" successfully debited from account: "+debitInfo.getAccountNumber());

        } else {
            response.setSuccess(false);
            response.setMessage("Transaction Not Authorized on "+debitInfo.getAccountNumber());
        }
        return response;
    }

    public Response deposit(CrDrDto creditInfo, int id) throws AppException {
        AppUser user = userRepo.findById(id).orElseThrow(()-> new AppException(""));
        Account account = accountRepo.findByAccountNumber(creditInfo.getAccountNumber());

        if (user != null) {
            performCreditTransaction(account.getAccountNumber(), creditInfo.getNarration(), creditInfo.getAmount(), DEPOSIT);

            response.setSuccess(true);
            response.setAccountNo(creditInfo.getAccountNumber());
            response.setMessage(creditInfo.getAmount() +" successfully credited into account: "+creditInfo.getAccountNumber());
        } else {
            response.setSuccess(false);
            response.setAccountNo(creditInfo.getAccountNumber());
            response.setMessage("Transaction Not Authorized on "+creditInfo.getAccountNumber());
        }
        return response;
    }

    @Override
    public Response transferFunds(TransferDto transferInfo, int id) throws AppException {
        AppUser user = userRepo.findById(id).orElseThrow(()-> new AppException(""));
        Account account1 = getUserAccount(transferInfo.getOriginAccountNumber(), id);
        Account account2 = accountRepo.findByAccountNumber(transferInfo.getDestinationAccountNumber());

        if (user != null && account1 != null && account2 != null) {
            performDebitTransaction(transferInfo.getOriginAccountNumber(), transferInfo.getNarration(), transferInfo.getAmount(), TRANSFER);
            performCreditTransaction(transferInfo.getDestinationAccountNumber(), transferInfo.getNarration(), transferInfo.getAmount(), TRANSFER);

            response.setSuccess(true);
            response.setAccountNo(transferInfo.getOriginAccountNumber());
            response.setMessage(transferInfo.getAmount() +" successfully transferred to: "+ transferInfo.getDestinationAccountNumber());

        } else {
            response.setSuccess(false);
            response.setMessage("Transaction Not Authorized on "+transferInfo.getOriginAccountNumber());
        }
        return response;
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

    public void performCreditTransaction(String accountNo, String narration, Double crAmount, TransactionType transactionType) {
        Account account = accountRepo.findByAccountNumber(accountNo);

        Double existingBalance = account.getBalance();
        Double newBalance = existingBalance + crAmount;
        accountRepo.save(account);

        Transaction transaction = createTransaction(narration, crAmount, newBalance, transactionType);
        addTransactionToAccount(account, transaction);
    }

    public void performDebitTransaction(String accountNo, String narration, Double drAmount, TransactionType transactionType) {
        Account account = accountRepo.findByAccountNumber(accountNo);

        Double existingBalance = account.getBalance();
        Double newBalance = existingBalance - drAmount;
        accountRepo.save(account);

        Transaction transaction = createTransaction(narration, drAmount, newBalance, transactionType);
        addTransactionToAccount(account, transaction);
    }
}

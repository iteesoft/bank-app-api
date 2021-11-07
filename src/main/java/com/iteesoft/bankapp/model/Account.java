package com.iteesoft.bankapp.model;

import com.iteesoft.bankapp.enums.AccountStatus;
import com.iteesoft.bankapp.enums.AccountType;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

import static com.iteesoft.bankapp.enums.AccountStatus.INACTIVE;
import static javax.persistence.FetchType.*;

@Getter
@Setter
@Entity
public class Account extends Base{

    private String accountNumber;
    private Double balance = 0.00;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private AccountStatus status = INACTIVE;

    @OneToMany(fetch = LAZY)
    private List<Transaction> transactions;
}
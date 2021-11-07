package com.iteesoft.bankapp.model;

import com.iteesoft.bankapp.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.LAZY;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser extends Base{

    private String name;

    @Email
    private String email;

    @Size(min=6, max=20, message="Invalid Password length")
    private String password;

    private Role role;

    @OneToOne
    private Contact contact;

    @OneToMany(fetch = LAZY)
    private List<Account> accounts = new ArrayList<>();
}

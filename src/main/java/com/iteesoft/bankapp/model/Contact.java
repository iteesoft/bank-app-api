package com.iteesoft.bankapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Contact extends Base{

    private String phoneNumber;
    private String address;
    private String occupation;
    private String bvn;

}

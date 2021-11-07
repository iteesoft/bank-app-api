package com.iteesoft.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationDto {
        private String name;
        private String userEmail;
        private String password;
        private String accountType;
}

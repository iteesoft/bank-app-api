package com.iteesoft.bankapp.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RandomNumber {
    private static final long serialVersionUID = 59008583001150707L;


    public static String generate() {
        StringBuilder randomNum = new StringBuilder();
        String numString = "0123456789";
        for (int i = 0; i < 10; i++) {
            int index = (int) (numString.length() * Math.random());
            randomNum.append(numString.charAt(index));
        }
        return randomNum.toString();
    }
}

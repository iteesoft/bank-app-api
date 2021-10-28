package com.iteesoft.algorithm;

public class StringAddition {

    public static void main(String[] args) {
        String num1 = "12";
        String num2 = "10";
        System.out.println(addStrings(num1,num2));
    }

    public static String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        while (i > -1 || j > -1) {
            int sum = carry + (i < 0 ? 0 : num1.charAt(i--) - 48);
            sum += j < 0 ? 0 : num2.charAt(j--) - 48;
            sb.append(sum % 10);
            carry = sum / 10;
        }
        return sb.append(carry == 1 ? "1" : "").reverse().toString();
    }
}

package com.iteesoft.algorithm;

import java.util.ArrayList;
import java.util.List;

public class TennisScore {
    public static void main(String[] args) {
        int[] tomScore = {1,4,7,2,4};
        int[] jackScore = {3,4,2,4,4};
        System.out.println((scorePoint(tomScore,jackScore)));
    }


    public static List<Integer> scorePoint(int[] score1, int[] score2) {
        List<Integer> output = new ArrayList<>();
        int player1 = 0;
        int player2 = 0;

        for (int i = 0; i <= 4; i++) {
            if (score1[i] > score2[i]) {
                player1++;
            } else if (score1[i] < score2[i]){
                player2++;
            }
        }
        output.add(player1);
        output.add(player2);
        return output;
    }
}

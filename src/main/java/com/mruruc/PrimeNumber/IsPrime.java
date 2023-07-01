package com.mruruc.PrimeNumber;

import java.util.ArrayList;

public class IsPrime {

    //O(n2) complexity:
    public static boolean isPrime(int number) {

        int first_step = (int) Math.sqrt(number);

        ArrayList<Integer> listOfPrime = new ArrayList<>();

        for (int i = 2; i <= first_step; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    break;
                } else {
                    listOfPrime.add(i);
                    break;
                }
            }
        }
        for (int i = 0; i < listOfPrime.size(); i++) {
            if (number % listOfPrime.get(i) == 0) {
                return false;
            }
        }

        return true;
    }


    public boolean isPrime2(int num){
        if (num <= 1) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        int check = 2;
        while (Math.pow(check, 2) <= num) {
            if (num % check == 0) {
                return false;
            }
            check++;
        }
        return true;
    }



    public static void main(String[] args) {
        System.out.println(isPrime(97));
    }
}

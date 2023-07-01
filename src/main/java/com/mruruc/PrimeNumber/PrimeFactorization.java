package com.mruruc.PrimeNumber;

import java.util.ArrayList;

public class PrimeFactorization {

    public static ArrayList<Integer> primeFactorization(int number){
        ArrayList<Integer> list=new ArrayList<>();
        for (int i = 2; i <= number; i++) {
            while (number % i == 0) {
                list.add(i);
                number /= i;
            }
        }
        if (number > 1) {
            list.add(number);
        }
      return list;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list= primeFactorization(40);
        list.forEach((e)-> System.out.print(e +" "));
    }
}

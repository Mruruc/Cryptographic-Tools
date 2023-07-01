package com.mruruc.gcd_and_lcm;

public class GreatestCommonDivisor {

    public static int byModule(int dividend,int divisor){
        if(dividend<0 || divisor< 0){
            throw new IllegalArgumentException("The numbers can not be negative !");
        }
        if(dividend==divisor){
            System.out.println("Numbers are equal !");
            return dividend;
        }

        while(divisor!= 0){
            int reminder= dividend % divisor;
            dividend=divisor;
            divisor=reminder;
        }
        return dividend;
    }
    public static int bySubstraction(int dividend,int divisor) {

        if(dividend<0 || divisor<0){
            throw new IllegalArgumentException("The numbers can not be negative !");
        }
        if(dividend==divisor){
            System.out.println("The numbers are equals !");
            return dividend;
        }
        while (dividend != divisor) {
            if (dividend > divisor) {
                dividend = dividend - divisor;
            } else {
                divisor = divisor - dividend;
            }
        }
        return dividend;
    }

    public static int gcd(int number1,int number2){

        int max=Math.max(number1,number2);
        int min=Math.min(number1,number2);

        if(max==0 && min==0){
            throw new ArithmeticException("Undefined");
        }
        if(max==0 && min!=0){
            return min;
        }

        while (max!=0){
            int result=max % min;

            if(result==0){
                return min;
            }
            max=min;
            min=result;

        }
        return -1;
    }

    public static void main(String[] args) {
        var gcd = gcd(18,12);
      //  System.out.println(gcd);
        System.out.println(((121* 11) + (4 * 27))%19 );
    }
}

package com.mruruc.gcd_and_lcm;

public class Finding_coefficients_with_The_Bezout {
    public static class Pair {
        public final int x;
        public final int y;

        public Pair(int first, int second) {
            this.x = first;
            this.y = second;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    // ax + by = gcd(a, b)
    public static Pair extendedGCD(int a, int b) {
        if (a == 0)
            return new Pair(0, 1);

        Pair pair = extendedGCD(b % a, a);

        int x = pair.y - (b / a) * pair.x;
        int y = pair.x;

        return new Pair(x, y);
    }

    public static void main(String[] args) {

        Pair coefficients = extendedGCD(11,40);
        System.out.println(coefficients);

    }
}

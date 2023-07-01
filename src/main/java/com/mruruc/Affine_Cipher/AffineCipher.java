package com.mruruc.Affine_Cipher;

public class AffineCipher {


    //f(x)=ax+b(mod26) ==>> Encryption function:
    public static String encrypted(int a,int b,String message){

        String result = "";
        message = message.toLowerCase();

        for (int i = 0; i < message.length(); i++) {
            char m = message.charAt(i);
            if(Character.isLetter(m)){
            //    ASCII value of 'a' is 97, we want to start from 0
                int x = (int) m - 97;
                int y = function(a,b,x);
                // convert back to the corresponding ASCII value
                char encrypted = (char) (y + 97);
                result += encrypted;
            }
            else {
                result += m;
            }
        }
        return result;
    }

    // Apply the formula (ax + b) mod 26
    private static int function(int a, int b, int x) {
        return (a * x +b) % 26;
    }


        public static String decrypted(int a, int b, String cipher){

            String result = "";
            cipher = cipher.toLowerCase();
            int a_inv = -1;

            //Find a^-1 (the multiplicative inverse of a in the group of integers modulo m.)
            for (int i = 0; i < 26; i++) {
                if ((a * i) % 26 == 1) {
                    a_inv = i;
                    break;
                }
            }

            if(a_inv == -1) {
                throw new IllegalArgumentException("a and 26 are not coprime. Choose a different a.");
            }

            for (char c : cipher.toCharArray()) {
                if(Character.isLetter(c)){
                    int y = c - 97;
                    int x = (a_inv * (y - b + 26)) % 26;
                    result += (char) (x + 97);
                }
                else {
                    result += c;
                }
            }
            return result;
        }
    private static int calculateInverse(int a,int b){
        return 0;
    }

    public static void main(String[] args) {
        String message="DO NOT PASS GO";
        int a=3;
        int b=7;

        System.out.println("=========Encrypted Message=======");
        var encryptedMessage=encrypted(a,b,message);
        System.out.println(encryptedMessage);

        System.out.println("=========Decrypted Message=======");
        System.out.println(decrypted(a,b,encryptedMessage));
    }
}

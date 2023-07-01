package com.mruruc.Asymmetric_Key_Cryptography.RSA;

public class RSA {
    public static int encrypted(int p,int q,int e,int message){
        int n=p * q;
        return fastModularExponentiation(message,e, n);
    }

    public static int decrypted(int p,int q,int decryptionKey,int cipher){
        return fastModularExponentiation(cipher,decryptionKey, (p*q));
    }

    private static int fastModularExponentiation(int base, int exponent, int mod){
        long result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exponent >>= 1;
        }
        return (int) result;
    }

    public static void main(String[] args) {
        int message=121;
        int p=7;
        int q=11;
        int e=13; // Note: This is not a valid public key exponent with p=7, q=11
        int d=37; // Note: This is not a valid private key with p=7, q=11 and e=13

        System.out.println("=========Encrypted Message=======");
        var encryptedMessage=encrypted(p,q,e,message);
        System.out.println(encryptedMessage);

        System.out.println("=========Decrypted Message=======");
        System.out.println(decrypted(p,q,d,encryptedMessage));
    }
}

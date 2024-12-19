package com.mruruc.encryption.symmetric;

import static com.mruruc.encryption.symmetric.SymmetricAlgorithm.CHACHA20;

public class Main {
    private static final String CHACHA20_256_KEY = "GXsDm86p/u1XVfPNDLxuCYiSQ6ZXi128IP8aSbLqCAI=";
    private static final String AES_256_KEY = "lFaSMZLJBgdHVqj6ipKH8sHNtNbhNLFIg/Rl24DGWH4=";

    public static void main(String[] args) {
        var symmetricEncryption = new SymmetricEncryption();

        EncryptionResult encryptionResult = symmetricEncryption.encrypt(
                CHACHA20_256_KEY,
                CHACHA20, "Hello");

        System.out.println(encryptionResult.cipherText());
        System.out.println("==================================");

        String plainText = symmetricEncryption.decrypt(CHACHA20, CHACHA20_256_KEY, encryptionResult);
        System.out.println(plainText);

    }
}

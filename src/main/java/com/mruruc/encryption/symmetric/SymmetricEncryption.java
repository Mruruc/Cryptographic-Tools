package com.mruruc.encryption.symmetric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.ChaCha20ParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static com.mruruc.util.EncryptionUtil.*;


public class SymmetricEncryption {

    public EncryptionResult encrypt(String secretKey, SymmetricAlgorithm algorithm, String input) {
        try {
            ChaCha20ParameterSpec spec = this.buildEncryptionSpec();
            Cipher cipher = Cipher.getInstance(algorithm.getName());
            cipher.init(Cipher.ENCRYPT_MODE, getKey(secretKey, algorithm.getName()), spec);

            byte[] cipherTextBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

            String base64Nonce = base64Encoding(spec.getNonce());
            String base64Ciphertext = base64Encoding(cipherTextBytes);

            return new EncryptionResult(base64Nonce, spec.getCounter(), base64Ciphertext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    private ChaCha20ParameterSpec buildEncryptionSpec() {
        byte[] nonce = new byte[12];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);
        int initialCounter = 1;
        return new ChaCha20ParameterSpec(nonce, initialCounter);
    }

    public String decrypt(SymmetricAlgorithm algorithm, String secretKey, EncryptionResult encryptionResult) {
        try {
            Cipher instance = Cipher.getInstance(algorithm.getName());
            instance.init(
                    Cipher.DECRYPT_MODE,
                    getKey(secretKey, algorithm.getName()),
                    buildDecryptionSpec(encryptionResult.nonce(), encryptionResult.initialCounter()));

            String cipherText = encryptionResult.cipherText();
            byte[] decryptedBytes = instance.doFinal(base64Decoding(cipherText));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    private ChaCha20ParameterSpec buildDecryptionSpec(String nonceBase64, int initialCounter) {
        return new ChaCha20ParameterSpec(base64Decoding(nonceBase64), initialCounter);
    }
}

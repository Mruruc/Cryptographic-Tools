package com.mruruc.encryption;

import com.mruruc.encryption.symmetric.SymmetricAlgorithm;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static com.mruruc.encryption.symmetric.SymmetricAlgorithm.AES;
import static com.mruruc.encryption.symmetric.SymmetricAlgorithm.CHACHA20;
import static java.nio.file.StandardOpenOption.*;

public class SecretKeyGeneration {

    public SecretKey generateSymmetricKey(SymmetricAlgorithm algorithm, int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.getName());
            keyGenerator.init(keySize, SecureRandom.getInstanceStrong());
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        var secretKeyGeneration = new SecretKeyGeneration();
        Path path = Paths.get("src", "main", "resources", "keys.txt");

        Files.writeString(path, AES.getName() + System.lineSeparator(), CREATE, TRUNCATE_EXISTING);
        for (int i = 1; i < 4; i++) {
            SecretKey secretKey = secretKeyGeneration.generateSymmetricKey(AES, 256);
            Files.writeString(path,
                    to64Encoding(secretKey.getEncoded()) + System.lineSeparator(),
                    APPEND);
        }

        Files.writeString(path, CHACHA20.getName() + System.lineSeparator(), APPEND);
        for (int i = 1; i < 4; i++) {
            SecretKey secretKey = secretKeyGeneration.generateSymmetricKey(CHACHA20, 256);
            Files.writeString(path,
                    to64Encoding(secretKey.getEncoded()) + System.lineSeparator(),
                    APPEND);
        }
    }

    private static String to64Encoding(byte[] digest) {
        return Base64.getEncoder().encodeToString(digest);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

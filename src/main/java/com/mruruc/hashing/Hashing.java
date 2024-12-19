package com.mruruc.hashing;

import com.mruruc.util.EncryptionUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    /**
     * SHA-1
     * MD2
     * MD5
     * SHA-512/256
     * SHA3-512
     * SHA-256
     * SHA-384
     * SHA-512/224
     * SHA-512
     * SHA3-256
     * SHA-224
     * SHA3-384
     * SHA3-224
     */

    public static String hash(HashAlgorithm algorithm, String input) throws NoSuchAlgorithmException {
        var messageDigest = MessageDigest.getInstance(algorithm.getAlgorithmName());
        byte[] digest = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        return EncryptionUtil.base64Encoding(digest);
    }

}

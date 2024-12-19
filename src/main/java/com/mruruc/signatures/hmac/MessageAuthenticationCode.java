package com.mruruc.signatures.hmac;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MessageAuthenticationCode {
    private Mac mac;
    private static final String secret = "secret-key";

    private String hmacDigest(HmacAlgorithm algorithm, String input) throws NoSuchAlgorithmException, InvalidKeyException {
        this.mac = Mac.getInstance(algorithm.getName());
        Key key = new SecretKeySpec(secret.getBytes(), algorithm.getName());
        mac.init(key);
        byte[] bytesInput = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        return this.to64Encoding(bytesInput);
    }

    private boolean verifyHmac(HmacAlgorithm algorithm, String digest, String plainText) throws NoSuchAlgorithmException, InvalidKeyException {
        String hmacDigest = this.hmacDigest(algorithm, plainText);
        return hmacDigest.equals(digest);
    }

    private String to64Encoding(byte[] digest) {
        return Base64.getEncoder().encodeToString(digest);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        var mac = new MessageAuthenticationCode();

        String digest = mac.hmacDigest(HmacAlgorithm.HMAC_SHA3_256, "Hello");
        System.out.println(digest);

        System.out.println(mac.verifyHmac(HmacAlgorithm.HMAC_SHA3_256, digest, "Hello"));
    }
}

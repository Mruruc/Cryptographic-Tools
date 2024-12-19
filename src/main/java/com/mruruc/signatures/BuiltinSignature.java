package com.mruruc.signatures;

import com.mruruc.util.EncryptionUtil;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;

import static com.mruruc.util.EncryptionUtil.base64Decoding;
import static com.mruruc.util.EncryptionUtil.base64Encoding;

public class BuiltinSignature {
    private static PublicKey PUBLIC_KEY;
    public static PrivateKey PRIVATE_KEY;

    static {
        Path publicKeyPath = Paths.get("src", "main", "resources", "public_key.pem");
        Path privateKeyPath = Paths.get("src", "main", "resources", "private_key.pem");
        KeyPair keyPair = EncryptionUtil.loadKeyPair(publicKeyPath, privateKeyPath);
        PUBLIC_KEY = keyPair.getPublic();
        PRIVATE_KEY = keyPair.getPrivate();
    }

    public static String sign(String message, PrivateKey privateKey) {
        try {
            var signature = Signature.getInstance("SHA3-256withRSA");
            signature.initSign(privateKey);
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] signedBytes = signature.sign();
            return base64Encoding(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String message, String signatureStr, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance("SHA3-256withRSA");
            signature.initVerify(publicKey);
            signature.update(message.getBytes(StandardCharsets.UTF_8));
            return signature.verify(base64Decoding(signatureStr));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String message = "Message to be signed!";

        String signature = sign(message, PRIVATE_KEY);
        boolean verify = verify(message, signature, PUBLIC_KEY);
        System.out.println(verify);

    }
}

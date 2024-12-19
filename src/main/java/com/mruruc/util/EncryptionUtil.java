package com.mruruc.util;

import com.mruruc.encryption.asymmetric.AsymmetricAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.PrimitiveIterator;

public class EncryptionUtil {
    public static byte[] base64Decoding(String secret) {
        return Base64.getDecoder().decode(secret);
    }

    public static String base64Encoding(byte[] inputs) {
        return Base64.getEncoder().encodeToString(inputs);
    }

    public static Key getKey(String secretKey, String algorithmName) {
        return new SecretKeySpec(base64Decoding(secretKey), algorithmName);
    }

    public static KeyPair loadKeyPair(Path publicKeyPath, Path privateKeyPath) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(AsymmetricAlgorithm.RSA.name());

            byte[] publicKeyBytes = loadKey(publicKeyPath);
            var x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey PUBLIC_KEY = keyFactory.generatePublic(x509EncodedKeySpec);


            byte[] privateKeyBytes = loadKey(privateKeyPath);
            var pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey PRIVATE_KEY = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            return new KeyPair(PUBLIC_KEY, PRIVATE_KEY);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] loadKey(Path path) throws IOException {
        String key = Files.readString(path)
                .replaceAll("-----BEGIN .*-----", "")
                .replaceAll("-----END .*-----", "")
                .replaceAll("\\s+", "");
        return Base64.getMimeDecoder().decode(key);
    }
}

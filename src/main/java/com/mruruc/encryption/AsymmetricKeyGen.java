package com.mruruc.encryption;

import com.mruruc.encryption.asymmetric.AsymmetricAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

import static java.lang.System.lineSeparator;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class AsymmetricKeyGen {
    public static KeyPair generateKeyPair(AsymmetricAlgorithm algorithm, int size) {
        try {
            var keyPairGen = KeyPairGenerator.getInstance(algorithm.getName());
            keyPairGen.initialize(size);
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void main(String[] args) throws IOException {

        KeyPair keyPair = generateKeyPair(AsymmetricAlgorithm.RSA, 2048);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

//        System.out.println(privateKey.getFormat());
//        System.out.println(publicKey.getFormat());

        Path path = Paths.get("src", "main", "resources", "private_key.pem");
        generatePrivateKey(path, privateKey);

        Path publicKeyPath = Paths.get("src", "main", "resources", "public_key.pem");
        generatePublicKey(publicKeyPath, publicKey);

    }

    public static void generatePrivateKey(Path path, PrivateKey privateKey) {
        try {
            String pemKey = buildKey(privateKey.getEncoded(), "PRIVATE");
            Files.writeString(path, pemKey, CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void generatePublicKey(Path publicKeyPath, PublicKey publicKey) {
        try {
            var pemBuilder = buildKey(publicKey.getEncoded(), "PUBLIC");
            Files.writeString(publicKeyPath, pemBuilder, CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String buildKey(byte[] byteEncodedKey, String keyType) {
        return new StringBuilder()
                .append(String.format("-----BEGIN %s KEY-----", keyType))
                .append(lineSeparator())
                .append(getBase64EncodedKey(byteEncodedKey))
                .append(lineSeparator())
                .append(String.format("-----END %s KEY-----", keyType))
                .append(lineSeparator())
                .toString();
    }

    private static String getBase64EncodedKey(byte[] keyBytes) {
        return Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(keyBytes);
    }
}

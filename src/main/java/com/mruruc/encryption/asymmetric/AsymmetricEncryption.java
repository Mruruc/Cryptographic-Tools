package com.mruruc.encryption.asymmetric;

import com.mruruc.util.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AsymmetricEncryption {
    private static PublicKey publicKey;
    public static PrivateKey privateKey;

    static {
        Path publicKeyPath = Paths.get("src", "main", "resources", "public_key.pem");
        Path privateKeyPath = Paths.get("src", "main", "resources", "private_key.pem");
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(AsymmetricAlgorithm.RSA.name());

            byte[] publicKeyBytes = loadKey(publicKeyPath);
            var x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);


            byte[] privateKeyBytes = loadKey(privateKeyPath);
            var pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] loadKey(Path path) throws IOException {
        String key = Files.readString(path)
                .replaceAll("-----BEGIN .*-----", "")
                .replaceAll("-----END .*-----", "")
                .replaceAll("\\s+", "");
        return Base64.getMimeDecoder().decode(key);
    }

    public String encrypt(PublicKey publicKey, String message) {
        try {
            Cipher instance = Cipher.getInstance(AsymmetricAlgorithm.RSA.getName());
            instance.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = instance.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return EncryptionUtil.base64Encoding(bytes);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(PrivateKey privateKey, String cipher) {
        try {
            Cipher instance = Cipher.getInstance(AsymmetricAlgorithm.RSA.getName());
            instance.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = instance.doFinal(EncryptionUtil.base64Decoding(cipher));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                 | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String message = "Hello, RSA!";
        var asymmetricEncryption = new AsymmetricEncryption();

        String cipher = asymmetricEncryption.encrypt(publicKey, message);
        System.out.println(cipher);

        String plainText = asymmetricEncryption.decrypt(privateKey, cipher);
        System.out.println(plainText);

    }
}

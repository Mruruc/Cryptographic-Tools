package com.mruruc.signatures.key_pair;

import com.mruruc.encryption.asymmetric.AsymmetricAlgorithm;
import com.mruruc.hashing.HashAlgorithm;
import com.mruruc.util.EncryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;

import static com.mruruc.hashing.Hashing.hash;
import static com.mruruc.util.EncryptionUtil.base64Decoding;
import static com.mruruc.util.EncryptionUtil.base64Encoding;

public class RSASignature {
    private static PublicKey PUBLIC_KEY;
    public static PrivateKey PRIVATE_KEY;

    static {
        Path publicKeyPath = Paths.get("src", "main", "resources", "public_key.pem");
        Path privateKeyPath = Paths.get("src", "main", "resources", "private_key.pem");
        KeyPair keyPair = EncryptionUtil.loadKeyPair(publicKeyPath, privateKeyPath);
        PUBLIC_KEY = keyPair.getPublic();
        PRIVATE_KEY = keyPair.getPrivate();
    }


    public static void main(String[] args) {
        String message = "Message to be signed!";

        String signature = sign(message, PRIVATE_KEY);
        boolean verify = verify(message, signature, PUBLIC_KEY);
        System.out.println(verify);

    }

    public static String sign(String message, PrivateKey privateKey) {
        try {
            String digest = hash(HashAlgorithm.SHA3_256, message);

            Cipher instance = Cipher.getInstance(AsymmetricAlgorithm.RSA.getName());
            instance.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] bytes = instance.doFinal(base64Decoding(digest));

            return base64Encoding(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String message, String signature, PublicKey publicKey) {
        try {
            Cipher instance = Cipher.getInstance(AsymmetricAlgorithm.RSA.getName());
            instance.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decryptedBytes = instance.doFinal(base64Decoding(signature));

            String digest = hash(HashAlgorithm.SHA3_256, message);
            return digest.equals(base64Encoding(decryptedBytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}

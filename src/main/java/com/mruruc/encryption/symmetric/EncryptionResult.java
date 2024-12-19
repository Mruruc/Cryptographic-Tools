package com.mruruc.encryption.symmetric;

public record EncryptionResult(
        String nonce,
        int initialCounter,
        String cipherText
) {
}

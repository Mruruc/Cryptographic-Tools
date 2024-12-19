package com.mruruc.encryption.symmetric;

public enum SymmetricAlgorithm {
    AES("AES"),
    CHACHA20("ChaCha20"),
    BLOWFISH("Blowfish");

    private final String algorithmName;

    SymmetricAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getName() {
        return algorithmName;
    }
}

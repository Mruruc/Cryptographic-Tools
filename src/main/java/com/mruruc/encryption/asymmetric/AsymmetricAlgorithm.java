package com.mruruc.encryption.asymmetric;

public enum AsymmetricAlgorithm {
    RSA("RSA"),
    DSA("DSA"),
    DIFFIE_HELLMAN("DH"),
    EC("EC"),
    XDH("XDH");

    private final String algorithmName;

    AsymmetricAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getName(){
        return this.algorithmName;
    }
}

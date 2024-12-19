package com.mruruc.signatures.hmac;

public enum HmacAlgorithm {
    HMAC_MD5("HmacMD5"),
    HMAC_SHA1("HmacSHA1"),
    HMAC_SHA224("HmacSHA224"),
    HMAC_SHA256("HmacSHA256"),
    HMAC_SHA384("HmacSHA384"),
    HMAC_SHA512("HmacSHA512"),
    HMAC_SHA3_224("HmacSHA3-224"),
    HMAC_SHA3_256("HmacSHA3-256"),
    HMAC_SHA3_384("HmacSHA3-384"),
    HMAC_SHA3_512("HmacSHA3-512");

    private final String algorithmName;

    HmacAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getName() {
        return algorithmName;
    }
}


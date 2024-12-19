package com.mruruc.signatures;

public enum SignatureAlgorithm {
    SHA3_256_WITH_RSA("SHA3-256withRSA"),
    ED448("Ed448"),
    ED25519("Ed25519"),
    EDDSA("EdDSA"),
    SHA256_WITH_ECDSA("SHA256withECDSA"),
    SHA3_512_WITH_ECDSA("SHA3-512withECDSA"),
    SHA224_WITH_ECDSA_P1363("SHA224withECDSAinP1363Format"),
    SHA256_WITH_ECDSA_P1363("SHA256withECDSAinP1363Format"),
    SHA3_224_WITH_ECDSA("SHA3-224withECDSA"),
    SHA3_256_WITH_ECDSA_P1363("SHA3-256withECDSAinP1363Format"),
    SHA3_384_WITH_ECDSA("SHA3-384withECDSA"),
    SHA224_WITH_ECDSA("SHA224withECDSA"),
    SHA384_WITH_ECDSA("SHA384withECDSA"),
    SHA512_WITH_ECDSA("SHA512withECDSA"),
    SHA3_256_WITH_ECDSA("SHA3-256withECDSA"),
    SHA3_384_WITH_ECDSA_P1363("SHA3-384withECDSAinP1363Format"),
    NONE_WITH_ECDSA("NONEwithECDSA"),
    NONE_WITH_ECDSA_P1363("NONEwithECDSAinP1363Format"),
    SHA3_224_WITH_ECDSA_P1363("SHA3-224withECDSAinP1363Format"),
    SHA3_512_WITH_ECDSA_P1363("SHA3-512withECDSAinP1363Format"),
    SHA384_WITH_ECDSA_P1363("SHA384withECDSAinP1363Format"),
    SHA512_WITH_ECDSA_P1363("SHA512withECDSAinP1363Format"),
    SHA1_WITH_ECDSA("SHA1withECDSA"),
    SHA1_WITH_ECDSA_P1363("SHA1withECDSAinP1363Format");

    private final String algorithm;

    SignatureAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getName() {
        return algorithm;
    }
}

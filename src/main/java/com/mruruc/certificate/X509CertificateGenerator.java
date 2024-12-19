package com.mruruc.certificate;

import com.mruruc.signatures.SignatureAlgorithm;
import com.mruruc.util.EncryptionUtil;

import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class X509CertificateGenerator {
    private static final PublicKey PUBLIC_KEY;
    public static PrivateKey PRIVATE_KEY;

    static {
        Path publicKeyPath = Paths.get("src", "main", "resources", "public_key.pem");
        Path privateKeyPath = Paths.get("src", "main", "resources", "private_key.pem");
        KeyPair keyPair = EncryptionUtil.loadKeyPair(publicKeyPath, privateKeyPath);
        PUBLIC_KEY = keyPair.getPublic();
        PRIVATE_KEY = keyPair.getPrivate();
    }

    public static void main(String[] args) throws Exception {

        X500Principal issuer =
                new X500Principal("C=PL, ST=WAW, L=Warsaw, O=Mr.Uruc tech, CN=Mr.Uruc, emailAddress=mr.uruc@uruc.com");

        X500Principal subject = issuer;

        BigInteger serialNumber = new BigInteger("57c5476e5ad23a29d81dfabb4f19bf2b997318cd", 16); // Serial number
        Date notBefore = new Date();
        Date notAfter = new Date(notBefore.getTime() + 365L * 24 * 60 * 60 * 1000); // Valid for 1 year


        var certificate = new X509CertificateIml(
                issuer, subject, notBefore, notAfter, serialNumber,
                new KeyPair(PUBLIC_KEY, PRIVATE_KEY),
                SignatureAlgorithm.SHA3_256_WITH_RSA
        );

        certificate.sign();

        Path certificatePath = Paths.get("src/main/resources/certificate.pem");
        String certificateBuilder = "-----BEGIN CERTIFICATE-----\n" +
                Base64.getMimeEncoder().encodeToString(certificate.getEncoded()) +
                "\n-----END CERTIFICATE-----\n";
        Files.writeString(certificatePath, certificateBuilder, CREATE, TRUNCATE_EXISTING);

        System.out.println("Certificate generated: certificate.pem");
    }

}

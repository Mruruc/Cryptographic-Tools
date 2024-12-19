package com.mruruc.certificate;

import com.mruruc.signatures.SignatureAlgorithm;

import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;

public class X509CertificateIml extends X509Certificate {
    private static int VERSION = 3;
    private X500Principal issuer;
    private X500Principal subject;
    private Date notBefore;
    private Date notAfter;
    private BigInteger serialNumber;
    private KeyPair keyPair;
    private SignatureAlgorithm signatureAlgorithm;
    private byte[] signature;
    private byte[] encodedCertificate;

    public X509CertificateIml(X500Principal issuer, X500Principal subject, Date notBefore, Date notAfter,
                              BigInteger serialNumber, KeyPair keyPair,
                              SignatureAlgorithm signatureAlgorithm) {
        this.issuer = issuer;
        this.subject = subject;
        this.notBefore = notBefore;
        this.notAfter = notAfter;
        this.serialNumber = serialNumber;
        this.keyPair = keyPair;
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public void sign() throws Exception {
        // Generate the TBS (To Be Signed) Certificate
        byte[] tbsCertificate = generateTBSCertificate();
        // Sign the TBS Certificate
        Signature sig = Signature.getInstance(signatureAlgorithm.getName());
        sig.initSign(keyPair.getPrivate());
        sig.update(tbsCertificate);

        this.signature = sig.sign();
        // Assemble the final certificate
        this.encodedCertificate = assembleEncodedCertificate(tbsCertificate, this.signature);
    }

    /**
     * Generates the TBS (To Be Signed) Certificate as a DER-encoded structure.
     */
    private byte[] generateTBSCertificate() throws Exception {
        var tbsBuilder = new StringBuilder();
        tbsBuilder.append("Version: ").append(VERSION)
                .append(lineSeparator())
                .append("Serial Number: ").append(getSerialNumber())
                .append(lineSeparator())
                .append("Signature Algorithm: ").append(signatureAlgorithm.getName())
                .append(lineSeparator())
                .append("Issuer: ").append(issuer)
                .append("Validity").append(lineSeparator())
                .append("Not Before: ").append(getNotBefore())
                .append("Not Before: ").append(getNotAfter())
                .append(lineSeparator())
                .append("Subject: ").append(subject)
                .append(lineSeparator())
                .append("Subject Public Key Info: ").append(lineSeparator())
                .append(keyPair.getPublic().getAlgorithm());

        return tbsBuilder.toString().getBytes(UTF_8);
    }

    /**
     * Combines the TBS Certificate, signature algorithm, and signature into a final DER-encoded X.509 certificate.
     */
    private byte[] assembleEncodedCertificate(byte[] tbsCertificate, byte[] signature) {
        // Mock assembly of DER-encoded certificate (replace with real ASN.1 encoding)
        byte[] encoded = new byte[tbsCertificate.length + signature.length];
        System.arraycopy(tbsCertificate, 0, encoded, 0, tbsCertificate.length);
        System.arraycopy(signature, 0, encoded, tbsCertificate.length, signature.length);
        return encoded;
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        if (encodedCertificate == null) {
            throw new CertificateEncodingException("Certificate has not been signed yet.");
        }
        return encodedCertificate;
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        try {
            return generateTBSCertificate();
        } catch (Exception e) {
            throw new CertificateEncodingException("Error generating TBS certificate", e);
        }
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        if (date.before(notBefore)) {
            throw new CertificateNotYetValidException("Certificate is not valid yet.");
        }
        if (date.after(notAfter)) {
            throw new CertificateExpiredException("Certificate has expired.");
        }
    }

    @Override
    public int getVersion() {
        return VERSION;
    }

    @Override
    public void verify(PublicKey key) throws InvalidKeyException, CertificateEncodingException, SignatureException, NoSuchAlgorithmException {
        Signature sig = Signature.getInstance(signatureAlgorithm.getName());
        sig.initVerify(key);
        sig.update(getTBSCertificate());
        if (!sig.verify(signature)) {
            throw new SignatureException("Certificate signature verification failed.");
        }
    }


    @Override
    public String toString() {
        return String.format("X509Certificate[Issuer=%s, Subject=%s, SerialNumber=%s, Validity=%s to %s]",
                issuer, subject, serialNumber, notBefore, notAfter);
    }

    @Override
    public byte[] getSignature() {
        return signature;
    }

    @Override
    public String getSigAlgName() {
        return signatureAlgorithm.getName();
    }

    @Override
    public String getSigAlgOID() {
        return signatureAlgorithm.getName();
    }

    @Override
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    @Override
    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    @Override
    public Principal getIssuerDN() {
        return issuer;
    }

    @Override
    public Principal getSubjectDN() {
        return subject;
    }

    @Override
    public Date getNotBefore() {
        return notBefore;
    }

    @Override
    public Date getNotAfter() {
        return notAfter;
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        checkValidity(new Date());
    }

    @Override
    public void verify(PublicKey key, String sigProvider) throws CertificateEncodingException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        verify(key);
    }

    @Override
    public byte[] getSigAlgParams() {
        return new byte[0];
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        return new boolean[0];
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        return new boolean[0];
    }

    @Override
    public boolean[] getKeyUsage() {
        return new boolean[0];
    }

    @Override
    public int getBasicConstraints() {
        return 0;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        return false;
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        return Set.of();
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        return Set.of();
    }

    @Override
    public byte[] getExtensionValue(String oid) {
        return new byte[0];
    }
}

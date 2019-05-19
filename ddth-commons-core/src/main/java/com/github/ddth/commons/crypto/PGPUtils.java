package com.github.ddth.commons.crypto;

import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.bcpg.sig.Features;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.jcajce.JcaPGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.jcajce.JcaPGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PBESecretKeyEncryptor;
import org.bouncycastle.openpgp.operator.PGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * PGP encryption/signature utility class.
 *
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 1.1.0
 */
public class PGPUtils {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * Export a key ring data.
     *
     * @param keyRing
     * @param asciiArmor if {@code true}, data is exported as ASCII-armor format; otherwise key ring data is encoded as base64 string
     * @return
     * @throws IOException
     */
    public static String exportKeyRing(PGPKeyRing keyRing, boolean asciiArmor) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (asciiArmor) {
                try (ArmoredOutputStream aos = new ArmoredOutputStream(baos)) {
                    keyRing.encode(aos);
                }
            } else {
                keyRing.encode(baos);
            }
            baos.flush();
            byte[] data = baos.toByteArray();
            return asciiArmor ? new String(data, StandardCharsets.UTF_8) : Base64.getEncoder().encodeToString(data);
        }
    }

    /**
     * Export a secret key data.
     *
     * @param pgpKeyRingGen
     * @param asciiArmor    if {@code true}, data is exported as ASCII-armor format; otherwise key data is encoded as base64 string
     * @return
     * @throws IOException
     */
    public static String exportSecretKey(PGPKeyRingGenerator pgpKeyRingGen, boolean asciiArmor) throws IOException {
        return exportKeyRing(pgpKeyRingGen.generateSecretKeyRing(), asciiArmor);
    }

    /**
     * Export a public key data.
     *
     * @param pgpKeyRingGen
     * @param asciiArmor    if {@code true}, data is exported as ASCII-armor format; otherwise key data is encoded as base64 string
     * @return
     * @throws IOException
     */
    public static String exportPublicKey(PGPKeyRingGenerator pgpKeyRingGen, boolean asciiArmor) throws IOException {
        return exportKeyRing(pgpKeyRingGen.generatePublicKeyRing(), asciiArmor);
    }

    private static PGPKeyPair buildMasterKey(KeyPair keyPair) throws PGPException {
        String algoName = keyPair.getPublic().getAlgorithm();
        if ("RSA".equalsIgnoreCase(algoName)) {
            return new JcaPGPKeyPair(PGPPublicKey.RSA_SIGN, keyPair, new Date());
        }
        if ("DSA".equalsIgnoreCase(algoName)) {
            return new JcaPGPKeyPair(PGPPublicKey.DSA, keyPair, new Date());
        }
        if ("ElGamal".equalsIgnoreCase(algoName)) {
            return new JcaPGPKeyPair(PGPPublicKey.ELGAMAL_GENERAL, keyPair, new Date());
        }
        throw new PGPException("Unsupported algorithm for master key [" + algoName + "]");
    }

    private static PGPKeyPair buildEncryptionKey(KeyPair keyPair) throws PGPException {
        String algoName = keyPair.getPublic().getAlgorithm();
        if ("RSA".equalsIgnoreCase(algoName)) {
            return new JcaPGPKeyPair(PGPPublicKey.RSA_ENCRYPT, keyPair, new Date());
        }
        if ("ElGamal".equalsIgnoreCase(algoName)) {
            return new JcaPGPKeyPair(PGPPublicKey.ELGAMAL_ENCRYPT, keyPair, new Date());
        }
        throw new PGPException("Unsupported algorithm for encryption key [" + algoName + "]");
    }

    /*
     * See: https://github.com/codesandnotes/openpgp-integration/blob/master/src/main/java/be/codesandnotes/OpenPGP.java#L111
     */
    private static PGPSignatureSubpacketVector buildEncryptionKeySignature() {
        PGPSignatureSubpacketGenerator encryptionKeySignatureGenerator = new PGPSignatureSubpacketGenerator();
        encryptionKeySignatureGenerator.setKeyFlags(false, KeyFlags.ENCRYPT_COMMS | KeyFlags.ENCRYPT_STORAGE);
        return encryptionKeySignatureGenerator.generate();
    }

    /*
     * See: https://github.com/codesandnotes/openpgp-integration/blob/master/src/main/java/be/codesandnotes/OpenPGP.java#L117
     */
    private static PGPSignatureSubpacketVector buildSigningKeySignature() {
        PGPSignatureSubpacketGenerator signingKeySignatureGenerator = new PGPSignatureSubpacketGenerator();
        signingKeySignatureGenerator.setKeyFlags(false, KeyFlags.SIGN_DATA | KeyFlags.CERTIFY_OTHER);
        signingKeySignatureGenerator
                .setPreferredSymmetricAlgorithms(false, new int[] { SymmetricKeyAlgorithmTags.AES_256 });
        signingKeySignatureGenerator.setPreferredHashAlgorithms(false, new int[] { HashAlgorithmTags.SHA512 });
        signingKeySignatureGenerator.setFeature(false, Features.FEATURE_MODIFICATION_DETECTION);
        return signingKeySignatureGenerator.generate();
    }

    /**
     * Generate a {@link PGPKeyRingGenerator} with an encryption sub-key, using RSA for both master and encryption sub-key.
     *
     * @param keySizeInBits
     * @param identity
     * @param password
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws PGPException
     */
    public static PGPKeyRingGenerator createPGPKeyRingGenerator(int keySizeInBits, String identity, String password)
            throws NoSuchProviderException, NoSuchAlgorithmException, PGPException {
        return createPGPKeyRingGenerator(KeyPairUtils.generateKeyPair(keySizeInBits, "RSA", null),
                KeyPairUtils.generateKeyPair(keySizeInBits, "RSA", null), identity, password);
    }

    /**
     * Generate a {@link PGPKeyRingGenerator} with an encryption sub-key.
     *
     * @param masterKeyPair
     * @param encryptionSubKeyPair
     * @param identity
     * @param password
     * @return
     * @throws PGPException
     */
    public static PGPKeyRingGenerator createPGPKeyRingGenerator(KeyPair masterKeyPair, KeyPair encryptionSubKeyPair,
            String identity, String password) throws PGPException {
        PGPKeyPair masterKey = buildMasterKey(masterKeyPair);
        PGPDigestCalculator checksumCalculator = new JcaPGPDigestCalculatorProviderBuilder().build()
                .get(HashAlgorithmTags.SHA1);
        PGPContentSignerBuilder keySignerBuilder = new JcaPGPContentSignerBuilder(
                masterKey.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA256);
        PBESecretKeyEncryptor keyEncryptor = new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.AES_256)
                .build(password.toCharArray());

        PGPKeyRingGenerator keyRingGenerator = new PGPKeyRingGenerator(PGPSignature.POSITIVE_CERTIFICATION, masterKey,
                identity, checksumCalculator, buildSigningKeySignature(), null, keySignerBuilder, keyEncryptor);

        PGPKeyPair encryptionSubKey = buildEncryptionKey(encryptionSubKeyPair);
        keyRingGenerator.addSubKey(encryptionSubKey, buildEncryptionKeySignature(), null);

        return keyRingGenerator;
    }

    /**
     * Load all public keys from a key ring.
     *
     * @param keyRingData    key ring data in base64 or armor format
     * @param asciiArmor     {@code true} if data is armor format; otherwise, base64
     * @param encryptionOnly {@code true} to load only encryption keys; otherwise, all keys
     * @return
     * @throws IOException
     * @throws PGPException
     */
    public static List<PGPPublicKey> loadPublicKeys(String keyRingData, boolean asciiArmor, boolean encryptionOnly)
            throws IOException, PGPException {
        List<PGPPublicKey> result = new ArrayList<>();
        ByteArrayInputStream baos = new ByteArrayInputStream(
                asciiArmor ? keyRingData.getBytes(StandardCharsets.UTF_8) : Base64.getDecoder().decode(keyRingData));
        try (InputStream is = asciiArmor ? new ArmoredInputStream(baos) : baos) {
            PGPPublicKeyRingCollection publicKeyRings = new JcaPGPPublicKeyRingCollection(is);
            publicKeyRings.iterator()
                    .forEachRemaining(pubKeyRing -> pubKeyRing.getPublicKeys().forEachRemaining(pubKey -> {
                        if (!encryptionOnly || pubKey.isEncryptionKey()) {
                            result.add(pubKey);
                        }
                    }));
        }
        return result;
    }

    /**
     * Load the first public key that matches filtering from a key ring.
     *
     * @param keyRingData     key ring data in base64 or armor format
     * @param asciiArmor      {@code true} if data is armor format; otherwise, base64
     * @param encryptionOnly  {@code true} to load only encryption key; otherwise, any key
     * @param acceptMasterKey {@code true} if master key can be returned; otherwise, the returned key is sub-key
     * @return
     * @throws IOException
     * @throws PGPException
     */
    public static PGPPublicKey loadFirstPublicKey(String keyRingData, boolean asciiArmor, boolean encryptionOnly,
            boolean acceptMasterKey) throws IOException, PGPException {
        List<PGPPublicKey> keys = loadPublicKeys(keyRingData, asciiArmor, encryptionOnly);
        for (PGPPublicKey key : keys) {
            if (acceptMasterKey || !key.isMasterKey()) {
                return key;
            }
        }
        return null;
    }

    /**
     * Load all public keys from a key ring.
     *
     * @param keyRingData key ring data in base64 or armor format
     * @param asciiArmor  {@code true} if data is armor format; otherwise, base64
     * @param signingOnly {@code true} to load only signing keys; otherwise, all keys
     * @return
     * @throws IOException
     * @throws PGPException
     */
    public static List<PGPSecretKey> loadSecretKeys(String keyRingData, boolean asciiArmor, boolean signingOnly)
            throws IOException, PGPException {
        List<PGPSecretKey> result = new ArrayList<>();
        ByteArrayInputStream baos = new ByteArrayInputStream(
                asciiArmor ? keyRingData.getBytes(StandardCharsets.UTF_8) : Base64.getDecoder().decode(keyRingData));
        try (InputStream is = asciiArmor ? new ArmoredInputStream(baos) : baos) {
            PGPSecretKeyRingCollection secretKeyRings = new JcaPGPSecretKeyRingCollection(is);
            secretKeyRings.iterator()
                    .forEachRemaining(secKeyRing -> secKeyRing.getSecretKeys().forEachRemaining(secKey -> {
                        if (!signingOnly || secKey.isSigningKey()) {
                            result.add(secKey);
                        }
                    }));
        }
        return result;
    }

    /**
     * Load the first secret key that matches filtering from a key ring.
     *
     * @param keyRingData     key ring data in base64 or armor format
     * @param asciiArmor      {@code true} if data is armor format; otherwise, base64
     * @param signingOnly     {@code true} to load only signing key; otherwise, any key
     * @param acceptMasterKey {@code true} if master key can be returned; otherwise, the returned key is sub-key
     * @return
     * @throws IOException
     * @throws PGPException
     */
    public static PGPSecretKey loadFirstSecretKey(String keyRingData, boolean asciiArmor, boolean signingOnly,
            boolean acceptMasterKey) throws IOException, PGPException {
        List<PGPSecretKey> keys = loadSecretKeys(keyRingData, asciiArmor, signingOnly);
        for (PGPSecretKey key : keys) {
            if (acceptMasterKey || !key.isMasterKey()) {
                return key;
            }
        }
        return null;
    }

    public static PGPPrivateKey extractPrivateKey(PGPSecretKey secretKey, String password) throws PGPException {
        if (secretKey == null || secretKey.isPrivateKeyEmpty()) {
            return null;
        }
        PBESecretKeyDecryptor keyDecryptor = new JcePBESecretKeyDecryptorBuilder().build(password.toCharArray());
        return secretKey.extractPrivateKey(keyDecryptor);
    }
}

package com.github.ddth.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang3.StringUtils;

/**
 * RSA encryption/signature utility class.
 * 
 * <p>
 * Encrypt/Decrypt data, sign data and verify signature using RSA public/private key:
 * <ul>
 * <li>Default: 512, 1024, 2048 bit encryption key</li>
 * <li>Default: {@code RSA/ECB/PKCS1Padding} transformation (11-byte padding size)</li>
 * <li>Support custom transformation and padding size</li>
 * </ul>
 * </p>
 *
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.7.0
 */
public class RSAUtils {
    public final static Charset UTF8 = Charset.forName("UTF-8");
    public final static String CIPHER_ALGORITHM = "RSA";

    public final static String CIPHER_RSA_ECB_PKCS1Padding = "RSA/ECB/PKCS1Padding";
    public final static int PADDING_SIZE_RSA_ECB_PKCS1Padding = 11;

    public final static String CIPHER_RSA_ECB_OAEPWithSHA1AndMGF1Padding = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
    public final static int PADDING_SIZE_RSA_ECB_OAEPWithSHA1AndMGF1Padding = 42;

    public final static String CIPHER_RSA_ECB_OAEPWithSHA256AndMGF1Padding = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    public final static int PADDING_SIZE_RSA_ECB_OAEPWithSHA256AndMGF1Padding = 66;

    public final static String DEFAULT_CIPHER_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    public final static int DEFAULT_PADDING_SIZE = PADDING_SIZE_RSA_ECB_PKCS1Padding;

    public final static String SIGNATURE_ALGORITHM = "SHA1withRSA";

    static {
        try {
            Cipher.getInstance(DEFAULT_CIPHER_TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Construct the RSA public key from a key string data.
     *
     * @param base64KeyData
     *            RSA public key in base64 (base64 of {@link RSAPublicKey#getEncoded()})
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPublicKey buildPublicKey(String base64KeyData)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyData = Base64.getDecoder().decode(base64KeyData);
        return buildPublicKey(keyData);
    }

    /**
     * Construct the RSA public key from a key binary data.
     *
     * @param keyData
     *            RSA public key data (value of {@link RSAPublicKey#getEncoded()})
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPublicKey buildPublicKey(byte[] keyData)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyData);
        KeyFactory keyFactory = KeyFactory.getInstance(CIPHER_ALGORITHM);
        PublicKey generatePublic = keyFactory.generatePublic(publicKeySpec);
        return (RSAPublicKey) generatePublic;
    }

    /**
     * Construct the RSA private key from a key binary data.
     *
     * @param keyData
     *            RSA private key data (value of {@link RSAPrivateKey#getEncoded()})
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateKey buildPrivateKey(final byte[] keyData)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyData);
        KeyFactory keyFactory = KeyFactory.getInstance(CIPHER_ALGORITHM);
        PrivateKey generatePrivate = keyFactory.generatePrivate(privateKeySpec);
        return (RSAPrivateKey) generatePrivate;
    }

    /**
     * Construct the RSA private key from a key string data.
     *
     * @param base64KeyData
     *            RSA private key in base64 (base64 of {@link RSAPrivateKey#getEncoded()})
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static RSAPrivateKey buildPrivateKey(final String base64KeyData)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyData = Base64.getDecoder().decode(base64KeyData);
        return buildPrivateKey(keyData);
    }

    /*----------------------------------------------------------------------*/
    /**
     * Sign a message with RSA private key.
     * 
     * @param key
     * @param message
     *            the message to sign
     * @param signAlgo
     *            signature algorithm to use. If empty, {@link #SIGNATURE_ALGORITHM} will be used.
     * @return the signature
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] signMessage(RSAPrivateKey key, byte[] message, String signAlgo)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (StringUtils.isBlank(signAlgo)) {
            signAlgo = SIGNATURE_ALGORITHM;
        }
        Signature sign = Signature.getInstance(signAlgo);
        sign.initSign(key, SecureRandom.getInstanceStrong());
        sign.update(message);
        return sign.sign();
    }

    /**
     * Sign a message with RSA private key, using {@link #SIGNATURE_ALGORITHM}.
     * 
     * @param key
     * @param message
     * @return the signature
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws SignatureException
     */
    public static byte[] signMessage(RSAPrivateKey key, byte[] message)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return signMessage(key, message, SIGNATURE_ALGORITHM);
    }

    /**
     * Sign a message with RSA private key.
     * 
     * @param keyData
     *            RSA private key data (value of {@link RSAPrivateKey#getEncoded()})
     * @param message
     * @param signAlgo
     *            signature algorithm to use. If empty, {@link #SIGNATURE_ALGORITHM} will be used.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] signMessageWithPrivateKey(byte[] keyData, byte[] message, String signAlgo)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            SignatureException {
        RSAPrivateKey key = buildPrivateKey(keyData);
        return signMessage(key, message, signAlgo);
    }

    /**
     * Sign a message with RSA private key, using {@link #SIGNATURE_ALGORITHM}.
     * 
     * @param keyData
     *            RSA private key data (value of {@link RSAPrivateKey#getEncoded()})
     * @param message
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws SignatureException
     */
    public static byte[] signMessageWithPrivateKey(byte[] keyData, byte[] message)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
            SignatureException {
        return signMessageWithPrivateKey(keyData, message, SIGNATURE_ALGORITHM);
    }

    /**
     * Sign a message with RSA private key.
     * 
     * @param base64PrivateKeyData
     *            RSA private key in base64 (base64 of {@link RSAPrivateKey#getEncoded()})
     * @param message
     * @param signAlgo
     *            signature algorithm to use. If empty, {@link #SIGNATURE_ALGORITHM} will be used.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] signMessageWithPrivateKey(String base64PrivateKeyData, byte[] message,
            String signAlgo) throws NoSuchAlgorithmException, InvalidKeySpecException,
            InvalidKeyException, SignatureException {
        RSAPrivateKey key = buildPrivateKey(base64PrivateKeyData);
        return signMessage(key, message, signAlgo);
    }

    /**
     * Sign a message with RSA private key, using {@link #SIGNATURE_ALGORITHM}.
     * 
     * @param base64PrivateKeyData
     *            RSA private key in base64 (base64 of {@link RSAPrivateKey#getEncoded()})
     * @param message
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws SignatureException
     */
    public static byte[] signMessageWithPrivateKey(String base64PrivateKeyData, byte[] message)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
            SignatureException {
        return signMessageWithPrivateKey(base64PrivateKeyData, message, SIGNATURE_ALGORITHM);
    }

    /*----------------------------------------------------------------------*/
    /**
     * Verify a signature with RSA public key.
     * 
     * @param key
     * @param message
     *            the message to verify
     * @param signature
     *            the signature created by {@link #signMessage(RSAPrivateKey, byte[])}
     * @param signAlgo
     *            signature algorithm to use. If empty, {@link #SIGNATURE_ALGORITHM} will be used.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifySignature(RSAPublicKey key, byte[] message, byte[] signature,
            String signAlgo)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (StringUtils.isBlank(signAlgo)) {
            signAlgo = SIGNATURE_ALGORITHM;
        }
        Signature sign = Signature.getInstance(signAlgo);
        sign.initVerify(key);
        sign.update(message);
        return sign.verify(signature);
    }

    /**
     * Verify a signature with RSA public key, using {@link #SIGNATURE_ALGORITHM}.
     * 
     * @param key
     * @param message
     * @param signature
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws SignatureException
     */
    public static boolean verifySignature(RSAPublicKey key, byte[] message, byte[] signature)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return verifySignature(key, message, signature, SIGNATURE_ALGORITHM);
    }

    /**
     * Verify a signature with RSA public key.
     * 
     * @param keyData
     *            RSA public key data (value of {@link RSAPublicKey#getEncoded()})
     * @param message
     * @param signature
     * @param signAlgo
     *            signature algorithm to use. If empty, {@link #SIGNATURE_ALGORITHM} will be used.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifySignatureWithPublicKey(byte[] keyData, byte[] message,
            byte[] signature, String signAlgo) throws NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException, SignatureException {
        RSAPublicKey key = buildPublicKey(keyData);
        return verifySignature(key, message, signature, signAlgo);
    }

    /**
     * Verify a signature with RSA public key, using {@link #SIGNATURE_ALGORITHM}.
     * 
     * @param keyData
     *            RSA public key data (value of {@link RSAPublicKey#getEncoded()})
     * @param message
     * @param signature
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws SignatureException
     */
    public static boolean verifySignatureWithPublicKey(byte[] keyData, byte[] message,
            byte[] signature) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, SignatureException {
        return verifySignatureWithPublicKey(keyData, message, signature, SIGNATURE_ALGORITHM);
    }

    /**
     * Verify a signature with RSA public key.
     * 
     * @param base64PublicKeyData
     *            RSA public key in base64 (base64 of {@link RSAPublicKey#getEncoded()})
     * @param message
     * @param signature
     * @param signAlgo
     *            signature algorithm to use. If empty, {@link #SIGNATURE_ALGORITHM} will be used.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifySignatureWithPublicKey(String base64PublicKeyData, byte[] message,
            byte[] signature, String signAlgo) throws NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException, SignatureException {
        RSAPublicKey key = buildPublicKey(base64PublicKeyData);
        return verifySignature(key, message, signature, signAlgo);
    }

    /**
     * Verify a signature with RSA public key, using {@link #SIGNATURE_ALGORITHM}.
     * 
     * @param base64PublicKeyData
     *            RSA public key in base64 (base64 of {@link RSAPublicKey#getEncoded()})
     * @param message
     * @param signature
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws SignatureException
     */
    public static boolean verifySignatureWithPublicKey(String base64PublicKeyData, byte[] message,
            byte[] signature) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, SignatureException {
        return verifySignatureWithPublicKey(base64PublicKeyData, message, signature,
                SIGNATURE_ALGORITHM);
    }
    /*----------------------------------------------------------------------*/

    /**
     * Encrypt data with RSA public key.
     * 
     * <p>
     * Note: input data is divided into chunks of
     * {@code size = key's size (in bytes) - paddingSizeInBytes}, so that long input data can be
     * encrypted.
     * </p>
     * 
     * @param key
     * @param data
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used.
     * @param paddingSizeInBytes
     * @return
     * @see https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html#Cipher
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] encrypt(RSAPublicKey key, byte[] data, String cipherTransformation,
            int paddingSizeInBytes) throws NoSuchAlgorithmException, NoSuchPaddingException,
            IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (StringUtils.isBlank(cipherTransformation)) {
            cipherTransformation = DEFAULT_CIPHER_TRANSFORMATION;
            paddingSizeInBytes = DEFAULT_PADDING_SIZE;
        }
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        try (ByteArrayInputStream bois = new ByteArrayInputStream(data)) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                int bytesRead;
                byte[] buf = new byte[key.getModulus().bitLength() / 8 - paddingSizeInBytes];
                while ((bytesRead = bois.read(buf)) != -1) {
                    baos.write(cipher.doFinal(buf, 0, bytesRead));
                }
                return baos.toByteArray();
            }
        }
    }

    /**
     * Encrypt data with RSA public key, using {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     * 
     * <p>
     * Note: input data is divided into chunks of
     * {@code size = key's size (in bytes) - paddingSizeInBytes}, so that long input data can be
     * encrypted.
     * </p>
     * 
     * @param key
     * @param data
     * @return
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] encrypt(RSAPublicKey key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        return encrypt(key, data, DEFAULT_CIPHER_TRANSFORMATION, DEFAULT_PADDING_SIZE);
    }

    /**
     * Encrypt data with RSA public key.
     * 
     * <p>
     * Note: input data is divided into chunks of
     * {@code size = key's size (in bytes) - paddingSizeInBytes}, so that long input data can be
     * encrypted.
     * </p>
     * 
     * @param base64PublicKeyData
     *            RSA public key in base64 (base64 of {@link RSAPublicKey#getEncoded()})
     * @param data
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used
     * @param paddingSizeInBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] encryptWithPublicKey(String base64PublicKeyData, byte[] data,
            String cipherTransformation, int paddingSizeInBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        RSAPublicKey publicKey = buildPublicKey(base64PublicKeyData);
        return encrypt(publicKey, data, cipherTransformation, paddingSizeInBytes);
    }

    /**
     * Encrypt data with RSA public key, using {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     * 
     * <p>
     * Note: input data is divided into chunks of
     * {@code size = key's size (in bytes) - paddingSizeInBytes}, so that long input data can be
     * encrypted.
     * </p>
     * 
     * @param base64PublicKeyData
     *            RSA public key in base64 (base64 of {@link RSAPublicKey#getEncoded()})
     * @param data
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] encryptWithPublicKey(String base64PublicKeyData, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        return encryptWithPublicKey(base64PublicKeyData, data, DEFAULT_CIPHER_TRANSFORMATION,
                DEFAULT_PADDING_SIZE);
    }

    /**
     * Encrypt data with RSA public key.
     * 
     * <p>
     * Note: input data is divided into chunks of
     * {@code size = key's size (in bytes) - paddingSizeInBytes}, so that long input data can be
     * encrypted.
     * </p>
     * 
     * @param publicKeyData
     *            RSA public key data (value of {@link RSAPublicKey#getEncoded()})
     * @param data
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used
     * @param paddingSizeInBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] encryptWithPublicKey(byte[] publicKeyData, byte[] data,
            String cipherTransformation, int paddingSizeInBytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        RSAPublicKey publicKey = buildPublicKey(publicKeyData);
        return encrypt(publicKey, data, cipherTransformation, paddingSizeInBytes);
    }

    /**
     * Encrypt data with RSA public key, using {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     * 
     * <p>
     * Note: input data is divided into chunks of
     * {@code size = key's size (in bytes) - paddingSizeInBytes}, so that long input data can be
     * encrypted.
     * </p>
     * 
     * @param publicKeyData
     *            RSA public key data (value of {@link RSAPublicKey#getEncoded()})
     * @param data
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] encryptWithPublicKey(byte[] publicKeyData, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        return encryptWithPublicKey(publicKeyData, data, DEFAULT_CIPHER_TRANSFORMATION,
                DEFAULT_PADDING_SIZE);
    }

    /*----------------------------------------------------------------------*/
    /**
     * Decrypt encrypted data with RSA private key.
     * 
     * <p>
     * Note: if long data was encrypted using
     * {@link #encrypt(RSAPublicKey, byte[], String, int)}, it will be correctly decrypted.
     * </p>
     * 
     * @param key
     * @param encryptedData
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decrypt(RSAPrivateKey key, byte[] encryptedData,
            String cipherTransformation) throws NoSuchAlgorithmException, NoSuchPaddingException,
            IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(StringUtils.isBlank(cipherTransformation)
                ? DEFAULT_CIPHER_TRANSFORMATION : cipherTransformation);
        cipher.init(Cipher.DECRYPT_MODE, key);
        try (ByteArrayInputStream bois = new ByteArrayInputStream(encryptedData)) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                int bytesRead;
                byte[] buf = new byte[key.getModulus().bitLength() / 8];
                while ((bytesRead = bois.read(buf)) != -1) {
                    baos.write(cipher.doFinal(buf, 0, bytesRead));
                }
                return baos.toByteArray();
            }
        }
    }

    /**
     * Decrypt encrypted data with RSA private key, using {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     * 
     * <p>
     * Note: if long data was encrypted using
     * {@link #encrypt(RSAPublicKey, byte[])}, it will be correctly decrypted.
     * </p>
     * 
     * @param key
     * @param encryptedData
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static byte[] decrypt(RSAPrivateKey key, byte[] encryptedData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, IOException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        return decrypt(key, encryptedData, DEFAULT_CIPHER_TRANSFORMATION);
    }

    /**
     * Decrypt encrypted data with RSA private key.
     * 
     * <p>
     * Note: if long data was encrypted using
     * {@link #encryptWithPublicKey(byte[], byte[], String, int)}, it will be correctly decrypted.
     * </p>
     * 
     * @param privateKeyData
     *            RSA private key data (value of {@link RSAPrivateKey#getEncoded()})
     * @param encryptedData
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] decryptWithPrivateKey(byte[] privateKeyData, byte[] encryptedData,
            String cipherTransformation)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        RSAPrivateKey privateKey = buildPrivateKey(privateKeyData);
        return decrypt(privateKey, encryptedData, cipherTransformation);
    }

    /**
     * Decrypt encrypted data with RSA private key, using {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     * 
     * <p>
     * Note: if long data was encrypted using
     * {@link #encryptWithPublicKey(byte[], byte[])}, it will be correctly decrypted.
     * </p>
     * 
     * @param privateKeyData
     *            RSA private key data (value of {@link RSAPrivateKey#getEncoded()})
     * @param encryptedData
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] decryptWithPrivateKey(byte[] privateKeyData, byte[] encryptedData)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        return decryptWithPrivateKey(privateKeyData, encryptedData, DEFAULT_CIPHER_TRANSFORMATION);
    }

    /**
     * Decrypt encrypted data with RSA private key.
     * 
     * <p>
     * Note: if long data was encrypted using
     * {@link #encryptWithPublicKey(String, byte[], String, int)}, it will be correctly decrypted.
     * </p>
     * 
     * @param base64PrivateKeyData
     *            RSA private key in base64 (base64 of {@link RSAPrivateKey#getEncoded()})
     * @param encryptedData
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] decryptWithPrivateKey(String base64PrivateKeyData, byte[] encryptedData,
            String cipherTransformation)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        RSAPrivateKey privateKey = buildPrivateKey(base64PrivateKeyData);
        return decrypt(privateKey, encryptedData, cipherTransformation);
    }

    /**
     * Decrypt encrypted data with RSA private key, using {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     * 
     * <p>
     * Note: if long data was encrypted using
     * {@link #encryptWithPublicKey(String, byte[])}, it will be correctly decrypted.
     * </p>
     * 
     * @param base64PrivateKeyData
     *            RSA private key in base64 (base64 of {@link RSAPrivateKey#getEncoded()})
     * @param encryptedData
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    public static byte[] decryptWithPrivateKey(String base64PrivateKeyData, byte[] encryptedData)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
        return decryptWithPrivateKey(base64PrivateKeyData, encryptedData,
                DEFAULT_CIPHER_TRANSFORMATION);
    }

    /*----------------------------------------------------------------------*/
    /**
     * Generate a random RSA keypair.
     * 
     * @param numBits
     *            key's length in bits, should be power of 2)
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeys(int numBits) throws NoSuchAlgorithmException {
        int numBitsPow2 = 1;
        while (numBitsPow2 < numBits) {
            numBitsPow2 <<= 1;
        }

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(CIPHER_ALGORITHM);
        kpg.initialize(numBitsPow2, SecureRandom.getInstanceStrong());
        KeyPair keyPair = kpg.generateKeyPair();
        return keyPair;
    }

    /**
     * Convert the key data to base64 string.
     * 
     * @param key
     * @return base64 of {@link Key#getEncoded()}
     */
    public static String toBase64(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    /**
     * Extract the private key data from a keypair as base64 string.
     * 
     * @param keyPair
     * @return
     */
    public static String extractPrivateKeyAsBase64(KeyPair keyPair) {
        return toBase64(keyPair.getPrivate());
    }

    /**
     * Extract the public key data from a keypair as base64 string.
     * 
     * @param keyPair
     * @return
     */
    public static String extractPublicKeyAsBase64(KeyPair keyPair) {
        return toBase64(keyPair.getPublic());
    }

    /*----------------------------------------------------------------------*/
    public static void main(String[] args) throws Exception {
        final int keySize = 2048;
        KeyPair keyPair = generateKeys(keySize);
        String privateKey = extractPrivateKeyAsBase64(keyPair);
        String publicKey = extractPublicKeyAsBase64(keyPair);
        PrivateKey privKey = keyPair.getPrivate();
        PublicKey pubKey = keyPair.getPublic();
        System.out.println("Private key (" + keySize + "/" + privKey.getAlgorithm() + ":"
                + privKey.getFormat() + "): " + privateKey);
        System.out.println("Public key (" + keySize + "/" + pubKey.getAlgorithm() + ":"
                + pubKey.getFormat() + "): " + publicKey);
        System.out.println(StringUtils.repeat('=', 80));

        {
            System.out.println("-= DEFAULT =-");
            final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            final byte[] dataBytes = data.getBytes(UTF8);
            final byte[] encryptedData = encryptWithPublicKey(publicKey, dataBytes);
            final byte[] decryptedData = decryptWithPrivateKey(privateKey, encryptedData);
            System.out.println("Data     : " + data);
            System.out.println("Data Size: " + dataBytes.length);
            System.out.println("Encrypted: " + encryptedData.length);
            System.out.println("Decrypted: " + new String(decryptedData, UTF8));

            final byte[] signature = signMessageWithPrivateKey(privateKey, dataBytes);
            System.out.println("Signature: " + signature.length);
            System.out.println(
                    "Verify   : " + verifySignatureWithPublicKey(publicKey, dataBytes, signature));

            System.out.println(StringUtils.repeat('=', 80));
        }

        {
            System.out.println("-= LONG DATA =-");
            final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/",
                    102);
            final byte[] dataBytes = data.getBytes(UTF8);
            final byte[] encryptedData = encryptWithPublicKey(publicKey, dataBytes);
            final byte[] decryptedData = decryptWithPrivateKey(privateKey, encryptedData);
            System.out.println("Data     : " + data);
            System.out.println("Data Size: " + dataBytes.length);
            System.out.println("Encrypted: " + encryptedData.length);
            System.out.println("Decrypted: " + new String(decryptedData, UTF8));

            final byte[] signature = signMessageWithPrivateKey(privateKey, dataBytes);
            System.out.println("Signature: " + signature.length);
            System.out.println(
                    "Verify   : " + verifySignatureWithPublicKey(publicKey, dataBytes, signature));

            System.out.println(StringUtils.repeat('=', 80));
        }

        {
            System.out.println("-= LONG DATA: RSA/ECB/OAEPWithSHA-1AndMGF1Padding =-");
            final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/",
                    102);
            final String cipherTransformation = CIPHER_RSA_ECB_OAEPWithSHA1AndMGF1Padding;
            final int paddingSize = PADDING_SIZE_RSA_ECB_OAEPWithSHA1AndMGF1Padding;
            final byte[] dataBytes = data.getBytes(UTF8);
            final byte[] encryptedData = encryptWithPublicKey(publicKey, dataBytes,
                    cipherTransformation, paddingSize);
            final byte[] decryptedData = decryptWithPrivateKey(privateKey, encryptedData,
                    cipherTransformation);
            System.out.println("Data     : " + data);
            System.out.println("Data Size: " + dataBytes.length);
            System.out.println("Encrypted: " + encryptedData.length);
            System.out.println("Decrypted: " + new String(decryptedData, UTF8));

            final byte[] signature = signMessageWithPrivateKey(privateKey, dataBytes);
            System.out.println("Signature: " + signature.length);
            System.out.println(
                    "Verify   : " + verifySignatureWithPublicKey(publicKey, dataBytes, signature));

            System.out.println(StringUtils.repeat('=', 80));
        }

        {
            System.out.println("-= LONG DATA: RSA/ECB/OAEPWithSHA256AndMGF1Padding =-");
            final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/",
                    102);
            final String cipherTransformation = CIPHER_RSA_ECB_OAEPWithSHA256AndMGF1Padding;
            final int paddingSize = PADDING_SIZE_RSA_ECB_OAEPWithSHA256AndMGF1Padding;
            final byte[] dataBytes = data.getBytes(UTF8);
            final byte[] encryptedData = encryptWithPublicKey(publicKey, dataBytes,
                    cipherTransformation, paddingSize);
            final byte[] decryptedData = decryptWithPrivateKey(privateKey, encryptedData,
                    cipherTransformation);
            System.out.println("Data     : " + data);
            System.out.println("Data Size: " + dataBytes.length);
            System.out.println("Encrypted: " + encryptedData.length);
            System.out.println("Decrypted: " + new String(decryptedData, UTF8));

            final byte[] signature = signMessageWithPrivateKey(privateKey, dataBytes);
            System.out.println("Signature: " + signature.length);
            System.out.println(
                    "Verify   : " + verifySignatureWithPublicKey(publicKey, dataBytes, signature));

            System.out.println(StringUtils.repeat('=', 80));
        }
    }
}

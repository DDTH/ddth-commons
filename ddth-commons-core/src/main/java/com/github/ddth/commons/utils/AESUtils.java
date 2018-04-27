package com.github.ddth.commons.utils;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;

/**
 * AES encryption utility class.
 * 
 * <p>
 * Encrypt/Decrypt data using AES:
 * <ul>
 * <li>Default: 128-bit encryption key</li>
 * <li>Default: {@code AES/ECB/PKCS5PADDING} transformation</li>
 * <li>Support custom transformation and IV.</li>
 * </ul>
 * </p>
 *
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.7.0
 */
public class AESUtils {
    public final static Charset UTF8 = Charset.forName("UTF-8");
    public final static String CIPHER_ALGORITHM = "AES";

    public final static String CIPHER_AES_CBC_NoPadding = "AES/CBC/NoPadding";
    public final static String CIPHER_AES_CBC_PKCS5Padding = "AES/CBC/PKCS5Padding";
    public final static String CIPHER_AES_ECB_NoPadding = "AES/ECB/NoPadding";
    public final static String CIPHER_AES_ECB_PKCS5Padding = "AES/ECB/PKCS5Padding";

    public final static String DEFAULT_CIPHER_TRANSFORMATION = CIPHER_AES_ECB_PKCS5Padding;
    public final static String DEFAULT_IV = "0000000000000000";
    private final static byte[] DEFAULT_IV_BYTES = DEFAULT_IV.getBytes(UTF8);

    private final static Set<Character> RANDOM_CHAR_SET = new HashSet<>(
            Arrays.asList('[', ']', '{', '}', ',', '.', '/', '?', '~', '!', '@', '#', '$', '%', '^',
                    '&', '*', '(', ')', '+', '=', '-', '_'));
    private final static RandomStringGenerator RSG = new RandomStringGenerator.Builder()
            .filteredBy(c -> ('0' <= c && c <= '9') || ('a' <= c && c <= 'z')
                    || ('A' <= c && c <= 'Z') || RANDOM_CHAR_SET.contains(c))
            .withinRange(33, 254).build();

    static {
        try {
            Cipher.getInstance(DEFAULT_CIPHER_TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypt data using AES.
     * 
     * @param keyData
     * @param iv
     *            initial vector. If {@code null} or empty, {@link #DEFAULT_IV}
     *            will be used.
     * @param data
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used.
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] encrypt(byte[] keyData, byte[] iv, byte[] data,
            String cipherTransformation)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (StringUtils.isBlank(cipherTransformation)) {
            cipherTransformation = DEFAULT_CIPHER_TRANSFORMATION;
        }
        if (cipherTransformation.startsWith("AES/CBC/")) {
            if (iv == null || iv.length == 0) {
                iv = DEFAULT_IV_BYTES;
            }
        } else {
            iv = null;
        }
        SecretKeySpec aesKey = new SecretKeySpec(keyData, CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        if (iv == null) {
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));
        }
        byte[] encrypted = cipher.doFinal(data);
        return encrypted;
    }

    /**
     * Encrypt data using AES with {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     *
     * @param keyData
     * @param iv
     *            initial vector. If {@code null} or empty, {@link #DEFAULT_IV}
     *            will be used.
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] encrypt(byte[] keyData, byte[] iv, byte[] data)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return encrypt(keyData, iv, data, DEFAULT_CIPHER_TRANSFORMATION);
    }

    /**
     * Encrypt data using AES.
     * 
     * @param key
     * @param iv
     *            initial vector. If {@code null} or empty, {@link #DEFAULT_IV}
     *            will be used.
     * @param data
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used.
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] encrypt(String key, String iv, byte[] data, String cipherTransformation)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return encrypt(key.getBytes(UTF8), iv != null ? iv.getBytes(UTF8) : DEFAULT_IV_BYTES, data,
                cipherTransformation);
    }

    /**
     * Encrypt data using AES with {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     *
     * @param key
     * @param iv
     *            initial vector. If {@code null} or empty, {@link #DEFAULT_IV}
     *            will be used.
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] encrypt(String key, String iv, byte[] data)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return encrypt(key, iv, data, DEFAULT_CIPHER_TRANSFORMATION);
    }

    /**
     * Decrypt data using AES.
     * 
     * @param keyData
     * @param iv
     *            initial vector. If {@code null} or empty, {@link #DEFAULT_IV}
     *            will be used.
     * @param encryptedData
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] decrypt(byte[] keyData, byte[] iv, byte[] encryptedData,
            String cipherTransformation)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (StringUtils.isBlank(cipherTransformation)) {
            cipherTransformation = DEFAULT_CIPHER_TRANSFORMATION;
        }
        if (cipherTransformation.startsWith("AES/CBC/")) {
            if (iv == null || iv.length != 16) {
                iv = DEFAULT_IV_BYTES;
            }
        } else {
            iv = null;
        }
        SecretKeySpec aesKey = new SecretKeySpec(keyData, CIPHER_ALGORITHM);
        Cipher cipher = Cipher.getInstance(cipherTransformation);
        if (iv == null) {
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(iv));
        }
        byte[] decrypted = cipher.doFinal(encryptedData);
        return decrypted;
    }

    /**
     * Decrypt data using AES with {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     *
     * @param keyData
     * @param iv
     *            initial vector. If {@code null} or empty, {@link #DEFAULT_IV}
     *            will be used.
     * @param encryptedData
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] decrypt(byte[] keyData, byte[] iv, byte[] encryptedData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return decrypt(keyData, iv, encryptedData, DEFAULT_CIPHER_TRANSFORMATION);
    }

    /**
     * Decrypt data using AES.
     * 
     * @param key
     * @param iv
     *            initial vector. If {@code null} or empty, {@link #DEFAULT_IV}
     *            will be used.
     * @param encryptedData
     * @param cipherTransformation
     *            cipher-transformation to use. If empty, {@link #DEFAULT_CIPHER_TRANSFORMATION}
     *            will be used.
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] decrypt(String key, String iv, byte[] encryptedData,
            String cipherTransformation)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return decrypt(key.getBytes(UTF8), iv != null ? iv.getBytes(UTF8) : DEFAULT_IV_BYTES,
                encryptedData, cipherTransformation);
    }

    /**
     * Decrypt data using AES with {@link #DEFAULT_CIPHER_TRANSFORMATION}.
     *
     * @param key
     * @param iv
     *            initial vector. If {@code null} or empty, {@link #DEFAULT_IV}
     *            will be used.
     * @param encryptedData
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] decrypt(String key, String iv, byte[] encryptedData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        return decrypt(key, iv, encryptedData, DEFAULT_CIPHER_TRANSFORMATION);
    }

    /**
     * AES key should be 16 bytes in length.
     *
     * @param key
     * @return
     */
    public static String normalizeKey(String key) {
        if (StringUtils.isBlank(key)) {
            key = RSG.generate(16);
        } else if (key.length() > 16) {
            key = key.substring(0, 16);
        } else if (key.length() < 16) {
            key += RSG.generate(16 - key.length());
        }
        return key;
    }

    /**
     * AES key should be 16 bytes in length.
     *
     * @param key
     * @return
     */
    public static byte[] normalizeKey(byte[] keyData) {
        if (keyData == null) {
            return RSG.generate(16).getBytes(UTF8);
        } else {
            return normalizeKey(new String(keyData, UTF8)).getBytes(UTF8);
        }
    }

    /**
     * Generate a random AES key.
     * 
     * @return
     */
    public static String randomKey() {
        return RSG.generate(16);
    }

    /**
     * Generate a random AES key.
     * 
     * @return
     */
    public static byte[] randomKeyAsBytes() {
        return RSG.generate(16).getBytes(UTF8);
    }

    /**
     * Generate a random AES IV.
     * 
     * @return
     * @since 0.9.1
     */
    public static String randomIV() {
        return RSG.generate(16);
    }

    /**
     * Generate a random AES IV.
     * 
     * @return
     * @since 0.9.1
     */
    public static byte[] randomIVAsBytes() {
        return RSG.generate(16).getBytes(UTF8);
    }

    public static void main(String[] args) throws Exception {
        {
            System.out.println("-= DEFAULT =-");
            String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            String key = randomKey();
            byte[] encrypted = encrypt(key, null, data.getBytes(UTF8));
            byte[] decrypted = decrypt(key, null, encrypted);
            System.out.println("Key = " + key);
            System.out.println("Data = " + data);
            System.out.println("Encrypted = " + encrypted.length);
            System.out.println("Decrypted = " + new String(decrypted, UTF8));
            System.out.println(StringUtils.repeat('=', 80));
        }
        {
            System.out.println("-= AES/CBC/PKCS5PADDING: DefaultIV =-");
            String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            String key = randomKey();
            byte[] encrypted = encrypt(key, null, data.getBytes(UTF8), CIPHER_AES_CBC_PKCS5Padding);
            byte[] decrypted = decrypt(key, null, encrypted, CIPHER_AES_CBC_PKCS5Padding);
            System.out.println("Key = " + key);
            System.out.println("Data = " + data);
            System.out.println("Encrypted = " + encrypted.length);
            System.out.println("Decrypted = " + new String(decrypted, UTF8));
            System.out.println(StringUtils.repeat('=', 80));
        }
        {
            System.out.println("-= LONG DATA =-");
            String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 102);
            String key = randomKey();
            byte[] encrypted = encrypt(key, null, data.getBytes(UTF8));
            byte[] decrypted = decrypt(key, null, encrypted);
            System.out.println("Key  = " + key);
            System.out.println("Data = " + data);
            System.out.println("Encrypted = " + encrypted.length);
            System.out.println("Decrypted = " + new String(decrypted, UTF8));
            System.out.println(StringUtils.repeat('=', 80));
        }
        {
            System.out.println("-= LONG DATA: AES/CBC/PKCS5PADDING: DefaultIV =-");
            String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 102);
            String key = randomKey();
            byte[] encrypted = encrypt(key, null, data.getBytes(UTF8), CIPHER_AES_CBC_PKCS5Padding);
            byte[] decrypted = decrypt(key, null, encrypted, CIPHER_AES_CBC_PKCS5Padding);
            System.out.println("Key  = " + key);
            System.out.println("Data = " + data);
            System.out.println("Encrypted = " + encrypted.length);
            System.out.println("Decrypted = " + new String(decrypted, UTF8));
            System.out.println(StringUtils.repeat('=', 80));
        }
        {
            System.out.println("-= AES/CBC/PKCS5PADDING: CustomIV =-");
            String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            String key = randomKey();
            String iv = randomKey();
            byte[] encrypted = encrypt(key, iv, data.getBytes(UTF8), CIPHER_AES_CBC_PKCS5Padding);
            byte[] decrypted = decrypt(key, iv, encrypted, CIPHER_AES_CBC_PKCS5Padding);
            System.out.println("Key  = " + key);
            System.out.println("IV   = " + iv);
            System.out.println("Data = " + data);
            System.out.println("Encrypted = " + encrypted.length);
            System.out.println("Decrypted = " + new String(decrypted, UTF8));
            System.out.println(StringUtils.repeat('=', 80));
        }
        {
            System.out.println("-= AES/CBC/PKCS5PADDING: 2 IV =-");
            String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            String key = randomKey();
            String iv1 = randomKey();
            String iv2 = randomKey();
            byte[] encrypted = encrypt(key, iv1, data.getBytes(UTF8), CIPHER_AES_CBC_PKCS5Padding);
            byte[] decrypted = decrypt(key, iv2, encrypted, CIPHER_AES_CBC_PKCS5Padding);
            System.out.println("Key = " + key);
            System.out.println("IV1 = " + iv1);
            System.out.println("IV2 = " + iv2);
            System.out.println("Data = " + data);
            System.out.println("Encrypted = " + encrypted.length);
            System.out.println("Decrypted = " + new String(decrypted, UTF8));
            System.out.println(StringUtils.repeat('=', 80));
        }
        {
            System.out.println("-= LONG DATA: AES/CBC/PKCS5PADDING: 2 IV =-");
            String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 102);
            String key = randomKey();
            String iv1 = randomKey();
            String iv2 = randomKey();
            byte[] encrypted = encrypt(key, iv1, data.getBytes(UTF8), CIPHER_AES_CBC_PKCS5Padding);
            byte[] decrypted = decrypt(key, iv2, encrypted, CIPHER_AES_CBC_PKCS5Padding);
            System.out.println("Key = " + key);
            System.out.println("IV1 = " + iv1);
            System.out.println("IV2 = " + iv2);
            System.out.println("Data = " + data);
            System.out.println("Encrypted = " + encrypted.length);
            System.out.println("Decrypted = " + new String(decrypted, UTF8));
            System.out.println(StringUtils.repeat('=', 80));
        }
    }

}

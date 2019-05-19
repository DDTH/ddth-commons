package com.github.ddth.commons.qnd.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import com.github.ddth.commons.crypto.AESUtils;

public class QndAesUtils {

    static void doTestShortData(String transformation) throws Exception {
        System.out.println("-= " + transformation + " =-");
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        if (transformation.startsWith("AES/GCM/")) {
            iv = iv.substring(0, 12);
        }
        byte[] encrypted = AESUtils.encrypt(key, iv, data.getBytes(StandardCharsets.UTF_8),
                transformation);
        byte[] decrypted = AESUtils.decrypt(key, iv, encrypted, transformation);
        String decryptedStr = new String(decrypted, StandardCharsets.UTF_8);
        System.out.println("Key      : " + key);
        System.out.println("IV       : " + iv);
        System.out.println("Data     : " + data);
        System.out.println("Encrypted: " + encrypted.length + " bytes");
        System.out.println("Decrypted: " + decryptedStr);
        System.out.println("Matched  : " + StringUtils.equals(data, decryptedStr));
        System.out.println(StringUtils.repeat('=', 80));
    }

    static void doTestLongData(String transformation) throws Exception {
        System.out.println("-= LONG DATA: " + transformation + " =-");
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 103);
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        if (transformation.startsWith("AES/GCM/")) {
            iv = iv.substring(0, 12);
        }
        byte[] encrypted = AESUtils.encrypt(key, iv, data.getBytes(StandardCharsets.UTF_8),
                transformation);
        byte[] decrypted = AESUtils.decrypt(key, iv, encrypted, transformation);
        String decryptedStr = new String(decrypted, StandardCharsets.UTF_8);
        System.out.println("Key      : " + key);
        System.out.println("IV       : " + iv);
        System.out.println("Data     : (" + data.length() + " chars) " + data);
        System.out.println("Encrypted: " + encrypted.length + " bytes");
        System.out.println("Decrypted: (" + decryptedStr.length() + " chars) " + decryptedStr);
        System.out.println("Matched  : " + StringUtils.equals(data, decryptedStr));
        System.out.println(StringUtils.repeat('=', 80));
    }

    static void doTestShortData2IV(String transformation) throws Exception {
        System.out.println("-= " + transformation + ": 2 IV =-");
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        String key = AESUtils.randomKey();
        String iv1 = AESUtils.randomIV();
        String iv2 = AESUtils.randomIV();
        if (transformation.startsWith("AES/GCM/")) {
            iv1 = iv1.substring(0, 12);
            iv2 = iv2.substring(0, 12);
        }
        try {
            byte[] encrypted = AESUtils.encrypt(key, iv1, data.getBytes(StandardCharsets.UTF_8),
                    transformation);
            byte[] decrypted = AESUtils.decrypt(key, iv2, encrypted, transformation);
            String decryptedStr = new String(decrypted, StandardCharsets.UTF_8);
            System.out.println("Key      : " + key);
            System.out.println("IV1      : " + iv1);
            System.out.println("IV2      : " + iv2);
            System.out.println("Data     : " + data);
            System.out.println("Encrypted: " + encrypted.length + " bytes");
            System.out.println("Decrypted: " + decryptedStr);
            System.out.println("Matched  : " + StringUtils.equals(data, decryptedStr));
        } catch (Exception e) {
            System.out.println("ERROR    :" + e);
        }
        System.out.println(StringUtils.repeat('=', 80));
    }

    static void doTestLongData2IV(String transformation) throws Exception {
        System.out.println("-= LONG DATA: " + transformation + ": 2 IV =-");
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 103);
        String key = AESUtils.randomKey();
        String iv1 = AESUtils.randomIV();
        String iv2 = AESUtils.randomIV();
        if (transformation.startsWith("AES/GCM/")) {
            iv1 = iv1.substring(0, 12);
            iv2 = iv2.substring(0, 12);
        }
        try {
            byte[] encrypted = AESUtils.encrypt(key, iv1, data.getBytes(StandardCharsets.UTF_8),
                    transformation);
            byte[] decrypted = AESUtils.decrypt(key, iv2, encrypted, transformation);
            String decryptedStr = new String(decrypted, StandardCharsets.UTF_8);
            System.out.println("Key      : " + key);
            System.out.println("IV1      : " + iv1);
            System.out.println("IV2      : " + iv2);
            System.out.println("Data     : " + data);
            System.out.println("Encrypted: " + encrypted.length + " bytes");
            System.out.println("Decrypted: " + decryptedStr);
            System.out.println("Matched  : " + StringUtils.equals(data, decryptedStr));
        } catch (Exception e) {
            System.out.println("ERROR    :" + e);
        }
        System.out.println(StringUtils.repeat('=', 80));
    }

    static void doTestStream(String transformation) throws Exception {
        System.out.println("-= STREAM: " + transformation + " =-");
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        if (transformation.startsWith("AES/GCM/")) {
            iv = iv.substring(0, 12);
        }
        byte[] encrypted, decrypted;
        {
            ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
            AESUtils.encrypt(key, iv, transformation,
                    new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), _encrypted);
            encrypted = _encrypted.toByteArray();
            ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
            AESUtils.decrypt(key, iv, transformation, new ByteArrayInputStream(encrypted),
                    _decrypted);
            decrypted = _decrypted.toByteArray();
        }
        String decryptedStr = new String(decrypted, StandardCharsets.UTF_8);
        System.out.println("Key      : " + key);
        System.out.println("IV       : " + iv);
        System.out.println("Data     : (" + data.length() + " chars) " + data);
        System.out.println("Encrypted: " + encrypted.length + " bytes");
        System.out.println("Decrypted: (" + decryptedStr.length() + " chars) " + decryptedStr);
        System.out.println("Matched  : " + StringUtils.equals(data, decryptedStr));
        System.out.println(StringUtils.repeat('=', 80));
    }

    public static void main(String[] args) throws Exception {
        // doTestShortData(AESUtils.CIPHER_AES_CTR_NoPadding);
        // doTestShortData(AESUtils.CIPHER_AES_GCM_NoPadding);
        // doTestShortData(AESUtils.CIPHER_AES_CBC_NoPadding);
        // doTestShortData(AESUtils.CIPHER_AES_CBC_PKCS5Padding);
        // doTestShortData(AESUtils.CIPHER_AES_ECB_NoPadding);
        // doTestShortData(AESUtils.CIPHER_AES_ECB_PKCS5Padding);
        // doTestShortData(AESUtils.CIPHER_AES_CFB128_NoPadding);
        // doTestShortData(AESUtils.CIPHER_AES_CFB128_PKCS5Padding);

        // doTestLongData(AESUtils.CIPHER_AES_CTR_NoPadding);
        // doTestLongData(AESUtils.CIPHER_AES_GCM_NoPadding);
        // doTestLongData(AESUtils.CIPHER_AES_CBC_NoPadding);
        // doTestLongData(AESUtils.CIPHER_AES_CBC_PKCS5Padding);
        // doTestLongData(AESUtils.CIPHER_AES_ECB_NoPadding);
        // doTestLongData(AESUtils.CIPHER_AES_ECB_PKCS5Padding);
        // doTestLongData(AESUtils.CIPHER_AES_CFB128_NoPadding);
        // doTestLongData(AESUtils.CIPHER_AES_CFB128_PKCS5Padding);

        // doTestShortData2IV(AESUtils.CIPHER_AES_CTR_NoPadding);
        // doTestShortData2IV(AESUtils.CIPHER_AES_GCM_NoPadding);
        // doTestShortData2IV(AESUtils.CIPHER_AES_CBC_NoPadding);
        // doTestShortData2IV(AESUtils.CIPHER_AES_CBC_PKCS5Padding);
        // doTestShortData2IV(AESUtils.CIPHER_AES_ECB_NoPadding);
        // doTestShortData2IV(AESUtils.CIPHER_AES_ECB_PKCS5Padding);
        // doTestShortData2IV(AESUtils.CIPHER_AES_CFB128_NoPadding);
        // doTestShortData2IV(AESUtils.CIPHER_AES_CFB128_PKCS5Padding);

        // doTestLongData2IV(AESUtils.CIPHER_AES_CTR_NoPadding);
        // doTestLongData2IV(AESUtils.CIPHER_AES_GCM_NoPadding);
        // doTestLongData2IV(AESUtils.CIPHER_AES_CBC_NoPadding);
        // doTestLongData2IV(AESUtils.CIPHER_AES_CBC_PKCS5Padding);
        // doTestLongData2IV(AESUtils.CIPHER_AES_ECB_NoPadding);
        // doTestLongData2IV(AESUtils.CIPHER_AES_ECB_PKCS5Padding);
        // doTestLongData2IV(AESUtils.CIPHER_AES_CFB128_NoPadding);
        // doTestLongData2IV(AESUtils.CIPHER_AES_CFB128_PKCS5Padding);

        // doTestStream(AESUtils.CIPHER_AES_CTR_NoPadding);
        // doTestStream(AESUtils.CIPHER_AES_GCM_NoPadding);
        // doTestStream(AESUtils.CIPHER_AES_CBC_NoPadding);
        // doTestStream(AESUtils.CIPHER_AES_CBC_PKCS5Padding);
        // doTestStream(AESUtils.CIPHER_AES_ECB_NoPadding);
        // doTestStream(AESUtils.CIPHER_AES_ECB_PKCS5Padding);
        // doTestStream(AESUtils.CIPHER_AES_CFB128_NoPadding);
        // doTestStream(AESUtils.CIPHER_AES_CFB128_PKCS5Padding);
    }
}

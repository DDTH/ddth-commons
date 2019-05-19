package com.github.ddth.commons.qnd.utils;

import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import com.github.ddth.commons.crypto.AESUtils;

public class QndAesUtils2 {
    static void test1(String transformation) throws Exception {
        System.out.println("-= " + transformation + " =-");
        for (int i = 0; i < 32; i++) {
            String data = StringUtils.repeat('0', i);
            byte[] dataArr = data.getBytes(StandardCharsets.UTF_8);
            String key = AESUtils.randomKey();
            String iv = AESUtils.randomIV();
            if (transformation.startsWith("AES/GCM/")) {
                iv = iv.substring(0, 12);
            }
            byte[] encrypted = AESUtils.encrypt(key, iv, dataArr, transformation);
            byte[] decrypted = AESUtils.decrypt(key, iv, encrypted, transformation);
            String decryptedStr = new String(decrypted, StandardCharsets.UTF_8);
            System.out.println(StringUtils.equals(data, decryptedStr) + " - [" + data + "] vs ["
                    + decryptedStr + "]");
        }
        System.out.println(StringUtils.repeat('-', 80));
    }

    public static void main(String[] args) throws Exception {
        {
            // test1(AESUtils.CIPHER_AES_CTR_NoPadding);
            // test1(AESUtils.CIPHER_AES_GCM_NoPadding);

            // test1(AESUtils.CIPHER_AES_CBC_NoPadding);
            // test1(AESUtils.CIPHER_AES_CBC_PKCS5Padding);

            // test1(AESUtils.CIPHER_AES_ECB_NoPadding);
            // test1(AESUtils.CIPHER_AES_ECB_PKCS5Padding);

            // test1(AESUtils.CIPHER_AES_CFB128_NoPadding);
            // test1(AESUtils.CIPHER_AES_CFB128_PKCS5Padding);
        }

//        String[] transformationList = { AESUtils.CIPHER_AES_CTR_NoPadding,
//                AESUtils.CIPHER_AES_GCM_NoPadding, AESUtils.CIPHER_AES_CBC_NoPadding,
//                AESUtils.CIPHER_AES_CBC_PKCS5Padding, AESUtils.CIPHER_AES_ECB_NoPadding,
//                AESUtils.CIPHER_AES_ECB_PKCS5Padding, AESUtils.CIPHER_AES_CFB128_NoPadding,
//                AESUtils.CIPHER_AES_CFB128_PKCS5Padding };
//        for (String transformation : transformationList) {
//            System.out.println("-= " + transformation + " =-");
//            String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
//            byte[] dataArr = data.getBytes(StandardCharsets.UTF_8);
//            String key = AESUtils.randomKey();
//            String iv = AESUtils.randomIV();
//            if (transformation.startsWith("AES/GCM/")) {
//                iv = iv.substring(0, 12);
//            }
//            byte[] encrypted = AESUtils.encrypt(key, iv, dataArr, transformation);
//            byte[] decrypted = AESUtils.decrypt(key, iv, encrypted, transformation);
//            String decryptedStr = new String(decrypted, StandardCharsets.UTF_8);
//            System.out.println("Key : " + key);
//            System.out.println("IV : " + iv);
//            System.out.println("Data : " + data);
//            System.out.println("Encrypted: " + encrypted.length + " bytes");
//            System.out.println("Decrypted: " + decryptedStr);
//            System.out.println("Matched : " + StringUtils.equals(data, decryptedStr));
//            System.out.println(StringUtils.repeat('=', 80));
//        }
    }
}

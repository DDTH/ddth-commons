package com.github.ddth.commons.test.utils;

import static org.junit.Assert.assertNotEquals;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.github.ddth.commons.utils.AESUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AESUtilsTest extends TestCase {

    public AESUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AESUtilsTest.class);
    }

    @org.junit.Test
    public void testGenerateKeyStringLenth16() {
        String key = AESUtils.randomKey();
        assertEquals(16, key.length());
    }

    @org.junit.Test
    public void testGenerateKeyBytesLength16() {
        byte[] key = AESUtils.randomKeyAsBytes();
        assertEquals(16, key.length);
    }

    @org.junit.Test
    public void testGenerateKeyStringDiff() {
        String key1 = AESUtils.randomKey();
        String key2 = AESUtils.randomKey();
        assertNotEquals(key1, key2);
    }

    @org.junit.Test
    public void testGenerateKeyBytesDiff() {
        byte[] key1 = AESUtils.randomKeyAsBytes();
        byte[] key2 = AESUtils.randomKeyAsBytes();
        assertNotEquals(key1, key2);
    }

    @org.junit.Test
    public void testNormalizeKeyStringLess16() {
        String key = "12345";
        String keyN = AESUtils.normalizeKey(key);
        assertEquals(16, keyN.length());
        assertNotEquals(key, keyN);
    }

    @org.junit.Test
    public void testNormalizeKeyString16() {
        String key = AESUtils.randomKey();
        String keyN = AESUtils.normalizeKey(key);
        assertEquals(16, keyN.length());
        assertEquals(key, keyN);
    }

    @org.junit.Test
    public void testNormalizeKeyStringMore16() {
        String key = AESUtils.randomKey() + "0";
        String keyN = AESUtils.normalizeKey(key);
        assertEquals(16, keyN.length());
        assertNotEquals(key, keyN);
    }

    @org.junit.Test
    public void testNormalizeKeyBytesLess16() {
        byte[] key = { '1', '2', '3', '4', '5' };
        byte[] keyN = AESUtils.normalizeKey(key);
        assertEquals(16, keyN.length);
        assertNotEquals(key, keyN);
    }

    @org.junit.Test
    public void testNormalizeKeyBytes16() {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] keyN = AESUtils.normalizeKey(key);
        assertEquals(16, keyN.length);
        assertTrue(new EqualsBuilder().append(key, keyN).isEquals());
    }

    @org.junit.Test
    public void testNormalizeKeyBytesMore16() {
        byte[] key = ArrayUtils.add(AESUtils.randomKeyAsBytes(), (byte) '0');
        byte[] keyN = AESUtils.normalizeKey(key);
        assertEquals(16, keyN.length);
        assertFalse(new EqualsBuilder().append(key, keyN).isEquals());
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testEncryptDecryptKeyString() throws Exception {
        String key = AESUtils.randomKey();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData = AESUtils.encrypt(key, null, data.getBytes(AESUtils.UTF8));
        byte[] decryptedData = AESUtils.decrypt(key, null, encryptedData);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringCBCPKCS5Padding() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String transformation = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                transformation);
        byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, transformation);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringCBCNoPadding() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String transformation = AESUtils.CIPHER_AES_CBC_NoPadding;
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/",
                key.length());
        byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                transformation);
        byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, transformation);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringCBCNoPadding2() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String transformation = AESUtils.CIPHER_AES_CBC_NoPadding;
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", key.length())
                + ' ';
        Throwable t = null;
        try {
            AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8), transformation);
        } catch (Exception e) {
            t = e;
        }
        assertTrue(t instanceof IllegalBlockSizeException);
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringECBPKCS5Padding() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String transformation = AESUtils.CIPHER_AES_ECB_PKCS5Padding;
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                transformation);
        byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, transformation);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringECBNoPadding() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String transformation = AESUtils.CIPHER_AES_ECB_NoPadding;
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/",
                key.length());
        byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                transformation);
        byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, transformation);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringECBNoPadding2() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String transformation = AESUtils.CIPHER_AES_ECB_NoPadding;
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", key.length())
                + ' ';
        Throwable t = null;
        try {
            AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8), transformation);
        } catch (Exception e) {
            t = e;
        }
        assertTrue(t instanceof IllegalBlockSizeException);
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringDiffTransformationns() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String[] txList1 = { AESUtils.CIPHER_AES_CBC_NoPadding,
                AESUtils.CIPHER_AES_CBC_PKCS5Padding };
        String[] txList2 = { AESUtils.CIPHER_AES_ECB_NoPadding,
                AESUtils.CIPHER_AES_ECB_PKCS5Padding };
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/",
                key.length());
        for (String tx1 : txList1) {
            for (String tx2 : txList2) {
                Throwable t = null;
                try {
                    byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                            tx1);
                    byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, tx2);
                    assertNotEquals(data, new String(decryptedData, AESUtils.UTF8));
                } catch (Exception e) {
                    t = e;
                }
                assertTrue(t == null || t instanceof BadPaddingException);
            }
        }
    }

    /*----------------------------------------------------------------------*/
    @org.junit.Test
    public void testEncryptDecryptKeyBytes() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData = AESUtils.encrypt(key, null, data.getBytes(AESUtils.UTF8));
        byte[] decryptedData = AESUtils.decrypt(key, null, encryptedData);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesCBCPKCS5Padding() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String transformation = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                transformation);
        byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, transformation);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesCBCNoPadding() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String transformation = AESUtils.CIPHER_AES_CBC_NoPadding;
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", key.length);
        byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                transformation);
        byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, transformation);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesCBCNoPadding2() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String transformation = AESUtils.CIPHER_AES_CBC_NoPadding;
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", key.length)
                + ' ';
        Throwable t = null;
        try {
            AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8), transformation);
        } catch (Exception e) {
            t = e;
        }
        assertTrue(t instanceof IllegalBlockSizeException);
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesECBPKCS5Padding() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String transformation = AESUtils.CIPHER_AES_ECB_PKCS5Padding;
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                transformation);
        byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, transformation);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesECBNoPadding() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String transformation = AESUtils.CIPHER_AES_ECB_NoPadding;
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", key.length);
        byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                transformation);
        byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, transformation);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesECBNoPadding2() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String transformation = AESUtils.CIPHER_AES_ECB_NoPadding;
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", key.length)
                + ' ';
        Throwable t = null;
        try {
            AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8), transformation);
        } catch (Exception e) {
            t = e;
        }
        assertTrue(t instanceof IllegalBlockSizeException);
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesDiffTransformationns() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String[] txList1 = { AESUtils.CIPHER_AES_CBC_NoPadding,
                AESUtils.CIPHER_AES_CBC_PKCS5Padding };
        String[] txList2 = { AESUtils.CIPHER_AES_ECB_NoPadding,
                AESUtils.CIPHER_AES_ECB_PKCS5Padding };
        String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", key.length);
        for (String tx1 : txList1) {
            for (String tx2 : txList2) {
                Throwable t = null;
                try {
                    byte[] encryptedData = AESUtils.encrypt(key, iv, data.getBytes(AESUtils.UTF8),
                            tx1);
                    byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, tx2);
                    assertNotEquals(data, new String(decryptedData, AESUtils.UTF8));
                } catch (Exception e) {
                    t = e;
                }
                assertTrue(t == null || t instanceof BadPaddingException);
            }
        }
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testEncryptDecryptSameIVsString() throws Exception {
        String key = AESUtils.randomKey();
        String iv1 = AESUtils.randomIV();
        String iv2 = iv1;
        String cipherTransformation = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData1 = AESUtils.encrypt(key, iv1, data.getBytes(AESUtils.UTF8),
                cipherTransformation);
        byte[] encryptedData2 = AESUtils.encrypt(key, iv2, data.getBytes(AESUtils.UTF8),
                cipherTransformation);
        byte[] decryptedData1 = AESUtils.decrypt(key, iv1, encryptedData2, cipherTransformation);
        byte[] decryptedData2 = AESUtils.decrypt(key, iv2, encryptedData1, cipherTransformation);
        assertEquals(data, new String(decryptedData1, AESUtils.UTF8));
        assertEquals(data, new String(decryptedData2, AESUtils.UTF8));
        assertTrue(new EqualsBuilder().append(encryptedData1, encryptedData2).isEquals());
    }

    @org.junit.Test
    public void testEncryptDecryptSameIVsBytes() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv1 = AESUtils.randomIVAsBytes();
        byte[] iv2 = iv1;
        String cipherTransformtion = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData1 = AESUtils.encrypt(key, iv1, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        byte[] encryptedData2 = AESUtils.encrypt(key, iv2, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        byte[] decryptedData1 = AESUtils.decrypt(key, iv1, encryptedData2, cipherTransformtion);
        byte[] decryptedData2 = AESUtils.decrypt(key, iv2, encryptedData1, cipherTransformtion);
        assertEquals(data, new String(decryptedData1, AESUtils.UTF8));
        assertEquals(data, new String(decryptedData2, AESUtils.UTF8));
        assertTrue(new EqualsBuilder().append(encryptedData1, encryptedData2).isEquals());
    }

    @org.junit.Test
    public void testEncryptDecryptDiffKeysString() throws Exception {
        Throwable t = null;
        try {
            String key1 = AESUtils.randomKey();
            String key2 = AESUtils.randomKey();
            String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            byte[] encryptedData = AESUtils.encrypt(key1, null, data.getBytes(AESUtils.UTF8));
            byte[] decryptedData = AESUtils.decrypt(key2, null, encryptedData);
            assertNotEquals(data, new String(decryptedData, AESUtils.UTF8));
        } catch (BadPaddingException e) {
            t = e;
        }
        assertTrue(t instanceof BadPaddingException);
    }

    @org.junit.Test
    public void testEncryptDecryptDiffKeysBytes() throws Exception {
        Throwable t = null;
        try {
            byte[] key1 = AESUtils.randomKeyAsBytes();
            byte[] key2 = AESUtils.randomKeyAsBytes();
            final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            byte[] encryptedData = AESUtils.encrypt(key1, null, data.getBytes(AESUtils.UTF8));
            byte[] decryptedData = AESUtils.decrypt(key2, null, encryptedData);
            assertNotEquals(data, new String(decryptedData, AESUtils.UTF8));
        } catch (BadPaddingException e) {
            t = e;
        }
        assertTrue(t instanceof BadPaddingException);
    }

    @org.junit.Test
    public void testEncryptDecryptDiffIVsString() throws Exception {
        String key = AESUtils.randomKey();
        String iv1 = AESUtils.randomKey();
        String iv2 = AESUtils.randomKey();
        String cipherTransformtion = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData1 = AESUtils.encrypt(key, iv1, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        byte[] encryptedData2 = AESUtils.encrypt(key, iv2, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        byte[] decryptedData1 = AESUtils.decrypt(key, iv1, encryptedData1, cipherTransformtion);
        byte[] decryptedData2 = AESUtils.decrypt(key, iv2, encryptedData2, cipherTransformtion);
        assertEquals(data, new String(decryptedData1, AESUtils.UTF8));
        assertEquals(data, new String(decryptedData2, AESUtils.UTF8));
        assertFalse(new EqualsBuilder().append(encryptedData1, encryptedData2).isEquals());

        assertNotEquals(data, new String(
                AESUtils.decrypt(key, iv2, encryptedData1, cipherTransformtion), AESUtils.UTF8));
        assertNotEquals(data, new String(
                AESUtils.decrypt(key, iv1, encryptedData2, cipherTransformtion), AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptDiffIVsBytes() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv1 = AESUtils.randomKeyAsBytes();
        byte[] iv2 = AESUtils.randomKeyAsBytes();
        String cipherTransformtion = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] encryptedData1 = AESUtils.encrypt(key, iv1, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        byte[] encryptedData2 = AESUtils.encrypt(key, iv2, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        byte[] decryptedData1 = AESUtils.decrypt(key, iv1, encryptedData1, cipherTransformtion);
        byte[] decryptedData2 = AESUtils.decrypt(key, iv2, encryptedData2, cipherTransformtion);
        assertEquals(data, new String(decryptedData1, AESUtils.UTF8));
        assertEquals(data, new String(decryptedData2, AESUtils.UTF8));
        assertFalse(new EqualsBuilder().append(encryptedData1, encryptedData2).isEquals());

        assertNotEquals(data, new String(
                AESUtils.decrypt(key, iv2, encryptedData1, cipherTransformtion), AESUtils.UTF8));
        assertNotEquals(data, new String(
                AESUtils.decrypt(key, iv1, encryptedData2, cipherTransformtion), AESUtils.UTF8));
    }
}

package com.github.ddth.commons.test.utils;

import static org.junit.Assert.assertNotEquals;

import javax.crypto.BadPaddingException;

import org.apache.commons.lang3.ArrayUtils;
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

    @org.junit.Test
    public void testEncryptDecryptKeyString() throws Exception {
        String key = AESUtils.randomKey();
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData = AESUtils.encrypt(key, null, data.getBytes(AESUtils.UTF8));
        final byte[] decryptedData = AESUtils.decrypt(key, null, encryptedData);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytes() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData = AESUtils.encrypt(key, null, data.getBytes(AESUtils.UTF8));
        final byte[] decryptedData = AESUtils.decrypt(key, null, encryptedData);
        assertEquals(data, new String(decryptedData, AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptSameIVsString() throws Exception {
        String key = AESUtils.randomKey();
        String iv1 = AESUtils.DEFAULT_IV;
        String iv2 = AESUtils.DEFAULT_IV;
        String cipherTransformtion = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData1 = AESUtils.encrypt(key, iv1, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        final byte[] encryptedData2 = AESUtils.encrypt(key, iv2, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        final byte[] decryptedData1 = AESUtils.decrypt(key, iv1, encryptedData2);
        final byte[] decryptedData2 = AESUtils.decrypt(key, iv2, encryptedData1);
        assertEquals(data, new String(decryptedData1, AESUtils.UTF8));
        assertEquals(data, new String(decryptedData2, AESUtils.UTF8));
        assertTrue(new EqualsBuilder().append(encryptedData1, encryptedData2).isEquals());
    }

    @org.junit.Test
    public void testEncryptDecryptSameIVsBytes() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv1 = AESUtils.DEFAULT_IV.getBytes(AESUtils.UTF8);
        byte[] iv2 = AESUtils.DEFAULT_IV.getBytes(AESUtils.UTF8);
        String cipherTransformtion = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData1 = AESUtils.encrypt(key, iv1, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        final byte[] encryptedData2 = AESUtils.encrypt(key, iv2, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        final byte[] decryptedData1 = AESUtils.decrypt(key, iv1, encryptedData2);
        final byte[] decryptedData2 = AESUtils.decrypt(key, iv2, encryptedData1);
        assertEquals(data, new String(decryptedData1, AESUtils.UTF8));
        assertEquals(data, new String(decryptedData2, AESUtils.UTF8));
        assertTrue(new EqualsBuilder().append(encryptedData1, encryptedData2).isEquals());
    }

    @org.junit.Test
    public void testEncryptDecryptDiffKeysString() throws Exception {
        try {
            String key1 = AESUtils.randomKey();
            String key2 = AESUtils.randomKey();
            final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            final byte[] encryptedData = AESUtils.encrypt(key1, null, data.getBytes(AESUtils.UTF8));
            final byte[] decryptedData = AESUtils.decrypt(key2, null, encryptedData);
            assertNotEquals(data, new String(decryptedData, AESUtils.UTF8));
        } catch (BadPaddingException e) {
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptDiffKeysBytes() throws Exception {
        try {
            byte[] key1 = AESUtils.randomKeyAsBytes();
            byte[] key2 = AESUtils.randomKeyAsBytes();
            final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            final byte[] encryptedData = AESUtils.encrypt(key1, null, data.getBytes(AESUtils.UTF8));
            final byte[] decryptedData = AESUtils.decrypt(key2, null, encryptedData);
            assertNotEquals(data, new String(decryptedData, AESUtils.UTF8));
        } catch (BadPaddingException e) {
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptDiffIVsString() throws Exception {
        String key = AESUtils.randomKey();
        String iv1 = AESUtils.randomKey();
        String iv2 = AESUtils.randomKey();
        String cipherTransformtion = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData1 = AESUtils.encrypt(key, iv1, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        final byte[] encryptedData2 = AESUtils.encrypt(key, iv2, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        final byte[] decryptedData1 = AESUtils.decrypt(key, iv1, encryptedData1);
        final byte[] decryptedData2 = AESUtils.decrypt(key, iv2, encryptedData2);
        assertEquals(data, new String(decryptedData1, AESUtils.UTF8));
        assertEquals(data, new String(decryptedData2, AESUtils.UTF8));
        assertFalse(new EqualsBuilder().append(encryptedData1, encryptedData2).isEquals());

        assertNotEquals(data,
                new String(AESUtils.decrypt(key, iv2, encryptedData1), AESUtils.UTF8));
        assertNotEquals(data,
                new String(AESUtils.decrypt(key, iv1, encryptedData2), AESUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptDiffIVsBytes() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv1 = AESUtils.randomKeyAsBytes();
        byte[] iv2 = AESUtils.randomKeyAsBytes();
        String cipherTransformtion = AESUtils.CIPHER_AES_CBC_PKCS5Padding;
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData1 = AESUtils.encrypt(key, iv1, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        final byte[] encryptedData2 = AESUtils.encrypt(key, iv2, data.getBytes(AESUtils.UTF8),
                cipherTransformtion);
        final byte[] decryptedData1 = AESUtils.decrypt(key, iv1, encryptedData1,
                cipherTransformtion);
        final byte[] decryptedData2 = AESUtils.decrypt(key, iv2, encryptedData2,
                cipherTransformtion);
        assertEquals(data, new String(decryptedData1, AESUtils.UTF8));
        assertEquals(data, new String(decryptedData2, AESUtils.UTF8));
        assertFalse(new EqualsBuilder().append(encryptedData1, encryptedData2).isEquals());

        assertNotEquals(data,
                new String(AESUtils.decrypt(key, iv2, encryptedData1), AESUtils.UTF8));
        assertNotEquals(data,
                new String(AESUtils.decrypt(key, iv1, encryptedData2), AESUtils.UTF8));
    }
}

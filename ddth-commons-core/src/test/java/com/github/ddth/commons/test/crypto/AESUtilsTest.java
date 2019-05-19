package com.github.ddth.commons.test.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.github.ddth.commons.crypto.AESUtils;
import com.github.ddth.commons.crypto.utils.CipherException;

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
        Assert.assertEquals(16, key.length());
    }

    @org.junit.Test
    public void testGenerateKeyBytesLength16() {
        byte[] key = AESUtils.randomKeyAsBytes();
        Assert.assertEquals(16, key.length);
    }

    @org.junit.Test
    public void testGenerateKeyBytesSecureLength16() {
        byte[] key = AESUtils.randomKeyAsBytesSecure();
        Assert.assertEquals(16, key.length);
    }

    @org.junit.Test
    public void testGenerateKeyStringDiff() {
        String key1 = AESUtils.randomKey();
        String key2 = AESUtils.randomKey();
        Assert.assertNotEquals(key1, key2);
    }

    @org.junit.Test
    public void testGenerateKeyBytesDiff() {
        byte[] key1 = AESUtils.randomKeyAsBytes();
        byte[] key2 = AESUtils.randomKeyAsBytes();
        Assert.assertFalse(Arrays.equals(key1, key2));
    }

    @org.junit.Test
    public void testGenerateKeyBytesSecureDiff() {
        byte[] key1 = AESUtils.randomKeyAsBytesSecure();
        byte[] key2 = AESUtils.randomKeyAsBytesSecure();
        Assert.assertFalse(Arrays.equals(key1, key2));
    }

    @org.junit.Test
    public void testNormalizeKeyStringLess16() {
        String key = "12345";
        String keyN = AESUtils.normalizeKey(key);
        Assert.assertEquals(16, keyN.length());
        Assert.assertNotEquals(key, keyN);
    }

    @org.junit.Test
    public void testNormalizeKeyString16() {
        String key = AESUtils.randomKey();
        String keyN = AESUtils.normalizeKey(key);
        Assert.assertEquals(16, keyN.length());
        Assert.assertEquals(key, keyN);
    }

    @org.junit.Test
    public void testNormalizeKeyStringMore16() {
        String key = AESUtils.randomKey() + "0";
        String keyN = AESUtils.normalizeKey(key);
        Assert.assertEquals(16, keyN.length());
        Assert.assertNotEquals(key, keyN);
    }

    @org.junit.Test
    public void testNormalizeKeyBytesLess16() {
        byte[] key = { '1', '2', '3', '4', '5' };
        byte[] keyN = AESUtils.normalizeKey(key);
        Assert.assertEquals(16, keyN.length);
        Assert.assertFalse(Arrays.equals(key, keyN));
    }

    @org.junit.Test
    public void testNormalizeKeyBytes16() {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] keyN = AESUtils.normalizeKey(key);
        Assert.assertEquals(16, keyN.length);
        Assert.assertTrue(Arrays.equals(key, keyN));
    }

    @org.junit.Test
    public void testNormalizeKeyBytesMore16() {
        byte[] key = ArrayUtils.add(AESUtils.randomKeyAsBytes(), (byte) '0');
        byte[] keyN = AESUtils.normalizeKey(key);
        Assert.assertEquals(16, keyN.length);
        Assert.assertFalse(Arrays.equals(key, keyN));
    }

    /*----------------------------------------------------------------------*/
    static final List<String> TRANSFORMATIONS = new LinkedList<>();
    static {
        for (String mode : AESUtils.CIPHER_MODES) {
            for (String padding : AESUtils.CIPHER_PADDINGS) {
                if ((mode.equals("CTR") || mode.equals("CTS") || mode.equals("GCM"))
                        && !padding.equals("NoPadding")) {
                    // CTR mode must be used with NoPadding
                    continue;
                }
                String transformation = AESUtils.CIPHER_ALGORITHM + "/" + mode + "/" + padding;
                TRANSFORMATIONS.add(transformation);
            }
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyString() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            String myIv = transformation.startsWith("AES/GCM/") ? iv.substring(0, 12) : iv;
            byte[] encryptedData = AESUtils.encrypt(key, myIv, dataBytes, transformation);
            byte[] decryptedData = AESUtils.decrypt(key, myIv, encryptedData, transformation);
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertArrayEquals(dataBytes, actualDecryptedData);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringDiffTransformationns() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String tx1 : TRANSFORMATIONS) {
            for (String tx2 : TRANSFORMATIONS) {
                String[] tokens1 = tx1.split("/");
                String[] tokens2 = tx2.split("/");
                if (StringUtils.startsWith(tokens1[1], tokens2[1])
                        || StringUtils.startsWith(tokens2[1], tokens1[1])) {
                    continue;
                }
                Throwable t = null;
                try {
                    byte[] encryptedData = AESUtils.encrypt(key, iv, dataBytes, tx1);
                    byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, tx2);
                    byte[] actualDecryptedData = tx2.endsWith("/ECB/NoPadding")
                            || tx2.endsWith("/CBC/NoPadding") || tx2.endsWith("/PCBC/NoPadding")
                                    ? Arrays.copyOf(decryptedData, dataBytes.length)
                                    : decryptedData;
                    Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                    t = new BadPaddingException();
                } catch (Exception e) {
                    t = e;
                }
                Assert.assertTrue(
                        t instanceof BadPaddingException || t instanceof IllegalBlockSizeException);
            }
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringDiffIVs() throws Exception {
        String key = AESUtils.randomKey();
        String iv1 = AESUtils.randomIV();
        String iv2 = AESUtils.randomIV();
        Assert.assertNotEquals(iv1, iv2);
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            if (transformation.startsWith("AES/ECB/")) {
                // ECB does not use IVs
                continue;
            }
            String myIV1 = transformation.startsWith("AES/GCM/") ? iv1.substring(0, 12) : iv1;
            String myIV2 = transformation.startsWith("AES/GCM/") ? iv2.substring(0, 12) : iv2;
            Throwable t = null;
            try {
                byte[] encryptedData = AESUtils.encrypt(key, myIV1, dataBytes, transformation);
                byte[] decryptedData = AESUtils.decrypt(key, myIV2, encryptedData, transformation);
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyStringDiffKeys() throws Exception {
        String key1 = AESUtils.randomKey();
        String key2 = AESUtils.randomKey();
        Assert.assertNotEquals(key1, key2);
        String iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            String myIV = transformation.startsWith("AES/GCM/") ? iv.substring(0, 12) : iv;
            Throwable t = null;
            try {
                byte[] encryptedData = AESUtils.encrypt(key1, myIV, dataBytes, transformation);
                byte[] decryptedData = AESUtils.decrypt(key2, myIV, encryptedData, transformation);
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testEncryptDecryptKeyBytes() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            byte[] myIv = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv, 12) : iv;
            byte[] encryptedData = AESUtils.encrypt(key, myIv, dataBytes, transformation);
            byte[] decryptedData = AESUtils.decrypt(key, myIv, encryptedData, transformation);
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertArrayEquals(dataBytes, actualDecryptedData);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesDiffTransformationns() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String tx1 : TRANSFORMATIONS) {
            for (String tx2 : TRANSFORMATIONS) {
                String[] tokens1 = tx1.split("/");
                String[] tokens2 = tx2.split("/");
                if (StringUtils.startsWith(tokens1[1], tokens2[1])
                        || StringUtils.startsWith(tokens2[1], tokens1[1])) {
                    continue;
                }
                Throwable t = null;
                try {
                    byte[] encryptedData = AESUtils.encrypt(key, iv, dataBytes, tx1);
                    byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, tx2);
                    byte[] actualDecryptedData = tx2.endsWith("/ECB/NoPadding")
                            || tx2.endsWith("/CBC/NoPadding") || tx2.endsWith("/PCBC/NoPadding")
                                    ? Arrays.copyOf(decryptedData, dataBytes.length)
                                    : decryptedData;
                    Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                    t = new BadPaddingException();
                } catch (Exception e) {
                    t = e;
                }
                Assert.assertTrue(
                        t instanceof BadPaddingException || t instanceof IllegalBlockSizeException);
            }
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesDiffIVs() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv1 = AESUtils.randomIVAsBytes();
        byte[] iv2 = AESUtils.randomIVAsBytes();
        Assert.assertFalse(Arrays.equals(iv1, iv2));
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            if (transformation.startsWith("AES/ECB/")) {
                // ECB does not use IVs
                continue;
            }
            byte[] myIV1 = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv1, 12) : iv1;
            byte[] myIV2 = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv2, 12) : iv2;
            Throwable t = null;
            try {
                byte[] encryptedData = AESUtils.encrypt(key, myIV1, dataBytes, transformation);
                byte[] decryptedData = AESUtils.decrypt(key, myIV2, encryptedData, transformation);
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesDiffKeys() throws Exception {
        byte[] key1 = AESUtils.randomKeyAsBytes();
        byte[] key2 = AESUtils.randomKeyAsBytes();
        Assert.assertFalse(Arrays.equals(key1, key2));
        byte[] iv = AESUtils.randomIVAsBytes();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            byte[] myIV = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv, 12) : iv;
            Throwable t = null;
            try {
                byte[] encryptedData = AESUtils.encrypt(key1, myIV, dataBytes, transformation);
                byte[] decryptedData = AESUtils.decrypt(key2, myIV, encryptedData, transformation);
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testEncryptDecryptKeyBytesSecure() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytesSecure();
        byte[] iv = AESUtils.randomIVAsBytesSecure();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            byte[] myIv = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv, 12) : iv;
            byte[] encryptedData = AESUtils.encrypt(key, myIv, dataBytes, transformation);
            byte[] decryptedData = AESUtils.decrypt(key, myIv, encryptedData, transformation);
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertArrayEquals(dataBytes, actualDecryptedData);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeySecureDiffTransformationns() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytesSecure();
        byte[] iv = AESUtils.randomIVAsBytesSecure();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String tx1 : TRANSFORMATIONS) {
            for (String tx2 : TRANSFORMATIONS) {
                String[] tokens1 = tx1.split("/");
                String[] tokens2 = tx2.split("/");
                if (StringUtils.startsWith(tokens1[1], tokens2[1])
                        || StringUtils.startsWith(tokens2[1], tokens1[1])) {
                    continue;
                }
                Throwable t = null;
                try {
                    byte[] encryptedData = AESUtils.encrypt(key, iv, dataBytes, tx1);
                    byte[] decryptedData = AESUtils.decrypt(key, iv, encryptedData, tx2);
                    byte[] actualDecryptedData = tx2.endsWith("/ECB/NoPadding")
                            || tx2.endsWith("/CBC/NoPadding") || tx2.endsWith("/PCBC/NoPadding")
                                    ? Arrays.copyOf(decryptedData, dataBytes.length)
                                    : decryptedData;
                    Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                    t = new BadPaddingException();
                } catch (Exception e) {
                    t = e;
                }
                Assert.assertTrue(
                        t instanceof BadPaddingException || t instanceof IllegalBlockSizeException);
            }
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesSecureDiffIVs() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytesSecure();
        byte[] iv1 = AESUtils.randomIVAsBytesSecure();
        byte[] iv2 = AESUtils.randomIVAsBytesSecure();
        Assert.assertFalse(Arrays.equals(iv1, iv2));
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            if (transformation.startsWith("AES/ECB/")) {
                // ECB does not use IVs
                continue;
            }
            byte[] myIV1 = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv1, 12) : iv1;
            byte[] myIV2 = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv2, 12) : iv2;
            Throwable t = null;
            try {
                byte[] encryptedData = AESUtils.encrypt(key, myIV1, dataBytes, transformation);
                byte[] decryptedData = AESUtils.decrypt(key, myIV2, encryptedData, transformation);
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptKeyBytesSecureDiffKeys() throws Exception {
        byte[] key1 = AESUtils.randomKeyAsBytesSecure();
        byte[] key2 = AESUtils.randomKeyAsBytesSecure();
        Assert.assertFalse(Arrays.equals(key1, key2));
        byte[] iv = AESUtils.randomIVAsBytesSecure();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            byte[] myIV = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv, 12) : iv;
            Throwable t = null;
            try {
                byte[] encryptedData = AESUtils.encrypt(key1, myIV, dataBytes, transformation);
                byte[] decryptedData = AESUtils.decrypt(key2, myIV, encryptedData, transformation);
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    /*--------------------------------------------------------------------------------*/

    @org.junit.Test
    public void testEncryptDecryptStreamKeyString() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            String myIv = transformation.startsWith("AES/GCM/") ? iv.substring(0, 12) : iv;
            byte[] encryptedData, decryptedData;
            {
                ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                AESUtils.encrypt(key, myIv, transformation, new ByteArrayInputStream(dataBytes),
                        _encrypted);
                encryptedData = _encrypted.toByteArray();
                ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                AESUtils.decrypt(key, myIv, transformation, new ByteArrayInputStream(encryptedData),
                        _decrypted);
                decryptedData = _decrypted.toByteArray();
            }
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertArrayEquals(dataBytes, actualDecryptedData);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyStringDiffTransformationns() throws Exception {
        String key = AESUtils.randomKey();
        String iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String tx1 : TRANSFORMATIONS) {
            for (String tx2 : TRANSFORMATIONS) {
                String[] tokens1 = tx1.split("/");
                String[] tokens2 = tx2.split("/");
                if (StringUtils.startsWith(tokens1[1], tokens2[1])
                        || StringUtils.startsWith(tokens2[1], tokens1[1])) {
                    continue;
                }
                Throwable t = null;
                try {
                    byte[] encryptedData, decryptedData;
                    {
                        ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                        AESUtils.encrypt(key, iv, tx1, new ByteArrayInputStream(dataBytes),
                                _encrypted);
                        encryptedData = _encrypted.toByteArray();
                        ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                        AESUtils.decrypt(key, iv, tx2, new ByteArrayInputStream(encryptedData),
                                _decrypted);
                        decryptedData = _decrypted.toByteArray();
                    }
                    byte[] actualDecryptedData = tx2.endsWith("/ECB/NoPadding")
                            || tx2.endsWith("/CBC/NoPadding") || tx2.endsWith("/PBC/NoPadding")
                                    ? Arrays.copyOf(decryptedData, dataBytes.length)
                                    : decryptedData;
                    Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                    t = new BadPaddingException();
                } catch (Exception e) {
                    t = e instanceof CipherException ? e.getCause() : e;
                }
                Assert.assertTrue(
                        t instanceof BadPaddingException || t instanceof IllegalBlockSizeException);
            }
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyStringDiffIVs() throws Exception {
        String key = AESUtils.randomKey();
        String iv1 = AESUtils.randomIV();
        String iv2 = AESUtils.randomIV();
        Assert.assertNotEquals(iv1, iv2);
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            if (transformation.startsWith("AES/ECB/")) {
                // ECB does not use IVs
                continue;
            }
            String myIV1 = transformation.startsWith("AES/GCM/") ? iv1.substring(0, 12) : iv1;
            String myIV2 = transformation.startsWith("AES/GCM/") ? iv2.substring(0, 12) : iv2;
            Throwable t = null;
            try {
                byte[] encryptedData, decryptedData;
                {
                    ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                    AESUtils.encrypt(key, myIV1, transformation,
                            new ByteArrayInputStream(dataBytes), _encrypted);
                    encryptedData = _encrypted.toByteArray();
                    ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                    AESUtils.decrypt(key, myIV2, transformation,
                            new ByteArrayInputStream(encryptedData), _decrypted);
                    decryptedData = _decrypted.toByteArray();
                }
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e instanceof CipherException ? e.getCause() : e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyStringDiffKeys() throws Exception {
        String key1 = AESUtils.randomKey();
        String key2 = AESUtils.randomKey();
        Assert.assertNotEquals(key1, key2);
        String iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            String myIV = transformation.startsWith("AES/GCM/") ? iv.substring(0, 12) : iv;
            Throwable t = null;
            try {
                byte[] encryptedData, decryptedData;
                {
                    ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                    AESUtils.encrypt(key1, myIV, transformation,
                            new ByteArrayInputStream(dataBytes), _encrypted);
                    encryptedData = _encrypted.toByteArray();
                    ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                    AESUtils.decrypt(key2, myIV, transformation,
                            new ByteArrayInputStream(encryptedData), _decrypted);
                    decryptedData = _decrypted.toByteArray();
                }
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e instanceof CipherException ? e.getCause() : e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }
    /*--------------------------------------------------------------------------------*/

    @org.junit.Test
    public void testEncryptDecryptStreamKeyBytes() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            byte[] myIv = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv, 12) : iv;
            byte[] encryptedData, decryptedData;
            {
                ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                AESUtils.encrypt(key, myIv, transformation, new ByteArrayInputStream(dataBytes),
                        _encrypted);
                encryptedData = _encrypted.toByteArray();
                ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                AESUtils.decrypt(key, myIv, transformation, new ByteArrayInputStream(encryptedData),
                        _decrypted);
                decryptedData = _decrypted.toByteArray();
            }
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertArrayEquals(dataBytes, actualDecryptedData);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyBytesDiffTransformationns() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String tx1 : TRANSFORMATIONS) {
            for (String tx2 : TRANSFORMATIONS) {
                String[] tokens1 = tx1.split("/");
                String[] tokens2 = tx2.split("/");
                if (StringUtils.startsWith(tokens1[1], tokens2[1])
                        || StringUtils.startsWith(tokens2[1], tokens1[1])) {
                    continue;
                }
                Throwable t = null;
                try {
                    byte[] encryptedData, decryptedData;
                    {
                        ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                        AESUtils.encrypt(key, iv, tx1, new ByteArrayInputStream(dataBytes),
                                _encrypted);
                        encryptedData = _encrypted.toByteArray();
                        ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                        AESUtils.decrypt(key, iv, tx2, new ByteArrayInputStream(encryptedData),
                                _decrypted);
                        decryptedData = _decrypted.toByteArray();
                    }
                    byte[] actualDecryptedData = tx2.endsWith("/ECB/NoPadding")
                            || tx2.endsWith("/CBC/NoPadding") || tx2.endsWith("/PCBC/NoPadding")
                                    ? Arrays.copyOf(decryptedData, dataBytes.length)
                                    : decryptedData;
                    Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                    t = new BadPaddingException();
                } catch (Exception e) {
                    t = e instanceof CipherException ? e.getCause() : e;
                }
                Assert.assertTrue(
                        t instanceof BadPaddingException || t instanceof IllegalBlockSizeException);
            }
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyBytesDiffIVs() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv1 = AESUtils.randomIVAsBytes();
        byte[] iv2 = AESUtils.randomIVAsBytes();
        Assert.assertFalse(Arrays.equals(iv1, iv2));
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            if (transformation.startsWith("AES/ECB/")) {
                // ECB does not use IVs
                continue;
            }
            byte[] myIV1 = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv1, 12) : iv1;
            byte[] myIV2 = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv2, 12) : iv2;
            Throwable t = null;
            try {
                byte[] encryptedData, decryptedData;
                {
                    ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                    AESUtils.encrypt(key, myIV1, transformation,
                            new ByteArrayInputStream(dataBytes), _encrypted);
                    encryptedData = _encrypted.toByteArray();
                    ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                    AESUtils.decrypt(key, myIV2, transformation,
                            new ByteArrayInputStream(encryptedData), _decrypted);
                    decryptedData = _decrypted.toByteArray();
                }
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e instanceof CipherException ? e.getCause() : e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyBytesDiffKeys() throws Exception {
        byte[] key1 = AESUtils.randomKeyAsBytes();
        byte[] key2 = AESUtils.randomKeyAsBytes();
        Assert.assertFalse(Arrays.equals(key1, key2));
        byte[] iv = AESUtils.randomIVAsBytes();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            byte[] myIV = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv, 12) : iv;
            Throwable t = null;
            try {
                byte[] encryptedData, decryptedData;
                {
                    ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                    AESUtils.encrypt(key1, myIV, transformation,
                            new ByteArrayInputStream(dataBytes), _encrypted);
                    encryptedData = _encrypted.toByteArray();
                    ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                    AESUtils.decrypt(key2, myIV, transformation,
                            new ByteArrayInputStream(encryptedData), _decrypted);
                    decryptedData = _decrypted.toByteArray();
                }
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e instanceof CipherException ? e.getCause() : e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }
    /*--------------------------------------------------------------------------------*/

    @org.junit.Test
    public void testEncryptDecryptStreamKeyBytesSecure() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytesSecure();
        byte[] iv = AESUtils.randomIVAsBytesSecure();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            byte[] myIv = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv, 12) : iv;
            byte[] encryptedData, decryptedData;
            {
                ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                AESUtils.encrypt(key, myIv, transformation, new ByteArrayInputStream(dataBytes),
                        _encrypted);
                encryptedData = _encrypted.toByteArray();
                ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                AESUtils.decrypt(key, myIv, transformation, new ByteArrayInputStream(encryptedData),
                        _decrypted);
                decryptedData = _decrypted.toByteArray();
            }
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertArrayEquals(dataBytes, actualDecryptedData);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyBytesSecureDiffTransformationns() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytesSecure();
        byte[] iv = AESUtils.randomIVAsBytesSecure();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String tx1 : TRANSFORMATIONS) {
            for (String tx2 : TRANSFORMATIONS) {
                String[] tokens1 = tx1.split("/");
                String[] tokens2 = tx2.split("/");
                if (StringUtils.startsWith(tokens1[1], tokens2[1])
                        || StringUtils.startsWith(tokens2[1], tokens1[1])) {
                    continue;
                }
                Throwable t = null;
                try {
                    byte[] encryptedData, decryptedData;
                    {
                        ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                        AESUtils.encrypt(key, iv, tx1, new ByteArrayInputStream(dataBytes),
                                _encrypted);
                        encryptedData = _encrypted.toByteArray();
                        ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                        AESUtils.decrypt(key, iv, tx2, new ByteArrayInputStream(encryptedData),
                                _decrypted);
                        decryptedData = _decrypted.toByteArray();
                    }
                    byte[] actualDecryptedData = tx2.endsWith("/ECB/NoPadding")
                            || tx2.endsWith("/CBC/NoPadding") || tx2.endsWith("/PCBC/NoPadding")
                                    ? Arrays.copyOf(decryptedData, dataBytes.length)
                                    : decryptedData;
                    Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                    t = new BadPaddingException();
                } catch (Exception e) {
                    t = e instanceof CipherException ? e.getCause() : e;
                }
                Assert.assertTrue(
                        t instanceof BadPaddingException || t instanceof IllegalBlockSizeException);
            }
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyBytesSecureDiffIVs() throws Exception {
        byte[] key = AESUtils.randomKeyAsBytesSecure();
        byte[] iv1 = AESUtils.randomIVAsBytesSecure();
        byte[] iv2 = AESUtils.randomIVAsBytesSecure();
        Assert.assertFalse(Arrays.equals(iv1, iv2));
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            if (transformation.startsWith("AES/ECB/")) {
                // ECB does not use IVs
                continue;
            }
            byte[] myIV1 = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv1, 12) : iv1;
            byte[] myIV2 = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv2, 12) : iv2;
            Assert.assertFalse(Arrays.equals(myIV1, myIV2));
            Throwable t = null;
            try {
                byte[] encryptedData, decryptedData;
                {
                    ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                    AESUtils.encrypt(key, myIV1, transformation,
                            new ByteArrayInputStream(dataBytes), _encrypted);
                    encryptedData = _encrypted.toByteArray();
                    ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                    AESUtils.decrypt(key, myIV2, transformation,
                            new ByteArrayInputStream(encryptedData), _decrypted);
                    decryptedData = _decrypted.toByteArray();
                }
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e instanceof CipherException ? e.getCause() : e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptStreamKeyBytesSecureDiffKeys() throws Exception {
        byte[] key1 = AESUtils.randomKeyAsBytesSecure();
        byte[] key2 = AESUtils.randomKeyAsBytesSecure();
        Assert.assertFalse(Arrays.equals(key1, key2));
        byte[] iv = AESUtils.randomIVAsBytesSecure();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            byte[] myIV = transformation.startsWith("AES/GCM/") ? Arrays.copyOf(iv, 12) : iv;
            Throwable t = null;
            try {
                byte[] encryptedData, decryptedData;
                {
                    ByteArrayOutputStream _encrypted = new ByteArrayOutputStream();
                    AESUtils.encrypt(key1, myIV, transformation,
                            new ByteArrayInputStream(dataBytes), _encrypted);
                    encryptedData = _encrypted.toByteArray();
                    ByteArrayOutputStream _decrypted = new ByteArrayOutputStream();
                    AESUtils.decrypt(key2, myIV, transformation,
                            new ByteArrayInputStream(encryptedData), _decrypted);
                    decryptedData = _decrypted.toByteArray();
                }
                byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                        || transformation.endsWith("/CBC/NoPadding")
                        || transformation.endsWith("/PCBC/NoPadding")
                                ? Arrays.copyOf(decryptedData, dataBytes.length)
                                : decryptedData;
                Assert.assertFalse(Arrays.equals(dataBytes, actualDecryptedData));
                t = new BadPaddingException();
            } catch (Exception e) {
                t = e instanceof CipherException ? e.getCause() : e;
            }
            Assert.assertTrue(t instanceof BadPaddingException);
        }
    }
}

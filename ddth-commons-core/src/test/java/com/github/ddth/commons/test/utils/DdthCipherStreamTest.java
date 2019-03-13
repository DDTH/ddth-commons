package com.github.ddth.commons.test.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import com.github.ddth.commons.utils.AESUtils;
import com.github.ddth.commons.utils.cipher.DdthCipherInputStream;
import com.github.ddth.commons.utils.cipher.DdthCipherOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DdthCipherStreamTest extends TestCase {

    public DdthCipherStreamTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DdthCipherStreamTest.class);
    }

    /*----------------------------------------------------------------------*/
    static final List<String> TRANSFORMATIONS = new LinkedList<>();
    static {
        for (String mode : AESUtils.CIPHER_MODES) {
            for (String padding : AESUtils.CIPHER_PADDINGS) {
                if ((mode.equals("CTR") || mode.equals("CTS") || mode.equals("GCM"))
                        && !padding.equals("NoPadding")) {
                    // CTR,CTS,GCM modes must be used with NoPadding
                    continue;
                }
                String transformation = AESUtils.CIPHER_ALGORITHM + "/" + mode + "/" + padding;
                TRANSFORMATIONS.add(transformation);
            }
        }
    }
    static byte[][] PADDING = { { 0 }, { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 }, { 7 }, { 8 },
            { 9 }, { 10 }, { 11 }, { 12 }, { 13 }, { 14 }, { 15 }, {} };
    static {
        for (int i = 0; i < 16; i++) {
            PADDING[i] = Arrays.copyOf(new byte[0], i);
        }
    }

    static void encryptUsingOutputStream(byte[] key, byte[] iv, String transformation,
            InputStream input, OutputStream output) throws Exception {
        Cipher cipher = AESUtils.createCipher(Cipher.ENCRYPT_MODE, key, iv, transformation);
        try (DdthCipherOutputStream cos = new DdthCipherOutputStream(output, cipher, true)) {
            long count = IOUtils.copy(input, cos, 1024);
            if (transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")) {
                cos.write(PADDING[(int) (16 - count % 16)]);
            }
        }
    }

    static void decryptUsingOutputStream(byte[] key, byte[] iv, String transformation,
            InputStream input, OutputStream output) throws Exception {
        Cipher cipher = AESUtils.createCipher(Cipher.DECRYPT_MODE, key, iv, transformation);
        try (DdthCipherOutputStream cos = new DdthCipherOutputStream(output, cipher, true)) {
            IOUtils.copy(input, cos, 1024);
        }
    }

    static void encryptUsingInputStream(byte[] key, byte[] iv, String transformation,
            InputStream input, OutputStream output) throws Exception {
        Cipher cipher = AESUtils.createCipher(Cipher.ENCRYPT_MODE, key, iv, transformation);
        try (DdthCipherInputStream cis = new DdthCipherInputStream(input, cipher, true)) {
            long count = IOUtils.copy(cis, output, 1024);
            if (transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")) {
                output.write(PADDING[(int) (16 - count % 16)]);
            }
        }
    }

    static void decryptUsingInputStream(byte[] key, byte[] iv, String transformation,
            InputStream input, OutputStream output) throws Exception {
        Cipher cipher = AESUtils.createCipher(Cipher.DECRYPT_MODE, key, iv, transformation);
        try (DdthCipherInputStream cis = new DdthCipherInputStream(input, cipher, true)) {
            IOUtils.copy(cis, output, 1024);
        }
    }

    @org.junit.Test
    public void testOutputStreamEncrypt() throws Exception {
        String key = AESUtils.randomKey();
        String _iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            String myIv = transformation.startsWith("AES/GCM/") ? _iv.substring(0, 12) : _iv;
            InputStream input = new ByteArrayInputStream(dataBytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            encryptUsingOutputStream(key.getBytes(), myIv.getBytes(), transformation, input,
                    output);
            byte[] encryptedData = output.toByteArray();
            byte[] decryptedData = AESUtils.decrypt(key.getBytes(), myIv.getBytes(), encryptedData,
                    transformation);
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertTrue("Test [" + transformation + "] failed",
                    Arrays.equals(dataBytes, actualDecryptedData));
        }
    }

    @org.junit.Test
    public void testOutputStreamDecrypt() throws Exception {
        String key = AESUtils.randomKey();
        String _iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            String myIv = transformation.startsWith("AES/GCM/") ? _iv.substring(0, 12) : _iv;
            byte[] encryptedData = AESUtils.encrypt(key.getBytes(), myIv.getBytes(), dataBytes,
                    transformation);
            InputStream input = new ByteArrayInputStream(encryptedData);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            decryptUsingOutputStream(key.getBytes(), myIv.getBytes(), transformation, input,
                    output);
            byte[] decryptedData = output.toByteArray();
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertTrue("Test [" + transformation + "] failed",
                    Arrays.equals(dataBytes, actualDecryptedData));
        }
    }

    @org.junit.Test
    public void testInputStreamEncrypt() throws Exception {
        String key = AESUtils.randomKey();
        String _iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            String myIv = transformation.startsWith("AES/GCM/") ? _iv.substring(0, 12) : _iv;
            if (transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")) {
                byte[] padding = PADDING[(int) (16 - dataBytes.length % 16)];
                if (padding.length > 0) {
                    dataBytes = Arrays.copyOf(dataBytes, dataBytes.length + padding.length);
                }
            }
            InputStream input = new ByteArrayInputStream(dataBytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            encryptUsingInputStream(key.getBytes(), myIv.getBytes(), transformation, input, output);
            byte[] encryptedData = output.toByteArray();
            byte[] decryptedData = AESUtils.decrypt(key.getBytes(), myIv.getBytes(), encryptedData,
                    transformation);
            Assert.assertTrue("Test [" + transformation + "] failed",
                    Arrays.equals(dataBytes, decryptedData));
        }
    }

    @org.junit.Test
    public void testInputStreamDecrypt() throws Exception {
        String key = AESUtils.randomKey();
        String _iv = AESUtils.randomIV();
        String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        for (String transformation : TRANSFORMATIONS) {
            String myIv = transformation.startsWith("AES/GCM/") ? _iv.substring(0, 12) : _iv;
            byte[] encryptedData = AESUtils.encrypt(key.getBytes(), myIv.getBytes(), dataBytes,
                    transformation);
            InputStream input = new ByteArrayInputStream(encryptedData);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            decryptUsingInputStream(key.getBytes(), myIv.getBytes(), transformation, input, output);
            byte[] decryptedData = output.toByteArray();
            byte[] actualDecryptedData = transformation.endsWith("/ECB/NoPadding")
                    || transformation.endsWith("/CBC/NoPadding")
                    || transformation.endsWith("/PCBC/NoPadding")
                            ? Arrays.copyOf(decryptedData, dataBytes.length)
                            : decryptedData;
            Assert.assertTrue("Test [" + transformation + "] failed",
                    Arrays.equals(dataBytes, actualDecryptedData));
        }
    }
}

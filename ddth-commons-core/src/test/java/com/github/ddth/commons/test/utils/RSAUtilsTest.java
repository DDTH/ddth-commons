package com.github.ddth.commons.test.utils;

import static org.junit.Assert.assertNotEquals;

import java.security.KeyPair;

import javax.crypto.BadPaddingException;

import org.apache.commons.lang3.StringUtils;

import com.github.ddth.commons.utils.RSAUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RSAUtilsTest extends TestCase {

    public RSAUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(RSAUtilsTest.class);
    }

    @org.junit.Test
    public void testEncryptDecrypt512() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(512);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData = RSAUtils.encryptWithPublicKey(publicKey,
                data.getBytes(RSAUtils.UTF8));
        final byte[] decryptedData = RSAUtils.decryptWithPrivateKey(privateKey, encryptedData);
        assertEquals(data, new String(decryptedData, RSAUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecrypt1024() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(1024);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData = RSAUtils.encryptWithPublicKey(publicKey,
                data.getBytes(RSAUtils.UTF8));
        final byte[] decryptedData = RSAUtils.decryptWithPrivateKey(privateKey, encryptedData);
        assertEquals(data, new String(decryptedData, RSAUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecrypt2048() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(2048);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] encryptedData = RSAUtils.encryptWithPublicKey(publicKey,
                data.getBytes(RSAUtils.UTF8));
        final byte[] decryptedData = RSAUtils.decryptWithPrivateKey(privateKey, encryptedData);
        assertEquals(data, new String(decryptedData, RSAUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptDiffKeys() throws Exception {
        try {
            KeyPair keypair1 = RSAUtils.generateKeys(1024);
            KeyPair keypair2 = RSAUtils.generateKeys(1024);
            String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair1);
            String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair2);
            final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
            final byte[] encryptedData = RSAUtils.encryptWithPublicKey(publicKey,
                    data.getBytes(RSAUtils.UTF8));
            final byte[] decryptedData = RSAUtils.decryptWithPrivateKey(privateKey, encryptedData);
            assertNotEquals(data, new String(decryptedData, RSAUtils.UTF8));
        } catch (BadPaddingException e) {
        }
    }

    @org.junit.Test
    public void testEncryptDecryptLongData512() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(512);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 1024);
        final byte[] encryptedData = RSAUtils.encryptWithPublicKey(publicKey,
                data.getBytes(RSAUtils.UTF8));
        final byte[] decryptedData = RSAUtils.decryptWithPrivateKey(privateKey, encryptedData);
        assertEquals(data, new String(decryptedData, RSAUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptLongData1024() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(1024);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 1024);
        final byte[] encryptedData = RSAUtils.encryptWithPublicKey(publicKey,
                data.getBytes(RSAUtils.UTF8));
        final byte[] decryptedData = RSAUtils.decryptWithPrivateKey(privateKey, encryptedData);
        assertEquals(data, new String(decryptedData, RSAUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptLongData2048() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(2048);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 1024);
        final byte[] encryptedData = RSAUtils.encryptWithPublicKey(publicKey,
                data.getBytes(RSAUtils.UTF8));
        final byte[] decryptedData = RSAUtils.decryptWithPrivateKey(privateKey, encryptedData);
        assertEquals(data, new String(decryptedData, RSAUtils.UTF8));
    }

    @org.junit.Test
    public void testEncryptDecryptLongDataDiffKeys() throws Exception {
        try {
            KeyPair keypair1 = RSAUtils.generateKeys(1024);
            KeyPair keypair2 = RSAUtils.generateKeys(1024);
            String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair1);
            String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair2);
            final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/",
                    1024);
            final byte[] encryptedData = RSAUtils.encryptWithPublicKey(publicKey,
                    data.getBytes(RSAUtils.UTF8));
            final byte[] decryptedData = RSAUtils.decryptWithPrivateKey(privateKey, encryptedData);
            assertNotEquals(data, new String(decryptedData, RSAUtils.UTF8));
        } catch (BadPaddingException e) {
        }
    }

    @org.junit.Test
    public void testSignVerify512() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(512);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] msg = data.getBytes(RSAUtils.UTF8);
        final byte[] signature = RSAUtils.signMessageWithPrivateKey(privateKey, msg);
        assertTrue(RSAUtils.verifySignatureWithPublicKey(publicKey, msg, signature));
    }

    @org.junit.Test
    public void testSignVerify1024() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(1024);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] msg = data.getBytes(RSAUtils.UTF8);
        final byte[] signature = RSAUtils.signMessageWithPrivateKey(privateKey, msg);
        assertTrue(RSAUtils.verifySignatureWithPublicKey(publicKey, msg, signature));
    }

    @org.junit.Test
    public void testSignVerify2048() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(2048);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] msg = data.getBytes(RSAUtils.UTF8);
        final byte[] signature = RSAUtils.signMessageWithPrivateKey(privateKey, msg);
        assertTrue(RSAUtils.verifySignatureWithPublicKey(publicKey, msg, signature));
    }

    @org.junit.Test
    public void testSignVerifyDiffKeys() throws Exception {
        KeyPair keypair1 = RSAUtils.generateKeys(1024);
        KeyPair keypair2 = RSAUtils.generateKeys(1024);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair1);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair2);
        final String data = "Nguyễn Bá Thành - https://github.com/DDTH/";
        final byte[] msg = data.getBytes(RSAUtils.UTF8);
        final byte[] signature = RSAUtils.signMessageWithPrivateKey(privateKey, msg);
        assertFalse(RSAUtils.verifySignatureWithPublicKey(publicKey, msg, signature));
    }

    @org.junit.Test
    public void testSignVerifyLongData512() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(512);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 1024);
        final byte[] msg = data.getBytes(RSAUtils.UTF8);
        final byte[] signature = RSAUtils.signMessageWithPrivateKey(privateKey, msg);
        assertTrue(RSAUtils.verifySignatureWithPublicKey(publicKey, msg, signature));
    }

    @org.junit.Test
    public void testSignVerifyLongData1024() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(1024);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 1024);
        final byte[] msg = data.getBytes(RSAUtils.UTF8);
        final byte[] signature = RSAUtils.signMessageWithPrivateKey(privateKey, msg);
        assertTrue(RSAUtils.verifySignatureWithPublicKey(publicKey, msg, signature));
    }

    @org.junit.Test
    public void testSignVerifyLongData2048() throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(2048);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair);
        final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 1024);
        final byte[] msg = data.getBytes(RSAUtils.UTF8);
        final byte[] signature = RSAUtils.signMessageWithPrivateKey(privateKey, msg);
        assertTrue(RSAUtils.verifySignatureWithPublicKey(publicKey, msg, signature));
    }

    @org.junit.Test
    public void testSignVerifyLongDataDiffKeys() throws Exception {
        KeyPair keypair1 = RSAUtils.generateKeys(1024);
        KeyPair keypair2 = RSAUtils.generateKeys(1024);
        String publicKey = RSAUtils.extractPublicKeyAsBase64(keypair1);
        String privateKey = RSAUtils.extractPrivateKeyAsBase64(keypair2);
        final String data = StringUtils.repeat("Nguyễn Bá Thành - https://github.com/DDTH/", 1024);
        final byte[] msg = data.getBytes(RSAUtils.UTF8);
        final byte[] signature = RSAUtils.signMessageWithPrivateKey(privateKey, msg);
        assertFalse(RSAUtils.verifySignatureWithPublicKey(publicKey, msg, signature));
    }
}

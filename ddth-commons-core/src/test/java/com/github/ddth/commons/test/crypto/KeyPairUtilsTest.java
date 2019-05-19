package com.github.ddth.commons.test.crypto;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Assert;

import com.github.ddth.commons.crypto.KeyPairUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class KeyPairUtilsTest extends TestCase {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public KeyPairUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(KeyPairUtilsTest.class);
    }

    private void testGenerateKeys(int numBits, String algorithm) throws Exception {
        if (("DSA".equalsIgnoreCase(algorithm) || "ElGamal".equalsIgnoreCase(algorithm))
                && numBits > 2048) {
            return;
        }

        KeyPair keyPair = KeyPairUtils.generateKeyPair(numBits, algorithm, null);
        Assert.assertNotNull(keyPair);
        PublicKey pubKey = keyPair.getPublic();
        Assert.assertTrue(algorithm.equalsIgnoreCase(pubKey.getAlgorithm()));
        PrivateKey privKey = keyPair.getPrivate();
        Assert.assertTrue(algorithm.equalsIgnoreCase(privKey.getAlgorithm()));
    }

    final static String[] ALGORITHMS = { "RSA", "DSA", "ElGamal" };

    @org.junit.Test
    public void testGenerateKeys512() throws Exception {
        for (String algo : ALGORITHMS) {
            testGenerateKeys(512, algo);
        }
    }

    @org.junit.Test
    public void testGenerateKeys1024() throws Exception {
        for (String algo : ALGORITHMS) {
            testGenerateKeys(1024, algo);
        }
    }

    @org.junit.Test
    public void testGenerateKeys2048() throws Exception {
        for (String algo : ALGORITHMS) {
            testGenerateKeys(2048, algo);
        }
    }

    @org.junit.Test
    public void testGenerateKeys3072() throws Exception {
        for (String algo : ALGORITHMS) {
            testGenerateKeys(3072, algo);
        }
    }

    @org.junit.Test
    public void testGenerateKeys4096() throws Exception {
        for (String algo : ALGORITHMS) {
            testGenerateKeys(4096, algo);
        }
    }

    // /*----------------------------------------------------------------------*/
    // static final String[] TRANSFORMATIONS = new String[RSAUtils.CIPHER_PADDINGS.length];
    // final static int[] PADDINGS = RSAUtils.CIPHER_PADDINGS_SIZE;
    //
    // static {
    // int index = 0;
    // for (String padding : RSAUtils.CIPHER_PADDINGS) {
    // String transformation = RSAUtils.CIPHER_ALGORITHM + "/" + RSAUtils.CIPHER_MODE + "/" +
    // padding;
    // TRANSFORMATIONS[index] = transformation;
    // PADDINGS[index] = RSAUtils.CIPHER_PADDINGS_SIZE[index];
    // index++;
    // }
    // }
    //
    // private void testEncryptDecrypt(int numBits, String transformation, int paddingSize) throws
    // Exception {
    // if (numBits / 8 < paddingSize) {
    // return;
    // }
    // KeyPair keyPair = RSAUtils.generateKeys(numBits);
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // String data = "Nguyễn Bá Thành";
    // byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
    // byte[] encryptedData = RSAUtils.encrypt(publicKey, dataBytes, transformation, paddingSize);
    // byte[] decryptedData = RSAUtils.decrypt(privateKey, encryptedData, transformation);
    // Assert.assertTrue(Arrays.equals(dataBytes, decryptedData));
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecrypt512() throws Exception {
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // testEncryptDecrypt(512, TRANSFORMATIONS[i], PADDINGS[i]);
    // }
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecrypt1024() throws Exception {
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // testEncryptDecrypt(1024, TRANSFORMATIONS[i], PADDINGS[i]);
    // }
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecrypt2048() throws Exception {
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // testEncryptDecrypt(2048, TRANSFORMATIONS[i], PADDINGS[i]);
    // }
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecrypt4096() throws Exception {
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // testEncryptDecrypt(4096, TRANSFORMATIONS[i], PADDINGS[i]);
    // }
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecryptDiffKeys() throws Exception {
    // int keySize = 1024;
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // if (keySize / 8 < PADDINGS[i]) {
    // continue;
    // }
    // try {
    // KeyPair keyPair1 = RSAUtils.generateKeys(keySize);
    // KeyPair keyPair2 = RSAUtils.generateKeys(keySize);
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair1.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair2.getPrivate();
    // String data = "Nguyễn Bá Thành";
    // byte[] dataBytes = data.getBytes(StandardCharsets.UTF_16);
    // byte[] encryptedData = RSAUtils.encrypt(publicKey, dataBytes, TRANSFORMATIONS[i],
    // PADDINGS[i]);
    // byte[] decryptedData = RSAUtils.decrypt(privateKey, encryptedData, TRANSFORMATIONS[i]);
    // Assert.assertFalse(Arrays.equals(dataBytes, decryptedData));
    // } catch (BadPaddingException e) {
    // }
    // }
    // }
    //
    // /*----------------------------------------------------------------------*/
    // private void testEncryptDecryptLongData(int numBits, String transformation, int paddingSize)
    // throws Exception {
    // if (numBits / 8 < paddingSize) {
    // return;
    // }
    // KeyPair keyPair = RSAUtils.generateKeys(numBits);
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // String data = StringUtils.repeat("Nguyễn Bá Thành", " - ", 1024);
    // byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
    // byte[] encryptedData = RSAUtils.encrypt(publicKey, dataBytes, transformation, paddingSize);
    // byte[] decryptedData = RSAUtils.decrypt(privateKey, encryptedData, transformation);
    // Assert.assertTrue(Arrays.equals(dataBytes, decryptedData));
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecryptLongData512() throws Exception {
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // testEncryptDecryptLongData(512, TRANSFORMATIONS[i], PADDINGS[i]);
    // }
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecryptLongData1024() throws Exception {
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // testEncryptDecryptLongData(1024, TRANSFORMATIONS[i], PADDINGS[i]);
    // }
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecryptLongData2048() throws Exception {
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // testEncryptDecryptLongData(2048, TRANSFORMATIONS[i], PADDINGS[i]);
    // }
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecryptLongData4096() throws Exception {
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // testEncryptDecryptLongData(4096, TRANSFORMATIONS[i], PADDINGS[i]);
    // }
    // }
    //
    // @org.junit.Test
    // public void testEncryptDecryptLongDataDiffKeys() throws Exception {
    // int keySize = 1024;
    // for (int i = 0; i < TRANSFORMATIONS.length; i++) {
    // if (keySize / 8 < PADDINGS[i]) {
    // continue;
    // }
    // try {
    // KeyPair keyPair1 = RSAUtils.generateKeys(keySize);
    // KeyPair keyPair2 = RSAUtils.generateKeys(keySize);
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair1.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair2.getPrivate();
    // String data = StringUtils.repeat("Nguyễn Bá Thành", " - ", 1024);
    // byte[] dataBytes = data.getBytes(StandardCharsets.UTF_16);
    // byte[] encryptedData = RSAUtils.encrypt(publicKey, dataBytes, TRANSFORMATIONS[i],
    // PADDINGS[i]);
    // byte[] decryptedData = RSAUtils.decrypt(privateKey, encryptedData, TRANSFORMATIONS[i]);
    // Assert.assertFalse(Arrays.equals(dataBytes, decryptedData));
    // } catch (BadPaddingException e) {
    // }
    // }
    // }
    //
    // /*----------------------------------------------------------------------*/
    // private void testSignVerify(int numBits, String algorithm) throws Exception {
    // if (numBits < 1024 && (algorithm.startsWith("SHA384") || algorithm.startsWith("SHA512"))) {
    // return;
    // }
    // KeyPair keyPair = RSAUtils.generateKeys(numBits);
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // String data = "Nguyễn Bá Thành";
    // byte[] msg = data.getBytes(StandardCharsets.UTF_8);
    // byte[] signature = RSAUtils.signMessage(privateKey, msg, algorithm);
    // Assert.assertTrue(RSAUtils.verifySignature(publicKey, msg, signature, algorithm));
    // }
    //
    // @org.junit.Test
    // public void testSignVerify512() throws Exception {
    // for (String algo : RSAUtils.SIGNATURE_ALGORITHMS) {
    // testSignVerify(512, algo);
    // }
    // }
    //
    // @org.junit.Test
    // public void testSignVerify1024() throws Exception {
    // for (String algo : RSAUtils.SIGNATURE_ALGORITHMS) {
    // testSignVerify(1024, algo);
    // }
    // }
    //
    // @org.junit.Test
    // public void testSignVerify2048() throws Exception {
    // for (String algo : RSAUtils.SIGNATURE_ALGORITHMS) {
    // testSignVerify(2048, algo);
    // }
    // }
    //
    // @org.junit.Test
    // public void testSignVerify4096() throws Exception {
    // for (String algo : RSAUtils.SIGNATURE_ALGORITHMS) {
    // testSignVerify(4096, algo);
    // }
    // }
    //
    // @org.junit.Test
    // public void testSignVerifyDiffKeys() throws Exception {
    // KeyPair keyPair1 = RSAUtils.generateKeys(1024);
    // KeyPair keyPair2 = RSAUtils.generateKeys(1024);
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair1.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair2.getPrivate();
    // String data = "Nguyễn Bá Thành";
    // byte[] msg = data.getBytes(StandardCharsets.UTF_8);
    // byte[] signature = RSAUtils.signMessage(privateKey, msg);
    // Assert.assertFalse(RSAUtils.verifySignature(publicKey, msg, signature));
    // }
    //
    // /*----------------------------------------------------------------------*/
    //
    // private void testSignVerifyLongData(int numBits) throws Exception {
    // KeyPair keyPair = RSAUtils.generateKeys(numBits);
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    // String data = StringUtils.repeat("Nguyễn Bá Thành", " - ", 1024);
    // byte[] msg = data.getBytes(StandardCharsets.UTF_8);
    // byte[] signature = RSAUtils.signMessage(privateKey, msg);
    // Assert.assertTrue(RSAUtils.verifySignature(publicKey, msg, signature));
    // }
    //
    // @org.junit.Test
    // public void testSignVerifyLongData512() throws Exception {
    // testSignVerifyLongData(512);
    // }
    //
    // @org.junit.Test
    // public void testSignVerifyLongData1024() throws Exception {
    // testSignVerifyLongData(1024);
    // }
    //
    // @org.junit.Test
    // public void testSignVerifyLongData2048() throws Exception {
    // testSignVerifyLongData(2048);
    // }
    //
    // @org.junit.Test
    // public void testSignVerifyLongData4096() throws Exception {
    // testSignVerifyLongData(4096);
    // }
    //
    // @org.junit.Test
    // public void testSignVerifyLongDataDiffKeys() throws Exception {
    // KeyPair keyPair1 = RSAUtils.generateKeys(1024);
    // KeyPair keyPair2 = RSAUtils.generateKeys(1024);
    // RSAPublicKey publicKey = (RSAPublicKey) keyPair1.getPublic();
    // RSAPrivateKey privateKey = (RSAPrivateKey) keyPair2.getPrivate();
    // String data = StringUtils.repeat("Nguyễn Bá Thành", " - ", 1024);
    // byte[] msg = data.getBytes(StandardCharsets.UTF_8);
    // byte[] signature = RSAUtils.signMessage(privateKey, msg);
    // Assert.assertFalse(RSAUtils.verifySignature(publicKey, msg, signature));
    // }
}

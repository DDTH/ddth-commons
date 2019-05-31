package com.github.ddth.commons.test.crypto;

import java.security.KeyPair;
import java.security.Security;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.junit.Assert;

import com.github.ddth.commons.crypto.KeyPairUtils;
import com.github.ddth.commons.crypto.PGPUtils;
import com.github.ddth.commons.crypto.PGPUtils.CompressionAlgorithm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PGPKeyGenerationTest extends TestCase {

    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public PGPKeyGenerationTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PGPKeyGenerationTest.class);
    }

    private void testGenerateKeys(int numBits, String encAlgo, String signAlgo, String password)
            throws Exception {
        KeyPair masterKeyPair = KeyPairUtils.generateKeyPair(numBits, signAlgo, null);
        KeyPair encryptionSubKeyPair = KeyPairUtils.generateKeyPair(numBits, encAlgo, null);
        PGPKeyRingGenerator keyRingGen = PGPUtils.createPGPKeyRingGenerator(masterKeyPair,
                encryptionSubKeyPair, "id", password);
        Assert.assertNotNull("Cannot create PGP key ring.", keyRingGen);

        List<PGPPublicKey> pubKeys = PGPUtils.extractPublicKeys(keyRingGen.generatePublicKeyRing());
        Assert.assertEquals("Expect one master and one sub-key.", 2, pubKeys.size());
        Assert.assertEquals("Invalid key size.", numBits, pubKeys.get(0).getBitStrength());

        List<PGPSecretKey> secKeys = PGPUtils.extractSecretKeys(keyRingGen.generateSecretKeyRing());
        Assert.assertEquals("Expect one master and one sub-key.", 2, secKeys.size());
    }

    final static String[] ALGORITHMS_ENC = { "RSA", "ElGamal" };
    final static String[] ALGORITHMS_SIGN = { "RSA", "DSA" };

    @org.junit.Test
    public void testGenerateKeys512() throws Exception {
        int keySize = 512;
        for (String signAlgo : ALGORITHMS_SIGN) {
            for (String encAlgo : ALGORITHMS_ENC) {
                testGenerateKeys(keySize, encAlgo, signAlgo, "s3cr3t");
            }
        }
    }

    @org.junit.Test
    public void testGenerateKeys1024() throws Exception {
        int keySize = 1024;
        for (String signAlgo : ALGORITHMS_SIGN) {
            for (String encAlgo : ALGORITHMS_ENC) {
                testGenerateKeys(keySize, encAlgo, signAlgo, "s3cr3t");
            }
        }
    }

    @org.junit.Test
    public void testGenerateKeys2048() throws Exception {
        int keySize = 2048;
        for (String signAlgo : ALGORITHMS_SIGN) {
            for (String encAlgo : ALGORITHMS_ENC) {
                testGenerateKeys(keySize, encAlgo, signAlgo, "s3cr3t");
            }
        }
    }

    /*----------------------------------------------------------------------*/
    @org.junit.Test
    public void testExportLoadPublicKeyRing() throws Exception {
        String password = "s3cr3t";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "id",
                password);
        {
            String exportNonArmor = PGPUtils.exportKeyRing(keyRingGenerator.generatePublicKeyRing(),
                    false);
            PGPPublicKeyRingCollection keyRingCollection = PGPUtils
                    .loadPublicKeyRingCollection(exportNonArmor, false);
            List<PGPPublicKey> pubKeys = PGPUtils.extractPublicKeys(keyRingCollection);
            Assert.assertEquals("Expect one master and one sub-key.", 2, pubKeys.size());
            Assert.assertEquals("Invalid key size.", 1024, pubKeys.get(0).getBitStrength());
        }
        {
            String exportArmor = PGPUtils.exportKeyRing(keyRingGenerator.generatePublicKeyRing(),
                    true);
            PGPPublicKeyRingCollection keyRingCollection = PGPUtils
                    .loadPublicKeyRingCollection(exportArmor, true);
            List<PGPPublicKey> pubKeys = PGPUtils.extractPublicKeys(keyRingCollection);
            Assert.assertEquals("Expect one master and one sub-key.", 2, pubKeys.size());
            Assert.assertEquals("Invalid key size.", 1024, pubKeys.get(0).getBitStrength());
        }
    }

    @org.junit.Test
    public void testExportLoadSecretKeyRing() throws Exception {
        String password = "s3cr3t";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "id",
                password);
        {
            String exportNonArmor = PGPUtils.exportKeyRing(keyRingGenerator.generateSecretKeyRing(),
                    false);
            PGPSecretKeyRingCollection keyRingCollection = PGPUtils
                    .loadSecretKeyRingCollection(exportNonArmor, false);
            List<PGPSecretKey> secKeys = PGPUtils.extractSecretKeys(keyRingCollection);
            Assert.assertEquals("Expect one master and one sub-key.", 2, secKeys.size());
            Assert.assertEquals("Invalid key size.", 1024,
                    secKeys.get(0).getPublicKey().getBitStrength());
        }
        {
            String exportArmor = PGPUtils.exportKeyRing(keyRingGenerator.generateSecretKeyRing(),
                    true);
            PGPSecretKeyRingCollection keyRingCollection = PGPUtils
                    .loadSecretKeyRingCollection(exportArmor, true);
            List<PGPSecretKey> secKeys = PGPUtils.extractSecretKeys(keyRingCollection);
            Assert.assertEquals("Expect one master and one sub-key.", 2, secKeys.size());
            Assert.assertEquals("Invalid key size.", 1024,
                    secKeys.get(0).getPublicKey().getBitStrength());
        }
    }

    @org.junit.Test
    public void testLoadPrivateKey() throws Exception {
        String password = "s3cr3t";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "id",
                password);
        {
            String exportNonArmor = PGPUtils.exportKeyRing(keyRingGenerator.generateSecretKeyRing(),
                    false);
            PGPSecretKeyRingCollection keyRingCollection = PGPUtils
                    .loadSecretKeyRingCollection(exportNonArmor, false);
            List<PGPSecretKey> secKeys = PGPUtils.extractSecretKeys(keyRingCollection);
            Assert.assertEquals("Expect one master and one sub-key.", 2, secKeys.size());
            for (PGPSecretKey secKey : secKeys) {
                PGPPrivateKey privKey = PGPUtils.extractPrivateKey(secKey, password);
                Assert.assertNotNull(privKey);
            }
        }
    }

    @org.junit.Test
    public void testCompressDecompress() throws Exception {
        byte[] data = StringUtils.repeat(this.getClass().getName(), 1024).getBytes();
        CompressionAlgorithm[] algos = { CompressionAlgorithm.ZIP, CompressionAlgorithm.ZLIB,
                CompressionAlgorithm.BZIP2 };
        for (CompressionAlgorithm algo : algos) {
            byte[] compressed = PGPUtils.compress(data, null, algo);
            byte[] decompressed = PGPUtils.decompress(compressed);
            Assert.assertTrue("Compressed size must be less than original data size.",
                    compressed.length < data.length);
            Assert.assertArrayEquals("Decompressed data is not equals to original one.", data,
                    decompressed);
        }
    }

    @org.junit.Test
    public void testSignVerify() throws Exception {
        String password = "s3cr3t";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "id",
                password);
        byte[] message = this.getClass().getName().getBytes();

        PGPSecretKey secKey = PGPUtils
                .extractFirstSecretKey(keyRingGenerator.generateSecretKeyRing(), true, true);
        Assert.assertNotNull("Cannot find signing key from key ring.", secKey);

        {
            byte[] signedMsgNonArmor = PGPUtils.sign(secKey, password, message, null, false);
            Assert.assertNotNull(signedMsgNonArmor);
            Assert.assertTrue(
                    PGPUtils.verify(keyRingGenerator.generatePublicKeyRing(), signedMsgNonArmor));
        }
        {
            byte[] signedMsgArmor = PGPUtils.sign(secKey, password, message, null, true);
            Assert.assertNotNull(signedMsgArmor);
            Assert.assertTrue(
                    PGPUtils.verify(keyRingGenerator.generatePublicKeyRing(), signedMsgArmor));
        }
    }

    @org.junit.Test
    public void testEncryptDecrypt() throws Exception {
        String password = "s3cr3t";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "id",
                password);
        byte[] message = this.getClass().getName().getBytes();

        PGPPublicKey publicKey = PGPUtils
                .extractFirstPublicKey(keyRingGenerator.generatePublicKeyRing(), true, true);
        Assert.assertNotNull("Cannot find encryption key from key ring.", publicKey);

        {
            byte[] encryptedMsgNonArmor = PGPUtils.encrypt(publicKey, message, false, null, null);
            Assert.assertNotNull(encryptedMsgNonArmor);
            byte[] decryptedData = PGPUtils.decrypt(keyRingGenerator.generateSecretKeyRing(),
                    password, encryptedMsgNonArmor, null);
            Assert.assertNotNull(decryptedData);
            Assert.assertArrayEquals("Decrypted message is not the same as original one.", message,
                    decryptedData);
        }
        {
            byte[] encryptedMsgNonArmor = PGPUtils.encrypt(publicKey, message, false,
                    PGPUtils.DEFAULT_COMPRESSION_ALGORITHM,
                    PGPUtils.DEFAULT_SYMMETRIC_ENCRYPTION_ALGORITHM);
            Assert.assertNotNull(encryptedMsgNonArmor);
            byte[] decryptedData = PGPUtils.decrypt(keyRingGenerator.generateSecretKeyRing(),
                    password, encryptedMsgNonArmor, null);
            Assert.assertNotNull(decryptedData);
            Assert.assertArrayEquals("Decrypted message is not the same as original one.", message,
                    decryptedData);
        }

        {
            byte[] encryptedMsgArmor = PGPUtils.encrypt(publicKey, message, true, null, null);
            Assert.assertNotNull(encryptedMsgArmor);
            byte[] decryptedData = PGPUtils.decrypt(keyRingGenerator.generateSecretKeyRing(),
                    password, encryptedMsgArmor, null);
            Assert.assertNotNull(decryptedData);
            Assert.assertArrayEquals("Decrypted message is not the same as original one.", message,
                    decryptedData);
        }
        {
            byte[] encryptedMsgArmor = PGPUtils.encrypt(publicKey, message, true,
                    PGPUtils.DEFAULT_COMPRESSION_ALGORITHM,
                    PGPUtils.DEFAULT_SYMMETRIC_ENCRYPTION_ALGORITHM);
            Assert.assertNotNull(encryptedMsgArmor);
            byte[] decryptedData = PGPUtils.decrypt(keyRingGenerator.generateSecretKeyRing(),
                    password, encryptedMsgArmor, null);
            Assert.assertNotNull(decryptedData);
            Assert.assertArrayEquals("Decrypted message is not the same as original one.", message,
                    decryptedData);
        }
    }

    @org.junit.Test
    public void testEncryptDecryptWithSignature() throws Exception {
        String password = "s3cr3t";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "id",
                password);
        byte[] message = this.getClass().getName().getBytes();

        PGPPublicKey pubKeyToEncrypt = PGPUtils
                .extractFirstPublicKey(keyRingGenerator.generatePublicKeyRing(), true, true);
        Assert.assertNotNull("Cannot find encryption key from key ring.", pubKeyToEncrypt);

        PGPSecretKey secKeyToSign = PGPUtils
                .extractFirstSecretKey(keyRingGenerator.generateSecretKeyRing(), true, true);
        Assert.assertNotNull("Cannot find signing key from key ring.", secKeyToSign);

        {
            byte[] encryptedMsgNonArmor = PGPUtils.signAndEncrypt(pubKeyToEncrypt, secKeyToSign,
                    password, message, false, null, null);
            Assert.assertNotNull(encryptedMsgNonArmor);
            byte[] decryptedData = PGPUtils.decrypt(keyRingGenerator.generateSecretKeyRing(),
                    password, encryptedMsgNonArmor, keyRingGenerator.generatePublicKeyRing());
            Assert.assertNotNull(decryptedData);
            Assert.assertArrayEquals("Decrypted message is not the same as original one.", message,
                    decryptedData);
        }
        {
            byte[] encryptedMsgNonArmor = PGPUtils.signAndEncrypt(pubKeyToEncrypt, secKeyToSign,
                    password, message, false, PGPUtils.DEFAULT_COMPRESSION_ALGORITHM,
                    PGPUtils.DEFAULT_SYMMETRIC_ENCRYPTION_ALGORITHM);
            Assert.assertNotNull(encryptedMsgNonArmor);
            byte[] decryptedData = PGPUtils.decrypt(keyRingGenerator.generateSecretKeyRing(),
                    password, encryptedMsgNonArmor, keyRingGenerator.generatePublicKeyRing());
            Assert.assertNotNull(decryptedData);
            Assert.assertArrayEquals("Decrypted message is not the same as original one.", message,
                    decryptedData);
        }

        {
            byte[] encryptedMsgArmor = PGPUtils.signAndEncrypt(pubKeyToEncrypt, secKeyToSign,
                    password, message, true, null, null);
            Assert.assertNotNull(encryptedMsgArmor);
            byte[] decryptedData = PGPUtils.decrypt(keyRingGenerator.generateSecretKeyRing(),
                    password, encryptedMsgArmor, keyRingGenerator.generatePublicKeyRing());
            Assert.assertNotNull(decryptedData);
            Assert.assertArrayEquals("Decrypted message is not the same as original one.", message,
                    decryptedData);
        }
        {
            byte[] encryptedMsgArmor = PGPUtils.signAndEncrypt(pubKeyToEncrypt, secKeyToSign,
                    password, message, true, PGPUtils.DEFAULT_COMPRESSION_ALGORITHM,
                    PGPUtils.DEFAULT_SYMMETRIC_ENCRYPTION_ALGORITHM);
            Assert.assertNotNull(encryptedMsgArmor);
            byte[] decryptedData = PGPUtils.decrypt(keyRingGenerator.generateSecretKeyRing(),
                    password, encryptedMsgArmor, keyRingGenerator.generatePublicKeyRing());
            Assert.assertNotNull(decryptedData);
            Assert.assertArrayEquals("Decrypted message is not the same as original one.", message,
                    decryptedData);
        }
    }
}

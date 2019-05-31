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

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testExportPublicKeyX509() throws Exception {
        for (String algo : ALGORITHMS) {
            KeyPair keyPair = KeyPairUtils.generateKeyPair(1024, algo, null);
            PublicKey pubKey = keyPair.getPublic();
            String pemPubKey = KeyPairUtils.exportPemX509(pubKey, true);
            Assert.assertTrue(
                    pemPubKey.startsWith("-----BEGIN " + KeyPairUtils.PEM_TYPE_X509_PUBLIC_KEY));
        }
    }

    @org.junit.Test
    public void testExportPrivateKeyPKCS8NoPassword() throws Exception {
        for (String algo : ALGORITHMS) {
            KeyPair keyPair = KeyPairUtils.generateKeyPair(1024, algo, null);
            PrivateKey privKey = keyPair.getPrivate();
            String pemPrivKey = KeyPairUtils.exportPemPKCS8(privKey, null, true);
            Assert.assertTrue(
                    pemPrivKey.startsWith("-----BEGIN " + KeyPairUtils.PEM_TYPE_PKCS8_PRIVATE_KEY));
        }
    }

    @org.junit.Test
    public void testExportPrivateKeyPKCS8WithPassword() throws Exception {
        for (String algo : ALGORITHMS) {
            KeyPair keyPair = KeyPairUtils.generateKeyPair(1024, algo, null);
            PrivateKey privKey = keyPair.getPrivate();
            String pemPrivKey = KeyPairUtils.exportPemPKCS8(privKey, "password", true);
            Assert.assertTrue(pemPrivKey
                    .startsWith("-----BEGIN " + KeyPairUtils.PEM_TYPE_PKCS8_ENCRYPTED_PRIVATE_KEY));
        }
    }

    @org.junit.Test
    public void testLoadPublicKey() throws Exception {
        for (String algo : ALGORITHMS) {
            KeyPair keyPair = KeyPairUtils.generateKeyPair(1024, algo, null);
            PublicKey pubKey = keyPair.getPublic();
            String pemPubKey = KeyPairUtils.exportPemX509(pubKey, true);
            PublicKey loadPubKey = KeyPairUtils.loadPublicKeyFromPem(pemPubKey);
            Assert.assertNotNull(loadPubKey);
            Assert.assertEquals(pubKey.getAlgorithm(), loadPubKey.getAlgorithm());
            Assert.assertEquals(pubKey.getFormat(), loadPubKey.getFormat());
            Assert.assertArrayEquals(pubKey.getEncoded(), loadPubKey.getEncoded());
        }
    }

    @org.junit.Test
    public void testLoadPrivateKeyNoPassword() throws Exception {
        for (String algo : ALGORITHMS) {
            KeyPair keyPair = KeyPairUtils.generateKeyPair(1024, algo, null);
            PrivateKey privKey = keyPair.getPrivate();
            String pemPrivKey = KeyPairUtils.exportPemPKCS8(privKey, null, true);
            PrivateKey loadPrivKey = KeyPairUtils.loadPrivateKeyFromPem(pemPrivKey, null);
            Assert.assertNotNull(loadPrivKey);
            Assert.assertEquals(privKey.getAlgorithm(), loadPrivKey.getAlgorithm());
            Assert.assertEquals(privKey.getFormat(), loadPrivKey.getFormat());
            Assert.assertArrayEquals(privKey.getEncoded(), loadPrivKey.getEncoded());
        }
    }

    @org.junit.Test
    public void testLoadPrivateKeyWithPassword() throws Exception {
        String password = "s3cr3t";
        for (String algo : ALGORITHMS) {
            KeyPair keyPair = KeyPairUtils.generateKeyPair(1024, algo, null);
            PrivateKey privKey = keyPair.getPrivate();
            String pemPrivKey = KeyPairUtils.exportPemPKCS8(privKey, password, true);
            PrivateKey loadPrivKey = KeyPairUtils.loadPrivateKeyFromPem(pemPrivKey, password);
            Assert.assertNotNull(loadPrivKey);
            Assert.assertEquals(privKey.getAlgorithm(), loadPrivKey.getAlgorithm());
            Assert.assertEquals(privKey.getFormat(), loadPrivKey.getFormat());
            Assert.assertArrayEquals(privKey.getEncoded(), loadPrivKey.getEncoded());
        }
    }
}

package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.KeyPairUtils;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.StringWriter;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class QndKeyPair {

    static void print(KeyPair keyPair) throws Exception {
        PrivateKey privKey = keyPair.getPrivate();
        PublicKey pubKey = keyPair.getPublic();
        System.out.println(
                "PublicKey : " + pubKey.getAlgorithm() + " / " + pubKey.getFormat() + " / " + pubKey.getClass());
        System.out.println(
                "PrivateKey: " + privKey.getAlgorithm() + " / " + privKey.getFormat() + " / " + privKey.getClass());

        SubjectPublicKeyInfo spki = SubjectPublicKeyInfo.getInstance(pubKey.getEncoded());
        try (StringWriter writer = new StringWriter()) {
            try (JcaPEMWriter pemWriter = new JcaPEMWriter(writer)) {
                pemWriter.writeObject(pubKey);
                pemWriter.writeObject(spki);
            }
            System.err.println(writer.toString());
        }

        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        int keySize = 1024 * 4;
        print(KeyPairUtils.generateKeyPair(keySize, "RSA", null));
        //        print(KeyPairUtils.generateKeyPair(keySize, "DSA", null));
        print(KeyPairUtils.generateKeyPair(keySize, "ElGamal", null));
    }
}

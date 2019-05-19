package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.KeyPairUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8EncryptorBuilder;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfoBuilder;

import java.io.StringWriter;
import java.security.KeyPair;

public class QndPrivateKeyExport {
    public static void main(String[] args) throws Exception {
        KeyPair kp = KeyPairUtils.generateKeyPair(512, "RSA", null);
        PrivateKeyInfo pki = PrivateKeyInfo.getInstance(kp.getPrivate().getEncoded());
        System.out.println(pki);
        System.out.println(pki.getAttributes());
        System.out.println(pki.getPrivateKeyAlgorithm().getAlgorithm().getId());

        String password = "demo";

        JceOpenSSLPKCS8EncryptorBuilder encryptorBuilder = new JceOpenSSLPKCS8EncryptorBuilder(PKCS8Generator.DES3_CBC);
        OutputEncryptor encryptor = encryptorBuilder.setPasssword(password.toCharArray()).build();
        PKCS8EncryptedPrivateKeyInfoBuilder builder = new PKCS8EncryptedPrivateKeyInfoBuilder(pki);
        PKCS8EncryptedPrivateKeyInfo epki = builder.build(encryptor);

        //        JceOpenSSLPKCS8EncryptorBuilder encryptorBuilder = new JceOpenSSLPKCS8EncryptorBuilder(
        //                PKCS8Generator.PBE_SHA1_RC2_128);
        //        OutputEncryptor encryptor = encryptorBuilder.setRandom(SECURE_RNG).setPasssword(password.toCharArray()).build();
        //        try (ByteArrayOutputStream bOut = new ByteArrayOutputStream()) {
        //            try (OutputStream cOut = encryptor.getOutputStream(bOut)) {
        //                cOut.write(keyData);
        //            }
        //            EncryptedPrivateKeyInfo privateKeyInfo = new EncryptedPrivateKeyInfo(encryptor.getAlgorithmIdentifier(),
        //                    bOut.toByteArray());
        //            return privateKeyInfo.getEncoded();
        //        }

        try (StringWriter writer = new StringWriter()) {
            try (JcaPEMWriter pemWriter = new JcaPEMWriter(writer)) {
                pemWriter.writeObject(pki);
                pemWriter.writeObject(epki);
            }
            System.out.println(writer.toString());
        }
    }
}

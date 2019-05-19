package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.KeyPairUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcePEMEncryptorBuilder;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.StringWriter;
import java.security.KeyPair;
import java.security.Security;

public class QndJcaPemWrriter {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static void main(String[] args) throws Exception {
        KeyPair kp = KeyPairUtils.generateKeyPair(512, "RSA", null);

        JcePEMEncryptorBuilder encryptorBuilder = new JcePEMEncryptorBuilder("DES-EDE3-CBC")
                .setProvider(BouncyCastleProvider.PROVIDER_NAME);
        String password = "demo";

        try (StringWriter writer = new StringWriter()) {
            try (JcaPEMWriter pemWriter = new JcaPEMWriter(writer)) {
                pemWriter.writeObject(kp.getPrivate());
                pemWriter.writeObject(kp.getPrivate(), encryptorBuilder.build(password.toCharArray()));

                JcaMiscPEMGenerator pemGenerator = new JcaMiscPEMGenerator(kp.getPrivate(),encryptorBuilder.build(password.toCharArray()));
                PemObject pemObject = pemGenerator.generate();
                pemWriter.writeObject(pemObject);
            }
            System.out.println(writer.toString());
        }
    }
}

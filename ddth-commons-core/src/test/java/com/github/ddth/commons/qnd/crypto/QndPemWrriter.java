package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.KeyPairUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.openssl.jcajce.JcePEMEncryptorBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.StringWriter;
import java.security.KeyPair;
import java.security.Security;

public class QndPemWrriter {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static void main(String[] args) throws Exception {
        KeyPair kp = KeyPairUtils.generateKeyPair(512, "RSA", null);

        JcePEMEncryptorBuilder encryptorBuilder = new JcePEMEncryptorBuilder("DES-EDE3-CBC")
                .setProvider(BouncyCastleProvider.PROVIDER_NAME);

        try (StringWriter writer = new StringWriter()) {
            try (PemWriter pemWriter = new PemWriter(writer)) {
                PemObject pemObject = new PemObject(KeyPairUtils.PEM_TYPE_PKCS8_PRIVATE_KEY,
                        kp.getPrivate().getEncoded());
                PemObject pemObject2 = new PemObject(KeyPairUtils.PEM_TYPE_PKCS8_ENCRYPTED_PRIVATE_KEY,
                        kp.getPrivate().getEncoded());
                {
                    JcaMiscPEMGenerator obj = new JcaMiscPEMGenerator(kp.getPrivate(),
                            encryptorBuilder.build("password".toCharArray()));
                    pemWriter.writeObject(obj);
                }
                {
                    JcaMiscPEMGenerator obj = new JcaMiscPEMGenerator(pemObject,
                            encryptorBuilder.build("password".toCharArray()));
                    pemWriter.writeObject(obj);
                }
                {
                    JcaMiscPEMGenerator obj = new JcaMiscPEMGenerator(pemObject2,
                            encryptorBuilder.build("password".toCharArray()));
                    pemWriter.writeObject(obj);
                }
            }
            System.out.println(writer.toString());
        }
    }
}

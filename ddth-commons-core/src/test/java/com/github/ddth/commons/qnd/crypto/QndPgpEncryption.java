package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.PGPUtils;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.operator.PGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;

import java.security.SecureRandom;

public class QndPgpEncryption {

    public static void main(String[] args) throws Exception {
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "Scommerce", "demo");

        PGPDataEncryptorBuilder dataEncryptorBuilder = new JcePGPDataEncryptorBuilder(SymmetricKeyAlgorithmTags.AES_256)
                .setWithIntegrityPacket(true).setSecureRandom(new SecureRandom());
        PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(dataEncryptorBuilder);
    }

}

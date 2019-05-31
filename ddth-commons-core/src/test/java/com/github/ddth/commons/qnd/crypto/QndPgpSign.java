package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.PGPUtils;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class QndPgpSign {

    public static void main(String[] args) throws Exception {
        String keyPassphrase = "demo";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "DDTH", keyPassphrase);
        PGPPublicKey pubKey = PGPUtils.extractFirstPublicKey(keyRingGenerator.generatePublicKeyRing(), false, true);
        PGPSecretKey secKey = PGPUtils.extractFirstSecretKey(keyRingGenerator.generateSecretKeyRing(), true, true);
        PGPPrivateKey privKey = PGPUtils.extractPrivateKey(secKey, keyPassphrase);
        System.out.println("Public Key : " + pubKey + "\t/ " + pubKey.getKeyID());
        System.out.println("Secret Key : " + secKey + "\t/ " + secKey.getKeyID());
        System.out.println("Private Key: " + privKey + "/ " + secKey.getKeyID());

        System.out.println();
        byte[] data = "Nguyễn Bá Thành".getBytes(StandardCharsets.UTF_8);
        {
            boolean asciiArmor = false;
            byte[] signedData = PGPUtils.sign(secKey, keyPassphrase, data, null, asciiArmor);
            System.out.println(asciiArmor ?
                    new String(signedData, StandardCharsets.UTF_8) :
                    Base64.getEncoder().encodeToString(signedData));
            System.out.println(PGPUtils.verify(keyRingGenerator.generatePublicKeyRing(), signedData));
        }
        {
            boolean asciiArmor = true;
            byte[] signedData = PGPUtils
                    .sign(secKey, keyPassphrase, data, PGPUtils.DEFAULT_COMPRESSION_ALGORITHM, asciiArmor);
            System.out.println(asciiArmor ?
                    new String(signedData, StandardCharsets.UTF_8) :
                    Base64.getEncoder().encodeToString(signedData));
            System.out.println(PGPUtils.verify(keyRingGenerator.generatePublicKeyRing(), signedData));
        }
    }
}

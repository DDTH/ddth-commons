package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.PGPUtils;
import org.bouncycastle.openpgp.*;

import java.util.List;

public class QndPgpLoadKeys {

    private static void printKey(PGPPublicKey key) {
        if (key != null) {
            System.err.print(Long.toHexString(key.getKeyID()) + ":" + key.getBitStrength() + " / Master: " + key
                    .isMasterKey() + " / Encrytion: " + key.isEncryptionKey() + " / " + key.getClass());
            if (key.isMasterKey()) {
                System.err.println(" - " + key.getUserIDs().next());
            } else {
                System.err.println();
            }
        }
    }

    private static void printKey(PGPSecretKey key, String password) throws PGPException {
        if (key != null) {
            System.err
                    .print(Long.toHexString(key.getKeyID()) + ":" + key.getPublicKey().getBitStrength() + " / Master: "
                            + key.isMasterKey() + " / Is PrivateKey Empty: " + key.isPrivateKeyEmpty() + " / Signing: "
                            + key.isSigningKey() + " / " + key.getClass());
            if (key.isMasterKey()) {
                System.err.println(" - " + key.getUserIDs().next());
            } else {
                System.err.println();
            }
            PGPPrivateKey privKey = PGPUtils.extractPrivateKey(key, password);
            if (privKey != null) {
                System.out.println("\t" + privKey.getKeyID() + " / " + privKey.getPrivateKeyDataPacket().getFormat());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String id = "DDTH";
        String password = "demo";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, id, password);

        String armorPublicKey = PGPUtils.exportPublicKey(keyRingGenerator, true);
        System.out.println(armorPublicKey);
        System.out.println("============================== Pubic Keys ====================");
        List<PGPPublicKey> pubKeys = PGPUtils.loadPublicKeys(armorPublicKey, true, false);
        for (PGPPublicKey pubKey : pubKeys) {
            printKey(pubKey);
        }
        printKey(PGPUtils.loadFirstPublicKey(armorPublicKey, true, true /*encryption*/, true /*master*/));
        printKey(PGPUtils.loadFirstPublicKey(armorPublicKey, true, true /*encryption*/, false /*master*/));
        printKey(PGPUtils.loadFirstPublicKey(armorPublicKey, true, false /*encryption*/, true /*master*/));
        printKey(PGPUtils.loadFirstPublicKey(armorPublicKey, true, false /*encryption*/, false /*master*/));
        System.out.println();

        String armorSecretKey = PGPUtils.exportSecretKey(keyRingGenerator, true);
        System.out.println(armorSecretKey);
        System.out.println("============================== Secret Keys ====================");
        List<PGPSecretKey> secKeys = PGPUtils.loadSecretKeys(armorSecretKey, true, false);
        for (PGPSecretKey secKey : secKeys) {
            printKey(secKey, password);
        }
        printKey(PGPUtils.loadFirstSecretKey(armorSecretKey, true, true /*signing*/, true /*master*/), password);
        printKey(PGPUtils.loadFirstSecretKey(armorSecretKey, true, true /*signing*/, false /*master*/), password);
        printKey(PGPUtils.loadFirstSecretKey(armorSecretKey, true, false /*signing*/, true /*master*/), password);
        printKey(PGPUtils.loadFirstSecretKey(armorSecretKey, true, false /*signing*/, false /*master*/), password);
        System.out.println();
    }
}

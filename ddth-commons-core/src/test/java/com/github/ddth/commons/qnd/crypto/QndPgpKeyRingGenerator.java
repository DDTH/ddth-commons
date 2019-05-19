package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.PGPUtils;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;

public class QndPgpKeyRingGenerator {
    public static void main(String[] args) throws Exception {
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "Scommerce", "demo");
        {
            System.err.println(PGPUtils.exportPublicKey(keyRingGenerator, false));
            System.err.println(PGPUtils.exportPublicKey(keyRingGenerator, true));
        }
        {
            System.err.println(PGPUtils.exportSecretKey(keyRingGenerator, false));
            System.err.println(PGPUtils.exportSecretKey(keyRingGenerator, true));
        }
    }
}

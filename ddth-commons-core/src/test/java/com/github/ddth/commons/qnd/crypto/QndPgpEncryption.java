package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.PGPUtils;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class QndPgpEncryption {
    //    static byte[] encrypt(byte[] clearData, char[] passPhrase, String fileName, int algorithm, boolean asciiArmor)
    //            throws IOException, PGPException {
    //        if (fileName == null) {
    //            fileName = PGPLiteralData.CONSOLE;
    //        }
    //
    //        PGPDataEncryptorBuilder dataEncryptorBuilder = new JcePGPDataEncryptorBuilder(algorithm)
    //                .setWithIntegrityPacket(true).setSecureRandom(new SecureRandom());
    //        PGPEncryptedDataGenerator encryptedDataGenerator = new PGPEncryptedDataGenerator(dataEncryptorBuilder);
    //        encryptedDataGenerator.addMethod(new JcePBEKeyEncryptionMethodGenerator(passPhrase));
    //
    //        byte[] compressedData = PGPUtils.compress(clearData, fileName, PGPUtils.CompressionAlgorithm.ZLIB);
    //        try (ByteArrayOutputStream osStorage = new ByteArrayOutputStream()) {
    //            try (OutputStream osEncrypted = asciiArmor ? new ArmoredOutputStream(osStorage) : osStorage) {
    //                try (OutputStream os = encryptedDataGenerator.open(osEncrypted, compressedData.length)) {
    //                    os.write(compressedData);
    //                }
    //            }
    //            return osStorage.toByteArray();
    //        }
    //    }
    //
    //    /*----------------------------------------------------------------------*/

    public static void main(String[] args) throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder dataBuilder = new StringBuilder();
        for (int i = 0; i < 1024 * 16; i++) {
            dataBuilder.append(rand.nextInt(1024));
        }
        byte[] data = dataBuilder.toString().getBytes(StandardCharsets.UTF_8);

        String keyPassphrase = "demo";
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "DDTH", keyPassphrase);
        PGPPublicKey pubKey = PGPUtils.extractFirstPublicKey(keyRingGenerator.generatePublicKeyRing(), true, false);
        PGPSecretKey secKey = PGPUtils.extractFirstSecretKey(keyRingGenerator.generateSecretKeyRing(), false, false);
        System.out.println("Public Key : " + pubKey + "\t/ " + pubKey.getKeyID());
        System.out.println("Secret Key : " + secKey + "\t/ " + secKey.getKeyID());

        System.out.println();
        {
            boolean asciiArmor = true;
            byte[] encryptedData = PGPUtils.encrypt(pubKey, data, asciiArmor, PGPUtils.DEFAULT_COMPRESSION_ALGORITHM,
                    PGPUtils.DEFAULT_SYMMETRIC_ENCRYPTION_ALGORITHM);
            System.out.println(asciiArmor ?
                    new String(encryptedData, StandardCharsets.UTF_8) :
                    Base64.getEncoder().encodeToString(encryptedData));
            byte[] decryptedData = PGPUtils
                    .decrypt(keyRingGenerator.generateSecretKeyRing(), keyPassphrase, encryptedData,
                            keyRingGenerator.generatePublicKeyRing());
            System.out.printf("Data: (%d) %s\nEncrypted data: %d\n", data.length,
                    new String(data, StandardCharsets.UTF_8), encryptedData.length);
            if (decryptedData == null) {
                System.out.printf("Decrypted data: [null]\n");
            } else {
                System.out.printf("Decrypted data: (%d) %s\n", decryptedData.length,
                        new String(decryptedData, StandardCharsets.UTF_8));
                System.out.println("Decryption ok: " + Arrays.equals(data, decryptedData));
            }
        }

        System.out.println();
        {
            data = "Nguyen Ba Thanh".getBytes(StandardCharsets.UTF_8);
            boolean asciiArmor = true;
            byte[] encryptedData = PGPUtils.encrypt(pubKey, data, asciiArmor, null, null);
            System.out.println(asciiArmor ?
                    new String(encryptedData, StandardCharsets.UTF_8) :
                    Base64.getEncoder().encodeToString(encryptedData));
            byte[] decryptedData = PGPUtils
                    .decrypt(keyRingGenerator.generateSecretKeyRing(), keyPassphrase, encryptedData,
                            keyRingGenerator.generatePublicKeyRing());
            System.out.printf("Data: (%d) %s\nEncrypted data: %d\n", data.length,
                    new String(data, StandardCharsets.UTF_8), encryptedData.length);
            if (decryptedData == null) {
                System.out.printf("Decrypted data: [null]\n");
            } else {
                System.out.printf("Decrypted data: (%d) %s\n", decryptedData.length,
                        new String(decryptedData, StandardCharsets.UTF_8));
                System.out.println("Decryption ok: " + Arrays.equals(data, decryptedData));
            }
        }
    }
}

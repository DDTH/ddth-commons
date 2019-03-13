package com.github.ddth.commons.qnd;

import javax.crypto.Cipher;

import com.github.ddth.commons.utils.AESUtils;

public class QndAes {

    public static void main(String[] args) {
        String[] ALGO = { "AES" };
        String[] MODES = { "ECB", "CBC", "PCBC", "CTR", "CTS", "CFB", "CFB8", "CFB16", "CFB32",
                "CFB64", "CFB128", "OFB", "OFB8", "OFB16", "OFB32", "OFB64", "OFB128", "GCM" };
        String[] PADDINGS = { "NoPadding", "PKCS5Padding", "ISO10126Padding" };

        byte[] key = AESUtils.randomKeyAsBytes();
        byte[] iv = AESUtils.randomIVAsBytes();
        for (String algo : ALGO) {
            for (String mode : MODES) {
                for (String padding : PADDINGS) {
                    if ((mode.equals("CTR") || mode.equals("CTS") || mode.equals("GCM"))
                            && !padding.equals("NoPadding")) {
                        // CTR mode must be used with NoPadding
                        continue;
                    }

                    String cipherTransformation = algo + "/" + mode + "/" + padding;
                    System.out.print(cipherTransformation + "...");
                    try {
                        Cipher cipher = AESUtils.createCipher(Cipher.ENCRYPT_MODE, key, iv,
                                cipherTransformation);
                        System.out.println(cipher);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }
            }
        }

    }

}

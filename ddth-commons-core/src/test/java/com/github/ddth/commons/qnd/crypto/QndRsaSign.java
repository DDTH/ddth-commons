package com.github.ddth.commons.qnd.crypto;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;

import com.github.ddth.commons.crypto.RSAUtils;

public class QndRsaSign {

    static String[] SIGNATURE_ALGORITHMS = { "MD2withRSA", "MD5withRSA", "SHA1withRSA",
            "SHA224withRSA", "SHA256withRSA", "SHA384withRSA", "SHA512withRSA" };
    static SecureRandom SECURE_RNG = new SecureRandom();

    public static void main(String[] args) throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(2048);
        PrivateKey privKey = keypair.getPrivate();

        for (String algo : SIGNATURE_ALGORITHMS) {
            System.out.print(algo + "...");
            Signature sign = Signature.getInstance(algo);
            sign.initSign(privKey, SECURE_RNG);
            System.out.println(sign);
            // sign.update(message);
            // return sign.sign();
            //
            //
            // String transformation = RSAUtils.CIPHER_ALGORITHM + "/" + RSAUtils.CIPHER_MODE + "/"
            // + padding;
            // System.out.print(transformation + "...");
            // System.out.println(calcPaddingSize(transformation));
        }
    }

}

package com.github.ddth.commons.qnd;

import java.security.Key;
import java.security.KeyPair;
import java.security.interfaces.RSAKey;

import javax.crypto.Cipher;

import com.github.ddth.commons.utils.RSAUtils;

public class QndRsa {

    static Cipher createCipher(int mode, Key key, String transformation) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(mode, key);
        return cipher;
    }

    static int calcPaddingSize(String transformation) throws Exception {
        KeyPair keypair = RSAUtils.generateKeys(2048);
        Key pubKey = keypair.getPublic();
        for (int i = 1; i < 1024; i++) {
            Cipher cipher = createCipher(Cipher.ENCRYPT_MODE, pubKey, transformation);
            byte[] data = new byte[i];
            try {
                cipher.doFinal(data);
            } catch (javax.crypto.IllegalBlockSizeException e) {
                return ((RSAKey) pubKey).getModulus().bitLength() / 8 - i + 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        for (String padding : RSAUtils.CIPHER_PADDINGS) {
            String transformation = RSAUtils.CIPHER_ALGORITHM + "/" + RSAUtils.CIPHER_MODE + "/"
                    + padding;
            System.out.print(transformation + "...");
            System.out.println(calcPaddingSize(transformation));
        }
    }

}

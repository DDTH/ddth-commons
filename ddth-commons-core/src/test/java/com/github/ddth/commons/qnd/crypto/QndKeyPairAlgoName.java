package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.KeyPairUtils;

public class QndKeyPairAlgoName {

    public static void main(String[] args) throws Exception {
        System.out.println(KeyPairUtils.generateKeyPair(1024, "RSA", null).getPublic().getAlgorithm());
        System.out.println(KeyPairUtils.generateKeyPair(1024, "DSA", null).getPublic().getAlgorithm());
        System.out.println(KeyPairUtils.generateKeyPair(1024, "ELGAMAL", null).getPublic().getAlgorithm());
        System.out.println(KeyPairUtils.generateKeyPair(1024, "ELG", null).getPublic().getAlgorithm());
    }
}

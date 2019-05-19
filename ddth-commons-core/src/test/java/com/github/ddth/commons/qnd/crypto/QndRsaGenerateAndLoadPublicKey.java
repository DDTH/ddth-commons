package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.KeyPairUtils;
import com.github.ddth.commons.crypto.RSAUtils;

import java.security.Key;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

public class QndRsaGenerateAndLoadPublicKey {

    static void printKeyInfo(Key key) {
        System.out.println("Key : " + key);
        System.out.println("Info: " + key.getClass() + " / " + key.getAlgorithm() + " / " + key.getFormat());
    }

    public static void main(String[] args) throws Exception {
        KeyPair kp = KeyPairUtils.generateKeyPair(512, "RSA", null);
        printKeyInfo(kp.getPublic());

        {
            String pemPublicKey = RSAUtils.exportPemX509((RSAPublicKey) kp.getPublic(), true);
            System.out.println(pemPublicKey);
            Key key = RSAUtils.loadPublicKeyFromPem(pemPublicKey);
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }

        System.out.println();

        {
            String pemPublicKey = RSAUtils.exportPemPKCS1((RSAPublicKey) kp.getPublic(), true);
            System.out.println(pemPublicKey);
            Key key = RSAUtils.loadPublicKeyFromPem(pemPublicKey);
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }

        System.out.println();

        {
            String pemPublicKey = "-----BEGIN PUBLIC KEY-----\n"
                    + "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxlOYG+W6OQFlgKzUipkD\n"
                    + "swFOTLPUe6+S3gF/rZNGtBEhLffhnWwwZuvxfUSbP7LwolC/Ee4SMtt75/EPYDap\n"
                    + "XkgTU/Qz6Bt+SgAb0f4QEy6qAYvNETSmzhPw9/EfMNwv3/7ejn7abCbv0DiZnysI\n"
                    + "Gq0omHeaYnCw7HI7ngAhC9GxzrOC42eLCdGJ5QBLHutwqG/ZvgD0npzAkXWVzlXF\n"
                    + "JY3GIiaslA6ze376hsoikKh13lZmx8bLVKfp7wOyWOM736BakaEzKiFWrNKQSCib\n"
                    + "8I65nDdI1Fp4RI0tmxCpSo2n8TlNbhGUsjL6YdzUMIaZk7eyRsl7VB3zuP6Bw6Vp\n" + "lwIDAQAB\n"
                    + "-----END PUBLIC KEY-----";
            System.out.println(pemPublicKey);
            Key key = RSAUtils.loadPublicKeyFromPem(pemPublicKey);
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }
    }
}

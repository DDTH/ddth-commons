package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.RSAUtils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class QndRsaEncryptDecrypt {

    public static void main(String[] args) throws Exception {
        String pubKey64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDaen2mkJqYUbBwTG3SQGjc5WOB\n"
                + "jeueRpd/uIvg6J9Kked0jNKFmea1tj7YHgfNoqb3IHvzVIxb9ZaGLRyS5YHTGUj1\n"
                + "+wWegZ8vrzMERWdgXzEf2eoXPdNppG87zK2Cg54X+oXPVIO1vAwItnMH5d0Cn2eu\n"
                + "X2ZUzP2LaCvXFCWgowIDAQAB";
        String privKey64 = "MIICXwIBAAKBgQDaen2mkJqYUbBwTG3SQGjc5WOBjeueRpd/uIvg6J9Kked0jNKF\n"
                + "mea1tj7YHgfNoqb3IHvzVIxb9ZaGLRyS5YHTGUj1+wWegZ8vrzMERWdgXzEf2eoX\n"
                + "PdNppG87zK2Cg54X+oXPVIO1vAwItnMH5d0Cn2euX2ZUzP2LaCvXFCWgowIDAQAB\n"
                + "AoGBAIkv4TZ2Fp0OrnvVgiBD0ZTjdVgkhY8dRIQunabcaymn9G56/rZlrTjs9wk9\n"
                + "0iiNucnz2+EvdfNIgvw913NmKd36+oeCKJuxVxfDW34la7Cke1qggdVCR69vPs7z\n"
                + "QIpEeHQI36i2NP4B0d1OHQ6z59TEvo//4IKFDVQP+SdnGBABAkEA9ub52MmjjQ+o\n"
                + "BNZSth8SfSoZQguzBhyhIsBhFPi0vW46WjhMkPTR+m4VrvLM8DfFSyKRw0bTX1fO\n"
                + "twwqIs/zYQJBAOKHZN0OzjPyNeIuGkNgdADO5PoH3G2uHPekHbDXdPWMR4WBW5xb\n"
                + "NKJ2vRUSkHPpqiP5vEVtIHK5fM7my8vp1oMCQQCXGRVv6T5w9lLh2vO1RuC+voPE\n"
                + "mnpN1Dl8lfUTq3yYrRuC2Q9qapkSREp6zVZD8C2KZLaFphN3Nrpn5UboFREhAkEA\n"
                + "i/wlPWHv1TQBTlM1EZviM7mm0EfZVMH6zqHRYz3R3gehDwRgS2AlR5xevk3pjGfC\n"
                + "J1clnDWSjrLHSMhg+hL35wJBAKxRbGJXeq9lh6FWeU6RAnW115WjvTMaaaiylcG6\n"
                + "biXrP3nwWH6fsPv8tqFKsB+4zibSJ6U+PPuTEytjyoRrntM=";

        RSAPublicKey pubKey = RSAUtils.buildPublicKey(pubKey64);
        System.out.println(pubKey);
        RSAPrivateKey privKey = RSAUtils.buildPrivateKey(privKey64);
        System.out.println(privKey);
    }

}

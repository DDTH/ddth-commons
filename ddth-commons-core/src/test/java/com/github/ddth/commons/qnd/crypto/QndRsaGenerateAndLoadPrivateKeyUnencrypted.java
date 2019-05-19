package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.KeyPairUtils;
import com.github.ddth.commons.crypto.RSAUtils;

import java.security.Key;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

public class QndRsaGenerateAndLoadPrivateKeyUnencrypted {

    static void printKeyInfo(Key key) {
        System.out.println("Key : " + key);
        System.out.println("Info: " + key.getClass() + " / " + key.getAlgorithm() + " / " + key.getFormat());
    }

    public static void main(String[] args) throws Exception {
        KeyPair kp = KeyPairUtils.generateKeyPair(512, "RSA", null);
        printKeyInfo(kp.getPrivate());

        {
            String pemPrivateKey = RSAUtils.exportPemPKCS8((RSAPrivateKey) kp.getPrivate(), null, true);
            System.out.println(pemPrivateKey);
            Key key = RSAUtils.loadPrivateKeyFromPem(pemPrivateKey, null);
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }

        System.out.println();

        {
            String pemPrivateKey = RSAUtils.exportPemPKCS1((RSAPrivateKey) kp.getPrivate(), null, true);
            System.out.println(pemPrivateKey);
            Key key = RSAUtils.loadPrivateKeyFromPem(pemPrivateKey, null);
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }

        System.out.println();

        {
            String pemPrivateKey = "-----BEGIN PRIVATE KEY-----\n"
                    + "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDGU5gb5bo5AWWA\n"
                    + "rNSKmQOzAU5Ms9R7r5LeAX+tk0a0ESEt9+GdbDBm6/F9RJs/svCiUL8R7hIy23vn\n"
                    + "8Q9gNqleSBNT9DPoG35KABvR/hATLqoBi80RNKbOE/D38R8w3C/f/t6OftpsJu/Q\n"
                    + "OJmfKwgarSiYd5picLDscjueACEL0bHOs4LjZ4sJ0YnlAEse63Cob9m+APSenMCR\n"
                    + "dZXOVcUljcYiJqyUDrN7fvqGyiKQqHXeVmbHxstUp+nvA7JY4zvfoFqRoTMqIVas\n"
                    + "0pBIKJvwjrmcN0jUWnhEjS2bEKlKjafxOU1uEZSyMvph3NQwhpmTt7JGyXtUHfO4\n"
                    + "/oHDpWmXAgMBAAECggEAZLCiRF+g6To9R1P34JoJF8Os9iJC2bztCsumAAEK33bm\n"
                    + "6oi0QhFgjZBynY/MZk26AB6Qs1C0ap+mS77GeKmn2ZcsQO51v/zdZEKj3Txo+uQx\n"
                    + "KfHJXlfl7DCRocAIfuz7XXTvl1buHAS0snlZ/UaCqdCKEMaAWkQufw0MGY6lOHMM\n"
                    + "TJ+kyx/GHqtqdE/7arPC7EQbfGyF/l1s1Aan7aMdV2lE4mM22+bI7ljQHbO3EjtZ\n"
                    + "d3zI56saU3/Kg7rztI4ljK0mXFIWU/PaMUkM4e1payAOylPnU7MrMGUu5W0qhWlH\n"
                    + "gj5twZwtdNKvwoZqqJOl7iAKEfjuWoucEly59VxAeQKBgQDjqXxDqYD0pggjmYQU\n"
                    + "juceZImCwyKRCbTZt0wHjNBEbp7weAdiUiR7EXZotyf5REmQ2V4UUCw1A9pwU7K/\n"
                    + "6ecVDYZOQbnEzxjuxb3RLIQkoomTJ7HUDReAeOiHoxtEkqYcPGmAJyLSzgRptp6J\n"
                    + "eBeRs88Muf6MjvbqEfihiEHWnQKBgQDfA1MSnGLlduzSS+2XyRLbqcRhdHqUhgm5\n"
                    + "CAmYcMERv60eWBDUzw4XOrHZB5+Z2mmnLUjETZRe2mpQy0q8EnGNbaMD3E6aBLCx\n"
                    + "qcwLmeXDAEzT1badhVAp9J4+pl0vVV6xyYY1ny63a35uZ/8/FlfqD/sMjmCtVGIF\n"
                    + "tKaD0c+wwwKBgFBYa0OR2bH9upedwTBkMorWXnVompVnQI0plEqB1qqgA7XIFosL\n"
                    + "hFZulkRkh1YAVJorLHmsxb08C+Zik44JZYVXjZOmvIIJuLnjzZa5yDUJq9XkR20X\n"
                    + "5bhwc1gTJzdvLfJ/G5iftzmAfaw1SAQp/t9r/uF17rD6b4eyKfYQGXQ9AoGBAMzL\n"
                    + "ScNCd5MPNgUcktAbZ0TvTpbMFzFDMbSIS/+AM9xvr7O43aP/jIoKbX3HAWqQ5wsN\n"
                    + "i9AxCfpdCtqtBTFWoo5kyxlle4z2l6hfLDDPH+6F3Qd/L1bSML6Gk7cmoVYxSGdt\n"
                    + "j+C4EHeBsDYFLKGrjAo2qK1sJh2l6uogbfWJe/dvAoGAapls1JfRvIlxwK45Vf7o\n"
                    + "jVOL/bY5CaYC/ueZOJUaxTwF8iD2Ecmp1DS5N3Ot7ibkovqgW+Q54rAgY0JJJnI1\n"
                    + "lGyrzC9dWVJOvIFERYEOqeAjTWmmV1gT+fl3IAmoXJxNYnT5/WDxH++/miFqIP2e\n"
                    + "EwrfxIjj/D4hpELhWiVwiy0=\n" + "-----END PRIVATE KEY-----";
            System.out.println(pemPrivateKey);
            Key key = RSAUtils.loadPrivateKeyFromPem(pemPrivateKey, null);
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }

        System.out.println();

        {
            String pemPrivateKey = "-----BEGIN RSA PRIVATE KEY-----\n"
                    + "MIIEowIBAAKCAQEAxlOYG+W6OQFlgKzUipkDswFOTLPUe6+S3gF/rZNGtBEhLffh\n"
                    + "nWwwZuvxfUSbP7LwolC/Ee4SMtt75/EPYDapXkgTU/Qz6Bt+SgAb0f4QEy6qAYvN\n"
                    + "ETSmzhPw9/EfMNwv3/7ejn7abCbv0DiZnysIGq0omHeaYnCw7HI7ngAhC9GxzrOC\n"
                    + "42eLCdGJ5QBLHutwqG/ZvgD0npzAkXWVzlXFJY3GIiaslA6ze376hsoikKh13lZm\n"
                    + "x8bLVKfp7wOyWOM736BakaEzKiFWrNKQSCib8I65nDdI1Fp4RI0tmxCpSo2n8TlN\n"
                    + "bhGUsjL6YdzUMIaZk7eyRsl7VB3zuP6Bw6VplwIDAQABAoIBAGSwokRfoOk6PUdT\n"
                    + "9+CaCRfDrPYiQtm87QrLpgABCt925uqItEIRYI2Qcp2PzGZNugAekLNQtGqfpku+\n"
                    + "xnipp9mXLEDudb/83WRCo908aPrkMSnxyV5X5ewwkaHACH7s+11075dW7hwEtLJ5\n"
                    + "Wf1GgqnQihDGgFpELn8NDBmOpThzDEyfpMsfxh6ranRP+2qzwuxEG3xshf5dbNQG\n"
                    + "p+2jHVdpROJjNtvmyO5Y0B2ztxI7WXd8yOerGlN/yoO687SOJYytJlxSFlPz2jFJ\n"
                    + "DOHtaWsgDspT51OzKzBlLuVtKoVpR4I+bcGcLXTSr8KGaqiTpe4gChH47lqLnBJc\n"
                    + "ufVcQHkCgYEA46l8Q6mA9KYII5mEFI7nHmSJgsMikQm02bdMB4zQRG6e8HgHYlIk\n"
                    + "exF2aLcn+URJkNleFFAsNQPacFOyv+nnFQ2GTkG5xM8Y7sW90SyEJKKJkyex1A0X\n"
                    + "gHjoh6MbRJKmHDxpgCci0s4EabaeiXgXkbPPDLn+jI726hH4oYhB1p0CgYEA3wNT\n"
                    + "Epxi5Xbs0kvtl8kS26nEYXR6lIYJuQgJmHDBEb+tHlgQ1M8OFzqx2Qefmdpppy1I\n"
                    + "xE2UXtpqUMtKvBJxjW2jA9xOmgSwsanMC5nlwwBM09W2nYVQKfSePqZdL1VescmG\n"
                    + "NZ8ut2t+bmf/PxZX6g/7DI5grVRiBbSmg9HPsMMCgYBQWGtDkdmx/bqXncEwZDKK\n"
                    + "1l51aJqVZ0CNKZRKgdaqoAO1yBaLC4RWbpZEZIdWAFSaKyx5rMW9PAvmYpOOCWWF\n"
                    + "V42TpryCCbi5482Wucg1CavV5EdtF+W4cHNYEyc3by3yfxuYn7c5gH2sNUgEKf7f\n"
                    + "a/7hde6w+m+Hsin2EBl0PQKBgQDMy0nDQneTDzYFHJLQG2dE706WzBcxQzG0iEv/\n"
                    + "gDPcb6+zuN2j/4yKCm19xwFqkOcLDYvQMQn6XQrarQUxVqKOZMsZZXuM9peoXyww\n"
                    + "zx/uhd0Hfy9W0jC+hpO3JqFWMUhnbY/guBB3gbA2BSyhq4wKNqitbCYdperqIG31\n"
                    + "iXv3bwKBgGqZbNSX0byJccCuOVX+6I1Ti/22OQmmAv7nmTiVGsU8BfIg9hHJqdQ0\n"
                    + "uTdzre4m5KL6oFvkOeKwIGNCSSZyNZRsq8wvXVlSTryBREWBDqngI01ppldYE/n5\n"
                    + "dyAJqFycTWJ0+f1g8R/vv5ohaiD9nhMK38SI4/w+IaRC4VolcIst\n" + "-----END RSA PRIVATE KEY-----";
            System.out.println(pemPrivateKey);
            Key key = RSAUtils.loadPrivateKeyFromPem(pemPrivateKey, null);
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }
    }
}

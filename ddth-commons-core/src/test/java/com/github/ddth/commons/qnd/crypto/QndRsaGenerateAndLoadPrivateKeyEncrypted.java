package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.KeyPairUtils;
import com.github.ddth.commons.crypto.RSAUtils;

import java.security.Key;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;

public class QndRsaGenerateAndLoadPrivateKeyEncrypted {

    static void printKeyInfo(Key key) {
        System.out.println("Key : " + key);
        System.out.println("Info: " + key.getClass() + " / " + key.getAlgorithm() + " / " + key.getFormat());
    }

    public static void main(String[] args) throws Exception {
        KeyPair kp = KeyPairUtils.generateKeyPair(512, "RSA", null);
        printKeyInfo(kp.getPrivate());
        final String passphrase = "demo";

        {
            String pemPrivateKey = RSAUtils.exportPemPKCS8((RSAPrivateKey) kp.getPrivate(), passphrase, true);
            System.out.println(pemPrivateKey);
            Key key = pemPrivateKey != null ? RSAUtils.loadPrivateKeyFromPem(pemPrivateKey, passphrase) : null;
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }

        System.out.println();

        {
            String pemPrivateKey = RSAUtils.exportPemPKCS1((RSAPrivateKey) kp.getPrivate(), passphrase, true);
            System.out.println(pemPrivateKey);
            Key key = pemPrivateKey != null ? RSAUtils.loadPrivateKeyFromPem(pemPrivateKey, passphrase) : null;
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }

        System.out.println();

        {
            String pemPrivateKey = "-----BEGIN ENCRYPTED PRIVATE KEY-----\n"
                    + "MIIE6TAbBgkqhkiG9w0BBQMwDgQI2MbXO7lUvl4CAggABIIEyOluK9gZChT385I8\n"
                    + "quwhqWl+d7CbQ8zAuA4mL4yFdPwG9v0zbUCngFLuDYaDkiBBSbU3NXkVImCFQ1HO\n"
                    + "f0pW8AWKQu+dY9sAEZ8zyZky4FT0cTrwicOUd/FDO2itC8Q3k4Eh5QqfK7wG7EEw\n"
                    + "VoDUiqIQfB9G1LZ5Zt/eHAIU5xv4lLRBnNvhtZdiZ0na45AzjT0zr177vdnHZ4f6\n"
                    + "zlTFppDF7Zi5978aQQEGq+k0be6u5OM/4tzWRnrmM4jaIi9DtG1WXWuHkObdA7UC\n"
                    + "mHKI/rADRpxcvaXbqQmvfsAcKEuc6QdQJ2PThygRDcpZ8VV1bhBrg+7ADf+PQdie\n"
                    + "ZXHjDTHCcjeeCKUz9bkB29bTg0b6cxaWo1Ki4TmtWrZi/lcI+8/CV6NuZnHLDKv0\n"
                    + "t12trCKiXhkfKrGY2WrnZZRmjhgLdzNSQmXWHX7POFYbQmAMmZFDLxx5Q04TkyCd\n"
                    + "f5gXaj4mfFb7VetQ02cN4GFsveRVBLvnlkhVIlVytlZYE7C16F0OdA2ubeTL6ZH9\n"
                    + "/N136XnWGIIPXJDxg89TpOjPnDsj4LS9yEf137MbOeMpEp0NakFKBUWkLY5w+RYo\n"
                    + "ufMY4MdiyEDLDKdHuyfH34Wn1mlxpFsKgmTCIvz8tZIuHsALMhblpbiRYeCWRZ7Y\n"
                    + "9YjFTE2zJROcBaeiHG4UzhiosuPI5aYMd6QM29Iql4lfuPh2Bzxa7TUKnmt2gRXG\n"
                    + "ZUiAQ8Y/sDyP8cMm75476gq9+KdCm0w+4kNB8IekGjkOyvSBjkenZz9zQyT7r+nO\n"
                    + "i+hAMT+swYbYIUPNmX6GHNSA1iRdir7yCtjxUWMNQJ7IWbTHCDMMtZyVTBYsXmLS\n"
                    + "qtolT8dQeH711cI9Jqmcnh0fRpncQDjzntVA8SCB9HR+SXmSLuWwDu4iqh6CV+63\n"
                    + "83SbO24E9kKsgU8IPrcNKYGjT0UFQRJLFA4pVFRzBBHyU50nhqUsAGF90eGH4vqU\n"
                    + "P3IgYVrmseS/EsSOeyW2XeEFxidum/liUHPpW9swHPQx3haxg3npQ2OsUNSq11qX\n"
                    + "sZYlpg71cVW1AvBbI4TUL4BYGUevbtZi2AxuyukF0Xg5crSvxQk3QAtr5Ow07R9O\n"
                    + "/LAN2O6Yj1tPqZTBtM7B6EdBgJVPJmdcDwSpts5U/Ela2b0f3+8lHjCjZ0M1wkXT\n"
                    + "6LynYcBfRuqWNAGJoZEqXR0cNfvTuXuAe24Dgriyz+uVyFBgAUGFNJ8DIuQ1ac+j\n"
                    + "1TazlI9qKLCna2wx6AI/sDnuzuMTfH8A+MjfxVE3/gqWNtkqCD1sj5iY7PrqwO0g\n"
                    + "qRPd6NR2O321KCoQrosg2ZnKU9vL/or4tFByzghxR78zzIl9SwCEgbe9ttowkEJB\n"
                    + "GjdcooRZlWO01ept89zyMCDofrVw2kaCB5at8ytj7ZQX4994oHpeMia5vv2oSlCt\n"
                    + "AkSZdh6LQ4tYxEerVvwem6EBSUxTt/Bt26scvk9OledbH5cZNA6/kKM916BWhIZr\n"
                    + "v0eKh36TX+qZ+5fhTH6MgosBdJ3Kc9YXNpRpp/xrW7LR8NkYBWU1ZIbI/QS/kJaI\n"
                    + "IvgNyFcTe/G0LIIfCrByE5Q9GSZlT9dShtCjXjXHXHyItcm6ZI8LzKNEmA4nKDuV\n" + "Ab+QvRBjItN5dvYp/g==\n"
                    + "-----END ENCRYPTED PRIVATE KEY-----";
            System.out.println(pemPrivateKey);
            Key key = pemPrivateKey != null ? RSAUtils.loadPrivateKeyFromPem(pemPrivateKey, "demo") : null;
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }

        System.out.println();

        {
            String pemPrivateKey = "-----BEGIN RSA PRIVATE KEY-----\n" + "Proc-Type: 4,ENCRYPTED\n"
                    + "DEK-Info: DES-EDE3-CBC,84517BCEC3B00BD2\n" + "\n"
                    + "MSexAG0Lml0fIp2HV62XmHGh5H5fc0wqRCMPOIWm6ZacWFYoon/NQS1m+2qIXutL\n"
                    + "opFpMeTVYVTWwt+jE9OEtaAv0AtYqZYCZmoiLXVDpSTj3QJkH1ChW7gCKeuAF5PC\n"
                    + "afAP6BGrBW46DWSHO/l1Di++9LwaDSfJcyrR1qXj1xHqTdMUUs1obDZvmNgpoHtw\n"
                    + "hEyvDrj2saiCXo7Vi2vlCAK0r8SAGJPU4EU0dXvDKJ3gbAO0ir96Mpkt418me9+M\n"
                    + "mW9CKKptzBQIH+0x124AyXI8xyiMe6MJSGHjAb9ikBThb66GnHscQbpHVtGgMdbL\n"
                    + "2U4t1N6nmzWKcDwrtUZ9b0P0hnGQR2GRfF5L7wRkrXceCfiZ3f7KlB12wBndGtFe\n"
                    + "voLfmo8D/iyn4GYdwxip6MQsBglu1IE4delR+EX8tw3jv4IaxoT3OGpVCWpRGC0H\n"
                    + "i2dKtXBpqXE2A6oavNzYjM288N/NgV86rCyFqGIRFyyBWYV30ETzvCAM5X1UrdOI\n"
                    + "1rJDfZcK4VH0gVx7E7wPl79+8YL4BRMJWJwb2rtzZtl3J9ZItnoClFfFhb33JdPA\n"
                    + "pzMBy1DHObVP6lQ6bHNYnSwA78Krqc725ydIbUO0zKSWONBk/e3QJr/Q50fi4+O2\n"
                    + "GQYpByzKE3ON99YgsRgAOGH+Fs9VexY0qpGPftTdbEvmXW2/wHa5i8KGMhHacVPz\n"
                    + "4K1ZILE27d1ZaRTCkDKc+Ky6fe8giLkwqM8wHcREnC5FCcp6bRqrSxF6KwSN1/oT\n"
                    + "ECfHoQM1QF7CVn9WFJMm6p9NQoWJ9mH8MVQEynV4UcH4+c12kux7CcNSy5npvNop\n"
                    + "bM2l2eaHC447jyFCG6NZKu4GshTjjG3AnNLC9G+LjTyDPNPBYNbB+Vtt5A/A/kVO\n"
                    + "9QV0eFqTyp3d71Pa8IYHlyTZdwwekU0qv1tOSmq4hjdV6Heg4E/3YXUBBU4QFOmG\n"
                    + "GM8hulv6YqCKZU1im7Z2suc/gzA6L8VBIUqINbGJx0Zwu4y90+hcyc224oCYPD8U\n"
                    + "m4ZkdSp/REd0aVVO9b/ewhOrsLVtgkf0eZsFQFaN9YYfjG2Ve3QfncxprPknf0Cz\n"
                    + "oXlnnPiBDFWnjdUWXtNsSsRufQIK3APdCiY+uvJliBJQp5sCwhqLfyIWagbuij8K\n"
                    + "3REMwCeeOsvD3EX9rsGB0GCclZE4rdm53AlTooBlIAJJsLBk8cmUoLWPeXGqPVjQ\n"
                    + "CCgaRbE4I4B4d1uUyUnTWp2Lyk/osCP+O11XWO5GSbxE0kdOfDT+AEKlMreV3qz+\n"
                    + "iJv7jrT9/8MBMMXs+JmyxRIXZ7c1FeycajTJ/eTpm6ml9Z5/ocyai5SCiNjn47sm\n"
                    + "8vTvrfBCXIqcNmStKBMQRLgGu15D7LmDTdBXY1jFiNsHIXs5tUXNMMQGqGRxdlwO\n"
                    + "q0OsNxcaSFESDinPdjhIgbH4UWvdOi2Vu+4D9X5+m3ihZhRUoXyQy7MjJZQZq0/C\n"
                    + "AHd2RomIERUXGxA7UwxCP/kiOll0wnwiOX7BHRdjWjJ2xUgBaQJyjqwrvU6gRDmR\n"
                    + "dADXBq1V/ArDE7Lcsgrhqtyq9Gq0RVprhkTYGc+MhDqoWtBqIr15Kg==\n" + "-----END RSA PRIVATE KEY-----";
            System.out.println(pemPrivateKey);
            Key key = pemPrivateKey != null ? RSAUtils.loadPrivateKeyFromPem(pemPrivateKey, "demo") : null;
            if (key != null) {
                printKeyInfo(key);
            } else {
                System.out.println("[null]");
            }
        }
    }
}

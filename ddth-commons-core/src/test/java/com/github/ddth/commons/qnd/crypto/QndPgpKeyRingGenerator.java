package com.github.ddth.commons.qnd.crypto;

import com.github.ddth.commons.crypto.PGPUtils;
import org.bouncycastle.openpgp.PGPKeyRingGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;

import java.util.Iterator;
import java.util.List;

public class QndPgpKeyRingGenerator {
    public static void main(String[] args) throws Exception {
        PGPKeyRingGenerator keyRingGenerator = PGPUtils.createPGPKeyRingGenerator(1024, "DDTH", "demo");
        {
            System.err.println(PGPUtils.exportPublicKey(keyRingGenerator, false));
            System.err.println(PGPUtils.exportPublicKey(keyRingGenerator, true));
        }
        {
            System.err.println(PGPUtils.exportSecretKey(keyRingGenerator, false));
            System.err.println(PGPUtils.exportSecretKey(keyRingGenerator, true));
        }
        {
            String pubKeyData = "-----BEGIN PGP PUBLIC KEY BLOCK-----\n" + "Version: BCPG v1.61\n" + "\n"
                    + "mQGhBFznsmkRBAD9f1OBHXUSKVLfSpwu7OTn9hG3UjzvRADDHj+AtlEmaUVdQCJR\n"
                    + "+1k9jVj6v8X1ujD2y5tVbNeBO4AdNG/yZmC3a5lQpaSfn+gEexAiwk+7qdf+t8Yb\n"
                    + "+DtX58aophUPBPuD9tPFHsMCNVQTWhaRMvZ1864rYdcq7/IiAxmd0UgBxwCgl2BQ\n"
                    + "jxUjC8yykrmCouuEC/BYHPUEAPfhoIXWmz3ey7yrXDa4V7l5lK+7+jrqgvlXTAs9\n"
                    + "B4JnUVlXjrrUWU/mcQcQgYC0SRZxI+hMKBYTt88JMozIpuE8FnqLVHyNKOCjrh4r\n"
                    + "s6Z1kW6jfwv6ITVi8ftiegEkO8yk8b6oUZCJqIPf4VrlnwaSi2ZegHtVJWQBTDv+\n"
                    + "z0kqA/jR7jtsKiU/xhYuTYb1sozH4RDC5fZnb9YTkX5ctMSJBnUoY+VzDId9diDJ\n"
                    + "JO3OrK4Lbw5t0Kmzoswa8dpBroepBHeuHA3EGGRuNQI0up4a44XZiAPE67vS/GNr\n"
                    + "4Z3GDcxOzqX3VFGcp7S5+5FsMQ1RES+JaDGBx3Bk3gRz8nqrtBBjb2Qtc3RhdGlv\n"
                    + "bi1wcm9kiEYEExECAAYFAlznsmkACgkQJNvHrbnzT06GFQCggaoMljEIpxPnGfwf\n"
                    + "IEyQGc/2t60An3bK438lqZd9NriOrFqm9O/1dTVyuQMMBFznsmkQCACVR1z12T5Z\n"
                    + "bD/NHZAq3QL0J/XzxyEDE7tF+01bsuX+HL1njNS73YTJg2vh8xwHd3Ja62wvw4uF\n"
                    + "9IB2+na82BRsyJpvsvcG3XGYmMIIPcjYlvhAYuLJyU0TewVKjYCWrbjVGVI5juyo\n"
                    + "UqCvEt+D5HWqZdTsDDipVg1WYRhv+Yufyetg7uiwMDdrI2vHO+Os29dP1hwdJHX6\n"
                    + "MHe48IBGeIH/fhylb+4GbXlQat5R7btUQ6Vjkn28S6UgCGdGF1yIhZJevGTGFHkG\n"
                    + "dzSWmQy3FOxmcwTiYfruM7PL3wCODD+pBlDZfTkJySdb9KyG/8s9A+bfyK2lk0JC\n"
                    + "3W07zKKkBssLB/9C3rudpbPYjMlW4IeH7D86Cbul9IuImnSq9TF0qg++fjxbj816\n"
                    + "U771Y7DphWAyiWCpUX9AFNMyX8eWK/HgSTcNdtExSnYTfnkvPw24WdCV5KW5MgJP\n"
                    + "B57PLvCceXRSsHcOE1B4LtV933lJedzvI8uW8YMGGWXE68k8nHHFa5JZVadflMzP\n"
                    + "FEmsQ9WG0L7uQyUbCyKHNJ1o3g0URAPxPoAvQUbYguBXrxm29idcZnbI+g48onE6\n"
                    + "Mlf9GyfQY59pXjR9jRz5rIGaJsqbBMsOubewNZiNFbusZSEqVSOc/H5Y+uONclCr\n"
                    + "mZH/vJcTQCX+jOBMQ5mtllab6RpUb0l4aTx6B/0TmLED3cUrG5zz5K2cm28QUTr9\n"
                    + "tiXAyk9O4E9yoppct8+XqnPjXOS+4qiYJPRzEhdm7sdMVN03fcw2SRtbUOWIKfET\n"
                    + "X8zdfajgG/WCLHwlsokDJ6Eo7h6FewmdLCdvw54O8RcJMvlWE5XJiZDNRnlK9pr+\n"
                    + "hcJMzJ71SNjpqyLK5RZSCXcjpSDK/tpCKt4saTO5QFT8d2nfHLTBQ8anHt27lo7P\n"
                    + "y4Xgd0Z71hKAxu1dzh2V5m7Wsk5prgwz5F5OH6kN5uvyVcScD4jxILXElB1Ec8JZ\n"
                    + "Zm7DsguWzE8wQXEA5IfxPaWPes8Z2fKXkzzHV05ZozjTGQGi+wkGrLqKp9IjiEYE\n"
                    + "GBECAAYFAlznsmkACgkQJNvHrbnzT04UngCcCgo5fPlcpCMYwrCIG01HJYTczY8A\n"
                    + "nAjOy/l2y/YthVjmnOBhDMY9Muw1\n" + "=Z8Y1\n" + "-----END PGP PUBLIC KEY BLOCK-----";
            List<PGPPublicKey> pubKeys = PGPUtils.loadPublicKeys(pubKeyData, true);
            for (PGPPublicKey pubKey : pubKeys) {
                System.out.println("KeyID     : " + pubKey.getKeyID());
                System.out.println("Encryption: " + pubKey.isEncryptionKey());
                System.out.println("Master    : " + pubKey.isMasterKey());
                System.out.println("Bits      : " + pubKey.getBitStrength());
                Iterator<String> it = pubKey.getUserIDs();
                while (it.hasNext()) {
                    System.out.println("\t" + it.next());
                }
                System.out.println();
            }
        }
    }
}

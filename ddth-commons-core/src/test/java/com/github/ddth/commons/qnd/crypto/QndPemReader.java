package com.github.ddth.commons.qnd.crypto;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.StringReader;

public class QndPemReader {
    public static void main(String[] args) throws IOException {
        String pubKey64 = "-----BEGIN PGP PUBLIC KEY BLOCK-----\n"
                + "mQENBFzQ+qcBCADjagD2DFDh+hP7xumVPFQHX1AvUYXSVhnoKJ839wdnZK4l1/gd\n"
                + "YosGZj+cbTP4uQyQd3hragbP2HhBFuqgHJJIXNIbeSt4SZayMOard+Ed+C1437f9\n"
                + "cqwuATeN17YjyftflcmZ1HuZTDn8nrLl0hUQuX9mmew8xUbNN/8L5NqZ5RlWj63Z\n"
                + "b5ei7owcDI6BfF3oggt96kdP7c0LHMJaLrTzJjZL5SnRT9Pw6N4IK9O8A5X/LIuA\n"
                + "r8qRnZe6o0ffmTgUs/UH5jvkDwgZRFJB28KmLJg3thkqCM+TzqwOAVBhWEqHdg4u\n"
                + "GUn8dnEh7robB3NqgX1BeddBaJDrGKF3YcVZABEBAAG0MEFQSVNURyBDT0QgKEFQ\n"
                + "SVNURyBDT0QpIDxhcGlzdGcuY29kQGFoYW1vdmUuY29tPokBOAQTAQIAIgUCXND6\n"
                + "pwIbAwYLCQgHAwIGFQgCCQoLBBYCAwECHgECF4AACgkQCFxigMgMJ+plZQf+NT3t\n"
                + "tr3LZCV09X/1W8Sasw4eMybU/spUIN8lcClbB5rBWJcT3zss3XHpZ1z/UZ5ERW1C\n"
                + "gWbNXknRPC+y6tIepfzoT9UNsDfIUfxhO5N/PE9JiVicvNRECaCQ2SoY0S6fmurB\n"
                + "gHRKgbJOv/QIJ7xDa6TgcQwkc0Kknah5Rsyao8w9QOCW+/82khGuJ4Kxb81WxK3y\n"
                + "DkcU+HMZUatPF3e9aBr2XLzdZEU6mm1mZHc+cELw40fBC/zUaHcpQ2cAoJy5NHL9\n"
                + "xuLgsqjvgUSfRweM5+0lfF+576aQzYpyfcesEKccGpRbborhCxaj86PNIMTKCUtY\n"
                + "dGOFHfba+fxhEBEcFbkBDQRc0PqnAQgAvlXJhYKUqn+HiYCizPdJsOM/PrZt/UrC\n"
                + "+AMvoCT77F4QvgYDd6OB9V9+n0OtCpK0MUKiHCT7K+h2czmDpnHMzM73A5r8mbnC\n"
                + "xvG8J1K6ms3ZkfX6CVuhrhalVfwKz7TA1WXEgEuVss1qFdFF1+MEHTOJQVjXKNwH\n"
                + "RqZUp4olwmjmu3SHzPVeb6a7zB7bvXlRMu/H1FCH4rWApHzrwfeYd1etaogfS77W\n"
                + "eWKQsiGX/fxmT6X8jyiszKEluW1Ibm2rjc3k9rBvYmrTmivD/yoJANFcm6IHA5RC\n"
                + "GKaY4TXaR5yTww+l/GvBiNDk7M7dnS+pS7VfJCbesUtiNwrA/LUKTQARAQABiQEf\n"
                + "BBgBAgAJBQJc0PqnAhsMAAoJEAhcYoDIDCfqkhAH/3obcLd+CmhgdLdIkwNLFl70\n"
                + "DTEoJHejVTwy7lYAhrY2VSnyUZLbu/wc0/oYEaCuprVzUJb2Wt6nVvrVkGyHw+sJ\n"
                + "HN1+ynpLSeXRlcueY6qH9X4zapPlrDAIc9EbO63olLrQXI0JJ8Q1/Ghs7LpbUX15\n"
                + "Zc6q43qvE0hWai2DQL1EVOXuQ2UZQLknoaCHTZU+9grx2+xpldCSR5DEDNUIcUsh\n"
                + "Ej69OrQNvRLs87xcF1G3w3CqzM4DC5RVe5fgt5ZLbLPsc2af7Mn6DdkgnP2JVYqk\n"
                + "ESv98u0bAT9I5yk0xdMUFPrgr4a9OzZlXVoozTYLixGrc90tJPFxyyI92wNby6w=\n" + "=5GXR\n"
                + "-----END PGP PUBLIC KEY BLOCK-----\n";
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

        try (PemReader pr = new PemReader(new StringReader(pubKey64))) {
            PemObject po = pr.readPemObject();
            if (po != null) {
                System.out.println(po.getType());
                System.out.println(po.getHeaders());
            }
        }
    }
}

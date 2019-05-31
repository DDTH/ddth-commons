# com.github.ddth.commons.crypto

Cryptography-related utility and helper classes.

_**Available since v1.1.0.**_

**Maven**

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-crypto</artifactId>
    <version>${ddth_commons_version}</version>
</dependency>
```

### AESUtils

AES encryption utility class.

- Default: 128-bit encryption key
- Default: `AES/CTR/NoPadding` transformation
- Support custom transformation and IV

See [Java Cryptography Architecture Oracle Providers Documentation for JDK 8](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#cipherTable)

**New since v0.9.2**

- Support all AES cipher modes provided by [SunJCE Provider](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#cipherTable).
- Change `DEFAULT_CIPHER_TRANSFORMATION` to `AES/CTR/NoPadding`
- New methods:
  - `String randomIV(int)` and `byte[] randomIVAsBytes(int)`
  - `byte[] randomKeyAsBytesSecure()`, `byte[] randomIVAsBytesSecure()` and `byte[] randomIVAsBytesSecure(int)`
  - `Cipher createCipher(int, byte[], byte[], String)`
  - `encrypt(...)` and `decrypt(...)` that support encrypting/decrypting stream of large data.
- New classes `CipherException`, `DdthCipherInputStream` and  `DdthCipherOutputStream`.


### KeyPairUtils

Utility class to generate key pairs.

- Generate key pairs.
- Export public key to X509 pem format.
- Export private key to PKCS#8 pem format.
- Load public/public key from pem format.


## PGPUtils

PGP encryption & signing utility class.

- Generate PGP key pairs.
- Export PGP public/private keys to ASCII-armor format.
- Load PGP public/private keys from ASCII-armor format.
- Encrypt/Decrypt/Sign/Verify message using PGP keys.


### RSAUtils

RSA encryption utility class.

- Support: 512, 1024, 2048 bit encryption key
- Default: `RSA/ECB/PKCS1Padding` transformation (11-byte padding size)
- Support custom transformation and padding size

**New since v0.9.2**

- Unit tested with all RSA cipher modes provided by [SunJCE Provider](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#cipherTable).
- Unit tested with all RSA signature algorithms provides by [SunRsaSign](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#SunRsaSignProvider).

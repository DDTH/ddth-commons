package com.github.ddth.commons.utils;

import java.nio.charset.Charset;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * Helper class to calculate hash values.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class HashUtils {
    private final static HashFunction linearHash = Hashing.murmur3_128(0);
    private final static HashFunction crc32 = Hashing.crc32();
    private final static HashFunction md5 = Hashing.md5();
    private final static HashFunction sha1 = Hashing.sha1();
    private final static HashFunction sha256 = Hashing.sha256();
    private final static HashFunction sha512 = Hashing.sha512();

    private final static Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Calculates hash value of an object using a fast
     * non-cryptographic-strength hash function.
     * 
     * @param object
     * @return
     */
    public static long fastHashValue(Object object) {
        if (object == null) {
            return 0;
        }
        HashFunction hashFunction = linearHash;
        if (object instanceof Boolean || object instanceof Number || object instanceof String) {
            return hashFunction.hashString(object.toString(), UTF8).asLong();
        }
        return hashFunction.hashInt(object.hashCode()).asLong();
    }

    /**
     * Maps an object to a bucket, using linear hash method.
     * 
     * This method uses a fast non-cryptographic-strength hash function to
     * calculate object's hash value.
     * 
     * @param object
     * @param numBuckets
     * @return the slot index (0 to numSlots-1)
     */
    public static long linearHashingMap(Object object, int numBuckets) {
        if (numBuckets < 1) {
            String msg = "Number of slots must be equal or larger than 1!";
            throw new IllegalArgumentException(msg);
        }
        if (numBuckets == 1 || object == null) {
            return 0;
        }
        return Math.abs(fastHashValue(object) % (long) numBuckets);
    }

    /**
     * Maps an object to a bucket, using consistent hash method.
     * 
     * This method uses a fast non-cryptographic-strength hash function to
     * calculate object's hash value.
     * 
     * @param object
     * @param numBucket
     * @return the slot index (0 to numSlots-1)
     */
    public static long consistentHashingMap(Object object, int numBucket) {
        if (numBucket < 1) {
            String msg = "Number of slots must be equal or larger than 1!";
            throw new IllegalArgumentException(msg);
        }
        if (numBucket == 1 || object == null) {
            return 0;
        }
        long hashValue = fastHashValue(object);
        return Hashing.consistentHash(hashValue, numBucket);
    }

    /**
     * Calculates CRC32-hash value of a string.
     * 
     * @param value
     * @return
     */
    public static String crc32(String value) {
        if (value == null) {
            return null;
        }
        return crc32.hashString(value, UTF8).toString().toLowerCase();
    }

    /**
     * Calculates CRC32-hash value of a byte array.
     * 
     * @param value
     * @return
     */
    public static String crc32(byte[] value) {
        if (value == null) {
            return null;
        }
        return crc32.hashBytes(value).toString().toLowerCase();
    }

    /**
     * Calculates MD5-hash value of a string.
     * 
     * @param value
     * @return
     */
    public static String md5(String value) {
        if (value == null) {
            return null;
        }
        return md5.hashString(value, UTF8).toString().toLowerCase();
    }

    /**
     * Calculates MD5-hash value of a byte array.
     * 
     * @param value
     * @return
     */
    public static String md5(byte[] value) {
        if (value == null) {
            return null;
        }
        return md5.hashBytes(value).toString().toLowerCase();
    }

    /**
     * Calculates SHA1-hash value of a string.
     * 
     * @param value
     * @return
     */
    public static String sha1(String value) {
        if (value == null) {
            return null;
        }
        return sha1.hashString(value, UTF8).toString().toLowerCase();
    }

    /**
     * Calculates SHA1-hash value of a byte array.
     * 
     * @param value
     * @return
     */
    public static String sha1(byte[] value) {
        if (value == null) {
            return null;
        }
        return sha1.hashBytes(value).toString().toLowerCase();
    }

    /**
     * Calculates SHA256-hash value of a string.
     * 
     * @param value
     * @return
     */
    public static String sha256(String value) {
        if (value == null) {
            return null;
        }
        return sha256.hashString(value, UTF8).toString().toLowerCase();
    }

    /**
     * Calculates SHA256-hash value of a byte array.
     * 
     * @param value
     * @return
     */
    public static String sha256(byte[] value) {
        if (value == null) {
            return null;
        }
        return sha256.hashBytes(value).toString().toLowerCase();
    }

    /**
     * Calculates SHA512-hash value of a string.
     * 
     * @param value
     * @return
     */
    public static String sha512(String value) {
        if (value == null) {
            return null;
        }
        return sha512.hashString(value, UTF8).toString().toLowerCase();
    }

    /**
     * Calculates SHA512-hash value of a byte array.
     * 
     * @param value
     * @return
     */
    public static String sha512(byte[] value) {
        if (value == null) {
            return null;
        }
        return sha512.hashBytes(value).toString().toLowerCase();
    }
}

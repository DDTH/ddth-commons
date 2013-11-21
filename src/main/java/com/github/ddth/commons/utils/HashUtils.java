package com.github.ddth.commons.utils;

import java.nio.charset.Charset;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * Helper class to calculate hash values.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class HashUtils {
    private final static ObjectPool<HashFunction> poolLinearHash = new GenericObjectPool<HashFunction>(
            new BasePoolableObjectFactory<HashFunction>() {
                @Override
                public HashFunction makeObject() throws Exception {
                    return Hashing.murmur3_128(0);
                }
            });
    private final static ObjectPool<HashFunction> poolCrc32 = new GenericObjectPool<HashFunction>(
            new BasePoolableObjectFactory<HashFunction>() {
                @Override
                public HashFunction makeObject() throws Exception {
                    return Hashing.crc32();
                }
            });
    private final static ObjectPool<HashFunction> poolMd5 = new GenericObjectPool<HashFunction>(
            new BasePoolableObjectFactory<HashFunction>() {
                @Override
                public HashFunction makeObject() throws Exception {
                    return Hashing.md5();
                }
            });
    private final static ObjectPool<HashFunction> poolSha1 = new GenericObjectPool<HashFunction>(
            new BasePoolableObjectFactory<HashFunction>() {
                @Override
                public HashFunction makeObject() throws Exception {
                    return Hashing.sha1();
                }
            });
    private final static ObjectPool<HashFunction> poolSha256 = new GenericObjectPool<HashFunction>(
            new BasePoolableObjectFactory<HashFunction>() {
                @Override
                public HashFunction makeObject() throws Exception {
                    return Hashing.sha256();
                }
            });
    private final static ObjectPool<HashFunction> poolSha512 = new GenericObjectPool<HashFunction>(
            new BasePoolableObjectFactory<HashFunction>() {
                @Override
                public HashFunction makeObject() throws Exception {
                    return Hashing.sha512();
                }
            });
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
        try {
            HashFunction hashFunction = poolLinearHash.borrowObject();
            try {
                if (object instanceof Boolean || object instanceof Number
                        || object instanceof String) {
                    return hashFunction.hashString(object.toString(), UTF8).asLong();
                }
                return hashFunction.hashInt(object.hashCode()).asLong();
            } finally {
                poolLinearHash.returnObject(hashFunction);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction crc32 = poolCrc32.borrowObject();
            try {
                return crc32.hashString(value, UTF8).toString().toLowerCase();
            } finally {
                poolCrc32.returnObject(crc32);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction crc32 = poolCrc32.borrowObject();
            try {
                return crc32.hashBytes(value).toString().toLowerCase();
            } finally {
                poolCrc32.returnObject(crc32);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction md5 = poolMd5.borrowObject();
            try {
                return md5.hashString(value, UTF8).toString().toLowerCase();
            } finally {
                poolMd5.returnObject(md5);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction md5 = poolMd5.borrowObject();
            try {
                return md5.hashBytes(value).toString().toLowerCase();
            } finally {
                poolMd5.returnObject(md5);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction sha1 = poolSha1.borrowObject();
            try {
                return sha1.hashString(value, UTF8).toString().toLowerCase();
            } finally {
                poolSha1.returnObject(sha1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction sha1 = poolSha1.borrowObject();
            try {
                return sha1.hashBytes(value).toString().toLowerCase();
            } finally {
                poolSha1.returnObject(sha1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction sha256 = poolSha256.borrowObject();
            try {
                return sha256.hashString(value, UTF8).toString().toLowerCase();
            } finally {
                poolSha256.returnObject(sha256);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction sha256 = poolSha256.borrowObject();
            try {
                return sha256.hashBytes(value).toString().toLowerCase();
            } finally {
                poolSha256.returnObject(sha256);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction sha512 = poolSha512.borrowObject();
            try {
                return sha512.hashString(value, UTF8).toString().toLowerCase();
            } finally {
                poolSha512.returnObject(sha512);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            HashFunction sha512 = poolSha512.borrowObject();
            try {
                return sha512.hashBytes(value).toString().toLowerCase();
            } finally {
                poolSha512.returnObject(sha512);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

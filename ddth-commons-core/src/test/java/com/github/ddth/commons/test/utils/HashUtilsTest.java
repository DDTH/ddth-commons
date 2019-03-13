package com.github.ddth.commons.test.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Assert;

import com.github.ddth.commons.utils.HashUtils;
import com.google.common.hash.HashFunction;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HashUtilsTest extends TestCase {

    public HashUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(HashUtilsTest.class);
    }

    private void testChecksumEquals(HashFunction hf, Object left, Object right) {
        Assert.assertEquals(HashUtils.checksum(left, hf), HashUtils.checksum(right, hf));
    }

    private void testChecksumNotEquals(HashFunction hf, Object left, Object right) {
        Assert.assertNotEquals(HashUtils.checksum(left, hf), HashUtils.checksum(right, hf));
    }

    private final static HashFunction[] HF_LIST = { HashUtils.crc32, HashUtils.fastHashFunc,
            HashUtils.md5, HashUtils.murmur3, HashUtils.murmur332bit, HashUtils.sha1,
            HashUtils.sha256, HashUtils.sha512 };

    @org.junit.Test
    public void testChecksumBigIntegerBigDecimal() {
        for (HashFunction hf : HF_LIST) {
            testChecksumNotEquals(hf, BigInteger.valueOf(1981), BigDecimal.valueOf(1981));
            testChecksumNotEquals(hf, BigInteger.valueOf(1981), BigDecimal.valueOf(1981.0));
        }
    }

    @org.junit.Test
    public void testChecksumFloatDouble() {
        for (HashFunction hf : HF_LIST) {
            testChecksumEquals(hf, Float.valueOf(1981), Double.valueOf(1981));
        }
    }

    @org.junit.Test
    public void testChecksumByteShortIntegerLong() {
        Object[] data = { (byte) 81, (short) 81, (int) 81, (long) 81 };
        for (HashFunction hf : HF_LIST) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    testChecksumEquals(hf, data[i], data[j]);
                }
            }
        }
    }

    @org.junit.Test
    public void testChecksumByteShortIntegerLongFloatDouble() {
        Object[] left = { (byte) 81, (short) 81, (int) 81, (long) 81 };
        Object[] right = { (float) 81, (double) 81 };
        for (HashFunction hf : HF_LIST) {
            for (int i = 0; i < left.length; i++) {
                for (int j = 0; j < right.length; j++) {
                    testChecksumNotEquals(hf, left[i], right[j]);
                }
            }
        }
    }

    private final static byte[] byteArr = { 1, 2, 3, 4, 5 };
    private final static short[] shortArr = { 1, 2, 3, 4, 5 };
    private final static int[] intArr = { 1, 2, 3, 4, 5 };
    private final static long[] longArr = { 1L, 2L, 3L, 4L, 5L };

    private final static Byte[] ByteArr = { 1, 2, 3, 4, 5 };
    private final static Short[] ShortArr = { 1, 2, 3, 4, 5 };
    private final static Integer[] IntegerArr = { 1, 2, 3, 4, 5 };
    private final static Long[] LongArr = { 1L, 2L, 3L, 4L, 5L };

    private final static List<Byte> byteList = Arrays.asList(ByteArr);
    private final static List<Short> shortList = Arrays.asList(ShortArr);
    private final static List<Integer> intList = Arrays.asList(IntegerArr);
    private final static List<Long> longList = Arrays.asList(LongArr);

    private final static float[] floatArr = { 1f, 2f, 3f, 4f, 5f };
    private final static double[] doubleArr = { 1d, 2d, 3d, 4d, 5d };
    private final static Float[] FloatArr = { 1f, 2f, 3f, 4f, 5f };
    private final static Double[] DoubleArr = { 1d, 2d, 3d, 4d, 5d };
    private final static List<Float> floatList = Arrays.asList(FloatArr);
    private final static List<Double> doubleList = Arrays.asList(DoubleArr);

    @org.junit.Test
    public void testChecksumIntegerArrayList() {
        Object[] data = { byteArr, shortArr, intArr, longArr, ByteArr, ShortArr, IntegerArr,
                LongArr, byteList, shortList, intList, longList };
        for (HashFunction hf : HF_LIST) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    testChecksumEquals(hf, data[i], data[j]);
                }
            }
        }
    }

    @org.junit.Test
    public void testChecksumFloatArrayList() {
        Object[] data = { floatArr, doubleArr, FloatArr, DoubleArr, floatList, doubleList };
        for (HashFunction hf : HF_LIST) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    testChecksumEquals(hf, data[i], data[j]);
                }
            }
        }
    }

    @org.junit.Test
    public void testChecksumByteShortIntegerLongListFloatDoubleList() {
        Object[] left = { byteArr, shortArr, intArr, longArr, ByteArr, ShortArr, IntegerArr,
                LongArr, byteList, shortList, intList, longList };
        Object[] right = { floatArr, doubleArr, FloatArr, DoubleArr, floatList, doubleList };
        for (HashFunction hf : HF_LIST) {
            for (int i = 0; i < left.length; i++) {
                for (int j = 0; j < right.length; j++) {
                    testChecksumNotEquals(hf, left[i], right[j]);
                }
            }
        }
    }

    private static class MyClass {
        public int age = 0;
        public String name = "";
        public boolean gender = false;

        public int hashCode() {
            HashCodeBuilder hcb = new HashCodeBuilder();
            hcb.append(age).append(name).append(gender);
            return hcb.hashCode();
        }
    }

    private final static Map<String, Object> mapBase = new HashMap<>();
    static {
        mapBase.put("key1", "value1");
        mapBase.put("key2", 2);
        mapBase.put("key3", 3.0);
        mapBase.put("key4", true);
        mapBase.put("key5", new MyClass());
    }
    private final static Map<String, Object> map1 = new HashMap<>(mapBase);
    private final static Map<String, Object> map2 = new TreeMap<>(mapBase);
    private final static Map<String, Object> map3 = new ConcurrentHashMap<>(mapBase);
    private final static Map<String, Object> map4 = new LinkedHashMap<>(mapBase);
    private final static Map<String, Object> map5 = new ConcurrentSkipListMap<>(mapBase);

    @org.junit.Test
    public void testChecksumMap() {
        Object[] data = { map1, map2, map3, map4, map5 };
        for (HashFunction hf : HF_LIST) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    testChecksumEquals(hf, data[i], data[j]);
                }
            }
        }
    }

    private final static Collection<Byte> byteCollection = new HashSet<>(byteList);
    private final static Collection<Short> shortCollection = new TreeSet<>(shortList);
    private final static Collection<Integer> intCollection = new ConcurrentSkipListSet<>(intList);
    private final static Collection<Long> longCollection = new LinkedHashSet<>(longList);

    @org.junit.Test
    public void testChecksumCollection() {
        Object[] data = { byteCollection, shortCollection, intCollection, longCollection };
        for (HashFunction hf : HF_LIST) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    testChecksumEquals(hf, data[i], data[j]);
                }
            }
        }
    }
}

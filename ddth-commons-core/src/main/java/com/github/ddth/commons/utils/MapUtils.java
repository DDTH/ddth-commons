package com.github.ddth.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * Utility class to work with {@link Map&lt;String, Object&gt;}s.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.6.1
 */
public class MapUtils {

    /**
     * Extract a value from a map.
     * 
     * @param map
     * @param key
     * @param clazz
     * @return
     */
    public static <T> T getValue(Map<String, Object> map, String key, Class<T> clazz) {
        return map != null ? ValueUtils.convertValue(map.get(key), clazz) : null;
    }

    /**
     * Create a {@link Map&lt;String, Object&gt;} from flat array of objects.
     * 
     * @param keysAndValues
     * @return
     */
    public static Map<String, Object> createMap(Object... keysAndValues) {
        if (keysAndValues == null) {
            return null;
        }
        if (keysAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Number of arguments must be even!");
        }
        return new LinkedHashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                for (int i = 0, n = keysAndValues.length / 2; i < n; i++) {
                    put(keysAndValues[i * 2].toString(), keysAndValues[i * 2 + 1]);
                }
            }
        };
    }

    /**
     * Calculate checksum of a map.
     * 
     * <p>
     * Checksum is independent of order of map's keys. I.e. two maps with same keys/values will
     * yield the same checksum regardless order of map's keys.
     * </p>
     * 
     * @param map
     * @return
     * @since 0.6.2
     */
    public static long checksum(Map<?, ?> map) {
        final List<HashCode> hashCodes = new ArrayList<>();
        final HashFunction hashFunc = Hashing.murmur3_128(0);
        for (Entry<?, ?> entry : map.entrySet()) {
            hashCodes.add(hashFunc.newHasher().putInt(entry.getKey().hashCode())
                    .putInt(entry.getValue().hashCode()).hash());
        }
        return Hashing.combineUnordered(hashCodes).padToLong();
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

    public static void main(String[] args) {
        Map<String, Object> mapBase = new HashMap<String, Object>();
        mapBase.put("key1", "value1");
        mapBase.put("key2", 2);
        mapBase.put("key3", 3.0);
        mapBase.put("key4", true);
        mapBase.put("key5", new MyClass());
        System.out.println(checksum(mapBase));

        Map<String, Object> map1 = new HashMap<>(mapBase);
        System.out.println(checksum(map1));

        Map<String, Object> map2 = new TreeMap<>(mapBase);
        System.out.println(checksum(map2));

        Map<String, Object> map3 = new ConcurrentHashMap<>(mapBase);
        System.out.println(checksum(map3));

        Map<String, Object> map4 = new LinkedHashMap<>(mapBase);
        System.out.println(checksum(map4));

        Map<String, Object> map5 = new ConcurrentSkipListMap<>(mapBase);
        System.out.println(checksum(map5));
    }
}

package com.github.ddth.commons.utils;

import java.util.LinkedHashMap;
import java.util.Map;

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

}

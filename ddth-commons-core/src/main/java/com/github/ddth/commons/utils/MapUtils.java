package com.github.ddth.commons.utils;

import java.util.Date;
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
     * Extract a date value from the map. If the extracted value is
     * a string, parse it as a {@link Date} using the specified date-time format.
     * 
     * @param map
     * @param key
     * @param dateTimeFormat
     * @return
     * @since 0.6.3.1
     */
    public static Date getDate(Map<String, Object> map, String key, String dateTimeFormat) {
        Object obj = map.get(key);
        return obj instanceof Number ? new Date(((Number) obj).longValue())
                : (obj instanceof String
                        ? DateFormatUtils.fromString(obj.toString(), dateTimeFormat)
                        : (obj instanceof Date ? (Date) obj : null));
    }

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
     * Create a {@link Map} from flat array of objects.
     * 
     * @param keysAndValues
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K extends Object, V extends Object> Map<K, V> createMap(
            Object... keysAndValues) {
        if (keysAndValues == null) {
            return null;
        }
        if (keysAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Number of arguments must be even!");
        }
        Map<K, V> result = new LinkedHashMap<>();
        for (int i = 0, n = keysAndValues.length / 2; i < n; i++) {
            result.put((K) keysAndValues[i * 2], (V) keysAndValues[i * 2 + 1]);
        }
        return result;
    }

    /**
     * Remove null values from map.
     * 
     * @param map
     * @return
     * @since 0.9.0
     */
    public static <K, V> Map<K, V> removeNulls(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            if (v != null) {
                result.put(k, v);
            }
        });
        return result;
    }

}

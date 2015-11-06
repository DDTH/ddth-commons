package com.github.ddth.commons.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility to access data from a hierarchy structure.
 * 
 * <p>
 * Notation:
 * </p>
 * 
 * <ul>
 * <li><code>.</code> (the dot character): path separator</li>
 * <li><code>name</code>: access a map's attribute specified by
 * <code>name</code></li>
 * <li><code>[i]</code>: access i'th element of a list/array (0-based)</li>
 * </ul>
 * (example: <code>employees.[1].first_name</code>).
 * 
 * <p>
 * Sample usage: assuming you have the following data structure:
 * </p>
 * 
 * <pre>
 * Map&lt;String, Object&gt; company = new HashMap&lt;String, Object&gt;();
 * company.put(&quot;name&quot;, &quot;Monster Corp.&quot;);
 * company.put(&quot;year&quot;, 2003);
 * 
 * List&lt;Map&lt;String, Object&gt;&gt; employees = new ArrayList&lt;Map&lt;String, Object&gt;&gt;();
 * company.put(&quot;employees&quot;, employees);
 * 
 * Map&lt;String, Object&gt; employee1 = new HashMap&lt;String, Object&gt;();
 * employee1.put(&quot;first_name&quot;, &quot;Mike&quot;);
 * employee1.put(&quot;last_name&quot;, &quot;Wazowski&quot;);
 * employee1.put(&quot;email&quot;, &quot;mike.wazowski@monster.com&quot;);
 * employee1.put(&quot;age&quot;, 29);
 * employees.add(employee1);
 * 
 * Map&lt;String, Object&gt; employee2 = new HashMap&lt;String, Object&gt;();
 * employee2.put(&quot;first_name&quot;, &quot;Sulley&quot;);
 * employee2.put(&quot;last_name&quot;, &quot;Sullivan&quot;);
 * employee2.put(&quot;email&quot;, &quot;sulley.sullivan@monster.com&quot;);
 * employee2.put(&quot;age&quot;, 30);
 * employees.add(employee2);
 * </pre>
 * 
 * <p>
 * You can access company's attributes:
 * </p>
 * 
 * <pre>
 * String companyName = DPathUtils.getValue(company, &quot;name&quot;, String.class);
 * // got string &quot;Monster Corp.&quot;
 * 
 * Integer companyYear = DPathUtils.getValue(company, &quot;year&quot;, Integer.class);
 * // got integer 2003
 * </pre>
 * 
 * <p>
 * You can access the two employee:
 * </p>
 * 
 * <pre>
 * Object user1 = DPathUtils.getValue(company, &quot;employees.[0]&quot;);
 * Map&lt;String, Object&gt; user2 = DPathUtils.getValue(company, &quot;employees.[1]&quot;, Map.class);
 * </pre>
 * 
 * <p>
 * Or, employee's attributes:
 * </p>
 * 
 * <pre>
 * String firstName1 = DPathUtils.getValue(company, &quot;employees.[0].first_name&quot;, String.class);
 * Object email2 = DPathUtils.getValue(company, &quot;employees.[1].email&quot;);
 * Long age2 = DPathUtils.getValue(company, &quot;employees.[1].age&quot;, Long.class);
 * </pre>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class DPathUtils {

    private final static Pattern PATTERN_INDEX = Pattern.compile("^\\[(\\-?\\d+)\\]$");

    @SuppressWarnings("unchecked")
    private static <N> N _convertNumber(Object target, Class<N> clazz) {
        if (clazz == Byte.class || clazz == byte.class) {
            byte value = target instanceof Number ? ((Number) target).byteValue() : Byte
                    .parseByte(target.toString());
            return (N) Byte.valueOf(value);
        }
        if (clazz == Short.class || clazz == short.class) {
            short value = target instanceof Number ? ((Number) target).shortValue() : Short
                    .parseShort(target.toString());
            return (N) Short.valueOf(value);
        }
        if (clazz == Integer.class || clazz == int.class) {
            int value = target instanceof Number ? ((Number) target).intValue() : Integer
                    .parseInt(target.toString());
            return (N) Integer.valueOf(value);
        }
        if (clazz == Long.class || clazz == long.class) {
            long value = target instanceof Number ? ((Number) target).longValue() : Long
                    .parseLong(target.toString());
            return (N) Long.valueOf(value);
        }
        if (clazz == Float.class || clazz == float.class) {
            float value = target instanceof Number ? ((Number) target).floatValue() : Float
                    .parseFloat(target.toString());
            return (N) Float.valueOf(value);
        }
        if (clazz == Double.class || clazz == double.class) {
            double value = target instanceof Number ? ((Number) target).doubleValue() : Double
                    .parseDouble(target.toString());
            return (N) Double.valueOf(value);
        }
        if (clazz == BigInteger.class) {
            if (target instanceof BigInteger) {
                return (N) target;
            }
            if (target instanceof Number) {
                return (N) BigInteger.valueOf(((Number) target).longValue());
            }
            return (N) BigInteger.valueOf(Long.parseLong(target.toString()));
        }
        if (clazz == BigDecimal.class) {
            if (target instanceof BigDecimal) {
                return (N) target;
            }
            if (target instanceof Number) {
                return (N) BigDecimal.valueOf(((Number) target).doubleValue());
            }
            return (N) BigDecimal.valueOf(Double.parseDouble(target.toString()));
        }
        return null;
    }

    private static Boolean _convertBoolean(Object target) {
        if (target instanceof Boolean) {
            return (Boolean) target;
        }
        return Boolean.parseBoolean(target.toString());
    }

    /*
     * @since 0.2.2.1
     */
    private static Character _convertChar(Object target) {
        if (target instanceof Character) {
            return (Character) target;
        }
        if (target instanceof String) {
            String temp = (String) target;
            return temp.length() > 0 ? temp.charAt(0) : null;
        }
        if (target instanceof Number) {
            return (char) ((Number) target).shortValue();
        }
        return null;
    }

    /*
     * @since 0.2.2.2
     */
    private static Date _convertDate(Object target) {
        if (target instanceof Date) {
            return (Date) target;
        }
        if (target instanceof Number) {
            return new Date(((Number) target).longValue());
        }
        DateFormat df = new SimpleDateFormat();
        try {
            return df.parse(target.toString());
        } catch (ParseException e) {
            return null;
        }
    }

    /*
     * @since 0.3.0.6
     */
    private static List<?> _convertArrayOrList(Object target) {
        if (target instanceof Object[]) {
            return Arrays.asList((Object[]) target);
        }
        if (target instanceof Collection) {
            return new ArrayList<Object>((Collection<?>) target);
        }
        return null;
    }

    /**
     * Extracts a value from the target object using DPath expression (generic
     * version).
     * 
     * @param target
     * @param dPath
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(final Object target, final String dPath, final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class parameter is null!");
        }
        Object temp = getValue(target, dPath);
        if (temp == null) {
            return null;
        }
        if (Number.class.isAssignableFrom(clazz) || byte.class == clazz || short.class == clazz
                || int.class == clazz || long.class == clazz || float.class == clazz
                || double.class == clazz) {
            return _convertNumber(temp, clazz);
        }
        if (clazz == Boolean.class || clazz == boolean.class) {
            return (T) _convertBoolean(temp);
        }
        if (clazz == Character.class || clazz == char.class) {
            return (T) _convertChar(temp);
        }
        if (Date.class.isAssignableFrom(clazz)) {
            return (T) _convertDate(temp);
        }
        if (Object[].class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz)) {
            return (T) _convertArrayOrList(temp);
        }
        if (clazz.isAssignableFrom(temp.getClass())) {
            return (T) temp;
        }
        if (clazz == String.class) {
            return (T) temp.toString();
        }
        return null;
    }

    /**
     * Extracts a value from the target object using DPath expression.
     * 
     * @param target
     * @param dPath
     */
    public static Object getValue(final Object target, final String dPath) {
        String[] paths = dPath.split("\\.");
        Object result = target;
        for (String path : paths) {
            result = extractValue(result, path);
        }
        return result;
    }

    /**
     * Sets a value to the target object using DPath expression.
     * 
     * @param target
     * @param dPath
     * @param value
     */
    @SuppressWarnings("unchecked")
    public static void setValue(final Object target, final String dPath, final Object value) {
        String[] paths = dPath.split("\\.");
        Object cursor = target;
        // "seek"to the correct position
        for (int i = 0; i < paths.length - 1; i++) {
            cursor = extractValue(cursor, paths[i]);
        }
        String index = paths[paths.length - 1];
        Matcher m = PATTERN_INDEX.matcher(index);
        if (m.matches() || "[]".equals(index)) {
            int i = "[]".equals(index) ? Integer.MAX_VALUE : Integer.parseInt(m.group(1));
            if (cursor instanceof List<?>) {
                List<Object> temp = (List<Object>) cursor;
                if (i < 0) {
                    throw new IllegalArgumentException("Invalid index [" + i + "]!");
                }
                if (i >= temp.size()) {
                    temp.add(value);
                } else {
                    temp.remove(i);
                    temp.add(i, value);
                }
            } else {
                throw new IllegalArgumentException("Target object is not a list or readonly!");
            }
        } else if (cursor instanceof Map<?, ?>) {
            ((Map<Object, Object>) cursor).put(index, value);
        } else {
            throw new IllegalArgumentException("Target object is not writable!");
        }
    }

    private static Object extractValue(Object target, String index) {
        if (target == null) {
            return null;
        }
        Matcher m = PATTERN_INDEX.matcher(index);
        if (m.matches()) {
            int i = Integer.parseInt(m.group(1));
            try {
                if (target instanceof Object[]) {
                    return ((Object[]) target)[i];
                }
                if (target instanceof List<?>) {
                    return ((List<?>) target).get(i);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
            throw new IllegalArgumentException("Expect an array or list!");
        }
        if (target instanceof Map<?, ?>) {
            return ((Map<?, ?>) target).get(index);
        }
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("key", 'c');

        char value = DPathUtils.getValue(obj, "key", char.class);
        System.out.println(value);
    }
}

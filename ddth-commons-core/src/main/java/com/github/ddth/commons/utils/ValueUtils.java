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
import java.util.List;

/**
 * Utility class to convert values.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.6.1
 */
public class ValueUtils {

    /**
     * Converts a target object to a specified number type.
     * 
     * @param target
     * @param clazz
     *            one of {@link Number}, {@link Byte}, {@link byte},
     *            {@link Short}, {@link short}, {@link Integer}, {@link int},
     *            {@link Long}, {@link long}, {@link Float}, {@link float},
     *            {@link Double}, {@link double}, {@link BigInteger} or
     *            {@link BigDecimal}
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <N> N convertNumber(Object target, Class<N> clazz) {
        if (clazz == Number.class) {
            return target instanceof Number ? (N) target : null;
        }
        if (clazz == Byte.class || clazz == byte.class) {
            byte value = target instanceof Number ? ((Number) target).byteValue()
                    : Byte.parseByte(target.toString());
            return (N) Byte.valueOf(value);
        }
        if (clazz == Short.class || clazz == short.class) {
            short value = target instanceof Number ? ((Number) target).shortValue()
                    : Short.parseShort(target.toString());
            return (N) Short.valueOf(value);
        }
        if (clazz == Integer.class || clazz == int.class) {
            int value = target instanceof Number ? ((Number) target).intValue()
                    : Integer.parseInt(target.toString());
            return (N) Integer.valueOf(value);
        }
        if (clazz == Long.class || clazz == long.class) {
            long value = target instanceof Number ? ((Number) target).longValue()
                    : Long.parseLong(target.toString());
            return (N) Long.valueOf(value);
        }
        if (clazz == Float.class || clazz == float.class) {
            float value = target instanceof Number ? ((Number) target).floatValue()
                    : Float.parseFloat(target.toString());
            return (N) Float.valueOf(value);
        }
        if (clazz == Double.class || clazz == double.class) {
            double value = target instanceof Number ? ((Number) target).doubleValue()
                    : Double.parseDouble(target.toString());
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

    /**
     * Converts a target object to {@link Boolean}.
     * 
     * @param target
     * @return
     */
    public static Boolean convertBoolean(Object target) {
        if (target instanceof Boolean) {
            return (Boolean) target;
        }
        return Boolean.parseBoolean(target.toString());
    }

    /**
     * Converts a target object to {@link Character}.
     * 
     * @param target
     * @return
     */
    public static Character convertChar(Object target) {
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

    /**
     * Converts a target object to {@link Date}.
     * 
     * @param target
     * @return
     */
    public static Date convertDate(Object target) {
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

    /**
     * Converts a target object to {@link List}.
     * 
     * @param target
     * @return
     */
    public static List<?> convertArrayOrList(Object target) {
        if (target instanceof Object[]) {
            return Arrays.asList((Object[]) target);
        }
        if (target instanceof Collection) {
            return new ArrayList<Object>((Collection<?>) target);
        }
        return null;
    }

    /**
     * Converts a target object a specified value type.
     * 
     * @param target
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertValue(Object target, Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class parameter is null!");
        }
        if (target == null) {
            return null;
        }
        if (Number.class.isAssignableFrom(clazz) || byte.class == clazz || short.class == clazz
                || int.class == clazz || long.class == clazz || float.class == clazz
                || double.class == clazz) {
            return convertNumber(target, clazz);
        }
        if (clazz == Boolean.class || clazz == boolean.class) {
            return (T) convertBoolean(target);
        }
        if (clazz == Character.class || clazz == char.class) {
            return (T) convertChar(target);
        }
        if (Date.class.isAssignableFrom(clazz)) {
            return (T) convertDate(target);
        }
        if (Object[].class.isAssignableFrom(clazz) || List.class.isAssignableFrom(clazz)) {
            return (T) convertArrayOrList(target);
        }
        if (clazz.isAssignableFrom(target.getClass())) {
            return (T) target;
        }
        if (clazz == String.class) {
            return (T) target.toString();
        }
        throw new IllegalArgumentException(
                "Cannot convert an object of type [" + target.getClass() + "] to [" + clazz + "]!");
    }

}

package com.github.ddth.commons.utils;

import java.math.BigInteger;

import com.google.common.primitives.UnsignedLongs;

/**
 * Utility to work with unsigned longs and ints, radix up to 62 (0-9, A-Z and
 * a-z).
 * 
 * Note: this class has Google Guava as its dependency.
 * 
 * Note: Some methods in this class are taken from Google Guava's UnsignedLongs'
 * and UnsignedInts' source code, and are modified to work with radix 62.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class UnsignedUtils {
    private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    private static final long INT_MASK = 0xFFFFFFFFL;

    /**
     * Max radix: 62
     */
    public final static int MAX_RADIX = digits.length;

    public static final long MAX_VALUE = -1L; // Equivalent to 2^64 - 1
    // calculated as 0xffffffffffffffff / radix
    private static final long[] maxValueDivs = new long[MAX_RADIX + 1];
    private static final int[] maxValueMods = new int[MAX_RADIX + 1];
    private static final int[] maxSafeDigits = new int[MAX_RADIX + 1];
    static {
        BigInteger overflow = new BigInteger("10000000000000000", 16);
        for (int i = Character.MIN_RADIX; i <= MAX_RADIX; i++) {
            maxValueDivs[i] = UnsignedLongs.divide(MAX_VALUE, i);
            maxValueMods[i] = (int) UnsignedLongs.remainder(MAX_VALUE, i);
            maxSafeDigits[i] = overflow.toString(i).length() - 1;
        }
    }

    /**
     * Returns the numeric value of the character {@code c} in the specified
     * radix.
     * 
     * @param c
     * @param radix
     * @return
     * @see #MAX_RADIX
     */
    public static int digit(char c, int radix) {
        for (int i = 0; i < MAX_RADIX && i < radix; i++) {
            if (digits[i] == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Determines the character representation for a specific digit in the
     * specified radix.
     * 
     * Note: If the value of radix is not a valid radix, or the value of digit
     * is not a valid digit in the specified radix, the null character
     * ('\u0000') is returned.
     * 
     * @param digit
     * @param radix
     * @return
     */
    public static char forDigit(int digit, int radix) {
        if (digit >= 0 && digit < radix && radix >= Character.MIN_RADIX && radix <= MAX_RADIX) {
            return digits[digit];
        }
        return '\u0000';
    }

    /**
     * Returns the unsigned {@code int} value represented by the given decimal
     * string.
     * 
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned integer, or
     *             if the value represented is too large to fit in an unsigned
     *             {@code int}.
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned {@code int}
     *             value
     */
    public static int parseInt(String s) {
        return parseInt(s, 10);
    }

    /**
     * Returns the unsigned {@code int} value represented by a string with the
     * given radix.
     * 
     * @param s
     *            the string containing the unsigned integer representation to
     *            be parsed.
     * @param radix
     *            the radix to use while parsing {@code s}; must be between
     *            {@link Character#MIN_RADIX} and {@link #MAX_RADIX}.
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned {@code int},
     *             or if supplied radix is invalid.
     */
    public static int parseInt(String s, int radix) {
        if (s == null || s.length() == 0 || s.trim().length() == 0) {
            throw new NumberFormatException("Null or empty string");
        }
        long result = parseLong(s, radix);
        if ((result & INT_MASK) != result) {
            throw new NumberFormatException("Input [" + s + "] in base [" + radix
                    + "] is not in the range of an unsigned integer");
        }
        return (int) result;
    }

    /**
     * Returns the unsigned {@code long} value represented by the given decimal
     * string.
     * 
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned {@code long}
     *             value
     */
    public static long parseLong(String s) throws NumberFormatException {
        return parseLong(s, 10);
    }

    /**
     * Returns the unsigned {@code long} value represented by a string with the
     * given radix.
     * 
     * @param s
     *            the string containing the unsigned {@code long} representation
     *            to be parsed.
     * @param radix
     *            the radix to use while parsing {@code s}
     * @throws NumberFormatException
     *             if the string does not contain a valid unsigned {@code long}
     *             with the given radix, or if {@code radix} is not between
     *             {@link Character#MIN_RADIX} and {@link MAX_RADIX}.
     */
    public static long parseLong(String s, int radix) throws NumberFormatException {
        if (s == null || s.length() == 0 || s.trim().length() == 0) {
            throw new NumberFormatException("Null or empty string");
        }

        if (radix < Character.MIN_RADIX || radix > MAX_RADIX) {
            throw new NumberFormatException("Illegal radix [" + radix + "]");
        }

        int max_safe_pos = maxSafeDigits[radix] - 1;
        long value = 0;
        for (int pos = 0; pos < s.length(); pos++) {
            int digit = digit(s.charAt(pos), radix);
            if (digit == -1) {
                throw new NumberFormatException(s);
            }
            if (pos > max_safe_pos && overflowInParse(value, digit, radix)) {
                throw new NumberFormatException("Too large for unsigned long: " + s);
            }
            value = (value * radix) + digit;
        }

        return value;
    }

    /**
     * Returns true if (current * radix) + digit is a number too large to be
     * represented by an unsigned long. This is useful for detecting overflow
     * while parsing a string representation of a number. Does not verify
     * whether supplied radix is valid, passing an invalid radix will give
     * undefined results or an ArrayIndexOutOfBoundsException.
     */
    private static boolean overflowInParse(long current, int digit, int radix) {
        if (current >= 0) {
            if (current < maxValueDivs[radix]) {
                return false;
            }
            if (current > maxValueDivs[radix]) {
                return true;
            }
            // current == maxValueDivs[radix]
            return (digit > maxValueMods[radix]);
        }

        // current < 0: high bit is set
        return true;
    }

    /**
     * Converts a unsigned long to string.
     * 
     * @param x
     * @return
     */
    public static String toString(long x) {
        return toString(x, 10);
    }

    /**
     * Converts a unsigned int to string.
     * 
     * @param x
     * @return
     */
    public static String toString(int x) {
        return toString(x, 10);
    }

    /**
     * Converts a unsigned long to string for the given radix.
     * 
     * @param x
     * @param radix
     * @return {@link IllegalArgumentException} if {@code radix} is not between
     *         {@link Character#MIN_RADIX} and {@link #MAX_RADIX}.
     */
    public static String toString(long x, int radix) throws IllegalArgumentException {
        if (radix < Character.MIN_RADIX || radix > MAX_RADIX) {
            throw new IllegalArgumentException("Radix [" + radix
                    + "] must be between Character.Min_RADIX and UnsignedUtils.MAX_RADIX");
        }

        if (x == 0) {
            return "0";
        } else {
            char[] buf = new char[64];
            int i = buf.length;
            if (x < 0) {
                // Split x into high-order and low-order halves.
                // Individual digits are generated from the bottom half into
                // which
                // bits are moved continously from the top half.
                long top = x >>> 32;
                long bot = (x & 0xffffffffl) + ((top % radix) << 32);
                top /= radix;
                while ((bot > 0) || (top > 0)) {
                    buf[--i] = forDigit((int) (bot % radix), radix);
                    bot = (bot / radix) + ((top % radix) << 32);
                    top /= radix;
                }
            } else {
                // Simple modulo/division approach
                while (x > 0) {
                    buf[--i] = forDigit((int) (x % radix), radix);
                    x /= radix;
                }
            }
            // Generate string
            return new String(buf, i, buf.length - i);
        }
    }

    /**
     * Converts a unsigned int to string for the given radix.
     * 
     * @param x
     * @param radix
     * @return {@link IllegalArgumentException} if {@code radix} is not between
     *         {@link Character#MIN_RADIX} and {@link #MAX_RADIX}.
     */
    public static String toString(int x, int radix) throws IllegalArgumentException {
        long asLong = x & INT_MASK;
        return toString(asLong, radix);
    }
}

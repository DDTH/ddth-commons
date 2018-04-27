package com.github.ddth.commons.test.utils;

import java.util.Calendar;

import com.github.ddth.commons.utils.DateFormatUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DateFormatUtilsTest extends TestCase {

    public DateFormatUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DateFormatUtilsTest.class);
    }

    private static int[] FIELDS = { Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH,
            Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND };
    private static int[] FIELD_VALUES = { 2018, Calendar.MARCH, 01, 04, 29, 01, 981 };
    private static String[] FORMATS = { "yyyy", "MM", "dd", "HH", "mm", "ss", "SSS" };
    private static String[] DELIMS = { " ", ":", "-", "_", "/", ".", ";" };

    private static Calendar testData() {
        final Calendar cal = Calendar.getInstance();
        for (int i = 0; i < FIELDS.length; i++) {
            cal.set(FIELDS[i], FIELD_VALUES[i]);
        }
        return cal;
    }

    private static String normValue(int i, int index) {
        if (index == 1) {
            i++;
        }
        return i < 10 ? "0" + String.valueOf(i) : String.valueOf(i);
    }

    @org.junit.Test
    public void testToStringDate1() {
        final Calendar cal = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            String format = FORMATS[i];
            String actual = DateFormatUtils.toString(cal.getTime(), format);
            String expected = normValue(FIELD_VALUES[i], i);
            assertEquals(expected, actual);
        }
    }

    @org.junit.Test
    public void testToStringDate2() {
        final Calendar cal = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            for (int j = 0; j < FORMATS.length; j++) {
                String delim = DELIMS[(i + j) % DELIMS.length];
                String format = FORMATS[i] + delim + FORMATS[j];
                String actual = DateFormatUtils.toString(cal.getTime(), format);
                String expected = normValue(FIELD_VALUES[i], i) + delim
                        + normValue(FIELD_VALUES[j], j);
                assertEquals(expected, actual);
            }
        }
    }

    @org.junit.Test
    public void testToStringDate3() {
        final Calendar cal = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            for (int j = 0; j < FORMATS.length; j++) {
                for (int k = 0; k < FORMATS.length; k++) {
                    String delim1 = DELIMS[(i + j) % DELIMS.length];
                    String delim2 = DELIMS[(j + k) % DELIMS.length];
                    String format = FORMATS[i] + delim1 + FORMATS[j] + delim2 + FORMATS[k];
                    String actual = DateFormatUtils.toString(cal.getTime(), format);
                    String expected = normValue(FIELD_VALUES[i], i) + delim1
                            + normValue(FIELD_VALUES[j], j) + delim2
                            + normValue(FIELD_VALUES[k], k);
                    assertEquals(expected, actual);
                }
            }
        }
    }

    @org.junit.Test
    public void testToStringCalendar1() {
        final Calendar cal = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            String format = FORMATS[i];
            String actual = DateFormatUtils.toString(cal, format);
            String expected = normValue(FIELD_VALUES[i], i);
            assertEquals(expected, actual);
        }
    }

    @org.junit.Test
    public void testToStringCalendar2() {
        final Calendar cal = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            for (int j = 0; j < FORMATS.length; j++) {
                String delim = DELIMS[(i + j) % DELIMS.length];
                String format = FORMATS[i] + delim + FORMATS[j];
                String actual = DateFormatUtils.toString(cal, format);
                String expected = normValue(FIELD_VALUES[i], i) + delim
                        + normValue(FIELD_VALUES[j], j);
                assertEquals(expected, actual);
            }
        }
    }

    @org.junit.Test
    public void testToStringCalendar3() {
        final Calendar cal = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            for (int j = 0; j < FORMATS.length; j++) {
                for (int k = 0; k < FORMATS.length; k++) {
                    String delim1 = DELIMS[(i + j) % DELIMS.length];
                    String delim2 = DELIMS[(j + k) % DELIMS.length];
                    String format = FORMATS[i] + delim1 + FORMATS[j] + delim2 + FORMATS[k];
                    String actual = DateFormatUtils.toString(cal, format);
                    String expected = normValue(FIELD_VALUES[i], i) + delim1
                            + normValue(FIELD_VALUES[j], j) + delim2
                            + normValue(FIELD_VALUES[k], k);
                    assertEquals(expected, actual);
                }
            }
        }
    }

    @org.junit.Test
    public void testFromString1() {
        final Calendar expected = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            String format = FORMATS[i];
            Calendar actual = Calendar.getInstance();
            actual.setTime(DateFormatUtils.fromString(normValue(FIELD_VALUES[i], i), format));
            assertEquals(expected.get(FIELDS[i]), actual.get(FIELDS[i]));
        }
    }

    @org.junit.Test
    public void testFromString2() {
        final Calendar expected = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            for (int j = 0; j < FORMATS.length; j++) {
                String delim = DELIMS[(i + j) % DELIMS.length];
                String format = FORMATS[i] + delim + FORMATS[j];
                Calendar actual = Calendar.getInstance();
                actual.setTime(DateFormatUtils.fromString(
                        normValue(FIELD_VALUES[i], i) + delim + normValue(FIELD_VALUES[j], j),
                        format));
                assertEquals(expected.get(FIELDS[i]), actual.get(FIELDS[i]));
                assertEquals(expected.get(FIELDS[j]), actual.get(FIELDS[j]));
            }
        }
    }

    @org.junit.Test
    public void testFromString3() {
        final Calendar expected = testData();
        for (int i = 0; i < FORMATS.length; i++) {
            for (int j = 0; j < FORMATS.length; j++) {
                for (int k = 0; k < FORMATS.length; k++) {
                    String delim1 = DELIMS[(i + j) % DELIMS.length];
                    String delim2 = DELIMS[(j + k) % DELIMS.length];
                    String format = FORMATS[i] + delim1 + FORMATS[j] + delim2 + FORMATS[k];
                    Calendar actual = Calendar.getInstance();
                    actual.setTime(DateFormatUtils.fromString(
                            normValue(FIELD_VALUES[i], i) + delim1 + normValue(FIELD_VALUES[j], j)
                                    + delim2 + normValue(FIELD_VALUES[k], k),
                            format));
                    assertEquals(expected.get(FIELDS[i]), actual.get(FIELDS[i]));
                    assertEquals(expected.get(FIELDS[j]), actual.get(FIELDS[j]));
                    assertEquals(expected.get(FIELDS[k]), actual.get(FIELDS[k]));
                }
            }
        }
    }
}

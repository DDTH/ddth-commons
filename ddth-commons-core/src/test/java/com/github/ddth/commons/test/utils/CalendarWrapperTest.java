package com.github.ddth.commons.test.utils;

import java.util.Calendar;
import java.util.Date;

import com.github.ddth.commons.utils.CalendarWrapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CalendarWrapperTest extends TestCase {

    public CalendarWrapperTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(CalendarWrapperTest.class);
    }

    private static int[] FIELDS = { Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH,
            Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND };
    private static int[] FIELD_VALUES = { 2018, Calendar.MARCH, 02, 04, 29, 01, 981 };

    private static Calendar testData() {
        final Calendar cal = Calendar.getInstance();
        for (int i = 0; i < FIELDS.length; i++) {
            cal.set(FIELDS[i], FIELD_VALUES[i]);
        }
        return cal;
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testCreateInstanceCalendar() {
        Calendar expected = Calendar.getInstance();
        CalendarWrapper actual = CalendarWrapper.newInstance(expected);
        assertEquals(expected, actual.getCalendar());
    }

    @org.junit.Test
    public void testCreateInstanceDate() {
        Date expected = new Date();
        CalendarWrapper actual = CalendarWrapper.newInstance(expected);
        assertEquals(expected, actual.getDate());
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testStartOfSecondCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).startOfSecond();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
    }

    @org.junit.Test
    public void testStartOfSecondDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).startOfSecond();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
    }

    @org.junit.Test
    public void testStartOfMinuteCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).startOfMinute();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
    }

    @org.junit.Test
    public void testStartOfMinuteDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).startOfMinute();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
    }

    @org.junit.Test
    public void testStartOfHourCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).startOfHour();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
    }

    @org.junit.Test
    public void testStartOfHourDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).startOfHour();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
    }

    @org.junit.Test
    public void testStartOfDayCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).startOfDay();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.getCalendar().get(Calendar.HOUR_OF_DAY));
    }

    @org.junit.Test
    public void testStartOfDayDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).startOfDay();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.getCalendar().get(Calendar.HOUR_OF_DAY));
    }

    @org.junit.Test
    public void testStartOfWeekCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).startOfWeek();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.getFirstDayOfWeek(), actual.getCalendar().get(Calendar.DAY_OF_WEEK));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.DAY_OF_WEEK) != actual.getCalendar().get(Calendar.DAY_OF_WEEK));
    }

    @org.junit.Test
    public void testStartOfWeekDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).startOfWeek();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.getFirstDayOfWeek(), actual.getCalendar().get(Calendar.DAY_OF_WEEK));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.DAY_OF_WEEK) != actual.getCalendar().get(Calendar.DAY_OF_WEEK));
    }

    @org.junit.Test
    public void testStartOfMonthCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).startOfMonth();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertTrue(
                cal.get(Calendar.DAY_OF_MONTH) != actual.getCalendar().get(Calendar.DAY_OF_MONTH));
    }

    @org.junit.Test
    public void testStartOfMonthDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).startOfMonth();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertTrue(
                cal.get(Calendar.DAY_OF_MONTH) != actual.getCalendar().get(Calendar.DAY_OF_MONTH));
    }

    @org.junit.Test
    public void testStartOfYearCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).startOfYear();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR));
        assertEquals(1, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY, actual.getCalendar().get(Calendar.MONTH));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertTrue(
                cal.get(Calendar.DAY_OF_MONTH) != actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MONTH) != actual.getCalendar().get(Calendar.MONTH));
    }

    @org.junit.Test
    public void testStartOfYearDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).startOfYear();
        assertEquals(0, actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY, actual.getCalendar().get(Calendar.MONTH));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.getCalendar().get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.getCalendar().get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.getCalendar().get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertTrue(
                cal.get(Calendar.DAY_OF_MONTH) != actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MONTH) != actual.getCalendar().get(Calendar.MONTH));
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testNextSecondCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) + 1, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) + 1, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMinuteCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextMinute();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 1, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMinuteDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextMinute();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 1, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextHourCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextHour();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 1,
                actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextHourDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextHour();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 1,
                actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextDayCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextDay();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextDayDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextDay();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextWeekCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextWeek();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.getCalendar().get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1,
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextWeekDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextWeek();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.getCalendar().get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1,
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMonthCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextMonth();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMonthDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextMonth();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextYearCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextYear();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.getCalendar().get(Calendar.DAY_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 1, actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextYearDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextYear();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.getCalendar().get(Calendar.DAY_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 1, actual.getCalendar().get(Calendar.YEAR));
    }
    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testPrevSecondCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) - 1, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) - 1, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevMinuteCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevMinute();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 1, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextPrevDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevMinute();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 1, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevHourCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevHour();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 1,
                actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevHourDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevHour();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 1,
                actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevDayCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevDay();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevDayDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevDay();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevWeekCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevWeek();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.getCalendar().get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 1,
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevWeekDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevWeek();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.getCalendar().get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 1,
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevMonthCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevMonth();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevMonthDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevMonth();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevYearCalendar() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevYear();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.getCalendar().get(Calendar.DAY_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 1, actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevYearDate() {
        Calendar cal = testData();
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevYear();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.getCalendar().get(Calendar.DAY_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 1, actual.getCalendar().get(Calendar.YEAR));
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testNextSecondMoveMinuteCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 1, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 1, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 1,
                actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 1,
                actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayMonthCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayMonthDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayWeekCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.DAY_OF_WEEK, cal.getMaximum(Calendar.DAY_OF_WEEK));
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1,
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayWeekDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.DAY_OF_WEEK, cal.getMaximum(Calendar.DAY_OF_WEEK));
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1,
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayMonthYearCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 1, actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayMonthYearDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).nextSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(0, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(0, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 1, actual.getCalendar().get(Calendar.YEAR));
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testPrevSecondMoveMinuteCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 1, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 1, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 1,
                actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 1,
                actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH),
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(23, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(23, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR),
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayMonthCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(23, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(31, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayMonthDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(23, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(31, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayWeekCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(23, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 1,
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayWeekDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(23, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1,
                actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 1,
                actual.getCalendar().get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayMonthYearCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(23, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(31, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.DECEMBER, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 1, actual.getCalendar().get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayMonthYearDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        CalendarWrapper actual = CalendarWrapper.newInstance(cal.getTime()).prevSecond();
        assertEquals(cal.get(Calendar.MILLISECOND), actual.getCalendar().get(Calendar.MILLISECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.SECOND));
        assertEquals(59, actual.getCalendar().get(Calendar.MINUTE));
        assertEquals(23, actual.getCalendar().get(Calendar.HOUR_OF_DAY));
        assertEquals(31, actual.getCalendar().get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.DECEMBER, actual.getCalendar().get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 1, actual.getCalendar().get(Calendar.YEAR));
    }

}

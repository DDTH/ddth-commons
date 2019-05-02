package com.github.ddth.commons.test.utils;

import java.util.Calendar;
import java.util.Date;

import com.github.ddth.commons.utils.DateTimeUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DateTimeUtilsTest extends TestCase {

    public DateTimeUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DateTimeUtilsTest.class);
    }

    private static int[] FIELDS = { Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH,
            Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND };
    private static int[] FIELD_VALUES = { 2018, Calendar.MARCH, 02, 04, 29, 01, 981 };

    private static Calendar testData() {
        final Calendar cal = Calendar.getInstance();
        for (int i = 0; i < FIELDS.length; i++) {
            cal.set(FIELDS[i], FIELD_VALUES[i]);
            // Calendar.set() does not interpret the value, hence we need to force the update by
            // calling Calendar.getTimeInMillis()
            cal.getTimeInMillis();
        }

        return cal;
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testCreateCalendar() {
        Date expected = new Date();
        Calendar actual = DateTimeUtils.createCalendar(expected);
        assertNotNull(actual);
        assertEquals(expected.getTime(), actual.getTimeInMillis());
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testStartOfSecondCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfSecond(cal);
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
    }

    @org.junit.Test
    public void testStartOfSecondDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfSecond(cal.getTime());
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
    }

    @org.junit.Test
    public void testStartOfMinuteCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfMinute(cal);
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
    }

    @org.junit.Test
    public void testStartOfMinuteDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfMinute(cal.getTime());
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
    }

    @org.junit.Test
    public void testStartOfHourCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfHour(cal);
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
    }

    @org.junit.Test
    public void testStartOfHourDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfHour(cal.getTime());
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
    }

    @org.junit.Test
    public void testStartOfDayCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfDay(cal);
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.get(Calendar.HOUR_OF_DAY));
    }

    @org.junit.Test
    public void testStartOfDayDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfDay(cal.getTime());
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.get(Calendar.HOUR_OF_DAY));
    }

    @org.junit.Test
    public void testStartOfWeekCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfWeek(cal);
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.getFirstDayOfWeek(), actual.get(Calendar.DAY_OF_WEEK));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.DAY_OF_WEEK) != actual.get(Calendar.DAY_OF_WEEK));
    }

    @org.junit.Test
    public void testStartOfWeekDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfWeek(cal.getTime());
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.getFirstDayOfWeek(), actual.get(Calendar.DAY_OF_WEEK));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.DAY_OF_WEEK) != actual.get(Calendar.DAY_OF_WEEK));
    }

    @org.junit.Test
    public void testStartOfMonthCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfMonth(cal);
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.DAY_OF_MONTH) != actual.get(Calendar.DAY_OF_MONTH));
    }

    @org.junit.Test
    public void testStartOfMonthDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfMonth(cal.getTime());
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.DAY_OF_MONTH) != actual.get(Calendar.DAY_OF_MONTH));
    }

    @org.junit.Test
    public void testStartOfYearCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfYear(cal);
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR));
        assertEquals(1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY, actual.get(Calendar.MONTH));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.DAY_OF_MONTH) != actual.get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MONTH) != actual.get(Calendar.MONTH));
    }

    @org.junit.Test
    public void testStartOfYearDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.startOfYear(cal.getTime());
        assertEquals(0, actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY, actual.get(Calendar.MONTH));
        assertTrue(cal.get(Calendar.MILLISECOND) != actual.get(Calendar.MILLISECOND));
        assertTrue(cal.get(Calendar.SECOND) != actual.get(Calendar.SECOND));
        assertTrue(cal.get(Calendar.MINUTE) != actual.get(Calendar.MINUTE));
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) != actual.get(Calendar.HOUR_OF_DAY));
        assertTrue(cal.get(Calendar.DAY_OF_MONTH) != actual.get(Calendar.DAY_OF_MONTH));
        assertTrue(cal.get(Calendar.MONTH) != actual.get(Calendar.MONTH));
    }

    /*----------------------------------------------------------------------*/
    @org.junit.Test
    public void testNextMillisecondCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextMillisecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND) + 1, actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMillisecondDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextMillisecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND) + 1, actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) + 1, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) + 1, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMinuteCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextMinute(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 1, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMinuteDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextMinute(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 1, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextHourCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextHour(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 1, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextHourDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextHour(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 1, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextDayCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextDay(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextDayDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextDay(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextWeekCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextWeek(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextWeekDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextWeek(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMonthCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextMonth(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextMonthDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextMonth(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextYearCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextYear(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.get(Calendar.DAY_OF_YEAR));
        // assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 1, actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextYearDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.nextYear(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.get(Calendar.DAY_OF_YEAR));
        // assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 1, actual.get(Calendar.YEAR));
    }

    /*----------------------------------------------------------------------*/
    @org.junit.Test
    public void testPrevMillisecondCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevMillisecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND) - 1, actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevMillisecondDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevMillisecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND) - 1, actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) - 1, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) - 1, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevMinuteCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevMinute(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 1, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevMinuteDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevMinute(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 1, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevHourCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevHour(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 1, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevHourDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevHour(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 1, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevDayCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevDay(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevDayDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevDay(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevWeekCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevWeek(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevWeekDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevWeek(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevMonthCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevMonth(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevMonthDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevMonth(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevYearCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevYear(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.get(Calendar.DAY_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 1, actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevYearDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.prevYear(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.get(Calendar.DAY_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 1, actual.get(Calendar.YEAR));
    }

    /*----------------------------------------------------------------------*/
    @org.junit.Test
    public void testAddMillisecondsCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addMilliseconds(cal, 3);
        assertEquals(cal.get(Calendar.MILLISECOND) + 3, actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddMillisecondsDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addMilliseconds(cal.getTime(), -3);
        assertEquals(cal.get(Calendar.MILLISECOND) - 3, actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddSecondsCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addSeconds(cal, -1);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) - 1, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddSecondsDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addSeconds(cal.getTime(), 7);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND) + 7, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddMinutesCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addMinutes(cal, 3);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 3, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddMinutesDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addMinutes(cal.getTime(), -5);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 5, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddHoursCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addHours(cal, -2);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 2, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddHoursDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addHours(cal.getTime(), 3);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 3, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddDaysCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addDays(cal, 7);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 7, actual.get(Calendar.DAY_OF_MONTH));
        // assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddDaysDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addDays(cal.getTime(), -1);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1, actual.get(Calendar.DAY_OF_MONTH));
        // assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddWeeksCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addWeeks(cal, 2);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 2, actual.get(Calendar.WEEK_OF_YEAR));
        // assertEquals(cal.get(Calendar.MONTH) - 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddWeeksDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addWeeks(cal.getTime(), -3);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_WEEK), actual.get(Calendar.DAY_OF_WEEK));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 3, actual.get(Calendar.WEEK_OF_YEAR));
        // assertEquals(cal.get(Calendar.MONTH) - 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddMonthsCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addMonths(cal, 3);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 3, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddMonthsDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addMonths(cal.getTime(), -2);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 2, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddYearsCalendar() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addYears(cal, -7);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.get(Calendar.DAY_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 7, actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testAddYearsDate() {
        Calendar cal = testData();
        Calendar actual = DateTimeUtils.addYears(cal.getTime(), 5);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(cal.get(Calendar.SECOND), actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE), actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_YEAR), actual.get(Calendar.DAY_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 5, actual.get(Calendar.YEAR));
    }

    /*----------------------------------------------------------------------*/
    @org.junit.Test
    public void testNextSecondMoveMinuteCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 1, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) + 1, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 1, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) + 1, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayMonthCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayMonthDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) + 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayWeekCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayWeekDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) + 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) + 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayMonthYearCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 1, actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testNextSecondMoveMinuteHourDayMonthYearDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.nextSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(0, actual.get(Calendar.SECOND));
        assertEquals(0, actual.get(Calendar.MINUTE));
        assertEquals(0, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) + 1, actual.get(Calendar.YEAR));
    }

    /*----------------------------------------------------------------------*/
    @org.junit.Test
    public void testPrevSecondMoveMinuteCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 1, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(cal.get(Calendar.MINUTE) - 1, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY), actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 1, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(cal.get(Calendar.HOUR_OF_DAY) - 1, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(23, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(23, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR), actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayMonthCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(23, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(31, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayMonthDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(23, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(31, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.MONTH) - 1, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayWeekCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(23, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayWeekDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(23, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(cal.get(Calendar.DAY_OF_MONTH) - 1, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(cal.get(Calendar.WEEK_OF_YEAR) - 1, actual.get(Calendar.WEEK_OF_YEAR));
        assertEquals(cal.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayMonthYearCalendar() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal);
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(23, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(31, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.DECEMBER, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 1, actual.get(Calendar.YEAR));
    }

    @org.junit.Test
    public void testPrevSecondMoveMinuteHourDayMonthYearDate() {
        Calendar cal = testData();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.getTimeInMillis();
        Calendar actual = DateTimeUtils.prevSecond(cal.getTime());
        assertEquals(cal.get(Calendar.MILLISECOND), actual.get(Calendar.MILLISECOND));
        assertEquals(59, actual.get(Calendar.SECOND));
        assertEquals(59, actual.get(Calendar.MINUTE));
        assertEquals(23, actual.get(Calendar.HOUR_OF_DAY));
        assertEquals(31, actual.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.DECEMBER, actual.get(Calendar.MONTH));
        assertEquals(cal.get(Calendar.YEAR) - 1, actual.get(Calendar.YEAR));
    }
}

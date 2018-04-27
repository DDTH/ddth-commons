package com.github.ddth.commons.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Helper class to work with {@link Date} and {@link Calendar}.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.3.1
 */
public class DateTimeUtils {
    /**
     * Create a new {@link Calendar} instance from a {@link Date} object.
     * 
     * @param date
     * @return
     */
    public static Calendar createCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /*----------------------------------------------------------------------*/

    /**
     * Calculate the start-of-second point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar startOfSecond(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * Calculate the next-second point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar nextSecond(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.SECOND, 1);
        return cal;
    }

    /**
     * Calculate the previous-second point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar prevSecond(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.SECOND, -1);
        return cal;
    }

    /**
     * Calculate the start-of-second point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar startOfSecond(Date origin) {
        return startOfSecond(createCalendar(origin));
    }

    /**
     * Calculate the next-second point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar nextSecond(Date origin) {
        return nextSecond(createCalendar(origin));
    }

    /**
     * Calculate the previous-second point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar prevSecond(Date origin) {
        return prevSecond(createCalendar(origin));
    }

    /*----------------------------------------------------------------------*/

    /**
     * Calculate the start-of-minute point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfMinute(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        return cal;
    }

    /**
     * Calculate the next-minute point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar nextMinute(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.MINUTE, 1);
        return cal;
    }

    /**
     * Calculate the previous-minute point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar prevMinute(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.MINUTE, -1);
        return cal;
    }

    /**
     * Calculate the start-of-minute point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar startOfMinute(Date origin) {
        return startOfMinute(createCalendar(origin));
    }

    /**
     * Calculate the next-minute point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar nextMinute(Date origin) {
        return nextMinute(createCalendar(origin));
    }

    /**
     * Calculate the previous-minute point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar prevMinute(Date origin) {
        return prevMinute(createCalendar(origin));
    }

    /*----------------------------------------------------------------------*/

    /**
     * Calculate the start-of-hour point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfHour(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return cal;
    }

    /**
     * Calculate the next-hour point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar nextHour(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal;
    }

    /**
     * Calculate the previous-hour point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar prevHour(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        return cal;
    }

    /**
     * Calculate the start-of-hour point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar startOfHour(Date origin) {
        return startOfHour(createCalendar(origin));
    }

    /**
     * Calculate the next-hour point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar nextHour(Date origin) {
        return nextHour(createCalendar(origin));
    }

    /**
     * Calculate the previous-hour point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar prevHour(Date origin) {
        return prevHour(createCalendar(origin));
    }

    /*----------------------------------------------------------------------*/

    /**
     * Calculate the start-of-day point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfDay(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal;
    }

    /**
     * Calculate the next-date point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar nextDay(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.DATE, 1);
        return cal;
    }

    /**
     * Calculate the previous-day point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar prevDay(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.DATE, -1);
        return cal;
    }

    /**
     * Calculate the start-of-day point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar startOfDay(Date origin) {
        return startOfDay(createCalendar(origin));
    }

    /**
     * Calculate the next-date point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar nextDay(Date origin) {
        return nextDay(createCalendar(origin));
    }

    /**
     * Calculate the previous-day point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar prevDay(Date origin) {
        return prevDay(createCalendar(origin));
    }

    /*----------------------------------------------------------------------*/

    /**
     * Calculate the start-of-week point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfWeek(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal;
    }

    /**
     * Calculate the next-week point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar nextWeek(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        return cal;
    }

    /**
     * Calculate the previous-week point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar prevWeek(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        return cal;
    }

    /**
     * Calculate the start-of-week point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar startOfWeek(Date origin) {
        return startOfWeek(createCalendar(origin));
    }

    /**
     * Calculate the next-week point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar nextWeek(Date origin) {
        return nextWeek(createCalendar(origin));
    }

    /**
     * Calculate the previous-week point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar prevWeek(Date origin) {
        return prevWeek(createCalendar(origin));
    }
    /*----------------------------------------------------------------------*/

    /**
     * Calculate the start-of-month point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfMonth(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal;
    }

    /**
     * Calculate the next-month point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar nextMonth(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.MONTH, 1);
        return cal;
    }

    /**
     * Calculate the previous-month point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar prevMonth(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.MONDAY, -1);
        return cal;
    }

    /**
     * Calculate the start-of-month point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar startOfMonth(Date origin) {
        return startOfMonth(createCalendar(origin));
    }

    /**
     * Calculate the next-month point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar nextMonth(Date origin) {
        return nextMonth(createCalendar(origin));
    }

    /**
     * Calculate the previous-month point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar prevMonth(Date origin) {
        return prevMonth(createCalendar(origin));
    }

    /*----------------------------------------------------------------------*/

    /**
     * Calculate the start-of-year point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfYear(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal;
    }

    /**
     * Calculate the next-year point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar nextYear(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.YEAR, 1);
        return cal;
    }

    /**
     * Calculate the previous-year point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar prevYear(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.YEAR, -1);
        return cal;
    }

    /**
     * Calculate the start-of-year point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar startOfYear(Date origin) {
        return startOfYear(createCalendar(origin));
    }

    /**
     * Calculate the next-year point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar nextYear(Date origin) {
        return nextYear(createCalendar(origin));
    }

    /**
     * Calculate the previous-year point of a supplied {@link Date}.
     * 
     * @param origin
     * @return
     * @since 0.9.1.1
     */
    public static Calendar prevYear(Date origin) {
        return prevYear(createCalendar(origin));
    }

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());
        System.out.println(startOfYear(cal).getTime());
        System.out.println(nextYear(cal).getTime());
        System.out.println(prevYear(cal).getTime());
    }
}

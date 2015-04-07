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
     * Creates a new {@link Calendar} instance from a {@link Date} object.
     * 
     * @param date
     * @return
     */
    public static Calendar createCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Calculates the start-of-minute point of a supplied {@link Calendar}.
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
     * Calculates the next-minute point of a supplied {@link Calendar}.
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
     * Calculates the previous-minute point of a supplied {@link Calendar}.
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
     * Calculates the start-of-hour point of a supplied {@link Calendar}.
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
     * Calculates the next-hour point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar nextHour(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.HOUR, 1);
        return cal;
    }

    /**
     * Calculates the previous-hour point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar prevHour(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.HOUR, -1);
        return cal;
    }

    /**
     * Calculates the start-of-day point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfDay(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal;
    }

    /**
     * Calculates the next-date point of a supplied {@link Calendar}.
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
     * Calculates the previous-day point of a supplied {@link Calendar}.
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
     * Calculates the start-of-week point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfWeek(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal;
    }

    /**
     * Calculates the next-week point of a supplied {@link Calendar}.
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
     * Calculates the previous-week point of a supplied {@link Calendar}.
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
     * Calculates the start-of-month point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfMonth(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal;
    }

    /**
     * Calculates the next-month point of a supplied {@link Calendar}.
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
     * Calculates the previous-month point of a supplied {@link Calendar}.
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
     * Calculates the start-of-year point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar startOfYear(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal;
    }

    /**
     * Calculates the next-year point of a supplied {@link Calendar}.
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
     * Calculates the previous-year point of a supplied {@link Calendar}.
     * 
     * @param origin
     * @return
     */
    public static Calendar prevYear(Calendar origin) {
        Calendar cal = (Calendar) origin.clone();
        cal.add(Calendar.YEAR, -1);
        return cal;
    }

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.getTime());
        System.out.println(startOfYear(cal).getTime());
        System.out.println(nextYear(cal).getTime());
        System.out.println(prevYear(cal).getTime());
    }
}

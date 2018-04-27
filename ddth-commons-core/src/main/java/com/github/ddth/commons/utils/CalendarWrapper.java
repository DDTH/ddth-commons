package com.github.ddth.commons.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Wrap around a {@link Calendar}, provide {@link DateTimeUtils} functionality with fluent-style.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.9.1.1
 */
public class CalendarWrapper {
    public static CalendarWrapper newInstance() {
        return new CalendarWrapper(Calendar.getInstance());
    }

    public static CalendarWrapper newInstance(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return new CalendarWrapper(cal);
    }

    public static CalendarWrapper newInstance(Calendar cal) {
        return new CalendarWrapper(cal);
    }

    private Calendar calendar;

    protected CalendarWrapper(Calendar calendar) {
        setCalendar(calendar);
    }

    protected CalendarWrapper setCalendar(Calendar calendar) {
        this.calendar = (Calendar) calendar.clone();
        return this;
    }

    public Calendar getCalendar() {
        return (Calendar) calendar.clone();
    }

    public Date getDate() {
        return calendar.getTime();
    }

    public CalendarWrapper startOfSecond() {
        return setCalendar(DateTimeUtils.startOfSecond(calendar));
    }

    public CalendarWrapper startOfMinute() {
        return setCalendar(DateTimeUtils.startOfMinute(calendar));
    }

    public CalendarWrapper startOfHour() {
        return setCalendar(DateTimeUtils.startOfHour(calendar));
    }

    public CalendarWrapper startOfDay() {
        return setCalendar(DateTimeUtils.startOfDay(calendar));
    }

    public CalendarWrapper startOfWeek() {
        return setCalendar(DateTimeUtils.startOfWeek(calendar));
    }

    public CalendarWrapper startOfMonth() {
        return setCalendar(DateTimeUtils.startOfMonth(calendar));
    }

    public CalendarWrapper startOfYear() {
        return setCalendar(DateTimeUtils.startOfYear(calendar));
    }

    public CalendarWrapper nextSecond() {
        return setCalendar(DateTimeUtils.nextSecond(calendar));
    }

    public CalendarWrapper nextMinute() {
        return setCalendar(DateTimeUtils.nextMinute(calendar));
    }

    public CalendarWrapper nextHour() {
        return setCalendar(DateTimeUtils.nextHour(calendar));
    }

    public CalendarWrapper nextDay() {
        return setCalendar(DateTimeUtils.nextDay(calendar));
    }

    public CalendarWrapper nextWeek() {
        return setCalendar(DateTimeUtils.nextWeek(calendar));
    }

    public CalendarWrapper nextMonth() {
        return setCalendar(DateTimeUtils.nextMonth(calendar));
    }

    public CalendarWrapper nextYear() {
        return setCalendar(DateTimeUtils.nextYear(calendar));
    }

    public CalendarWrapper prevSecond() {
        return setCalendar(DateTimeUtils.prevSecond(calendar));
    }

    public CalendarWrapper prevMinute() {
        return setCalendar(DateTimeUtils.prevMinute(calendar));
    }

    public CalendarWrapper prevHour() {
        return setCalendar(DateTimeUtils.prevHour(calendar));
    }

    public CalendarWrapper prevDay() {
        return setCalendar(DateTimeUtils.prevDay(calendar));
    }

    public CalendarWrapper prevWeek() {
        return setCalendar(DateTimeUtils.prevWeek(calendar));
    }

    public CalendarWrapper prevMonth() {
        return setCalendar(DateTimeUtils.prevMonth(calendar));
    }

    public CalendarWrapper prevYear() {
        return setCalendar(DateTimeUtils.prevYear(calendar));
    }
}

package org.ua.oblik.domain.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Anton Bakalets
 */
public final class DateUtils {

    private static final int LAST_HOUR_OF_DAY = 23;
    private static final int LAST_MINUTE = 59;
    private static final int LAST_SECOND = 59;
    private static final int LAST_MILLISECOND = 999;
    
    private DateUtils() {
    }

    private static Calendar toCalendar(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private static void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setTimeToEndofDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, LAST_HOUR_OF_DAY);
        calendar.set(Calendar.MINUTE, LAST_MINUTE);
        calendar.set(Calendar.SECOND, LAST_SECOND);
        calendar.set(Calendar.MILLISECOND, LAST_MILLISECOND);
    }

    public static Date getMonthBegining(Date date) {
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        setTimeToBeginningOfDay(calendar);
        return calendar.getTime();
    }

    public static Date getMonthEnd(Date date) {
        Date end;
        Calendar calendar = toCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setTimeToEndofDay(calendar);
        end = calendar.getTime();
        return end;
    }
}

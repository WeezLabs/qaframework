package util;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class containing utils for working with dates.
 */
public class DateUtils {
    private static final String DATE_FORMAT_MONTH_STRING = "LLLL yyyy";
    private static final String DATE_FORMAT_SHORT_STRING = "dd.MM.yyyy";
    private static final String DATE_FORMAT_LONG_STRING = "dd MMMM yyyy";
    private static final String TIME_FORMAT_STRING = "HH:mm";

    public static String formatDateShort(Date date) {
        if (date == null) {
            return "null";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT_STRING, new Locale("ru"));
        return sdf.format(date);
    }

    public static String formatDateLong(Date date) {
        if (date == null) {
            return "null";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_LONG_STRING, new Locale("ru"));
        return sdf.format(date);
    }

    public static String formatDateMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_MONTH_STRING, new Locale("ru"));
        return sdf.format(date);
    }

    public static String formatTime(Date date) {
        if ( date == null) {
            return "null";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_STRING, new Locale("ru"));
        return sdf.format(date);
    }


    /**
     * Difference between two dates in days.
     *
     * @param startDate start date.
     * @param endDate   end date.
     * @return difference between start and end dates.
     */
    public static Integer dateDiff(Date startDate, Date endDate) {
        DateTime dateTime1 = new DateTime(startDate);
        DateTime dateTime2 = new DateTime(endDate);

        Days d = Days.daysBetween(dateTime1.toDateMidnight(), dateTime2.toDateMidnight());
        return d.getDays();
    }

    /**
     * Get last day of the current month.
     *
     * @return last day of the current month.
     */
    public static Date getCurrentMonthEnd() {
        return getMonthEnd(null);
    }

    /**
     * Get last day of the current month.
     *
     * @return last day of the current month.
     */
    public static Date getMonthEnd(Date date) {
        LocalDate ld = null == date ? LocalDate.now() : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(ld.with(TemporalAdjusters.lastDayOfMonth())
                .atTime(23, 59, 59, 999)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * Get first day of the current month.
     *
     * @return first day of the current month.
     */
    public static Date getCurrentMonthStart() {
        return getMonthStart(null);
    }

    /**
     * Get first day of the current month.
     *
     * @return first day of the current month.
     */
    public static Date getMonthStart(Date date) {
        LocalDate ld = null == date ? LocalDate.now() : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(ld.with(TemporalAdjusters.firstDayOfMonth())
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * Add one month to the supplied date.
     *
     * @param date date to add month too.
     * @return date + one month.
     */
    public static Date addMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * Substract one month to the supplied date.
     *
     * @param date date to add month too.
     * @return date - one month.
     */
    public static Date substractMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    public static Date truncateTime(Date date){
        return org.apache.commons.lang3.time.DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
    }
}

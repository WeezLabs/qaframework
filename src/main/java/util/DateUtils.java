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
 * сласс для работы с датами
 */
public class DateUtils {
    public static final String DATE_FORMAT_MONTH_STRING = "LLLL yyyy";
    public static final String DATE_FORMAT_SHORT_STRING = "dd.MM.yyyy";
    public static final String DATE_FORMAT_LONG_STRING = "dd MMMM yyyy";
    public static final String TIME_FORMAT_STRING = "HH:mm";

    public static String formatDateShort(Date date) {
        if(date==null) return "null";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT_STRING, new Locale("ru"));
        return sdf.format(date);
    }

    public static String formatDateLong(Date date) {
        if(date==null) return "null";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_LONG_STRING, new Locale("ru"));
        return sdf.format(date);
    }

    public static String formatDateMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_MONTH_STRING, new Locale("ru"));
        return sdf.format(date);
    }

    public static String formatTime(Date date) {
        if(date==null) return "null";
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_STRING, new Locale("ru"));
        return sdf.format(date);
    }


    /**
     * Расчет разницы между датами в днях
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer dateDiff(Date startDate, Date endDate) {
        Integer result;
        DateTime dateTime1 = new DateTime(startDate);
        DateTime dateTime2 = new DateTime(endDate);
        Days d = Days.daysBetween(dateTime1.toDateMidnight(), dateTime2.toDateMidnight());
        result = d.getDays();
        return result;
    }

    /**
     * @return
     */
    public static Date getCurrentMonthEnd() {
        return getMonthEnd(null);
    }

    public static Date getMonthEnd(Date date) {
        LocalDate ld = null == date ? LocalDate.now() : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(ld.with(TemporalAdjusters.lastDayOfMonth())
                .atTime(23, 59, 59, 999)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * @return
     */
    public static Date getCurrentMonthStart() {
        return getMonthStart(null);
    }

    public static Date getMonthStart(Date date) {
        LocalDate ld = null == date ? LocalDate.now() : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(ld.with(TemporalAdjusters.firstDayOfMonth())
                .atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date addMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

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

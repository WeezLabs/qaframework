package util;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class DateTimeUtils {
    private DateTimeUtils() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    /**
     * Compare two dates/times using accuracy accuracy.
     *
     * @param lhs first date/time to compare.
     * @param rhs second date/time to compare.
     * @param chronoUnit a unit of measurement that is used to compare date/time, {@link ChronoUnit#SECONDS} for example.
     * @param accuracy maximum permissible difference in between two objects.
     * @return True if difference between two dates/times no more than accuracy. False otherwise.
     */
    public static boolean compareWithAccuracy(Temporal lhs, Temporal rhs, ChronoUnit chronoUnit, long accuracy) {
        return Math.abs(chronoUnit.between(lhs, rhs)) <= accuracy;
    }
}


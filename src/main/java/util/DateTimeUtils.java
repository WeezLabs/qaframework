package util;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class DateTimeUtils {
    private DateTimeUtils() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    /**
     * Compare two dates/times using accuracy.
     *
     * @param lhs        first date/time to compare.
     * @param rhs        second date/time to compare.
     * @param chronoUnit a unit of measurement that is used to compare date/time, {@link ChronoUnit#SECONDS} for example.
     * @param accuracy   maximum permissible difference in between two objects.
     * @return True if difference between two dates/times no more than accuracy. False otherwise.
     */
    public static boolean compareWithAccuracy(Temporal lhs, Temporal rhs, ChronoUnit chronoUnit, long accuracy) {
        assert accuracy >= 0 : "accuracy value should be positive.";

        return compareWithRangeAccuracy(lhs, rhs, chronoUnit, 0, accuracy);
    }

    /**
     * Compare two dates/times using specified range.
     *
     * @param lhs        first date/time to compare.
     * @param rhs        second date/time to compare.
     * @param chronoUnit a unit of measurement that is used to compare date/time, {@link ChronoUnit#SECONDS} for example.
     * @param from       lower bound of the range.
     * @param to         upper bound of the range.
     * @return True if difference between two dates/times within the specified range
     * (up to the upper limit of the range, inclusive). False otherwise.
     */
    public static boolean compareWithRangeAccuracy(Temporal lhs,
                                                   Temporal rhs,
                                                   ChronoUnit chronoUnit,
                                                   long from,
                                                   long to) {
        assert from >= 0 : "from value should be positive.";
        assert to >= 0 : "to value should be positive.";
        assert from <= to : "from value should be less or equal to to value.";

        long diff = Math.abs(chronoUnit.between(lhs, rhs));
        return diff >= from && diff <= to;
    }
}


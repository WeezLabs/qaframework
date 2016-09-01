package util;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class DateTimeUtils {
    private DateTimeUtils() {
        throw new UnsupportedOperationException("You try to create instance of utility class.");
    }

    /**
     * Compare two dates/times using accuracyInSeconds accuracy.
     *
     * @param lhs first date/time to compare.
     * @param rhs second date/time to compare.
     * @param accuracyInSeconds maximum permissible difference in seconds between two objects.
     * @return True if difference between two dates/times no more than accuracyInSeconds. False otherwise.
     */
    public static boolean compareWithAccuracy(Temporal lhs, Temporal rhs, long accuracyInSeconds) {
        return Math.abs(ChronoUnit.SECONDS.between(lhs, rhs)) <= accuracyInSeconds;
    }
}

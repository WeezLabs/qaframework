package util;

import com.google.common.base.Throwables;
import io.qameta.allure.Attachment;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.List;

import static org.apache.log4j.Level.OFF;
import static util.TestFlowStepHandler.TestFlowStepLevel.DS_LL_TRACE;
import static util.TestFlowStepHandler.TestFlowStepLevel.DS_TRACE;
import static util.TestFlowStepHandler.TestFlowStepLevel.DS_TRACE_ERROR;

public class TestFlowStepHandler {

    private static final String EXPECTED_NO_ERRORS_RESULT = " no errors produced";
    private static final String SKIP_STEP = " Skip this step for: ";
    private static final String EXPECTED_RESULT = " Expected result: ";

    private static Logger logger = Logger.getLogger(TestFlowStepHandler.class);

    private static boolean enableLogDesignStep = true;
    private static boolean enableLogError = true;
    private static boolean enableLogLowLevelDesignStep = true;

    static {
        BasicConfigurator.configure();
        turnOffHttpLogging();
    }

    private static void turnOffHttpLogging() {
        Logger.getLogger("org.apache.http.client").setLevel(OFF);
        Logger.getLogger("org.apache.http.wire").setLevel(OFF);
        Logger.getLogger("org.apache.http.headers").setLevel(OFF);
        Logger.getLogger("org.apache.http.impl").setLevel(OFF);
    }

    public static void logDesignStep(final String step) {
        if (TestFlowStepHandler.enableLogDesignStep) {
            TestFlowStepHandler.logger.log(DS_TRACE, step);
        }
    }

    public static void logError(final String step) {
        if (TestFlowStepHandler.enableLogError) {
            TestFlowStepHandler.logger.log(DS_TRACE_ERROR, step);
        }
    }

    /**
     * Prints error stack trace to system output and attaches it to Allure report.
     * Should be used instead of {@link Throwable#printStackTrace()} or other ways
     * of errors logging.
     * @return formatted exception stack trace, will be handled by Allure automatically
     */
    @Attachment(value = "errorStackTrace")
    @SuppressWarnings("UnusedReturnValue")
    public static String logError(@Nonnull Throwable throwable) {
        if (TestFlowStepHandler.enableLogError) {
            TestFlowStepHandler.logger.log(DS_TRACE_ERROR, throwable.getMessage(), throwable);
        }

        // return a string for Allure report
        return Throwables.getStackTraceAsString(throwable);
    }

    public static void logDesignStep(final String step,
                                      final String expectedResult) {
        TestFlowStepHandler.logDesignStep(step + TestFlowStepHandler.EXPECTED_RESULT
                                          + expectedResult);
    }

    public static void logDesignStepLL(final String step) {
        if (TestFlowStepHandler.enableLogLowLevelDesignStep) {
            TestFlowStepHandler.logger.log(DS_LL_TRACE, step);
        }
    }

    public static void logDSClickIcon(final String iconName) {
        TestFlowStepHandler.logDesignStepLL("Click '" + iconName + "' icon");
    }

    public static void logDSSelectValue(final String spinnerValue, final String spinnerName){
        TestFlowStepHandler.logDesignStepLL("Select " + spinnerValue + " in " + spinnerName);
    }

    public static void logDesignStepLL(final String step,
                                        final String expectedResult) {
        TestFlowStepHandler.logDesignStepLL(step + TestFlowStepHandler.EXPECTED_RESULT
                                            + expectedResult);
    }

    public static void logDesignStepLLNoError(final String step) {
        TestFlowStepHandler.logDesignStepLL(step,
                TestFlowStepHandler.EXPECTED_NO_ERRORS_RESULT);
    }

    public static void logDesignStepNoError(final String step) {
        TestFlowStepHandler.logDesignStep(step,
                TestFlowStepHandler.EXPECTED_NO_ERRORS_RESULT);
    }

    public static void logDesignStepSkip(final String step,
                                          final String skipCondition) {
        TestFlowStepHandler.logDesignStep(step + TestFlowStepHandler.SKIP_STEP
                                          + skipCondition);
    }

    public static void logDSClick(final String nameElement) {
        TestFlowStepHandler.logDesignStep("Click '" + nameElement + "' button.");
    }

    public static void logDSClickTab(final String nameElement,
                                      final String expectedResult) {
        TestFlowStepHandler.logDesignStep("Click '" + nameElement + "' tab.",
                expectedResult);
    }

    public static void logDSClick(final String nameElement,
                                   final String expectedResult) {
        TestFlowStepHandler.logDesignStep("Click '" + nameElement + "' element.",
                expectedResult);
    }

    public static void logDSClickTab(final String nameElement) {
        TestFlowStepHandler.logDesignStep("Click '" + nameElement + "' tab.");
    }

    public static void logDSContainsList(
            final List<? extends Object> commonList,
            final List<? extends Object> innerList) {
        TestFlowStepHandler.logDesignStep("Verify " + commonList + "' contains "
                                          + innerList);
    }

    public static void logDSDetailChecked(final String nameElement) {
        TestFlowStepHandler.logDesignStepLL("Check '" + nameElement + "'.");
    }

    public static void logDSDetailClick(final String nameButton) {
        TestFlowStepHandler.logDesignStepLL("Click '" + nameButton + "' button.");
    }

    public static void logDSDetailClickIcon(final String nameIcon){
        TestFlowStepHandler.logDesignStepLL("Click '" + nameIcon + "' icon.");
    }

    public static void logDSDetailClick(final String nameButton,
                                         final String expectedResult) {
        TestFlowStepHandler.logDesignStepLL("Click '" + nameButton + "' button.",
                expectedResult);
    }

    public static void logDSDetailClickNoError(final String nameElement) {
        TestFlowStepHandler.logDesignStepLLNoError("Click '" + nameElement
                                                   + "' button.");
    }

    public static void logDSDetailEditable(final String nameElement) {
        TestFlowStepHandler.logDesignStepLL("Verify '" + nameElement + "' editable.");
    }

    public static void logDSDetailInput(final String value,
                                         final String nameField) {
        TestFlowStepHandler.logDesignStepLL("Input '" + value + "' value to '"
                                            + nameField + "' field.");
    }

    public static void logDSDetailIsNotPresent(final String nameElement) {
        TestFlowStepHandler.logDesignStep("Verify " + nameElement
                                          + " is not present.");
    }

    public static void logDSDetailIsPresent(final String nameElement) {
        TestFlowStepHandler.logDesignStep("Verify '" + nameElement + "' is present.");
    }

    public static void logDSDetailNoEditable(final String nameElement) {
        TestFlowStepHandler.logDesignStepLL("Verify '" + nameElement
                                            + "' no editable.");
    }

    public static void logDSDetailSelect(final String value,
                                          final String nameDropdown) {
        TestFlowStepHandler.logDesignStepLL("Select '" + value + "' in '"
                                            + nameDropdown + "' drop down.");
    }

    public static void logDSDetailUnchecked(final String nameElement) {
        TestFlowStepHandler.logDesignStepLL("Uncheck '" + nameElement + "'.");
    }

    public static void logDSIsNotPresent(final String nameElement) {
        TestFlowStepHandler.logDesignStep("Verify " + nameElement
                                          + " is not present.");
    }

    public static void logDSIsPresent(final String nameElement) {
        TestFlowStepHandler.logDesignStep("Verify '" + nameElement + "' is present.");
    }

    public static void logDSRightClickMenu(final String nameElement) {
        TestFlowStepHandler.logDesignStep("Right mouse click '" + nameElement + "'.");
    }

    public static void logTestData(final String testData) {
        TestFlowStepHandler.logDesignStep("TEST DATA - " + testData);
    }

    static class TestFlowStepLevel extends Level {

        private static final String BEGIN_LOG_WORD = "DESIGN STEP";
        private static final String ERROR_LOG_WORD = "ERROR";
        private static final String PREFIX_DETAIL = BEGIN_LOG_WORD+ " DETAIL";
        private static final int SYS_LOG_EQUIVALENT_AS_INT = 7;
        private static final int DS_TRACE_INT = Level.INFO_INT + 10;

        static final Level DS_TRACE =
                new TestFlowStepLevel(TestFlowStepLevel.DS_TRACE_INT, BEGIN_LOG_WORD, SYS_LOG_EQUIVALENT_AS_INT);
        static final Level DS_LL_TRACE =
                new TestFlowStepLevel(TestFlowStepLevel.DS_TRACE_INT, PREFIX_DETAIL, SYS_LOG_EQUIVALENT_AS_INT);
        static final Level DS_TRACE_ERROR =
                new TestFlowStepLevel(TestFlowStepLevel.DS_TRACE_INT, ERROR_LOG_WORD, SYS_LOG_EQUIVALENT_AS_INT);

        private TestFlowStepLevel(int level, String levelStr, int syslogEquivalent) {
            super(level, levelStr, syslogEquivalent);
        }
    }
}

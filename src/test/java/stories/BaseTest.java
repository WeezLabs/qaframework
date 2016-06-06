package stories;

import org.testng.ITestContext;
import org.testng.annotations.*;
import testRail.*;

import java.util.Map;

/**
 * Base class for any types of tests.
 * It is getting and keeping test run number and test case number for concrete test.
 */
public abstract class BaseTest {
    @TestRailRunId
    protected int runId = 0;

    @TestRailCaseId
    protected int caseId = 0;

    @TestRailDefects
    protected String defects = "";

    @TestRailDescription
    protected String description = "";

    protected String testDescription;

    @Parameters({"runId", "defects"})
    @BeforeTest
    public void beforeTest(@Optional String runId, @Optional String defects) {
        if (runId != null) {
            this.runId = Integer.parseInt(runId);
        }

        if (defects != null) {
            this.defects = defects;
        }
    }

    /**
     * Creating descriptions of the test, which will be displayed in case of error
     *
     * @param context test context supplied by TestNG.
     */
    @BeforeClass
    public void setTestDescription(ITestContext context) {
        String xmlFileName = context
                .getSuite()
                .getXmlSuite()
                .getFileName()
                .replaceAll("(?i)(.*[\\\\/]?)([\\\\/])([a-z][a-z\\d_]+\\.xml)", "$3");

        StringBuffer suiteParamsStr = new StringBuffer("\n");
        if (context.getSuite().getXmlSuite().getParameters() != null) {
            suiteParamsStr.append("Suite parameters:\n");
            Map<String, String> params = context.getSuite().getXmlSuite().getParameters();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                suiteParamsStr.append("\t" + entry.getKey() + " : " + entry.getValue() + "\n");
            }
        }

        StringBuffer testParamsStr = new StringBuffer("\n");
        if (context.getCurrentXmlTest().getTestParameters() != null) {
            testParamsStr.append("Test parameters:\n");
            Map<String, String> params = context.getCurrentXmlTest().getTestParameters();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                testParamsStr.append("\t" + entry.getKey() + " : " + entry.getValue() + "\n");
            }
        }

        testDescription =
                "\n==================================================================" +
                        "\nSuite file name: " + xmlFileName +
                        "\nSuite: " + context.getSuite().getName() +
                        suiteParamsStr +
                        "\nTest:  " + context.getCurrentXmlTest().getName() +
                        testParamsStr.toString() +
                        "\n------------------------------------------------------------------\n";
        System.out.println(testDescription);
    }

    @BeforeMethod
    public void resetCaseId() {
        caseId = 0;
    }

    @BeforeMethod
    public void resetDescription() {
        description = "";
    }
}


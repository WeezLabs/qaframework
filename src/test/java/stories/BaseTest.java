package stories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.collections.comparators.NullComparator;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import testRail.TestRailCaseId;
import testRail.TestRailDefects;
import testRail.TestRailRunId;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public abstract class BaseTest {

    @TestRailRunId
    protected int runId = 0;

    @TestRailCaseId
    protected int caseId = 0;

    @TestRailDefects
    protected String defects = null;
    
    protected String testDescription;

    @Parameters( {"runId", "defects"})
    @BeforeTest
    public void beforeTest(@Optional String runId, @Optional String defectsStr) throws IOException, SQLException {
        if (runId != null) {
            this.runId = Integer.parseInt(runId);
        }
        if (defectsStr != null) {
            this.defects = defectsStr;
        }
    }

    @BeforeClass
    public void setTestDescription(ITestContext context) {
        String xmlFileName =
                context.getSuite().getXmlSuite().getFileName().replaceAll(
                        "(?i)(.*[\\\\/]?)([\\\\/])([a-z][a-z\\d_]+\\.xml)",
                        "$3");
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
                "\n=================================================================="
                        + "\nSuite file name: " + xmlFileName
                        + "\nSuite: " + context.getSuite().getName()
                        + suiteParamsStr
                        + "\nTest:  " + context.getCurrentXmlTest().getName()
                        + testParamsStr.toString()
                        + "\n------------------------------------------------------------------\n";
        System.out.println(testDescription);
    }

    @BeforeMethod
    public void resetCaseId() {
        caseId = 0;
    }
}

package stories;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import listeners.ScreenRecordingListener;
import listeners.TestListener;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import page.AbstractPage;
import testRail.TestRailCaseId;
import testRail.TestRailDefects;
import testRail.TestRailRunId;
import util.AppiumHelper;
import util.PropertyUtils;
import util.TestFlowStepHandler;
import util.TestProperties;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Listeners({TestListener.class, ScreenRecordingListener.class})
public abstract class BaseTest {
    private static final String FRONTEND_TESTING = "frontend";

    @TestRailRunId
    protected int runId = 0;

    @TestRailCaseId
    protected int caseId = 0;

    @TestRailDefects
    protected String defects = null;
    
    protected String testDescription;
    /**
     * The Appium driver providing a way to interact with the actual application.
     * In the most of cases used to create {@link AbstractPage} extending classes instances.
     */
    public static AppiumDriver<MobileElement> driver;

    protected final TestProperties properties = PropertyUtils.getTestProperties();

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

    @BeforeSuite(alwaysRun = true)
    public final void setUpSuite() {
        if(properties.testingType.equals(FRONTEND_TESTING)) {
            try {
                driver = AppiumHelper.startAppium(properties);
            } catch (Throwable e) {
                TestFlowStepHandler.logError(e);
            }
        }
    }

    @AfterSuite(alwaysRun = true)
    public final void tearDownAppium() {
        try {
            driver.quit();
        } catch (Throwable e) {
            TestFlowStepHandler.logError(e);
        }
    }
}

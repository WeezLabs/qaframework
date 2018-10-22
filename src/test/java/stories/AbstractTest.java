package stories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.collections.comparators.NullComparator;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.annotations.Optional;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Abstract class that describes base for any test. Contanins properties extraction and so on
 */
@SuppressWarnings("ALL")
public abstract class AbstractTest {
    protected ResourceBundle rbTest = ResourceBundle.getBundle("test");
    protected ResourceBundle rbServer = ResourceBundle.getBundle("server");
    protected ObjectMapper mapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
            enable(SerializationFeature.INDENT_OUTPUT);
    private NullComparator comparator = new NullComparator(true);
    protected String testDescription;

    // String constants
    protected final String ASC = "asc";
    protected final String DESC = "desc";
    protected final String DDT_DATA_PATH = "/ddt/";
    protected final String PHOTO_DATA_PATH = "/photo/";
    protected DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");

    protected String mailbox;
    protected String mailboxPassword;
    protected String mailbox_base;

    @BeforeTest
    public void beforeTest(@Optional String runId) throws IOException, SQLException, InterruptedException {
        // initialize password and username for mailbox access
        mailbox = rbServer.getString("_EMAIL_ADDRESS");
        mailboxPassword = rbServer.getString("_EMAIL_PASSWORD");
        mailbox_base = mailbox.substring(0, mailbox.indexOf("@")) + "+";
    }

    @BeforeClass
    public void setTestDescription(ITestContext context) {
        String xmlFileName = context.getSuite().getXmlSuite().getFileName()
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
}


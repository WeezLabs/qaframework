package stories;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dao.DBDao;
import domain.User;
import org.apache.commons.collections.comparators.NullComparator;
import org.testng.ITestContext;
import org.testng.annotations.*;
import testRail.TestRailCaseId;
import testRail.TestRailRunId;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * получение и сохранение номера тестрана и тесткейза конкретного теста
 */
public abstract class AbstractTestV2 {

    @TestRailRunId
    protected int runId = 0;

    @TestRailCaseId
    protected int caseId = 0;

    protected ResourceBundle rbTest = ResourceBundle.getBundle("test");
    protected ObjectMapper mapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
            enable(SerializationFeature.INDENT_OUTPUT);
    private NullComparator comparator = new NullComparator(true);
    protected String testDescription;
    protected User user;
    //можно добавить несколько рабочих пользователей
    protected DBDao dBDao;
    //  порядок сортировки
    protected final String ASC = "asc";
    protected final String DESC = "desc";
    protected String[] ords = {ASC, DESC};

    //обработка входных параметров их xml файла
    @Parameters({"userName", "userPassword", "runId"})
    @BeforeTest
    public void beforeTest(@Optional String login, @Optional String password, @Optional String runId) throws IOException, SQLException {
        user = new User();

        dBDao = new DBDao();
        if (runId != null) this.runId = Integer.parseInt(runId);
        if (login != null)
            user.setEmail(rbTest.getString(login));
        if (password != null)
            user.setPassword(rbTest.getString(password));
        if (login != null && password != null) {
            String userName = rbTest.getString(login);
            String userPassword = rbTest.getString(password);
            user = new User(userName, userPassword);
        }
    }

    //содание описания теста, которое будет выведено в случае ошибки
    @BeforeClass
    public void setTestDescription(ITestContext context) {
        String xmlFileName = context.getSuite().getXmlSuite().getFileName().replaceAll("(?i)(.*[\\\\/]?)([\\\\/])([a-z][a-z\\d_]+\\.xml)", "$3");
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


}


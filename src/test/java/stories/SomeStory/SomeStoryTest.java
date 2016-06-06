package stories.SomeStory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ddto.DdtDataProvider;
import ddto.DdtoSet;
import domain.User;
import dto.SomeClass;
import org.testng.ITestContext;
import org.testng.annotations.*;
import service.RestAPIFacade;
import stories.BaseRestFulTest;
import util.SoftAssert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Example of the test.
 */
public class SomeStoryTest extends BaseRestFulTest {
    // Path to the files with input data.
    private static final String DDT_DATA_PATH = "/ddt/";

    private RestAPIFacade restAPIFacade;

    private ObjectMapper mapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
            enable(SerializationFeature.INDENT_OUTPUT);

    private User testUser;

    // Processing of file with input data.
    @DataProvider(name = "ddtManager")
    public Object[][] ddtManager(ITestContext context) throws IOException {
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return ddtDataProvider.ddtProvider(context, "jsonDdtFile", DDT_DATA_PATH);
    }

    @BeforeClass(dependsOnMethods = "setTestDescription")
    public void precondition()
            throws JsonProcessingException {
        // If you want to perform REST API calls with user credentials:
        //      restAPIFacade = new RestAPIFacade(user.getTgt().method_to_get_token);
        // Token is stored inside the AuthenticatedResponseModel.

        // If REST API calls does'nt need authorization just use:
        //      restAPIFacade = new RestAPIFacade(null);
        restAPIFacade = new RestAPIFacade(null);
    }

    @BeforeMethod
    public void beforeMethod() throws IOException, InterruptedException {
        // Pre processing before each method call if needed.

        // For example you can create test user.
        testUser = new User();
    }

    @AfterMethod
    public void afterMethod() throws IOException, InterruptedException {
        // Post processing after each method call if needed.
    }

    @Test(description = "Test description",
          dataProvider = "ddtManager")
    public void createCharacterTest(Map ddtSetMap)
            throws IOException, SQLException, InterruptedException {
        // {SomeClass} - DTO class from data provider.
        DdtoSet<SomeClass> ddtSet =
                mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<SomeClass>>() {});
        caseId = ddtSet.getTestCaseId();
        String cd = testDescription + "\nedit profile test\nsetId:" + ddtSet.getSetId() +
                    "\n" + ddtSet.getDescription() +
                    "\n(caseId:" + caseId + ")\n[ERROR] ";
        SoftAssert sa = new SoftAssert(cd);

        // Test itself.

        sa.assertAll();
    }
}


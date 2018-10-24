package stories.SampleStory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import ddto.DdtDataProvider;
import ddto.DdtoSet;
import domain.Actions;
import domain.User;
import dto.ErrorDto;
import dto.ListDto;
import dto.ListInput;
import org.testng.ITestContext;
import org.testng.annotations.*;
import stories.AbstractTest;
import util.SoftAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SampleTest extends AbstractTest {
    private Actions userActions;
    private Map<Long, Integer> listIds;

    @DataProvider(name = "ddtManager", parallel = true)
    public Object[][] ddtManager(ITestContext context) throws IOException {
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return ddtDataProvider.ddtProvider(context, "jsonDdtFile", DDT_DATA_PATH);
    }

    @BeforeClass(dependsOnMethods = "setTestDescription")
    public void precondition() throws IOException {
        // todo set up your test environment here
        listIds = new HashMap<>();

        /* it's better to create new user and then delete him, but in this example I'm using
           existed one to prevent flooding of real production server. We don't have any way to delete users on it :)
         */
        User user = new User("vmarkov@distillery.com", "password");
        userActions = new Actions(user.getAccessToken());
    }

    @Test(description = "Sample test with data provider", dataProvider = "ddtManager", threadPoolSize = 5,
            invocationCount = 1)
    public void sampleTestWithDataProvider(Map ddtSetMap) throws IOException {
        DdtoSet<ListInput> ddtSet = mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<ListInput>>() {
        });
        SoftAssert sa = new SoftAssert();

        // keep listId form current thread to be able to remove the list in postconditions
        long threadId = Thread.currentThread().getId();
        listIds.put(threadId, 0);

        if (ddtSet.getStatusCode() != 201) {
            Response response = userActions.listsService().postList(ddtSet.getDto(), ddtSet.getStatusCode());
            ErrorDto error = mapper.readValue(response.asString(), ErrorDto.class);
            // todo check error response here!

        } else {
            ListDto list = userActions.listsService().postList(ddtSet.getDto());
            int listId = list.getId();
            listIds.replace(threadId, listId);
            // todo check positive response here!

            // get created list by id and check that all data was really saved correctly
            list = userActions.listsService().getList(listId);
            // todo check GET response here!
        }

        sa.assertAll();
    }

    @AfterMethod()
    public void postConditionMethod() {
        // todo perform cleanup after each test method here here!

        // remove the object that was created in current thread
        long threadId = Thread.currentThread().getId();
        int listId = listIds.get(threadId);
        if (listId != 0)
            userActions.listsService().deleteList(listId);
    }


    @AfterClass()
    public void postConditionClass() {
        // todo perform cleanup after the whole test here! For example, delete your test user
    }
}

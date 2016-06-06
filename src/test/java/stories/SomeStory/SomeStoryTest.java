package stories.SomeStory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ddto.DdtDataProvider;
import ddto.DdtoSet;
import dto.SomeClass;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.RestAPIFacade;
import stories.BaseTest;
import util.SoftAssert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Maria on 24.03.2016.
 */
public class SomeStoryTest extends BaseTest {
    private static final String DDT_DATA_PATH = "/ddt/"; //путь к файлам с входными данными
    private RestAPIFacade restAPIFacade;// переменная со всеми необходимыми методами
    private ObjectMapper mapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
            enable(SerializationFeature.INDENT_OUTPUT);


    //обработка конкретного входного файла
    @DataProvider(name = "ddtManager")
    public Object[][] ddtManager(ITestContext context) throws IOException {
        DdtDataProvider ddtDataProvider = new DdtDataProvider();
        return ddtDataProvider.ddtProvider(context, "jsonDdtFile", DDT_DATA_PATH);
    }

    //получение и логин тестовым пользователем restAPIFacade = new RestAPIFacade(user.getTgt().обращение_к_функции_получения_токена);
    // токен сохраняется в AuthenticatedResponseModel
    //если логин не нужен оставляем restAPIFacade = new RestAPIFacade(null);

    @BeforeClass(dependsOnMethods = "setTestDescription")
    public void precondition()
            throws JsonProcessingException {
        // если пользователя надо создавать в самом тесте то в параметрах xml сьюта убираем входные параметры логин\пароль и создаем пользователя
        //user = new User(userName, userPassword);
        restAPIFacade = new RestAPIFacade(null);
    }

    @AfterMethod
    public void postCondition() throws IOException, InterruptedException {
        // пост тестовая обработка при необходимости
    }

    @Test(description = "описание теста",
            dataProvider = "ddtManager")
    public void createCharacterTest(Map ddtSetMap) throws IOException, SQLException, InterruptedException {
        //{SomeClass} - меняется на тот что необходим в дата провайдере для конкретного теста
        DdtoSet<SomeClass> ddtSet = mapper.convertValue(ddtSetMap, new TypeReference<DdtoSet<SomeClass>>() {
        });
        caseId = ddtSet.getTestCaseId();
        String cd = testDescription + "\nedit profile test\nsetId:" + ddtSet.getSetId() + "\n" + ddtSet.getDescription() + "\n(caseId:" + caseId + ")\n[ERROR] ";
        SoftAssert sa = new SoftAssert(cd);
        //далее следует собственно выполнение тестовых действий

        sa.assertAll();
    }


}


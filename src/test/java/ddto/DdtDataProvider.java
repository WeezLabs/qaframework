package ddto;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.testng.ITestContext;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DdtDataProvider {

    protected ObjectMapper mapper =
            new ObjectMapper()
                    .configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Object[][] ddtProvider(ITestContext context, String jsonFileParameterName, String ddtDataPath) throws
            IOException {
        String jsonDdtFile = context.getCurrentXmlTest().getParameter(jsonFileParameterName);
        URL resourceUrl = getClass().getResource(ddtDataPath + jsonDdtFile);
        ArrayList<LinkedHashMap> dataArrayList = new ArrayList<LinkedHashMap>();

        dataArrayList =
                mapper.readValue(new File(resourceUrl.getFile()), new TypeReference<ArrayList<LinkedHashMap>>() {});
        Object[][] objectArray = new Object[dataArrayList.size()][];
        for (int i = 0; i < dataArrayList.size(); i++) {
            objectArray[i] = new Object[] {dataArrayList.get(i)};
        }
        return objectArray;
    }
}

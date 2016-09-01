package util.ddto;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.ITestContext;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Class for reading the file from the input data and converting it into a data provider.
 */
public class DdtDataProvider {
    private ObjectMapper mapper =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public Object[][] ddtProvider(ITestContext context, String jsonFileParameterName, String ddtDataPath)
            throws IOException {
        String jsonDdtFile = context.getCurrentXmlTest().getParameter(jsonFileParameterName);
        URL resourceUrl = getClass().getResource(ddtDataPath + jsonDdtFile);
        ArrayList<LinkedHashMap> dataArrayList;

        dataArrayList =
                mapper.readValue(new File(resourceUrl.getFile()), new TypeReference<ArrayList<LinkedHashMap>>(){});

        Object[][] objectArray = new Object[dataArrayList.size()][];
        for (int i = 0; i < dataArrayList.size(); i++) {
            objectArray[i] = new Object[]{dataArrayList.get(i)};
        }

        return objectArray;
    }
}

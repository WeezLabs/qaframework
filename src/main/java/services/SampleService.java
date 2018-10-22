package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import dto.SampleDto;
import rest.RestMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Example of some services (endpoints grouped by some base bath)
 */
public class SampleService extends BaseService {
    private static final String SUB_PATH = "/sample/";

    SampleService(RestMethods restMethods) {
        super(restMethods);
    }

    // TODO Just an example of some GET endpoint call. Replace it with something real
    public Response getSamples(Integer page, Integer perPage, Integer statusCode) {
        List<String> query = new ArrayList<>();
        if (page!= null)
            query.add("page=" + page);
        if (perPage != null)
            query.add("per_page=" + perPage);

        String queryString = SUB_PATH + ((query.size() > 0) ? "?" + String.join("&", query) : "");
        return restMethods.get(queryString, statusCode);
    }

    public List<SampleDto> getSamples() throws IOException {
        Response response = getSamples(null, null, 200);
        return mapper.readValue(response.asString(), new TypeReference<List<SampleDto>>() {
        });
    }
}

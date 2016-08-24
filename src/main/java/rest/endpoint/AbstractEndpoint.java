package rest.endpoint;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import rest.Rest;
import rest.RestService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for RestFul rest.endpoint calls.
 */
public abstract class AbstractEndpoint {
    private RestService rest;
    private String subPath;

    protected ObjectMapper mapper =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public AbstractEndpoint(RestService rest, String subPath) {
        this.rest = rest;
        this.subPath = subPath;
    }

    public Response get(String method, String description)
            throws IOException {
        return get(method, 200, description);
    }

    public Response get(String method, Map<String, String> headers, String description)
            throws IOException {
        return get(method, headers, 200, description);
    }

    public Response get(String method, Map<String, String> headers, int expectedStatusCode, String description)
            throws IOException {
        return rest.get(subPath + method, headers, expectedStatusCode, description);
    }

    public Response get(String method, int expectedStatusCode, String description)
            throws IOException {
        Map<String, String> headers = new HashMap<>();
        return rest.get(subPath + method, headers, expectedStatusCode, description);
    }

    public Response post(String method, String description)
            throws IOException {
        return post(method, 200, null, description);
    }

    public Response post(String method, int expectedStatusCode, Object obj, String description)
            throws IOException {
        Map<String, String> headers = new HashMap<>();
        return rest.post(subPath + method, headers, obj, expectedStatusCode, description);
    }

    public Response post(String method,
                         Map<String, String> headers,
                         int expectedStatusCode,
                         Object obj,
                         String description)
            throws IOException {
        return rest.post(subPath + method, headers, obj, expectedStatusCode, description);
    }

    public Response post(String method, String filename, int expectedStatusCode, String description)
            throws IOException {
        return rest.post(subPath + method, filename, expectedStatusCode, description);
    }

    public Response patch(String method, int expectedStatusCode, Object obj, String description)
            throws IOException {
        Map<String, String> headers = new HashMap<>();
        return rest.patch(subPath + method, headers, obj, expectedStatusCode, description);
    }

    public Response delete(String method,
                           Map<String, String> headers,
                           int expectedStatusCode,
                           Object obj,
                           String description)
            throws IOException {
        return rest.delete(subPath + method, headers, obj, expectedStatusCode, description);
    }

    public Response delete(String method, int expectedStatusCode, Object obj, String description)
            throws IOException {
        Map<String, String> headers = new HashMap<>();
        return rest.delete(subPath + method, headers, obj, expectedStatusCode, description);
    }

    public Response put(String method, int expectedStatusCode, Object obj, String description)
            throws IOException {
        Map<String, String> headers = new HashMap<>();
        return rest.put(subPath + method, headers, obj, expectedStatusCode, description);
    }

    public Response put(String method,
                        Map<String, String> headers,
                        int expectedStatusCode,
                        Object obj,
                        String description)
            throws IOException {
        return rest.put(subPath + method, headers, obj, expectedStatusCode, description);
    }

    public final Rest getRest() {
        return rest;
    }

    public final String getBasePath() {
        return rest.getBasePath();
    }

    public final String getSubPath() {
        return subPath;
    }

    public String getBaseUri() {
        return rest.getBaseUri();
    }
}

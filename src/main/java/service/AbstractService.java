package service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.response.Response;
import rest.RestService;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractService  {
    private final static int SUCCESS_STATUS_CODE = 200;


    private RestService rest;
    private String subPath;

    public AbstractService(RestService rest, String subPath) {
        this.rest = rest;
        this.subPath = subPath;
    }

    public final RestService rest() {
        return rest;
    }

    public final String getSubPath() {
        return subPath;
    }

    public Response get(String method, String description) {
        return get(method, SUCCESS_STATUS_CODE, description);
    }

    public Response get(String method, Map<String, ?> headers, String description) {
        return get(method, headers, SUCCESS_STATUS_CODE, description);
    }

    public Response get(String method, Map<String, ?> headers, int expectedStatusCode, String description) {
        return rest.get(subPath + method, headers, expectedStatusCode, description);
    }

    public Response get(String method, int expectedStatusCode, String description) {
        return rest.get(subPath + method, null, expectedStatusCode, description);
    }

    public Response post(String method, String description) {
        return post(method, SUCCESS_STATUS_CODE, null, description);
    }

    public Response post(String method, int expectedStatusCode, String requestBody, String description) {
        return rest.post(subPath + method, null, requestBody, expectedStatusCode, description);
    }

    public Response post(String method, Map<String, ?> headers, int expectedStatusCode, String requestBody, String description) {
        return rest.post(subPath + method, headers, requestBody, expectedStatusCode, description);
    }

    public Response patch(String method, int expectedStatusCode, String requestBody, String description) {
        return rest.patch(subPath + method, null, requestBody, expectedStatusCode, description);
    }

    public Response delete(String method, Map<String, ?> headers, int expectedStatusCode, String requestBody, String description) {
        return rest.delete(subPath + method, headers, requestBody, expectedStatusCode, description);
    }

    public Response delete(String method, int expectedStatusCode, String requestBody, String description) {
        return rest.delete(subPath + method, null, requestBody, expectedStatusCode, description);
    }

    public Response put(String method, int expectedStatusCode, String requestBody, String description) {
        return rest.put(subPath + method, null, requestBody, expectedStatusCode, description);
    }

    public Response put(String method, Map<String, ?> headers, int expectedStatusCode, String requestBody, String description) {
        return rest.put(subPath + method, headers, requestBody, expectedStatusCode, description);
    }
}

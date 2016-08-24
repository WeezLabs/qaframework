package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.response.Response;

import java.util.Map;

/**
 * Interface that Rest rest.endpoint classes must implement.
 */
public interface Rest {
    Response get(String methodPath, Map<String, String> headers, int expectedStatusCode, String description);

    Response get(String methodPath,
                 Map<String, String> headers,
                 Map<String, ?> parameters,
                 int expStatusCode,
                 String description);

    Response post(String methodPath, Map<String, String> headers, Object object, int expStatusCode, String description)
            throws JsonProcessingException;

    Response post(String methodPath,
                  Map<String, String> headers,
                  Map<String, ?> parameters,
                  int expStatusCode,
                  String description)
            throws JsonProcessingException;

    Response put(String methodPath, Map<String, String> headers, Object object, int expStatusCode, String description)
            throws JsonProcessingException;

    Response patch(String methodPath, Map<String, String> headers, Object object, int expStatusCode, String description)
            throws JsonProcessingException;

    Response delete(String methodPath,
                    Map<String, String> headers,
                    Object object,
                    int expStatusCode,
                    String description)
            throws JsonProcessingException;

    Response post(String methodPath, String fileName, int expStatusCode, String description)
            throws JsonProcessingException;

    Map<String, String> getStandardHeaders();

    String getBaseUri();

    String getBasePath();
}
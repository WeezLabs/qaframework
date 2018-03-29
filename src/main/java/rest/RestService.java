package rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.config.DecoderConfig;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.HttpClientConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import util.PropertyUtils;

import static com.jayway.restassured.config.RedirectConfig.redirectConfig;

public class RestService {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String DEFAULT_CONTENT_CHARSET = "UTF-8";
    private static final Integer CONNECTION_TIMEOUT_GET_REQUEST = 25000;
    private static final Integer SOCKET_TIMEOUT_GET_REQUEST = 25000;
    private static final Integer CONNECTION_TIMEOUT_POST_REQUEST = 40000;
    private static final Integer SOCKET_TIMEOUT_POST_REQUEST = 40000;
    private static final String METHOD_TYPE_GET = "GET ";
    private static final String METHOD_TYPE_POST = "POST ";
    private static final String METHOD_TYPE_DELETE = "DELETE ";
    private static final String METHOD_TYPE_PUT = "PUT ";
    private static final String METHOD_TYPE_PATCH = "PATCH ";
    private static final int STACK_TRACE_DEPTH = 3;
    private static ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(SerializationFeature.INDENT_OUTPUT);
    private Map<String, String> standardHeaders = new HashMap<>();
    private RequestSpecification postSpecification;
    private RequestSpecification getSpecification;

    public RestService(String userToken) {
        RestAssured.config =
                RestAssuredConfig
                        .newConfig()
                        .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset(DEFAULT_CONTENT_CHARSET));

        standardHeaders.put("Host", PropertyUtils.getServerHost());
        standardHeaders.put("Accept-Language", "ru-RU");
        standardHeaders.put("Accept-Charset", "UTF-8");
        standardHeaders.put("User-Agent", "API-TEST");
        standardHeaders.put("Authorization", "Bearer " + userToken);

        HttpClientConfig getHttpConfig =
                RestAssuredConfig
                        .newConfig()
                        .getHttpClientConfig()
                        .setParam("http.connection.timeout", CONNECTION_TIMEOUT_GET_REQUEST)
                        .setParam("http.socket.timeout", SOCKET_TIMEOUT_GET_REQUEST);

        HttpClientConfig postHttpConfig =
                RestAssuredConfig
                        .newConfig()
                        .getHttpClientConfig()
                        .setParam("http.connection.timeout", CONNECTION_TIMEOUT_POST_REQUEST)
                        .setParam("http.socket.timeout", SOCKET_TIMEOUT_POST_REQUEST);

        getSpecification = setRequestSpecification(getHttpConfig);
        postSpecification = setRequestSpecification(postHttpConfig);

        RestAssured.urlEncodingEnabled = false;
    }

    private RequestSpecification setRequestSpecification(HttpClientConfig httpClientConfig) {
        RequestSpecBuilder getRequestSpecBuilder = new RequestSpecBuilder();
        String fullAPiUrl = PropertyUtils.getServerHost() + PropertyUtils.getServerApiBaseUrl();
        getRequestSpecBuilder.setBaseUri(fullAPiUrl);
        getRequestSpecBuilder.setPort(PropertyUtils.getServerPort());
        getRequestSpecBuilder.addHeaders(standardHeaders);
        getRequestSpecBuilder.setContentType(CONTENT_TYPE);
        getRequestSpecBuilder.setConfig(RestAssuredConfig.newConfig()
                .httpClient(httpClientConfig)
                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation())
                .decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset(DEFAULT_CONTENT_CHARSET))
                .encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset(DEFAULT_CONTENT_CHARSET))
                .redirect(redirectConfig().followRedirects(false)));
        return getRequestSpecBuilder.build();
    }


    private void checkStatusCode(Response response, int expectedStatusCode) {
        if (response.getStatusCode() == expectedStatusCode) {
            return;
        }
        assert response.getStatusCode() == expectedStatusCode :
                "\nClass: " + Thread.currentThread().getStackTrace()[2].getClassName()
                        + "\nMethod: " + Thread.currentThread().getStackTrace()[2].getMethodName()
                        + "\nExpected StatusCode:" + expectedStatusCode + ", but actual:" + response.getStatusCode()
                        + "\nActual Response:\n" + jsonStringPretty(response.asString());
    }

    private void checkStatusCode(String restMethodFullPath,
                                 Response response,
                                 int expectedStatusCode,
                                 Map reqHeaders,
                                 String reqBody,
                                 String description) {
        if (response.getStatusCode() == expectedStatusCode || expectedStatusCode < 0) {
            return;
        }
        String respBodyPretty = response.getContentType().contains("json") ? jsonStringPretty(response.asString()) :
                response.prettyPrint();

        assert response.getStatusCode() == expectedStatusCode :
                description + requestDescription(restMethodFullPath, reqHeaders, reqBody, 4)
                        + "\nExpected StatusCode:" + expectedStatusCode + ", but actual:" + response.getStatusCode()
                        + (respBodyPretty.equals("") ? "\nActual response body is empty" : "\nActual response:\n"
                        + respBodyPretty) + "\n";
    }

    private String requestDescription(String restMethodFullPath,
                                      Map reqHeaders,
                                      String reqBody,
                                      int stackTraceDepth) {
        return "\nClass: " + Thread.currentThread().getStackTrace()[stackTraceDepth].getClassName()
                + "\nMethod: " + Thread.currentThread().getStackTrace()[stackTraceDepth].getMethodName()
                + "\nREST method: " + restMethodFullPath
                + ((reqHeaders != null) ? "\nRequest headers:\n" + mapToString(reqHeaders) : "")
                + ((reqBody != null && !reqBody.equals("")) ? "\nRequest body:\n" + jsonStringPretty(reqBody) : "");

    }

    public Response get(String methodPath, Map<String, ?> headers, int expStatusCode, String description) {
        Response response;
        if (null == headers) {
            headers = new HashMap();
        }
        try {
            response = RestAssured.given().spec(getSpecification).headers(headers).get(methodPath);
        } catch (Exception exc) {
            throw new Error(description
                    + requestDescription(METHOD_TYPE_GET + methodPath, headers, "", STACK_TRACE_DEPTH)
                    + "\n" + exc);
        }
        checkStatusCode(METHOD_TYPE_GET + methodPath, response, expStatusCode, headers, "", description);
        return response;
    }

    public Response post(String methodPath, Map<String, ?> headers, String requestBody, int expStatusCode, String description) {
        Response response;
        if (null == headers) {
            headers = new HashMap();
        }
        if (null == requestBody) {
            requestBody = "";
        }
        try {
            response = RestAssured.given().spec(postSpecification).headers(headers).body(requestBody).post(methodPath);
        } catch (Exception exc) {
            throw new Error(description
                    + requestDescription(METHOD_TYPE_POST + methodPath, headers, requestBody, STACK_TRACE_DEPTH)
                    + "\n" + exc);
        }
        checkStatusCode(METHOD_TYPE_POST + methodPath, response, expStatusCode, headers, requestBody, description);
        return response;
    }

    public Response put(String methodPath, Map<String, ?> headers, String requestBody, int expStatusCode, String description) {
        Response response;
        try {
            response = RestAssured.given().spec(postSpecification).headers(headers).body(requestBody).put(methodPath);
        } catch (Exception exc) {
            throw new Error(description
                    + requestDescription(METHOD_TYPE_PUT + methodPath, headers, requestBody, STACK_TRACE_DEPTH)
                    + "\n" + exc);
        }
        checkStatusCode(METHOD_TYPE_PUT + methodPath, response, expStatusCode, headers, requestBody, description);
        return response;
    }

    public Response delete(String methodPath, Map<String, ?> headers, String requestBody, int expStatusCode, String description) {
        Response response;
        if (null == headers) {
            headers = new HashMap();
        }
        if (null == requestBody) {
            requestBody = "";
        }
        try {
            response = RestAssured.given().spec(postSpecification).headers(headers).body(requestBody).delete(methodPath);
        } catch (Exception exc) {
            throw new Error(description
                    + requestDescription(METHOD_TYPE_DELETE + methodPath, headers, requestBody, STACK_TRACE_DEPTH)
                    + "\n" + exc);
        }
        checkStatusCode(METHOD_TYPE_DELETE + methodPath, response, expStatusCode, headers, requestBody, description);
        return response;
    }

    public Response patch(String methodPath, Map<String, ?> headers, String requestBody, int expStatusCode, String description) {
        Response response;
        if (null == headers) {
            headers = new HashMap();
        }
        if (null == requestBody) {
            requestBody = "";
        }
        try {
            response = RestAssured.given().spec(postSpecification).headers(headers).body(requestBody).patch(methodPath);

        } catch (Exception exc) {
            throw new Error(description
                    + requestDescription(METHOD_TYPE_PATCH + methodPath, headers, requestBody, STACK_TRACE_DEPTH)
                    + "\n" + exc);
        }
        checkStatusCode(METHOD_TYPE_PATCH + methodPath, response, expStatusCode, headers, requestBody, description);
        return response;
    }

    private String jsonStringPretty(@Nonnull String jsonString) {
        try {
            Object json = mapper.readValue(jsonString, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (IOException exc) {
            throw new IllegalStateException("Failed to convert json string", exc);
        }
    }

    private String mapToString(@Nonnull Map<String, ?> map) {
        try {
            Object json = mapper.convertValue(map, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (IOException exc) {
            throw new IllegalStateException("Failed to convert map to string", exc);
        }
    }
}

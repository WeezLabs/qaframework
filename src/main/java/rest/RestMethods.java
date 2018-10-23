package rest;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.*;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Common rest methods
 */
public class RestMethods {

    private static final Integer CONNECTION_TIMEOUT = 25000;
    private static final Integer SOCKET_TIMEOUT = 25000;
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private RequestSpecification postSpecification;
    private RequestSpecification uploadSpecification;
    private RequestSpecification getSpecification;

    private String basePath;

    public RestMethods(String accessToken) {

        // extract parameters from resource bundle
        ResourceBundle rb = ResourceBundle.getBundle("server");
        boolean ssl = Boolean.valueOf(rb.getString("SSL"));
        basePath = rb.getString("_SERVER");

        int port;
        if (ssl) {
            port = 443;
            basePath = "https://" + basePath;
        }
        else {
            port = Integer.valueOf(rb.getString("_SERVER_PORT"));
            basePath = "http://" + basePath;
        }

        HttpClientConfig httpConfig = RestAssuredConfig.newConfig().getHttpClientConfig()
                .setParam("http.connection.timeout", CONNECTION_TIMEOUT)
                .setParam("http.socket.timeout", SOCKET_TIMEOUT);

        RestAssuredConfig restAssuredConfig = RestAssuredConfig.newConfig().
                httpClient(httpConfig).
                sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));

        // Add required headers
        Map<String, String> STANDARD_HEADERS = new HashMap<>();
        STANDARD_HEADERS.put("Accept-Charset", "UTF-8");
        if (accessToken != null)
            STANDARD_HEADERS.put("Authorization", accessToken);

        // GET specification
        RequestSpecBuilder getRequestSpecBuilder = requestSpecConfigAssembler(
                port,
                basePath,
                STANDARD_HEADERS,
                CONTENT_TYPE,
                restAssuredConfig);
        getSpecification = getRequestSpecBuilder.build();

        // POST specification (customize its builder if needed)
        RequestSpecBuilder postRequestSpecBuilder = requestSpecConfigAssembler(
                port,
                basePath,
                STANDARD_HEADERS,
                CONTENT_TYPE,
                restAssuredConfig);
        postSpecification = postRequestSpecBuilder.build();

        // file upload specification
        RequestSpecBuilder uploadRequestSpecBuilder = requestSpecConfigAssembler(
                port,
                basePath,
                STANDARD_HEADERS,
                "multipart/form-data",
                restAssuredConfig);
        uploadSpecification = uploadRequestSpecBuilder.build();
    }

    /**
     * Status code checker. It is custom instead of native RestAssured assertion,
     * because I want to display actual response in case of fail. It speeds up testing and debugging
     @param response Response to validate
     @param expectedStatusCode expected status code
     */
    private void checkStatusCode(Response response, int expectedStatusCode) {
        // -1 means that we don't care about status code
        int statusCode = response.statusCode();
        if (expectedStatusCode != -1) {
            assert (statusCode == expectedStatusCode) :
                "Wrong status code!" +
                        "\nExpected " + expectedStatusCode + ", but actual is " + statusCode +
                        "\nActual response:\n" + response.asString();

        }
    }

    /**
     * Constructs request specification by given params
     * @param port port
     * @param basePath base URL
     * @param headers standard request headers
     * @param contentType request content_type
     * @param config RestAssuredConfig object
     * @return RequestSpecBuilder object
     */
    @SuppressWarnings("unchecked")
    private RequestSpecBuilder requestSpecConfigAssembler(int port, String basePath, Map headers, String contentType,
            RestAssuredConfig config) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setPort(port);
        requestSpecBuilder.setBaseUri(basePath);
        requestSpecBuilder.addHeaders(headers);
        requestSpecBuilder.setContentType(contentType);
        requestSpecBuilder.setConfig(config);

        return requestSpecBuilder;
    }

    //------------------------------------------------------------------//

    /* GET method */
    public Response get(String methodPath, int expStatusCode) {
        Response response = RestAssured.given().
                spec(getSpecification).
                get(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }

    /* POST method */
    public Response post(String methodPath, Object body, int expStatusCode) {
        Response response;
        response = RestAssured.given().
                spec(postSpecification).
                body(body).
                post(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }

    // POST with file upload
    public Response post(String methodPath, List<File> files, int expStatusCode) {
        Response response;

        for (File file : files){
            uploadSpecification.multiPart("file", file, "image/jpg");
        }

        response = RestAssured.given().
                spec(uploadSpecification).
                post(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }

    /* DELETE method */
    public Response delete(String methodPath, Object body, int expStatusCode) {
        Response response = RestAssured.given().
                spec(postSpecification).
                body(body).
                delete(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }

    /* PUT method */
    public Response put(String methodPath, Object body, int expStatusCode) {
        Response response;
        response = RestAssured.given().
                spec(postSpecification).
                body(body).
                put(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }

    /* PATCH method */
    public Response patch(String methodPath, Object body, int expStatusCode) {
        Response response;
        response = RestAssured.given().
                spec(postSpecification).
                body(body).
                patch(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }
}


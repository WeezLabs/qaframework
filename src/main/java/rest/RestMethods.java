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
    private RequestSpecification uploadSepcification;
    private RequestSpecification getSpecification;

    private String basePath;

    public RestMethods(String accessToken) {

        // extract parameters from resource bundle
        ResourceBundle rb = ResourceBundle.getBundle("server");
        boolean ssl = Boolean.valueOf(rb.getString("SSL"));

        int port;
        if (ssl) {
            port = 443;
        }
        else {
            port = Integer.valueOf(rb.getString("_SERVER_PORT"));
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

        // Get the base path
        basePath = rb.getString("_SERVER");

        // GET specification
        RequestSpecBuilder getRequestSpecBuilder = new RequestSpecBuilder();
        getRequestSpecBuilder.setPort(port);
        getRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        getRequestSpecBuilder.setContentType(CONTENT_TYPE);
        getRequestSpecBuilder.setConfig(restAssuredConfig);
        getSpecification = getRequestSpecBuilder.build();

        // POST specification
        RequestSpecBuilder postRequestSpecBuilder = new RequestSpecBuilder();
        postRequestSpecBuilder.setPort(port);
        postRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        postRequestSpecBuilder.setContentType(CONTENT_TYPE);
        postRequestSpecBuilder.setConfig(restAssuredConfig);
        postSpecification = postRequestSpecBuilder.build();

        // file upload specification
        RequestSpecBuilder uploadRequestSpecBuilder = new RequestSpecBuilder();
        uploadRequestSpecBuilder.setPort(port);
        uploadRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        uploadRequestSpecBuilder.setContentType("multipart/form-data");
        uploadRequestSpecBuilder.setConfig(restAssuredConfig);
        uploadSepcification = uploadRequestSpecBuilder.build();
    }

    /* Status code checker. It is custom instead of native RestAssured assertion,
       because I want to display actual response in case of fail. It speeds up testing and debugging
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
            uploadSepcification.multiPart("file", file, "image/jpg");
        }

        response = RestAssured.given().
                spec(uploadSepcification).
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


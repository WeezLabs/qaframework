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

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static ResourceBundle rb = ResourceBundle.getBundle("server");

    private String basePath;

    private Map<String, String> STANDARD_HEADERS = new HashMap<>();
    private RequestSpecification postSpecification;
    private RequestSpecification getSpecification;
    private boolean ssl;
    private int port;
    private RequestSpecBuilder getRequestSpecBuilder = new RequestSpecBuilder();
    private RequestSpecBuilder postRequestSpecBuilder = new RequestSpecBuilder();
    private RequestSpecBuilder uploadRequestSpecBuilder = new RequestSpecBuilder();

    public RestMethods(String accessToken) {
        String sslStr = rb.getString("SSL").toLowerCase();
        String sslDefaultStr = rb.getString("SSL_DEFAULT").toLowerCase();
        ssl = sslStr.equals("${ssl}")
                ? (sslDefaultStr.contains("true") || sslDefaultStr.contains("yes"))
                : (sslStr.contains("true") || sslStr.contains("yes"));

        HttpClientConfig httpConfig = RestAssuredConfig.newConfig().getHttpClientConfig()
                .setParam("http.connection.timeout", 25000)
                .setParam("http.socket.timeout", 25000);

        RestAssuredConfig restAssuredConfig = RestAssuredConfig.newConfig().
                httpClient(httpConfig).
                sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));

        // Add required headers
        STANDARD_HEADERS.put("Accept-Charset", "UTF-8");
        if (accessToken != null)
            STANDARD_HEADERS.put("Authorization", accessToken);

        // Get base path
        basePath = rb.getString("_SERVER");

        // GET specification
        getRequestSpecBuilder.setPort(port);
        getRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        getRequestSpecBuilder.setContentType(CONTENT_TYPE);
        getRequestSpecBuilder.setConfig(restAssuredConfig);
        getSpecification = getRequestSpecBuilder.build();

        // POST specification
        postRequestSpecBuilder.setPort(port);
        postRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        postRequestSpecBuilder.setContentType(CONTENT_TYPE);
        postRequestSpecBuilder.setConfig(restAssuredConfig);
        postSpecification = postRequestSpecBuilder.build();

        // file upload specification
        uploadRequestSpecBuilder = new RequestSpecBuilder();
        uploadRequestSpecBuilder.setPort(port);
        uploadRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        uploadRequestSpecBuilder.setContentType("multipart/form-data");
        uploadRequestSpecBuilder.setConfig(RestAssuredConfig.newConfig().
                httpClient(httpConfig).
                sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
    }

    // Status code checker
    private void checkStatusCode(Response response, int expectedStatusCode) {
        // -1 means that we don't care about status code
        if (expectedStatusCode != -1) {
            response.then().statusCode(expectedStatusCode);
        }
    }

    //------------------------------------------------------------------//

    /* GET method */
    public Response get(String methodPath, int expStatusCode) {
        Response response = RestAssured.given().
                spec(getSpecification).
                headers(STANDARD_HEADERS).
                get(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }

    /* POST method */
    public Response post(String methodPath, Object body, int expStatusCode) {
        Response response;
        response = RestAssured.given().
                spec(postSpecification).
                headers(STANDARD_HEADERS).
                body(body).
                post(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }

    public Response post(String methodPath, List<File> files, int expStatusCode) {
        Response response;
        RequestSpecification spec = uploadRequestSpecBuilder.build();

        for(File file:files){
            spec.multiPart("file", file, "image/jpg");
        }

        response = RestAssured.given().
                spec(spec).
                post(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }

    /* DELETE method */
    public Response delete(String methodPath, Object body, int expStatusCode) {
        Response response = RestAssured.given().
                spec(postSpecification).
                headers(STANDARD_HEADERS).
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
                headers(STANDARD_HEADERS).
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
                headers(STANDARD_HEADERS).
                body(body).
                patch(basePath + methodPath);
        checkStatusCode(response, expStatusCode);
        return response;
    }
}


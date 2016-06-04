package rest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.config.*;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * заполнение хедеров идентификационными данными, полученными после логина, для дальнейшего использования
 */
public class RestService implements Rest {
    private String st;

    private static final String GET_CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String POST_CONTENT_TYPE = "application/json;charset=UTF-8";
    private static ObjectMapper mapper = new ObjectMapper().
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).
            enable(SerializationFeature.INDENT_OUTPUT);
    private static ResourceBundle rb = ResourceBundle.getBundle("server");

    private Map<String, String> STANDARD_HEADERS = new HashMap<String, String>();
    private Map<String, String> ADDITIONAL_HEADERS = new HashMap<String, String>();
    private RequestSpecification postSpecification;
    private RequestSpecification postSpecificationUnContent;
    private RequestSpecification postSpecificationUnloadContent;
    private RequestSpecification getSpecification;
    private ResponseSpecification responseSpecification;
    private boolean ssl;
    private String baseUri;
    private int port;
    private String basePath;
    private RequestSpecBuilder getRequestSpecBuilder = new RequestSpecBuilder();
    private RequestSpecBuilder postRequestSpecBuilder = new RequestSpecBuilder();
    private RequestSpecBuilder postRequestUnContentSpecBuilder = new RequestSpecBuilder();
    private RequestSpecBuilder postRequestUnloadContentSpecBuilder = new RequestSpecBuilder();
    private ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();

    private ScriptEngineManager factory = new ScriptEngineManager();
    private ScriptEngine engine = factory.getEngineByName("JavaScript");

    private void setSt(String st) {
        /*if ((st != null) && (!st.isEmpty())) {
            this.st = st;
            STANDARD_HEADERS.put("Authorization", "AccessToken "+st);
            Map<String,String> xTokenHeader = new HashMap<>();
            xTokenHeader.put("Authorization", "AccessToken "+st);

            getRequestSpecBuilder.addHeaders(xTokenHeader);
            getSpecification = getRequestSpecBuilder.build();

            postRequestSpecBuilder.addHeaders(xTokenHeader);
            postSpecification = postRequestSpecBuilder.build();

            postRequestUnContentSpecBuilder.addHeaders(xTokenHeader);
            postSpecificationUnContent = postRequestUnContentSpecBuilder.build();
        }*/
    }

    public String getSt() {
        return st;
    }

    public RestService(String tgt) throws JsonProcessingException {
        //инициация переменных
        if (tgt != null) {
            setSt(tgt);
        }

        basePath = rb.getString( "_BASE_PATH");

        String sslStr = rb.getString("SSL").toLowerCase();
        String sslDefaultStr = rb.getString("SSL_DEFAULT").toLowerCase();
        ssl = sslStr.equals("${ssl}")
                ? (sslDefaultStr.contains("true") || sslDefaultStr.contains("yes"))
                : (sslStr.contains("true") || sslStr.contains("yes"));
        String xHost = rb.getString("_SERVER");
        baseUri = (ssl ? "https://" : "http://") + xHost;
        port = Integer.parseInt(rb.getString("_SERVER_PORT"));

        RestAssured.config = RestAssuredConfig.newConfig().decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.config = RestAssuredConfig.newConfig().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));

        // параметры таймаута отдельно для пост, отдельно для гет запросов
        HttpClientConfig getHttpConfig = RestAssuredConfig.newConfig().getHttpClientConfig().setParam("http.connection.timeout", new Integer(25000)).setParam("http.socket.timeout", new Integer(25000));
        HttpClientConfig postHttpConfig = RestAssuredConfig.newConfig().getHttpClientConfig().setParam("http.connection.timeout", new Integer(40000)).setParam("http.socket.timeout", new Integer(40000));

        //инициализация сиандартных заголовков, используются в каждом запросе
        STANDARD_HEADERS.put("Host", xHost);
        STANDARD_HEADERS.put("Accept-Language", "ru-RU");
        STANDARD_HEADERS.put("Accept-Charset", "UTF-8");
        // настройка спецификаций для гет запросов
        getRequestSpecBuilder.setBaseUri(baseUri + basePath);
        getRequestSpecBuilder.setPort(port);
        getRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        getRequestSpecBuilder.setContentType(GET_CONTENT_TYPE);
        getRequestSpecBuilder.setConfig(RestAssuredConfig.newConfig().
                httpClient(getHttpConfig).
                sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
        getSpecification = getRequestSpecBuilder.build();
        // настройка спецификаций для пост запросов
        postRequestSpecBuilder.setBaseUri(baseUri + basePath);
        postRequestSpecBuilder.setPort(port);
        postRequestSpecBuilder.addHeaders(STANDARD_HEADERS);
        postRequestSpecBuilder.setContentType(POST_CONTENT_TYPE);
        postRequestSpecBuilder.setConfig(RestAssuredConfig.newConfig().
                httpClient(postHttpConfig).
                sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
        postSpecification = postRequestSpecBuilder.build();
        //настройка спецификаций для аплоада
        postRequestUnContentSpecBuilder.setBaseUri(baseUri + basePath);
        postRequestUnContentSpecBuilder.setPort(port);
        postRequestUnContentSpecBuilder.addHeaders(STANDARD_HEADERS);
        postRequestUnContentSpecBuilder.setConfig(RestAssuredConfig.newConfig().
                httpClient(postHttpConfig).
                sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
        postSpecificationUnContent = postRequestUnContentSpecBuilder.build();


        postRequestUnloadContentSpecBuilder.setBaseUri(baseUri + basePath);
        postRequestUnloadContentSpecBuilder.setPort(port);
        postRequestUnloadContentSpecBuilder.addHeaders(STANDARD_HEADERS);
        postRequestUnloadContentSpecBuilder.setContentType("multipart/form-data");
        postRequestUnloadContentSpecBuilder.setConfig(RestAssuredConfig.newConfig().
                httpClient(postHttpConfig).
                sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation()).
                decoderConfig(DecoderConfig.decoderConfig().defaultContentCharset("UTF-8")).
                encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")));
        postSpecificationUnloadContent = postRequestUnloadContentSpecBuilder.build();

        responseSpecification = responseSpecBuilder.build();
        RestAssured.urlEncodingEnabled = false;
    }

    protected void checkStatusCode(Response response, int expectedStatusCode) {
        if (response.getStatusCode() == expectedStatusCode) return;
        String respBodyPretty = response.asString();
        if (response.getContentType().contains("json")) {
            try {
                Object json = mapper.readValue(response.asString(), Object.class);
                respBodyPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        assert response.getStatusCode() == expectedStatusCode :
                "\nClass: " + Thread.currentThread().getStackTrace()[2].getClassName() +
                "\nMethod: " + Thread.currentThread().getStackTrace()[2].getMethodName() +
                "\nExpected StatusCode:" + expectedStatusCode + ", but actual:" + response.getStatusCode() +
                "\nActual Response:\n" + respBodyPretty;
    }

    protected void checkStatusCode(String restMethodFullPath, Response response, int expectedStatusCode, Map reqHeaders, Map reqParameters, String reqBody) {
        checkStatusCode(restMethodFullPath, response, expectedStatusCode, reqHeaders, reqParameters, reqBody, "");
    }

    protected void checkStatusCode(String restMethodFullPath, Response response, int expectedStatusCode, Map reqHeaders, Map reqParameters, String reqBody, String description) {
        if (response.getStatusCode() == expectedStatusCode || expectedStatusCode < 0) return;
//        String reqBodyPretty = jsonStringPretty(reqBody);

        String respBodyPretty = response.getContentType().contains("json") ? jsonStringPretty(response.asString()) : response.prettyPrint();

        assert response.getStatusCode() == expectedStatusCode :
                description + requestDescription(restMethodFullPath, reqHeaders, reqParameters, reqBody, 4) +
                "\nExpected StatusCode:" + expectedStatusCode + ", but actual:" + response.getStatusCode() +
                (respBodyPretty.equals("") ? "\nActual response body is empty" : "\nActual response:\n" + respBodyPretty) + "\n";
    }

    protected String requestDescription(String restMethodFullPath, Map reqHeaders, Map reqParameters, String reqBody, int stackTraceDepth) {
        return "\nClass: " + Thread.currentThread().getStackTrace()[stackTraceDepth].getClassName() +
               "\nMethod: " + Thread.currentThread().getStackTrace()[stackTraceDepth].getMethodName() +
               "\nBASE_URI: " + baseUri +
               "\nREST method: " + restMethodFullPath +
               ((reqHeaders != null) ? "\nRequest headers:\n" + mapToString(reqHeaders) : "") +
               ((reqParameters != null) ? "\nRequest parameters:\n" + mapToString(reqParameters) : "") +
               ((reqBody != null && !reqBody.equals("")) ? "\nRequest body:\n" + jsonStringPretty(reqBody) : "");

    }

    @Deprecated
    public Response post(String methodPath, Map headers, String bodyStr, int expStatusCode) {
        headers.putAll(STANDARD_HEADERS);
        Response response = RestAssured.given().
                spec(postSpecification).
                headers(headers).
                body(bodyStr).
                post(methodPath);
        checkStatusCode("POST " + basePath + methodPath, response, expStatusCode, headers, null, bodyStr);
        return response;
    }

    @Deprecated
    public Response get(String methodPath, Map headers, int expStatusCode) {
        headers.putAll(STANDARD_HEADERS);
        Response response = RestAssured.given().
                spec(getSpecification).
                headers(headers).
                get(methodPath);
        checkStatusCode("GET " + basePath + methodPath, response, expStatusCode, headers, null, "");
        return response;
    }

    @Deprecated
    public Response delete(String methodPath, Map headers, String bodyStr, int expStatusCode) {
        headers.putAll(STANDARD_HEADERS);
        Response response = RestAssured.given().
                spec(postSpecification).
                headers(headers).
                body(bodyStr).
                delete(methodPath);
        checkStatusCode("DELETE " + basePath + methodPath, response, expStatusCode, headers, null, bodyStr);
        return response;
    }

//    =================================================

    @Override
    public Response get(String methodPath, Map headers, int expStatusCode, String description) throws InterruptedException {
        // headers.putAll(ADDITIONAL_HEADERS);
        Response response = null;
        try {

            if (null == headers) {
                response = RestAssured.given().
                        spec(getSpecification).

                        get(methodPath);
            } else {
                response = RestAssured.given().
                        spec(getSpecification).
                        headers(headers).

                        get(methodPath);
            }
        } catch (Exception e) {
//            throw new Error(description + "\nGET "+ basePath + methodPath + "\n" + e);
            throw new Error(description + requestDescription("GET " + basePath + methodPath, headers, null, "", 3) + "\n" + e);
        }
        checkStatusCode("GET " + basePath + methodPath, response, expStatusCode, headers, null, "", description);
        return response;
    }

    @Override
    public Response get(String methodPath, Map headers, Map parameters, int expStatusCode, String description) {
        //  headers.putAll(ADDITIONAL_HEADERS);
        Response response = null;
        try {

            if (headers != null) {
                if (parameters != null) {
                    response = RestAssured.given().
                            spec(getSpecification).
                            headers(headers).

                            parameters(parameters).
                            get(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(getSpecification).
                            headers(headers).

                            get(methodPath);
                }
            } else {
                if (parameters != null) {
                    response = RestAssured.given().
                            spec(getSpecification).

                            parameters(parameters).
                            get(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(getSpecification).

                            get(methodPath);
                }
            }
        } catch (Exception e) {
//            throw new Error(description + "\nGET "+ basePath + methodPath + "\n" + e);
            throw new Error(description + requestDescription("GET " + basePath + methodPath, headers, null, "", 3) + "\n" + e);
        }
        checkStatusCode("GET " + basePath + methodPath, response, expStatusCode, headers, null, "", description);
        return response;
    }

    @Override
    public Response post(String methodPath, Map headers, Object object, int expStatusCode, String description) throws JsonProcessingException {
        //  headers.putAll(ADDITIONAL_HEADERS);
        Response response = null;
        String bodyStr = object != null
                ? (object instanceof String ? (String) object : mapper.writeValueAsString(object))
                : null;
        try {
            if (headers != null) {
                if (object != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).

                            body(bodyStr).
                            post(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).

                            post(methodPath);
                }
            } else {
                if (object != null) {
                    response = RestAssured.given().
                            spec(postSpecification).

                            body(bodyStr).
                            post(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).

                            post(methodPath);
                }
            }
        } catch (Exception e) {
//            throw new Error(description + "\nPOST "+ basePath + methodPath + "\n" + e);
            throw new Error(description + requestDescription("POST " + basePath + methodPath, headers, null, bodyStr, 3) + "\n" + e);
        }
        checkStatusCode("POST " + basePath + methodPath, response, expStatusCode, headers, null, bodyStr, description);
        return response;
    }

    @Override
    public Response post(String methodPath, Map headers, Map parameters, int expStatusCode, String description) throws JsonProcessingException {
        // headers.putAll(ADDITIONAL_HEADERS);
//        String bodyStr = mapper.writeValueAsString(object);
        Response response = null;
        try {
            if (headers != null && headers.size() != 0) {
                if (parameters != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).

                            parameters(parameters).
                            post(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).

                            post(methodPath);
                }
            } else {
                if (parameters != null) {
                    response = RestAssured.given().
                            spec(postSpecification).

                            parameters(parameters).
                            post(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecificationUnContent).

                            post(methodPath);
                }
            }
        } catch (Exception e) {
//            throw new Error(description + "\nPOST "+ basePath + methodPath + "\n" + e);
            throw new Error(description + requestDescription("POST " + basePath + methodPath, headers, parameters, "", 3) + "\n" + e);
        }
        checkStatusCode("POST " + basePath + methodPath, response, expStatusCode, headers, parameters, "", description);
        return response;
    }

    @Override
    public Response put(String methodPath, Map headers, Object object, int expStatusCode, String description) throws JsonProcessingException {
        // headers.putAll(ADDITIONAL_HEADERS);
        String bodyStr = mapper.writeValueAsString(object);
        Response response = null;
        try {
            response = RestAssured.given().
                    spec(postSpecification).
                    headers(headers).
                    body(bodyStr).
                    put(methodPath);
        } catch (Exception e) {
//            throw new Error(description + "\nPUT "+ basePath + methodPath + "\n" + e);
            throw new Error(description + requestDescription("PUT " + basePath + methodPath, headers, null, bodyStr, 3) + "\n" + e);
        }
        checkStatusCode("PUT " + basePath + methodPath, response, expStatusCode, headers, null, bodyStr, description);
        return response;
    }

    @Override
    public Response delete(String methodPath, Map headers, Object object, int expStatusCode, String description) throws JsonProcessingException {
        // headers.putAll(ADDITIONAL_HEADERS);
        String bodyStr = null != object ? mapper.writeValueAsString(object) : null;
        Response response = null;
        try {
            if (null != headers) {
                if (null != bodyStr) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).

                            body(bodyStr).
                            delete(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).

                            delete(methodPath);
                }
            } else {
                if (null != bodyStr) {
                    response = RestAssured.given().
                            spec(postSpecification).

                            body(bodyStr).
                            delete(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).

                            delete(methodPath);
                }
            }
        } catch (Exception e) {
//            throw new Error(description + "\nDELETE "+ basePath + methodPath + "\n" + e);
            throw new Error(description + requestDescription("DELETE " + basePath + methodPath, headers, null, bodyStr, 3) + "\n" + e);
        }
        checkStatusCode("DELETE " + basePath + methodPath, response, expStatusCode, headers, null, bodyStr, description);
        return response;
    }

    @Override
    public Response patch(String methodPath, Map headers, Object object, int expStatusCode, String description) throws JsonProcessingException {
        // headers.putAll(ADDITIONAL_HEADERS);
        Response response = null;
        String bodyStr = object != null
                ? (object instanceof String ? (String) object : mapper.writeValueAsString(object))
                : null;
        try {
            if (headers != null) {
                if (object != null) {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).

                            body(bodyStr).
                            patch(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).
                            headers(headers).

                            patch(methodPath);
                }
            } else {
                if (object != null) {
                    response = RestAssured.given().
                            spec(postSpecification).

                            body(bodyStr).
                            patch(methodPath);
                } else {
                    response = RestAssured.given().
                            spec(postSpecification).

                            patch(methodPath);
                }
            }
        } catch (Exception e) {
//            throw new Error(description + "\nPOST "+ basePath + methodPath + "\n" + e);
            throw new Error(description + requestDescription("PATCH " + basePath + methodPath, headers, null, bodyStr, 3) + "\n" + e);
        }
        checkStatusCode("PATCH " + basePath + methodPath, response, expStatusCode, headers, null, bodyStr, description);
        return response;
    }

    @Override
    public Response post(String methodPath, String file, int expStatusCode, String description) throws JsonProcessingException {
        Response response = null;
        try {
            response = RestAssured.given().
                    spec(postSpecificationUnContent).
                    multiPart(new File(file)).
                    log().all().
                    when().
                    post(methodPath);
        } catch (Exception e) {
//            throw new Error(description + "\nPUT "+ basePath + methodPath + "\n" + e);
            throw new Error(description + requestDescription("POST " + basePath + methodPath, null, null, null, 3) + "\n" + e);
        }
        checkStatusCode("POST " + basePath + methodPath, response, expStatusCode, null, null, null, description);
        return response;
    }

    @Override
    public Map getStandardHeaders() {
        return STANDARD_HEADERS;
    }

    @Override
    public String getBaseUri() {
        return baseUri;
    }

    @Override
    public String getBasePath() {
        return basePath;
    }

    protected String jsonStringPretty(String jsonString) {
        String retPretty = jsonString;
        if (jsonString != null && !jsonString.equals("")) {
            try {
                Object json = mapper.readValue(jsonString, Object.class);
                retPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (JsonParseException e) {
//                e.printStackTrace();
            } catch (JsonProcessingException e) {
//                e.printStackTrace();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        return retPretty;
    }

    protected String mapToString(Map map) {
        StringBuffer retStr = new StringBuffer();
        for (Map.Entry<String, Object> entry : ((HashMap<String, Object>) map).entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String valueStr = null;
            if (value == null) {
                valueStr = "null";
            } else if (value instanceof String) {
                valueStr = (String) value;
            }
            if (value instanceof Boolean) {
                valueStr = ((Boolean) value).toString();
            } else if (value instanceof Byte) {
                valueStr = ((Byte) value).toString();
            } else if (value instanceof Character) {
                valueStr = ((Character) value).toString();
            } else if (value instanceof Short) {
                valueStr = ((Short) value).toString();
            } else if (value instanceof Integer) {
                valueStr = ((Integer) value).toString();
            } else if (value instanceof Long) {
                valueStr = ((Long) value).toString();
            } else if (value instanceof Float) {
                valueStr = ((Float) value).toString();
            } else if (value instanceof Double) {
                valueStr = ((Double) value).toString();
            } else if (value instanceof BigDecimal) {
                valueStr = ((BigDecimal) value).toString();
            } else
                try {
                    valueStr = mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    valueStr = "!!! unknown object !!!";
                    e.printStackTrace();
                }
            if (valueStr.contains("{"))
                valueStr = jsonStringPretty(value.toString()).replaceAll("\r\n", "\r\n\t");
            retStr.append(key + "=" + valueStr + "\n");
            if (key.equals("X-Filter")) try {
                String valueStrDecode = null;
                try {
                    valueStrDecode = URLDecoder.decode((String) engine.eval("escape('" + valueStr + "')"), "UTF-8");
                } catch (ScriptException e) {
//                    e.printStackTrace();
                    try {
                        valueStrDecode = URLDecoder.decode(valueStr, "UTF-8");
                    } catch (IllegalArgumentException e1) {
//                        e1.printStackTrace();
                    }
                }
                if (valueStrDecode != null)
                    retStr.append("decode " + key + "=" + jsonStringPretty(valueStrDecode).replaceAll("\r\n", "\r\n\t") + "\n");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return retStr.toString();
    }

    public void setAdditionalHeaders(Headers headers) {
        if (ADDITIONAL_HEADERS.size() > 0) {
            if (headers.getValue("access-token") != null)
                ADDITIONAL_HEADERS.replace("access-token", headers.getValue("access-token"));
            if (headers.getValue("uid") != null)
                ADDITIONAL_HEADERS.replace("uid", headers.getValue("uid"));
            if (headers.getValue("client") != null)
                ADDITIONAL_HEADERS.replace("client", headers.getValue("client"));
        } else {
            ADDITIONAL_HEADERS.put("access-token", headers.getValue("access-token"));
            ADDITIONAL_HEADERS.put("uid", headers.getValue("uid"));
            ADDITIONAL_HEADERS.put("client", headers.getValue("client"));
        }
    }
}


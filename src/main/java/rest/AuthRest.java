package rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import dto.AuthenticatedResponseModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User authentication.
 */
public class AuthRest {
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthRest() {}

    public AuthenticatedResponseModel postTickets(String userName, String password)
            throws IOException {
        /*
        Here we need to write authentication procedure for our project.

        Response response;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type","password");
        parameters.put("username",userName);
        parameters.put("password",password);

        response = RestAssured.post("/account/signin", null, parameters, 200, "sign in");
        return mapper.readValue(response.asString(), new TypeReference<AuthenticatedResponseModel>() {});
        */

        return null;
    }
}

package rest.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import model.dto.AuthenticatedResponseModel;
import rest.RestService;

import java.io.IOException;

/**
 * Example of the RestFul rest.endpoint class.
 */
public class AuthEndpoint extends AbstractEndpoint {
    public AuthEndpoint(RestService rest) {
        super(rest, "/{main_path}");
    }

    /**
     * Sign in API.
     *
     * @param email email of the user.
     * @param password password of the user.
     * @return authentication data of the user.
     * @throws IOException
     */

    public AuthenticatedResponseModel signIn(String email, String password)
            throws IOException {
        Response response = signIn(email, password, 200, "sign in");
        return mapper.readValue(response.asString(), new TypeReference<AuthenticatedResponseModel>() {
        });
    }

    /**
     * Sign in API.
     *
     * @param email email of the user.
     * @param password password of the user.
     * @param StatusCode expected status code.
     * @param description description of your request.
     * @return authentication data of the user.
     * @throws IOException
     */
    public Response signIn(String email, String password, Integer StatusCode, String description)
            throws IOException {
        return post("/signin",
                    StatusCode,
                    "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}",
                    description);
    }
}

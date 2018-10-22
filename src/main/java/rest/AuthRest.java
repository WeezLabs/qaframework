package rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.jayway.restassured.response.*;
import domain.LoginResponse;
import dto.LoginInput;
import dto.SignupInput;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

/**
 * class for creating and authenticating of the user
 */
public class AuthRest {
    private RestMethods restMethods;
    private ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public AuthRest() {
        restMethods = new RestMethods(null);
    }

    public LoginResponse postTickets(String userName, String password) throws IOException {
        boolean isUuid = false;

        // Check if email of user contains UUID. If so, we must just create new user without login attempt
        String emailUuid = userName;

        // get email part before @
        emailUuid = emailUuid.substring(0, emailUuid.indexOf("@"));

        // check that length is more than 36 symbols (original UUID length in Java)
        if (emailUuid.length() >= 36) {
            // check that email contains real UUID
            emailUuid = emailUuid.substring(emailUuid.length() - 36);

            // try to convert string to uuid
            try {
                UUID.fromString(emailUuid);
                isUuid = true;
            } catch (Exception e) {
                // email is not UUID! so this is most likely NOT a new user
            }
        }

        Response response;
        // if email contains UUID, just create new user
        if (isUuid) {
            response = createUser(userName, password);

        } else {
            // if email doesn't contain UUID, try to login with given credentials
            response = authenticateUser(userName, password);
            // if login attempt failed, we have to create new user
            if ((response.getStatusCode() != 200) && (response.getStatusCode() != 201)) {
                response = createUser(userName, password);
            }
        }

        return mapper.readValue(response.asString(), new TypeReference<LoginResponse>() {});
    }

    private Response authenticateUser(String userName, String password) {
        LoginInput signInCredentials = new LoginInput();
        signInCredentials.setPassword(password);
        signInCredentials.setUsername(userName);
        signInCredentials.setGrant_type("password");
        return restMethods.post("/oauth/token", signInCredentials, -1);
    }

    private Response createUser(String userName, String password) {
        SignupInput registrationInput = new SignupInput();
        registrationInput.setEmail(userName);
        registrationInput.setPassword(password);
        restMethods.post("/users", registrationInput, 200);

        return authenticateUser(userName, password);
    }
}

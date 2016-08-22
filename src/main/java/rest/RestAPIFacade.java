package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import rest.endpoint.AuthEndpoint;

/**
 * Class for access to all RestFul endpoints.
 */
public class RestAPIFacade {
    private RestService rest;

    private AuthEndpoint authEndpoint;

    public RestAPIFacade(String auth)
            throws JsonProcessingException {
        this.rest = new RestService(auth);
    }

    public AuthEndpoint getAuthEndpoint() {
        if (null == authEndpoint) {
            authEndpoint = new AuthEndpoint(rest);
        }
        return authEndpoint;
    }
}


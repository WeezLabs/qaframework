package model;

import model.dto.AuthenticatedResponseModel;
import rest.RestService;

import java.io.IOException;

/**
 * Class that hides the details of user creation.
 */
public class User {
    private String id;
    private String login;
    private String password;

    private AuthenticatedResponseModel tgt;

    public User(String login, String password, boolean recreateIfExists, boolean needToLogin)
            throws IOException {
        // Here we need to write user creation procedure for our project.
        this.login = login;
        this.password = password;

        if (recreateIfExists) {
            // Recreate user if needed.
        }

        if (needToLogin) {
            RestService rest = new RestService(null);
            this.tgt = rest.postTickets(login, password);
        }
    }

    public User() throws IOException {
        this(null, null, false, false);
    }

    public User(String login, String password) throws IOException {
        this(login, password, true, false);
    }

    public User(String login, String password, boolean recreateIfExists) throws IOException {
        this(login, password, recreateIfExists, false);
    }

    public AuthenticatedResponseModel getTgt() {
        return tgt;
    }

    public void setTgt(AuthenticatedResponseModel tgt) {
        this.tgt = tgt;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

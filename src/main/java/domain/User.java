package domain;

import rest.AuthRest;

import java.io.IOException;

/**
 * Internal User class.
 * Creates/logins user with given email/password and keeps user data (tokens, ids etc) for tests
 */
public class User {
    private Integer id;
    private String email;
    private String password;
    private String accessToken;
    private LoginResponse tgt;

    public User(String email, String password) throws IOException {
        this.email = email;
        this.password = password;
        AuthRest authRest = new AuthRest();
        this.tgt = authRest.postTickets(email, password);
        this.id = tgt.getUser_id();
        this.accessToken = "Bearer " + tgt.getAccess_token();
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponse getTgt() {
        return tgt;
    }

    public void setTgt(LoginResponse tgt) {
        this.tgt = tgt;
    }
}

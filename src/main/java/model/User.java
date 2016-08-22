package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import db.DBDao;
import model.dto.AuthenticatedResponseModel;
import rest.RestService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Class that hides the details of user creation.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {
    @JsonIgnore
    private Integer id;
    private String login;
    private String password;

    @JsonIgnore
    private AuthenticatedResponseModel tgt;

    @JsonIgnore
    private DBDao dbDao = new DBDao();

    @JsonIgnore
    public User(String login, String password, boolean needToLogin)
            throws IOException, SQLException {
        this.login = login;
        this.password = password;

        // Here we need to write user creation procedure for our project.
        if (needToLogin) {
            RestService rest = new RestService(null);
            this.tgt = rest.postTickets(login, password);
        }
    }

    public User() {
    }

    @JsonIgnore
    public AuthenticatedResponseModel getTgt() {
        return tgt;
    }

    @JsonIgnore
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

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Integer id) {
        this.id = id;
    }

}

package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import rest.AuthRest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Admin on 01.09.2015.
 */
@SuppressWarnings("ALL")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {
    @JsonIgnore
    protected Integer id;
    protected String email;
    protected String password;
    @JsonIgnore
    protected LoginResponse tgt;


    @JsonIgnore
    public User(String email, String password) throws IOException, SQLException, InterruptedException {
        this.email = email;
        this.password = password;
        AuthRest authRest = new AuthRest();
        this.tgt = authRest.postTickets(email, password);
        this.id = tgt.getUser_id();
    }

    public User() {
    }

    @JsonIgnore
    public LoginResponse getTgt() {
        return tgt;
    }

    @JsonIgnore
    public void setTgt(LoginResponse tgt) {
        this.tgt = tgt;
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

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Integer id) {
        this.id = id;
    }

}

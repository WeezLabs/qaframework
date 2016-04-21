package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import dao.DBDao;
import dto.AuthenticatedResponseModel;
import rest.AuthRest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.sql.SQLException;

/**
 * класс для сохранения данных залогиненого пользователя
 * поля дополняются при необходимости
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {
    @JsonIgnore
    protected Integer id;
    protected String email;
    protected String password;
    @JsonIgnore
    protected AuthenticatedResponseModel tgt;
    @JsonIgnore
    protected DBDao dbDao = new DBDao();


    @JsonIgnore
    public User(String email, String password) throws IOException, SQLException {
        this.email = email;
        this.password = password;
        AuthRest authRest=new AuthRest();
        //при необходимости можно вынести блок создания пользователя из функции authRest.postTickets в данный конструктор
        // блок создания пользователя при его отсутсвии должен создаваться под каждый проект индивидуально.
        this.tgt = authRest.postTickets(email, password);

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

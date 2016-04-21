package rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import dto.AuthenticatedResponseModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * первонас=чальная идентификация пользователя - логин
 * необходимо заменить путь и параметры в пост запросе в соотвествии с особенностями проекта
 */
public class AuthRest extends AbstractRest {
    public AuthRest() {
        super(Module.NONE);
    }

    public AuthenticatedResponseModel postTickets(String userName, String password) throws IOException {
        Response response ;
      /* здесь надо для каждого проекта индивидуально написать функцию создания пользователя если его не существует и логина только что созданным пользователем
         при необходимости создание пользователя можно вынести в конструктор класса domain.User, а в данном классе только вызывать логин пользователя по полученным логин\пароль
        Response response ;
        ADDITIONAL_HEADERS.put("uid",userName);
        response =post("/oauth/token", null, "{\"username\":\"" + userName + "\",\"password\":\"" + password + "\", \"grant_type\": \"password\"}",-1, "sign in");
        if(response.getStatusCode()!=200){
            post("/users", null, "{\"email\":\"" + userName + "\",\"password\":\"" + password + "\"}",200, "sign up");
            response =post("/oauth/token", null, "{\"username\":\"" + userName + "\",\"password\":\"" + password + "\", \"grant_type\": \"password\"}",200, "sign in");
        }
        return mapper.readValue(response.asString(), new TypeReference<AuthenticatedResponseModel>() {});
        */

    }

}

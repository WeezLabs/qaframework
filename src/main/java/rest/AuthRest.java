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
 * первонас=чальная идентификация пользователя - логин
 * необходимо заменить путь и параметры в пост запросе в соотвествии с особенностями проекта
 */
public class AuthRest {
    private static final ObjectMapper mapper = new ObjectMapper();

    private AuthRest() {}

    public static AuthenticatedResponseModel postTickets(String userName, String password)
            throws IOException {
        /* здесь надо для каждого проекта индивидуально написать функцию создания пользователя если его не существует
           и логина только что созданным пользователем  при необходимости создание пользователя можно
           в конструктор класса domain.User, а в данном классе только вызывать логин пользователя
           по полученным логин\пароль.
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

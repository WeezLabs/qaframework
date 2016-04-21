package service;

import dto.*;
import rest.RestService;

import java.io.IOException;

/**
 *класс авторизации
 * заменить {main_path} на путь к функциям логина
 * пример функции логина закоментирован
 * для каждой группы функций создается свой класс типа SomeService с описанием набора необходимых методов
 */
public class AuthService extends AbstractService {

    public AuthService(RestService rest) {
        super(rest, "/{main_path}");
    }


    /**
     * sign in
     * @param email
     * @param password
     * @return
     * @throws IOException
     */
  /*  public AuthenticatedResponseModel signIn(String email, String password) throws IOException {
        Response response = signIn(email, password, 200, "sign in");
        AuthenticatedResponseModel  retVal = mapper.readValue(response.asString(), new TypeReference<AuthenticatedResponseModel >(){});
        return retVal;
    }
    public Response signIn(String email, String password, Integer StatusCode, String description) throws IOException {
        Response response = post("/signin", StatusCode, "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}", description);
        return response;
    }*/

}

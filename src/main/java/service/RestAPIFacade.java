package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import rest.RestService;

/**
 * класс в который сведены все сервисы необходимые для работы системы
 * заполняется перемынными типа SomeService someService
 * c конструктором
 * public SomeService someService() {
 if(null == someService) someService = new SomeService(rest);
 return someService;
 }
 */
public class RestAPIFacade {
    private RestService rest;

    private AuthEndpoint authEndpoint;

    public RestAPIFacade(String tgt)
            throws JsonProcessingException {
        this.rest = new RestService(tgt);
    }

    public AuthEndpoint getAuthEndpoint() {
        if (null == authEndpoint){
            authEndpoint = new AuthEndpoint(rest);
        }
        return authEndpoint;
    }
}


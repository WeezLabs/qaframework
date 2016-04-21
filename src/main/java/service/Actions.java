package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import rest.Module;

/**
 * класс в который сведены все сервисы необходимые для работы системы
 * заполняется перемынными типа SomeService someService
 * c конструктором
 * public SomeService someService() {
 if(null == someService) someService = new SomeService(rest);
 return someService;
 }
 */
public class Actions extends AbstractModule {
    private AuthService authService;


    public Actions(String tgt) throws JsonProcessingException {
        super(Module.NONE,tgt);
    }

    public AuthService authService() {
        if(null == authService) authService = new AuthService(rest);
        return authService;
    }


}


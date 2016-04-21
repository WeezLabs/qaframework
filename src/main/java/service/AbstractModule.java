package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import rest.Module;
import rest.RestService;


/**
 * инициализация модуля для работы. расширение для использования нескольких точек входа
 */
public abstract class AbstractModule {
    protected RestService rest;
    protected ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public AbstractModule(Module module, String tgt) throws JsonProcessingException {
        this.rest = new RestService(module,tgt);
    }

    public RestService getRest() {
        return rest;
    }

    public void setRest(RestService rest) {
        this.rest = rest;
    }
}

package services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import rest.RestMethods;

// Plot for any services. Provides mapper and RestMethods object
abstract class BaseService {
    ObjectMapper mapper;
    RestMethods restMethods;

    BaseService(RestMethods restMethods) {
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.restMethods = restMethods;
    }
}

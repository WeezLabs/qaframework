package domain;

import rest.RestMethods;
import services.SampleService;

/**
 * actions for api services
 */
public class Actions {
    private RestMethods restMethods;
    private SampleService sampleService;

    // constructs actions object that allows to access endpoints using given access token
    public Actions(String accessToken) {
        restMethods = new RestMethods(accessToken);
    }

    // Just a sample of services object initialization. Replace it with something real
    public SampleService sampleService() {
        if (null == sampleService) sampleService = new SampleService(restMethods);
        return sampleService;
    }
}


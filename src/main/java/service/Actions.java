package service;

import rest.RestService;

public class Actions {

    private RestService restService;

    public Actions(String userToken) {
        restService = new RestService(userToken);
    }
}

package services;

import com.jayway.restassured.response.Response;
import rest.RestMethods;

import java.io.File;
import java.util.List;

public class FilesService extends BaseService {
    private static final String SUB_PATH = "/files/";

    public FilesService(RestMethods restMethods) {
        super(restMethods);
    }

    //----- Upload file ------//
    public Response postFile(List<File> files, Integer statusCode) {
        return restMethods.post(SUB_PATH, files, statusCode);
    }
}

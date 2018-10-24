package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.restassured.response.Response;
import dto.ListDto;
import dto.ListInput;
import rest.RestMethods;

import java.io.IOException;
import java.util.List;

/**
 * Example of some services (endpoints grouped by some base bath). Based on Curago lists CRUD
 */
public class ListsService extends BaseService {
    private static final String SUB_PATH = "/lists/";

    public ListsService(RestMethods restMethods) {
        super(restMethods);
    }

    //----- Create list ------//
    public Response postList(ListInput input, Integer statusCode) {
        return restMethods.post(SUB_PATH, input, statusCode);
    }

    public ListDto postList(ListInput input) throws IOException {
        Response response = restMethods.post(SUB_PATH, input, 201);
        return mapper.readValue(response.asString(), ListDto.class);
    }

    //----- Get all user's lists------//
    public Response getLists(int statusCode) {
        return restMethods.get(SUB_PATH, statusCode);
    }

    public List<ListDto> getLists() throws IOException {
        Response response = getLists(200);
        return mapper.readValue(response.asString(), new TypeReference<List<ListDto>>() {
        });
    }

    //----- Get list by id------//
    public Response getList(int listId, int statusCode) {
        return restMethods.get(SUB_PATH + listId, statusCode);
    }

    public ListDto getList(int listId) throws IOException {
        Response response = getList(listId, 200);
        return mapper.readValue(response.asString(), ListDto.class);
    }

    //----- Delete list by id------//
    public Response deleteList(int listId, int statusCode) {
        return restMethods.delete(SUB_PATH + listId, statusCode);
    }

    public Response deleteList(int listId){
        return restMethods.delete(SUB_PATH + listId, 204);
    }
}

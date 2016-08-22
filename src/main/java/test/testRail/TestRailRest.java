package test.testRail;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import test.testRail.com.gurock.testrail.APIClient;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Kichkaylo
 * Date: 03.04.14
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 */
public class TestRailRest {
    private static ResourceBundle rb = ResourceBundle.getBundle("testrail");
    private final static String TestRail_LOGIN = rb.getString("TestRail_LOGIN");
    private final static String TestRail_PASSWORD = rb.getString("TestRail_PASSWORD");
    private final static String TestRail_API_KEY = rb.getString("TestRail_API_KEY");
    private final static String TestRail_URI = rb.getString("TestRail_URI");

    private static RequestSpecBuilder requestTestRailSpecBuilder = new RequestSpecBuilder();
    private static RequestSpecification requestTestRailSpec = requestTestRailSpecBuilder.build();

    private static ResponseSpecBuilder responseTestRailSpecBuilder = new ResponseSpecBuilder();
    private static ResponseSpecification responseTestRailSpec = responseTestRailSpecBuilder.build();

    public static void postTestResult(int runId, int caseId, int testResult, String comment){
        HashMap params = new HashMap();
        params.put("status_id", testResult);
        params.put("comment",comment);

        APIClient client = new APIClient(TestRail_URI);
        client.setUser(TestRail_LOGIN);
        client.setPassword(TestRail_PASSWORD);

        try{
//            if(cookies==null){
//                cookies = RestAssured.given().
//                        specification(requestTestRailSpec).
//                        header("Connection", "keep-alive").
//                        header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8").
//                        header("Accept-Encoding","gzip, deflate").
//                        parameters("name", TestRail_LOGIN,
//                                "password", TestRail_PASSWORD,
//                                "rememberme",1).
//                        expect().specification(responseTestRailSpec).
//                        post(TestRail_URI + "/auth/login/").getCookies();
//                System.out.println("cookies:\n - tr_session = "+cookies.get("tr_session")+"\n - tr_rememberme = "+cookies.get("tr_rememberme"));
//            }
          /*  Response response = RestAssured.given().
                    specification(requestTestRailSpec).
                    header("Accept", "application/json").
                    contentType("application/json;charset=UTF-8").
                    parameters(params).
                    expect().specification(responseTestRailSpec).
                    post(TestRail_URI + "/index.php?/api/v2/add_result_for_case/" + runId + "/" + caseId + "&key=" + TestRail_API_KEY);*/
            client.sendPost("add_result_for_case/" + runId + "/" + caseId, params);

            //assert response.getBody().jsonPath().getBoolean("result") : "\nTestRail: sending results failed"+
              //      "\nactual statusCode:"+response.getStatusCode()+
                //    "\nactual response body:\n"+response.getBody().asString();
        } catch (Throwable t){
            // TODO Do we need processing?
            System.out.println("TestRail send:" + runId +
                               "/" + caseId +
                               " - " + testResult +
                               " >" + comment + " FAILED");
            System.out.println(t);
        }
    }
}

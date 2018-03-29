package testRail;

import testRail.com.gurock.testrail.APIClient;

import java.util.HashMap;
import java.util.ResourceBundle;

public class TestRailRest {
    protected static ResourceBundle rb = ResourceBundle.getBundle("testrail");
    protected final static String TestRail_LOGIN = rb.getString("TestRail_LOGIN");
    protected final static String TestRail_PASSWORD = rb.getString("TestRail_PASSWORD");
    private final static String TestRail_URI = rb.getString("TestRail_URI");

    public static void postTestResult(int runId, int caseId, int testResult, String comment, String defects) {
        HashMap params = new HashMap();
        params.put("status_id", testResult);
        params.put("comment", comment);
        params.put("defects", defects);
        APIClient client = new APIClient(TestRail_URI);
        client.setUser(TestRail_LOGIN);
        client.setPassword(TestRail_PASSWORD);
        try {
            client.sendPost("add_result_for_case/" + runId + "/" + caseId, params);
        } catch (Throwable t) {
            System.out.println("TestRail send:" + runId + "/" + caseId + " - " + testResult + " >" + comment + " FAILED");
            System.out.println(t);
        }
    }
}

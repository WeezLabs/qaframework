package testRail;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.lang.reflect.Field;

import static testRail.TestRailRest.postTestResult;

/**
 * Created with IntelliJ IDEA.
 * User: Kichkaylo
 * Date: 31.03.14
 * Time: 9:24
 * To change this template use File | Settings | File Templates.
 */
public class TestRailListener extends TestListenerAdapter {

    private static final int PASSED = 1;
    private static final int FAILED = 5;
    private static final int BLOCKED = 2;
    private static final int SKIPPED = 6;

    @Override
    public void onTestSuccess(ITestResult tr){
        resultProcessing(tr, PASSED);
    }

    @Override
    public void onTestFailure(ITestResult tr){
        resultProcessing(tr, FAILED);
    }

    @Override
    public void onTestSkipped(ITestResult tr){
        resultProcessing(tr, BLOCKED);
    }

    private void resultProcessing(ITestResult tr, int result){
        int fldRunId = 0;
        int fldCaseId = 0;
        int mtdRunId = 0;
        int mtdCaseId = 0;
        int runId = 0;
        int caseId = 0;
        String comment = tr.getThrowable()==null ? "" : tr.getThrowable().toString();
        if(tr.getMethod().getMethod().isAnnotationPresent(TestRail.class)){
            mtdRunId = tr.getMethod().getMethod().getAnnotation(TestRail.class).runId();
            mtdCaseId = tr.getMethod().getMethod().getAnnotation(TestRail.class).caseId();
            if(mtdCaseId > 0 && mtdRunId>0){
                postTestResult(mtdRunId,mtdCaseId,result,comment);
                return;
            }
        } else if(tr.getMethod().getMethod().isAnnotationPresent(TestRailCaseId.class)){
            caseId = tr.getMethod().getMethod().getAnnotation(TestRailCaseId.class).caseId();
        }

        if(tr.getInstance()!=null){
            Field[] fields1 = ArrayUtils.addAll(tr.getInstance().getClass().getDeclaredFields(),
                    tr.getInstance().getClass().getSuperclass().getDeclaredFields());
            Field[] fields = null;
            if(tr.getInstance().getClass().getSuperclass().getSuperclass()!=null){
                fields = ArrayUtils.addAll(fields1, tr.getInstance().getClass().getSuperclass().getSuperclass().getDeclaredFields());
            } else {
                fields = fields1;
            }
            for (Field fld : fields){
                if(fld.isAnnotationPresent(TestRail.class) && (fld.getType().equals(Integer.class) || fld.getType().equals(int.class))){
                    fldRunId = fld.getAnnotation(TestRail.class).runId();
                    try {
                        fld.setAccessible(true);
                        fldCaseId = (Integer) fld.get(tr.getInstance());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
//                    System.out.println("from listener field: runId="+fldRunId+" caseId="+fldCaseId+" testResult="+tr.getStatus());
//                    if(fldCaseId > 0 && fldRunId>0){postTestResult(fldRunId,fldCaseId,result,comment);}
                } else if (fld.isAnnotationPresent(TestRailRunId.class) && (fld.getType().equals(Integer.class) || fld.getType().equals(int.class))){
                    try {
                        fld.setAccessible(true);
                        runId = (Integer) fld.get(tr.getInstance());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if(fld.isAnnotationPresent(TestRailCaseId.class) && (fld.getType().equals(Integer.class) || fld.getType().equals(int.class))){
                    try {
                        fld.setAccessible(true);
                        int cId = (Integer) fld.get(tr.getInstance());
                        if(cId > 0) caseId = cId;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(runId > 0 && caseId > 0) {
            postTestResult(runId, caseId, result, comment);
            return;
        } else if(fldRunId > 0 && fldCaseId > 0){
            postTestResult(fldRunId, fldCaseId, result, comment);
        }
    }
}

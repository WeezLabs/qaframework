package listeners;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.IResultListener2;

import java.util.List;
import java.util.stream.Collectors;

public class TestListener implements IResultListener2 {

    @Override
    public void beforeConfiguration(ITestResult tr) {
        if (tr.getMethod().isBeforeMethodConfiguration()) {
            System.out.println("STARTED TEST SETUP");
        }
    }

    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        if (itr.getMethod().isBeforeMethodConfiguration()) {
            System.out.println("TEST SETUP COMPLETED");
        }
    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
        if (itr.getMethod().isBeforeMethodConfiguration()) {
            System.out.println("TEST SETUP FAILED");
        }
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
        if (itr.getMethod().isBeforeMethodConfiguration()) {
            System.out.println("TEST SETUP SKIPPED");
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("TEST FLOW BEGIN - " + iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("TEST SUCCEEDED: " + iTestResult.getName());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("TEST FAILED: " + iTestResult.getName());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("TEST SKIPPED: " + iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("TEST UNSTABLE: " + iTestResult.getName());
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        ITestNGMethod[] tests = iTestContext.getAllTestMethods();
        System.out.println("TESTS TO RUN:");
        for (ITestNGMethod method : tests) {
            System.out.println(method.getMethodName());
        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        List<String> passedTests = iTestContext.getPassedTests().getAllResults().stream()
                .map(ITestResult::getName).collect(Collectors.toList());
        List<String> failedTests = iTestContext.getFailedTests().getAllResults().stream()
                .map(ITestResult::getName).collect(Collectors.toList());
        List<String> skippedTests = iTestContext.getSkippedTests().getAllResults().stream()
                .map(ITestResult::getName).collect(Collectors.toList());

        System.out.println(iTestContext.getName());

        System.out.println("SUCCESSFUL TESTS:");
        passedTests.forEach(System.out::println);

        System.out.println("FAILED TESTS:");
        failedTests.forEach(System.out::println);

        System.out.println("SKIPPED TESTS:");
        skippedTests.forEach(System.out::println);
    }
}

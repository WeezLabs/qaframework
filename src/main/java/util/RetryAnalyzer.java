package util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry test if it has timed out.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private Integer retryCount = 0;
    private Integer retryMaxCount = 3;

    @Override
    public boolean retry(ITestResult testResult) {
        boolean result = false;
        String stackTrace=testResult.getThrowable().fillInStackTrace().toString();
        if (!testResult.isSuccess() && !stackTrace.contains("java.lang")) {
            System.out.println("retry count = " + retryCount + "\n max retry count = " + retryMaxCount);

            if (retryCount < retryMaxCount) {
                System.out.println("Retrying" + testResult.getName() +
                                   " with status " + testResult.getStatus() +
                                   " for the try " + (retryCount + 1) + " of " + retryMaxCount +
                                   " max times, with stacktrace "+stackTrace);
                retryCount++;
                result = true;
            }
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}


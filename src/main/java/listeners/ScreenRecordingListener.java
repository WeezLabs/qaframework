package listeners;

import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener2;
import util.PropertyUtils;
import util.ScreenRecorder;
import util.TestFlowStepHandler;
import util.TestProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class ScreenRecordingListener implements IResultListener2 {

    @Nullable
    private ScreenRecorder screenRecorder;

    @Override
    public void onFinish(ITestContext context) {
        screenRecorder = null;
    }

    @Override
    public void beforeConfiguration(ITestResult tr) {
        if (tr.getMethod().isBeforeMethodConfiguration()) {
            startRecorder();
        }
    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
        if (itr.getMethod().isBeforeMethodConfiguration()) {
            stopRecorder();
        }
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
        if (itr.getMethod().isBeforeMethodConfiguration()) {
            stopRecorder();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        startRecorder();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        stopRecorder();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        stopRecorder();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        stopRecorder();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        stopRecorder();
    }

    //region Unused callbacks

    @Override
    public void onConfigurationSuccess(ITestResult itr) {}

    @Override
    public void onStart(ITestContext context) {}

    //endregion

    private void startRecorder() {
        TestProperties properties = PropertyUtils.getTestProperties();

        if (!properties.isScreenRecorderEnabled) {
            return;
        }

        if (screenRecorder == null) {
            screenRecorder = new ScreenRecorder(properties.deviceUdid, properties.buildPath);
        }

        if (!screenRecorder.isRecording()) {
            screenRecorder.start(properties);
        }
    }

    private void stopRecorder() {
        TestProperties properties = PropertyUtils.getTestProperties();
        if (screenRecorder != null && screenRecorder.isRecording()) {
            saveVideoAttachment(screenRecorder.stop(properties));
        }
    }

    /**
     * Adds provided video file to Allure report attachments.
     * @return bytes of the passed file, will be handled by Allure automatically
     */
    @Attachment(value = "testVideo", type = "video/mp4")
    @SuppressWarnings("UnusedReturnValue") // Allure collects return value automatically
    private byte[] saveVideoAttachment(@Nonnull File video) {
        try {
            System.out.println("Saving test video attachment");
            return Files.toByteArray(video);
        } catch (IOException e) {
            TestFlowStepHandler.logError(e);
            return null;
        }
    }
}

package util;

import javax.annotation.Nonnull;
import java.io.File;

public final class ScreenRecorder {

    private static final String FILE_NAME = "testVideo";

    private static final String COMMAND_START = "flick video -a start -p %1$s -u %2$s -c 5000";
    private static final String COMMAND_STOP = "flick video -a stop -p %1$s -u %2$s -o %3$s -n %4$s -f mp4";
    private static final String COMMAND_START_RECORD_SIMULATOR = "xcrun simctl io booted recordVideo %1$s";
    private static final String COMMAND_STOP_RECORD_SIMULATOR = "kill -s INT %1$s";

    @Nonnull
    private final String device;
    @Nonnull
    private final File path;

    private boolean isRecording = false;
    private String pid;

    public ScreenRecorder(@Nonnull String device, @Nonnull File path) {
        this.device = device;
        this.path = path;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void start(TestProperties properties) {
        if (isRecording) {
            throw new IllegalStateException("Recording is already started");
        }
        if (!properties.simulator) {
            ShellUtils.runCommand(COMMAND_START, properties.platformName.toLowerCase(), device);
        } else {
            String filePath = path.getAbsolutePath() + "/" + FILE_NAME + ".mp4";
            pid = ShellUtils.runProcess(COMMAND_START_RECORD_SIMULATOR, filePath);
        }
        isRecording = true;
    }

    @Nonnull
    public File stop(TestProperties properties) {
        if (!isRecording) {
            throw new IllegalStateException("Recording is not started");
        }

        if (!properties.simulator) {
            ShellUtils.runCommand(
                    COMMAND_STOP,
                    properties.platformName.toLowerCase(),
                    device,
                    path.getAbsolutePath(),
                    FILE_NAME);
        } else {
            ShellUtils.runCommand(COMMAND_STOP_RECORD_SIMULATOR, pid);
            Utilities.waitUntil(1000);
        }
        isRecording = false;

        File video = new File(path, FILE_NAME + ".mp4");
        if (video.exists()) {
            return video;
        } else {
            throw new IllegalStateException("Failed to find the recorded video");
        }
    }
}

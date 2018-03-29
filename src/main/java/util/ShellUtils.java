package util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

public class ShellUtils {

    private static final String COMMAND_GET_APP_NAME = "aapt dump badging %1$s" +
            " | grep 'application: label=' | awk -F\\' '{print $2}'";

    static String getAppName(@Nonnull TestProperties properties) {
        return runCommand(COMMAND_GET_APP_NAME, properties.applicationFilePath.getAbsolutePath());
    }

    @Nonnull
    static String runCommand(@Nonnull String command, String... parameters) {
        command = String.format(command, (Object[]) parameters);
        System.out.println(command);

        Process process;
        try {
            process = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", command});
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Failed to run command: " + command, e);
        }

        if (process.exitValue() == 0) {
            String result = parseInputStream(process.getInputStream());
            return result == null ? "" : result;
        } else {
            String errors = parseInputStream(process.getErrorStream());
            throw new IllegalStateException("Command execution failed: " + command + "\n" + errors);
        }
    }

    @Nonnull
    static String runProcess(@Nonnull String command, String... parameters) {
        command = String.format(command, (Object[]) parameters);
        System.out.println(command);
        try {
            Process process = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", command});
            return String.valueOf(getPID(process));
        } catch (IOException e) {
            throw new IllegalStateException("Failed to run command: " + command, e);
        }
    }

    @Nullable
    private static int getPID(Process process) {
        if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
            /* get the PID on unix/linux systems */
            try {
                Field f = process.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                return f.getInt(process);
            } catch (Throwable e) {
                throw new IllegalStateException("Failed to get pid of process: ", e);
            }
        } else {
            return 0;
        }
    }

    @Nullable
    private static String parseInputStream(InputStream input) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            StringBuilder resultBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line);
            }

            return resultBuilder.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

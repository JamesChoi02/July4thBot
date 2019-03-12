package frc.robot.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Logger {
    private static boolean PRINT_TIMESTAMP = true;
    private static boolean PRINT_CALLER = true;

    public static void log(String msg) {
        List<String> prefixes = new LinkedList<>();

        if (PRINT_TIMESTAMP) {
            String timestamp = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss").format(new Date());
            prefixes.add(timestamp);
        }

        if (PRINT_CALLER) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            StackTraceElement caller = stackTraceElements[stackTraceElements.length - 1];
            prefixes.add(caller.getClassName() + ":" + caller.getLineNumber());
        }

        if (!prefixes.isEmpty()) {
            prefixes.add("-");
        }

        for (String prefix : prefixes) {
            System.out.print(prefix + " ");
        }

        System.out.println(msg);
    }
}
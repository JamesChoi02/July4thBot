package frc.robot.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;

public class Logger extends Thread {
    private static final boolean PRINT_TIMESTAMP = true;
    private static final boolean PRINT_CALLER = true;
    private static List<Loggable> loggables = new LinkedList<>();
    private final BadLog logger;
    private final long delta;

    public Logger(long delta) {
        this.delta = delta;

        String timestamp = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH'-'mm").format(new Date());
        logger = BadLog.init("/home/lvuser/" + timestamp + ".badbag");

        BadLog.createValue("OS Version", System.getProperty("os.version"));
        BadLog.createValue("Match Type", DriverStation.getInstance().getMatchType().toString());
        BadLog.createValue("Match Number", "" + DriverStation.getInstance().getMatchNumber());
        BadLog.createTopic("Match Time", "s", DriverStation.getInstance()::getMatchTime);
        BadLog.createTopic("Battery Voltage", "V", RobotController::getBatteryVoltage);
        BadLog.createValue("Alliance", DriverStation.getInstance().getAlliance().name());
        BadLog.createValue("Event Name", DriverStation.getInstance().getEventName());

        for (Loggable loggable : loggables)
            loggable.initLogging();

        logger.finishInitialization();
    }

    public void run() {
        logger.updateTopics();
        logger.log();

        try {
            Thread.sleep(delta);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
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

    public static void addLoggable(Loggable loggable) {
        loggables.add(loggable);
    }
}
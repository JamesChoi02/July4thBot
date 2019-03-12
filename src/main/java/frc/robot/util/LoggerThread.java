package frc.robot.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DriverStation;

public class LoggerThread extends Thread {
    private static List<Loggable> loggables;
    private static BadLog logger;
    private long delta;

    public LoggerThread(long delta) {
        this.delta = delta;

        String timestamp = new SimpleDateFormat("yyyy'-'MM'-'dd'T'HH'-'mm").format(new Date());
        logger = BadLog.init("/home/lvuser/" + timestamp + ".badbag");

        BadLog.createValue("OS Version", System.getProperty("os.version"));

        BadLog.createValue("Match Type", DriverStation.getInstance().getMatchType().toString());
        BadLog.createValue("Match Number", "" + DriverStation.getInstance().getMatchNumber());
        BadLog.createTopic("Match Time", "s", DriverStation.getInstance()::getMatchTime);

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

    public static BadLog getLogger() {
        return logger;
    }

    public static void addLoggable(Loggable loggable) {
        loggables.add(loggable);
    }
}
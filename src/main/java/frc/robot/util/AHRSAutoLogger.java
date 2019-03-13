package frc.robot.util;

import com.kauailabs.navx.frc.AHRS;

import badlog.lib.BadLog;

public class AHRSAutoLogger extends AutoLogger<AHRS> {
    public static void register() {
        try {
            AutoLoggerFactory.registerAutoLogger(AHRS.class, AHRSAutoLogger.class.getConstructors()[0]);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public AHRSAutoLogger(String subsystemName, String deviceName, AHRS ahrs) {
        super(subsystemName, deviceName, ahrs);
    }

    public void initLogging() {
        BadLog.createValue(combo + "Firmware", device.getFirmwareVersion());

        BadLog.createTopic(combo + "Temperature", "C", () -> (double) device.getTempC(),
                "join:" + subsystemName + "/Temperatures");

        BadLog.createTopic(combo + "Pitch", "deg", () -> (double) device.getPitch());
        BadLog.createTopic(combo + "Yaw", "deg", () -> (double) device.getYaw());
        BadLog.createTopic(combo + "Roll", "deg", () -> (double) device.getRoll());

        BadLog.createTopic(combo + "Velocity X", "M", () -> (double) device.getVelocityX());
        BadLog.createTopic(combo + "Velocity Y", "M", () -> (double) device.getVelocityY());
    }
}
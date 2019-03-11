package frc.robot.util;

import badlog.lib.BadLog;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import frc.robot.drivers.CANTalonSRX;

public class AutoLogger {
    public static void log(String subsystemName, String deviceName, Object device) {
        try {
            AutoLogger.class.getDeclaredMethod("log", String.class, String.class, device.getClass()).invoke(null,
                    subsystemName, deviceName, device);
        } catch (NoSuchMethodException e) {
            System.err.println("Device of type " + device.getClass().getName() + " has no auto log implementation");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void log(String subsystemName, String deviceName, CANSparkMax spark) {
        String combo = subsystemName + "/" + deviceName;

        BadLog.createValue(combo + " Firmware", spark.getFirmwareString());

        BadLog.createTopic(combo + " Output Percent", BadLog.UNITLESS, () -> spark.get(), "hide",
                "join:" + subsystemName + "Output Percents");

        BadLog.createTopic(combo + " Current", "A", () -> spark.getOutputCurrent(), "hide",
                "join:" + subsystemName + "Output Currents");
        BadLog.createTopic(combo + " Temperature", "C", () -> spark.getMotorTemperature(), "hide",
                "join:" + subsystemName + "Output Temperatures");
    }

    private static void log(String subsystemName, String deviceName, CANTalonSRX talon) {
        String combo = subsystemName + "/" + deviceName;

        BadLog.createValue(combo + " Firmware", "" + talon.getFirmwareVersion());

        BadLog.createTopic(combo + " Output Percent", BadLog.UNITLESS, () -> talon.get(),
                "join:" + subsystemName + "Output Percents");

        BadLog.createTopic(combo + " Current", "A", () -> talon.getOutputCurrent(),
                "join:" + subsystemName + "Output Currents");
        BadLog.createTopic(combo + " Temperature", "C", () -> talon.getTemperature(),
                "join:" + subsystemName + "Output Temperatures");
    }

    private static void log(String subsystemName, String deviceName, AHRS ahrs) {
        String combo = subsystemName + "/" + deviceName;

        BadLog.createValue(combo + " Firmware", "" + ahrs.getFirmwareVersion());
        BadLog.createTopic(combo + " Pitch", "deg", () -> (double) ahrs.getPitch());
        BadLog.createTopic(combo + " Yaw", "deg", () -> (double) ahrs.getYaw());
        BadLog.createTopic(combo + " Roll", "deg", () -> (double) ahrs.getRoll());
        BadLog.createTopic(combo + " Temperature", "C", () -> (double) ahrs.getTempC(),
                "join:" + subsystemName + "Output Temperatures");
    }
}
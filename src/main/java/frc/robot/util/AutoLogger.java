package frc.robot.util;

import badlog.lib.BadLog;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import frc.robot.drivers.CANTalonSRX;

public class AutoLogger {
    public static void log(String subsystemName, String deviceName, Object device) {
        try {
            AutoLogger.class.getDeclaredMethod("log", String.class, String.class, device.getClass()).invoke(null,
                    subsystemName, subsystemName + "/" + deviceName + " ", device);
        } catch (NoSuchMethodException e) {
            System.err.println("Device of type " + device.getClass().getName() + " has no auto log implementation");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void log(String subsystemName, String combo, CANSparkMax spark) {
        BadLog.createValue(combo + "Firmware", spark.getFirmwareString());

        BadLog.createTopic(combo + "Output Percent", BadLog.UNITLESS, () -> spark.get(),
                "join:" + subsystemName + "/Output Percents");

        BadLog.createTopic(combo + "Current", "A", () -> spark.getOutputCurrent(),
                "join:" + subsystemName + "/Output Currents");
        BadLog.createTopic(combo + "Temperature", "C", () -> spark.getMotorTemperature(),
                "join:" + subsystemName + "/Temperatures");
    }

    private static void log(String subsystemName, String combo, CANTalonSRX talon) {
        BadLog.createValue(combo + "Firmware", "" + talon.getFirmwareVersion());

        BadLog.createTopic(combo + "Output Percent", BadLog.UNITLESS, () -> talon.get(),
                "join:" + subsystemName + "/Output Percents");

        BadLog.createTopic(combo + "Current", "A", () -> talon.getOutputCurrent(),
                "join:" + subsystemName + "/Output Currents");
        BadLog.createTopic(combo + "Temperature", "C", () -> talon.getTemperature(),
                "join:" + subsystemName + "/Temperatures");
    }

    private static void log(String subsystemName, String combo, AHRS ahrs) {
        BadLog.createValue(combo + "Firmware", ahrs.getFirmwareVersion());

        BadLog.createTopic(combo + "Temperature", "C", () -> (double) ahrs.getTempC(),
                "join:" + subsystemName + "/Temperatures");

        BadLog.createTopic(combo + "Pitch", "deg", () -> (double) ahrs.getPitch());
        BadLog.createTopic(combo + "Yaw", "deg", () -> (double) ahrs.getYaw());
        BadLog.createTopic(combo + "Roll", "deg", () -> (double) ahrs.getRoll());

        BadLog.createTopic(combo + "Velocity X", "M", () -> (double) ahrs.getVelocityX());
        BadLog.createTopic(combo + "Velocity Y", "M", () -> (double) ahrs.getVelocityY());
    }
}
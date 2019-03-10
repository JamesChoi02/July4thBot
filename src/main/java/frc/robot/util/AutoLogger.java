package frc.robot.util;

import badlog.lib.BadLog;
import com.revrobotics.CANSparkMax;
import frc.robot.drivers.CANTalonSRX;

public class AutoLogger {
    public static void log(String subsystemName, String deviceName, Object device) {
        if (device instanceof CANSparkMax)
            logSpark(subsystemName, deviceName, (CANSparkMax) device);
        else if (device instanceof CANTalonSRX)
            logTalon(subsystemName, deviceName, (CANTalonSRX) device);
        else
            throw new UnsupportedOperationException(
                    "Device of type " + device.getClass().getName() + " has no auto log implementation");
    }

    private static void logSpark(String subsystemName, String deviceName, CANSparkMax spark) {
        String combo = subsystemName + "/" + deviceName;

        BadLog.createValue(combo + " Firmware", spark.getFirmwareString());

        BadLog.createTopic(combo + " Output Percent", BadLog.UNITLESS, () -> spark.get(), "hide",
                "join:" + subsystemName + "Output Percents");

        BadLog.createTopic(combo + " Current", "A", () -> spark.getOutputCurrent(), "hide",
                "join:" + subsystemName + "Output Currents");
        BadLog.createTopic(combo + " Temperature", "C", () -> spark.getMotorTemperature(), "hide",
                "join:" + subsystemName + "Output Temperatures");
    }

    private static void logTalon(String subsystemName, String deviceName, CANTalonSRX talon) {
        String combo = subsystemName + "/" + deviceName;

        BadLog.createValue(combo + " Firmware", "" + talon.getFirmwareVersion());

        BadLog.createTopic(combo + " Output Percent", BadLog.UNITLESS, () -> talon.get(), "hide",
                "join:" + subsystemName + "Output Percents");

        BadLog.createTopic(combo + " Current", "A", () -> talon.getOutputCurrent(), "hide",
                "join:" + subsystemName + "Output Currents");
        BadLog.createTopic(combo + " Temperature", "C", () -> talon.getTemperature(), "hide",
                "join:" + subsystemName + "Output Temperatures");
    }
}
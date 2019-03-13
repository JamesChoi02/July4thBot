package frc.robot.util;

import com.revrobotics.CANSparkMax;

import badlog.lib.BadLog;

public class CANSparkMaxAutoLogger extends AutoLogger<CANSparkMax> {
    public static void register() {
        try {
            AutoLoggerFactory.registerAutoLogger(CANSparkMax.class, CANSparkMaxAutoLogger.class.getConstructors()[0]);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public CANSparkMaxAutoLogger(String subsystemName, String deviceName, CANSparkMax spark) {
        super(subsystemName, deviceName, spark);
    }

    public void initLogging() {
        BadLog.createValue(combo + "Firmware", device.getFirmwareString());

        BadLog.createTopic(combo + "Output Percent", BadLog.UNITLESS, device::get,
                "join:" + subsystemName + "/Output Percents");

        BadLog.createTopic(combo + "Current", "A", device::getOutputCurrent,
                "join:" + subsystemName + "/Output Currents");
        BadLog.createTopic(combo + "Temperature", "C", device::getMotorTemperature,
                "join:" + subsystemName + "/Temperatures");
    }
}
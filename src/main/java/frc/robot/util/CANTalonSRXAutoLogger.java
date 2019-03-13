package frc.robot.util;

import badlog.lib.BadLog;
import frc.robot.driver.CANTalonSRX;

public class CANTalonSRXAutoLogger extends AutoLogger<CANTalonSRX> {
    public static void register() {
        try {
            AutoLoggerFactory.registerAutoLogger(CANTalonSRX.class, CANTalonSRXAutoLogger.class.getConstructors()[0]);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public CANTalonSRXAutoLogger(String subsystemName, String deviceName, CANTalonSRX talon) {
        super(subsystemName, deviceName, talon);
    }

    public void initLogging() {
        BadLog.createValue(combo + "Firmware", "" + device.getFirmwareVersion());

        BadLog.createTopic(combo + "Output Percent", BadLog.UNITLESS, device::get,
                "join:" + subsystemName + "/Output Percents");

        BadLog.createTopic(combo + "Current", "A", device::getOutputCurrent,
                "join:" + subsystemName + "/Output Currents");
        BadLog.createTopic(combo + "Temperature", "C", device::getTemperature,
                "join:" + subsystemName + "/Temperatures");
    }
}
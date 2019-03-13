package frc.robot.util;

import badlog.lib.BadLog;
import frc.robot.driver.CANTalonSRX;

public class CANTalonSRXAutoLogger implements AutoLogger<CANTalonSRX> {
    @Override
    public void log(String subsystemName, String combo, CANTalonSRX talon) {
        BadLog.createValue(combo + "Firmware", "" + talon.getFirmwareVersion());

        BadLog.createTopic(combo + "Output Percent", BadLog.UNITLESS, talon::get,
                "join:" + subsystemName + "/Output Percents");

        BadLog.createTopic(combo + "Current", "A", talon::getOutputCurrent,
                "join:" + subsystemName + "/Output Currents");
        BadLog.createTopic(combo + "Temperature", "C", talon::getTemperature,
                "join:" + subsystemName + "/Temperatures");
    }
}
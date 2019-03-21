package frc.robot.log;

import com.revrobotics.CANSparkMax;

import badlog.lib.BadLog;

public class CANSparkMaxAutoLogger implements AutoLogger<CANSparkMax> {
    @Override
    public void log(String subsystemName, String combo, CANSparkMax spark) {
        BadLog.createValue(combo + "Firmware", spark.getFirmwareString());

        BadLog.createTopic(combo + "Output Percent", BadLog.UNITLESS, spark::get,
                "join:" + subsystemName + "/Output Percents");

        BadLog.createTopic(combo + "Current", "A", spark::getOutputCurrent,
                "join:" + subsystemName + "/Output Currents");
        BadLog.createTopic(combo + "Temperature", "C", spark::getMotorTemperature,
                "join:" + subsystemName + "/Temperatures");
    }
}
package frc.robot.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;
import frc.robot.driver.CANTalonSRX;

/**
 * Represents the cam and velcro system for low hatches on the back of the robot
 */
public class BackCams extends BadSubsystem {
    private CANTalonSRX motor;

    @Override
    public void initComponents() {
        motor = new CANTalonSRX(RobotMap.CAM_MOTOR);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Output", () -> motor.get(), null);
        builder.addDoubleProperty("Current", () -> motor.getOutputCurrent(), null);
    }

    /**
     * Spins the cams at a given speed
     * 
     * @param speed between -1 and 1
     */
    public void spin(double speed) {
        motor.set(speed);
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void close() {
        motor.DestroyObject();
    }

    @Override
    public void test() {
        spin(0.1);
        sleep(2);
        spin(-0.1);
        sleep(2);
        stop();
    }
}
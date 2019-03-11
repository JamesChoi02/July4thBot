package frc.robot.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;
import frc.robot.driver.CANTalonSRX;

public class Articulator extends BadSubsystem {
    private CANTalonSRX motor;

    @Override
    public void initComponents() {
        motor = new CANTalonSRX(RobotMap.ARTICULATOR_MOTOR);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Output", () -> motor.get(), null);
        builder.addDoubleProperty("Current", () -> motor.getOutputCurrent(), null);
    }

    public void rotate(double speed) {
        motor.set(speed);
    }

    public void rotateTo(double target) {

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
        rotate(0.1);
        sleep(2);
        rotate(-0.1);
        sleep(2);
        stop();
    }
}
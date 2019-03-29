package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;
import frc.robot.driver.CANTalonSRX;

/**
 * Represents the articulation system for the claw that makes it tilt forward and back
 */
public class Articulator extends BadSubsystem {
    protected CANTalonSRX motor;

    @Override
    public void initComponents() {
        motor = new CANTalonSRX(RobotMap.ARTICULATOR_MOTOR);
        motor.overrideSoftLimitsEnable(false);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Output", motor::get, null);
        builder.addDoubleProperty("Current", motor::getOutputCurrent, null);
        // addChild(motor);
    }

    /**
     * Rotate the motor at a given speed in order to cause articulation of the claw
     *
     * @param speed between -1 and 1
     */
    public void rotate(double speed) {
        motor.set(speed);
    }

    /**
     * Articulate the claw to a target setpoint
     *
     * @param target
     */
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

    public static boolean isEnabled() {
        return true;
    }
}

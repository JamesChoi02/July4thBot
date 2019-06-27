package frc.robot.subsystem;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;

/**
 * Represents the wheels at the front of the claw that pull in and shoot out the balls
 */
public class Wheels extends BadSubsystem {
    protected PWMTalonSRX wheel1;
    protected PWMTalonSRX wheel2;

    @Override
    public void initComponents() {
        wheel1 = new PWMTalonSRX(RobotMap.WHEEL_1);
        wheel2 = new PWMTalonSRX(RobotMap.WHEEL_2);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
    }

    /**
     * Spin the grabber wheels at a given speed
     *
     * @param speed between -1 and 1
     */
    public void spin(double speed) {
        wheel1.set(speed);
        wheel2.set(speed);
    }

    @Override
    public void stop() {
        wheel1.stopMotor();
        wheel2.stopMotor();
    }

    @Override
    public void close() {
        wheel1.close();
    }

    @Override
    public void test() {
    }

    public static boolean isEnabled() {
        return true;
    }
}

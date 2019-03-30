package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;
import frc.robot.command.SpinBackCams;
import frc.robot.driver.CANTalonSRX;

/**
 * Represents the cam and velcro system for low hatches on the back of the robot
 */
public class BackCams extends BadSubsystem {
    protected CANTalonSRX motor;

    @Override
    public void initComponents() {
        motor = new CANTalonSRX(RobotMap.CAM_MOTOR);
        motor.overrideSoftLimitsEnable(true);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Output", motor::get, null);
        builder.addDoubleProperty("Current", motor::getOutputCurrent, null);
        // addChild(motor);
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

    public static boolean isEnabled() {
        return true;
    }
}

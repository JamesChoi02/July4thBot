package frc.robot.subsystem;

import static org.junit.Assert.assertTrue;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;

public class DriveTrain extends BadSubsystem {
    private CANSparkMax leftLeaderMotor, leftFollowerMotor, rightLeaderMotor, rightFollowerMotor;
    private DifferentialDrive differentialDrive;
    private AHRS navx;
    private boolean inverted;

    @Override
    public void initComponents() {
        leftLeaderMotor = new CANSparkMax(RobotMap.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        leftFollowerMotor = new CANSparkMax(RobotMap.BACK_LEFT_MOTOR, MotorType.kBrushless);
        rightLeaderMotor = new CANSparkMax(RobotMap.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rightFollowerMotor = new CANSparkMax(RobotMap.BACK_RIGHT_MOTOR, MotorType.kBrushless);

        leftFollowerMotor.follow(leftLeaderMotor);
        rightFollowerMotor.follow(rightLeaderMotor);

        differentialDrive = new DifferentialDrive(leftLeaderMotor, rightLeaderMotor);

        navx = new AHRS(Port.kMXP);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Left Output", () -> leftLeaderMotor.get(), null);
        builder.addDoubleProperty("Right Output", () -> rightLeaderMotor.get(), null);
        builder.addDoubleProperty("Velocity", () -> navx.getVelocityX(), null);
        builder.addDoubleProperty("Yaw", () -> navx.getYaw(), null);
    }

    public void tankDrive(double left, double right) {
        if (inverted)
            differentialDrive.tankDrive(-right, -left);
        else
            differentialDrive.tankDrive(left, right);
    }

    public void driveStraight(double speed) {
        if (inverted) {
            leftLeaderMotor.getPIDController().setReference(-speed, ControlType.kDutyCycle);
            rightLeaderMotor.getPIDController().setReference(-speed, ControlType.kDutyCycle);
        } else {
            leftLeaderMotor.getPIDController().setReference(speed, ControlType.kDutyCycle);
            rightLeaderMotor.getPIDController().setReference(speed, ControlType.kDutyCycle);
        }
    }

    public double getAngle() {
        return navx.getAngle();
    }

    public void invert() {
        inverted = !inverted;
    }

    @Override
    public void stop() {
        differentialDrive.stopMotor();
    }

    @Override
    public void close() {
        differentialDrive.close();
        leftFollowerMotor.close();
        rightFollowerMotor.close();
        navx.close();
    }

    @Override
    public void test() {
        tankDrive(0.1, 0);
        sleep(1);
        assertTrue("Left DriveTrain Motors Move", leftLeaderMotor.getEncoder().getVelocity() > 0);
        sleep(1);

        tankDrive(0, 0.1);
        sleep(1);
        assertTrue("Right DriveTrain Motors Move", rightLeaderMotor.getEncoder().getVelocity() > 0);
        sleep(1);
        
        stop();
    }
}
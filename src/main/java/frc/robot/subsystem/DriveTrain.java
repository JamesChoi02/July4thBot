package frc.robot.subsystem;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;

public class DriveTrain extends BadSubsystem {
    private CANSparkMax leftLeaderMotor, leftFollowerMotor, rightLeaderMotor, rightFollowerMotor;
    private DifferentialDrive differentialDrive;
    private AHRS navx;

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
        differentialDrive.tankDrive(left, right);
    }

    public double getAngle() {
        return navx.getAngle();
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
    }

    @Override
    public void test() {
        tankDrive(0.1, 0);
        sleep(2);
        tankDrive(0, 0.1);
        sleep(2);
        stop();
    }
}
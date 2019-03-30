package frc.robot.subsystem;

import static org.junit.Assert.assertTrue;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;

/**
 * Represents the chasis of the robot including its drive wheels and sensors
 */
public class DriveTrain extends BadSubsystem {
    protected CANSparkMax leftLeaderMotor, leftFollowerMotor, rightLeaderMotor, rightFollowerMotor;
    protected DifferentialDrive differentialDrive;
    protected AHRS navx;
    protected boolean inverted;

    @Override
    public void initComponents() {
        leftLeaderMotor = new CANSparkMax(RobotMap.FRONT_LEFT_MOTOR, MotorType.kBrushless);
        leftFollowerMotor = new CANSparkMax(RobotMap.BACK_LEFT_MOTOR, MotorType.kBrushless);
        rightLeaderMotor = new CANSparkMax(RobotMap.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
        rightFollowerMotor = new CANSparkMax(RobotMap.BACK_RIGHT_MOTOR, MotorType.kBrushless);

        leftFollowerMotor.follow(leftLeaderMotor);
        rightFollowerMotor.follow(rightLeaderMotor);

        differentialDrive = new DifferentialDrive(leftLeaderMotor, rightLeaderMotor);
        differentialDrive.setSafetyEnabled(true);

        navx = new AHRS(Port.kMXP);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Left Output", leftLeaderMotor::get, null);
        builder.addDoubleProperty("Right Output", rightLeaderMotor::get, null);
        builder.addDoubleProperty("Velocity", navx::getVelocityX, null);
        builder.addDoubleProperty("Yaw", navx::getYaw, null);
        builder.addBooleanProperty("Inverted", this::isInverted, null);
        // addChild(leftLeaderMotor);
        // addChild(leftFollowerMotor);
        // addChild(rightLeaderMotor);
        // addChild(rightFollowerMotor);
        // addChild(navx);
    }

    /**
     * Makes the wheels spin
     *
     * @param left    left speed
     * @param right   right speed
     * @param squared whether the inputs should be squared
     */
    public void tankDrive(double left, double right, boolean squared) {
        left *= -1;
        right *= -1;

        if (inverted)
            differentialDrive.tankDrive(-right, -left, squared);
        else
            differentialDrive.tankDrive(left, right, squared);
    }

    /**
     * Get the current reading from the NavX
     *
     * @return yaw of the robot in degrees
     */
    public double getAngle() {
        return navx.getAngle();
    }

    /**
     * Flip which direction is the front of the robot, such that the robot acts as if it were
     * rotated 180 degrees
     */
    public void invert() {
        inverted = !inverted;
    }

    /**
     * @return {@link #inverted} the current inversion state of the drivetrain
     */
    public boolean isInverted() {
        return inverted;
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
        tankDrive(0.1, 0, false);
        sleep(1);
        assertTrue("Left DriveTrain Motors Move", leftLeaderMotor.getEncoder().getVelocity() > 0);
        sleep(1);

        tankDrive(0, 0.1, false);
        sleep(1);
        assertTrue("Right DriveTrain Motors Move", rightLeaderMotor.getEncoder().getVelocity() > 0);
        sleep(1);

        stop();
    }

    public static boolean isEnabled() {
        return true;
    }
}

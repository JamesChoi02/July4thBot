package frc.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;

/**
 * Represents the lifter that elevates the claw to various heights
 */
public class Lifter extends BadSubsystem {
    /**
     * Defines setpoints that the user may wish to elevate the claw to
     */
    public enum Position {
        HATCH_HIGH(38.25), HATCH_MIDDLE(22.1), HATCH_LOW(2.6), BALL_HIGH(45.4), BALL_MIDDLE(
                28), BALL_LOW(10.4), GROUND(0);

        private final double revolutions;

        /**
         * Creates a setpoint for the lifter
         * 
         * @param revolutions the number of revolutions of the {@link #motor} that are required to
         *                    get to the desired height as measured by the Neo's encoder
         */
        private Position(double revolutions) {
            this.revolutions = revolutions;
        }

        public double getRevolutions() {
            return revolutions;
        }
    }

    private CANSparkMax motor;

    @Override
    public void initComponents() {
        motor = new CANSparkMax(RobotMap.LIFTER_MOTOR, MotorType.kBrushless);
        motor.getPIDController().setP(0.1);
        motor.getPIDController().setI(0.15);
        motor.getPIDController().setD(0.15);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Output", () -> motor.get(), null);
        builder.addDoubleProperty("Position", () -> motor.getEncoder().getPosition(), null);
        builder.addDoubleProperty("Velocity", () -> motor.getEncoder().getVelocity(), null);
        builder.addDoubleProperty("Current", () -> motor.getOutputCurrent(), null);
        builder.addDoubleProperty("P", () -> motor.getPIDController().getP(),
                (p) -> motor.getPIDController().setP(p));
        builder.addDoubleProperty("I", () -> motor.getPIDController().getI(),
                (i) -> motor.getPIDController().setI(i));
        builder.addDoubleProperty("D", () -> motor.getPIDController().getD(),
                (d) -> motor.getPIDController().setD(d));
    }

    /**
     * Move the lifter up or down at a given speed where +1 speed is full speed upwards and -1 is
     * full speed downwards
     * 
     * @param speed between -1 and 1
     */
    public void move(double speed) {
        motor.set(speed);
    }

    /**
     * Move to an arbitrary height as measured in revolutions of the {@link #motor}. This method
     * uses the SparkMax's integrated PID controller.
     * 
     * @param target the Neo encoder value that should be reached to attain the desired height
     */
    public void moveTo(double target) {
        motor.getPIDController().setReference(target, ControlType.kPosition);
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void close() {
        motor.close();
    }

    @Override
    public void test() {
        move(0.1);
        sleep(2);
        stop();
    }
}

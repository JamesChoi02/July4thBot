package frc.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;
import frc.robot.Trigger;

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

    protected CANSparkMax motor;
    protected Trigger bottomLimitSwitch;

    @Override
    public void initComponents() {
        motor = new CANSparkMax(RobotMap.LIFTER_MOTOR, MotorType.kBrushless);
        motor.setInverted(true);
        motor.getPIDController().setOutputRange(-0.35, 0.85);
        motor.getPIDController().setP(0.1);
        motor.getPIDController().setI(0);
        motor.getPIDController().setD(0);

        DigitalInput bottomLimitSwitchDI = new DigitalInput(10);

        bottomLimitSwitch = new Trigger(bottomLimitSwitchDI::get) {
            @Override
            public void close() {
                super.close();
                bottomLimitSwitchDI.close();
            }
        };
        // bottomLimitSwitch.whenActive(new ZeroEncoder());
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Output", motor::get, null);
        builder.addDoubleProperty("Position", motor.getEncoder()::getPosition, null);
        builder.addDoubleProperty("Velocity", motor.getEncoder()::getVelocity, null);
        builder.addDoubleProperty("Current", motor::getOutputCurrent, null);
        builder.addDoubleProperty("P", motor.getPIDController()::getP,
                motor.getPIDController()::setP);
        builder.addDoubleProperty("I", motor.getPIDController()::getI,
                motor.getPIDController()::setI);
        builder.addDoubleProperty("D", motor.getPIDController()::getD,
                motor.getPIDController()::setD);
        // addChild(motor);
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
        System.err.println("Set Point: " + target);
        motor.getPIDController().setReference(target, ControlType.kPosition);
    }

    /**
     * Zero the lifter motor's encoder value
     */
    public void zeroEncoder() {
        motor.getEncoder().setPosition(0);
    }

    public double getEncoderValue() {
        return motor.getEncoder().getPosition();
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void close() {
        motor.close();
        bottomLimitSwitch.close();
    }

    @Override
    public void test() {
        move(0.1);
        sleep(2);
        stop();
    }

    public static boolean isEnabled() {
        return true;
    }
}

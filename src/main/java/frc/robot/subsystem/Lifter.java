package frc.robot.subsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.RobotMap;

public class Lifter extends BadSubsystem {
    public enum Position {
        HATCH_HIGH(38), HATCH_MIDDLE(24), HATCH_LOW(2), BALL_HIGH(46), BALL_MIDDLE(32), BALL_LOW(8);

        private final double revolutions;

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
        builder.addDoubleProperty("P", () -> motor.getPIDController().getP(), (p) -> motor.getPIDController().setP(p));
        builder.addDoubleProperty("I", () -> motor.getPIDController().getI(), (i) -> motor.getPIDController().setI(i));
        builder.addDoubleProperty("D", () -> motor.getPIDController().getD(), (d) -> motor.getPIDController().setD(d));
    }

    public void move(double speed) {
        motor.set(speed);
    }

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
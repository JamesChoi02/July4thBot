package frc.robot.command;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.log.Logger;

public class DriveStraight extends PIDCommand {
    private Supplier<Double> speedInput;

    public DriveStraight(Supplier<Double> speedInput) {
        super(0.05, 0.001, 0.025, Robot.driveTrain);
        this.speedInput = speedInput;
        setInputRange(0, 360);
    }
    
    @Override
    protected void initialize() {
        setSetpoint(Robot.driveTrain.getAngle());
        Logger.log("Starting Angle " + getSetpoint());
    }

    @Override
    protected double returnPIDInput() {
        return Robot.driveTrain.getAngle();
    }

    @Override
    protected void usePIDOutput(double pidOutput) {
        double speed = speedInput.get();
        Robot.driveTrain.tankDrive(speed - pidOutput, speed + pidOutput);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
        getPIDController().reset();
    }
}
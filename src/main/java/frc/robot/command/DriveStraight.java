package frc.robot.command;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveStraight extends Command {
    private Supplier<Double> speedInput;

    public DriveStraight(Supplier<Double> speedInput) {
        super(Robot.driveTrain);
        this.speedInput = speedInput;
    }

    @Override
    protected void execute() {
        Robot.driveTrain.driveStraight(speedInput.get());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }
}
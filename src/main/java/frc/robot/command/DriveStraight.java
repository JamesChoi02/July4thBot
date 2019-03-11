package frc.robot.command;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystem.DriveTrain;

public class DriveStraight extends Command {
    private DriveTrain driveTrain;
    private Supplier<Double> speedInput;

    public DriveStraight(DriveTrain driveTrain, Supplier<Double> speedInput) {
        this.driveTrain = driveTrain;
        this.speedInput = speedInput;
    }

    @Override
    protected void execute() {
        driveTrain.driveStraight(speedInput.get());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
package frc.robot.command;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystem.DriveTrain;

public class TankDrive extends Command {
    private DriveTrain driveTrain;
    private Supplier<Double> leftInput, rightInput;

    public TankDrive(DriveTrain driveTrain, Supplier<Double> leftInput, Supplier<Double> rightInput) {
        this.driveTrain = driveTrain;
        this.leftInput = leftInput;
        this.rightInput = rightInput;
    }

    @Override
    protected void execute() {
        driveTrain.tankDrive(leftInput.get(), rightInput.get());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
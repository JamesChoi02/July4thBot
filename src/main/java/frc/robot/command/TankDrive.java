package frc.robot.command;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TankDrive extends Command {
    private Supplier<Double> leftInput, rightInput;

    public TankDrive(Supplier<Double> leftInput, Supplier<Double> rightInput) {
        super(Robot.driveTrain);
        this.leftInput = leftInput;
        this.rightInput = rightInput;
    }

    @Override
    protected void execute() {
        Robot.driveTrain.tankDrive(leftInput.get(), rightInput.get());
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

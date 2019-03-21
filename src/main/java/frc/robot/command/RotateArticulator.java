package frc.robot.command;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RotateArticulator extends Command {
    private Supplier<Double> speedInput;

    public RotateArticulator(Supplier<Double> speedInput) {
        super(Robot.articulator);
        this.speedInput = speedInput;
    }

    @Override
    protected void execute() {
        Robot.articulator.rotate(speedInput.get());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.articulator.stop();
    }
}

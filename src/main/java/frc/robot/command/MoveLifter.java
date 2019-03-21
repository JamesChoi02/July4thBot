package frc.robot.command;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveLifter extends Command {
    private Supplier<Double> speedInput;

    public MoveLifter(Supplier<Double> speedInput) {
        super(Robot.lifter);
        this.speedInput = speedInput;
    }

    @Override
    protected void execute() {
        Robot.lifter.move(speedInput.get());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.lifter.stop();
    }
}

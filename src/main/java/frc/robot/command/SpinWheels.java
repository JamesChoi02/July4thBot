package frc.robot.command;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SpinWheels extends Command {
    private Supplier<Double> speedInput;

    public SpinWheels(Supplier<Double> speedInput) {
        super(Robot.wheels);
        this.speedInput = speedInput;
    }

    @Override
    protected void execute() {
        Robot.wheels.spin(speedInput.get());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.wheels.stop();
    }
}

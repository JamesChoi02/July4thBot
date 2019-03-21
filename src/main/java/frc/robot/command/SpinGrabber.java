package frc.robot.command;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SpinGrabber extends Command {
    private Supplier<Double> speedInput;

    public SpinGrabber(Supplier<Double> speedInput) {
        super(Robot.grabber);
        this.speedInput = speedInput;
    }

    @Override
    protected void execute() {
        Robot.grabber.spin(speedInput.get());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.grabber.stop();
    }
}

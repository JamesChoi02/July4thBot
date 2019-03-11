package frc.robot.command;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SpinBackCams extends Command {
    private Supplier<Double> speedInput;

    public SpinBackCams(Supplier<Double> speedInput) {
        super(Robot.backCams);
        this.speedInput = speedInput;
    }

    @Override
    protected void execute() {
        Robot.backCams.spin(speedInput.get());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.backCams.stop();
    }
}
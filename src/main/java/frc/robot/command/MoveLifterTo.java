package frc.robot.command;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class MoveLifterTo extends Command {
    private double position;

    public MoveLifterTo(double position) {
        super(Robot.lifter);
        this.position = position;
    }

    @Override
    protected void execute() {
        Robot.lifter.moveTo(position);
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
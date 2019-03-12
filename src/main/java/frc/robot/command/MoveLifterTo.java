package frc.robot.command;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystem.Lifter.Position;

public class MoveLifterTo extends Command {
    private Position position;

    public MoveLifterTo(Position position) {
        super(Robot.lifter);
        this.position = position;
    }

    @Override
    protected void execute() {
        Robot.lifter.moveTo(position.getRevolutions());
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
package frc.robot.command;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ZeroEncoder extends InstantCommand {
    public ZeroEncoder() {
        super(Robot.lifter);
    }

    @Override
    protected void execute() {
        Robot.lifter.zeroEncoder();
    }
}

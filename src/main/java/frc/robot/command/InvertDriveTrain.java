package frc.robot.command;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class InvertDriveTrain extends InstantCommand {
    @Override
    protected void execute() {
        Robot.driveTrain.invert();
    }
}

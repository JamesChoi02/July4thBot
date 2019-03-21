package frc.robot.command;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

public class ChangeCameraView extends InstantCommand {
    public ChangeCameraView() {
        super(Robot.cameras);
    }

    @Override
    protected void execute() {
        Robot.cameras.nextCamera();
    }
}

package frc.robot.driver;

import edu.wpi.first.wpilibj.XboxController;

public class Xbox360Controller extends XboxController {
    public Xbox360Controller(int port) {
        super(port);
    }

    public boolean getLeftBumper() {
        return getBumper(Hand.kLeft);
    }

    public boolean getRightBumper() {
        return getBumper(Hand.kRight);
    }

    public double getLeftY() {
        return getY(Hand.kLeft);
    }

    public double getRightY() {
        return getY(Hand.kRight);
    }

    public double getLeftTrigger() {
        return getTriggerAxis(Hand.kLeft);
    }

    public double getRightTrigger() {
        return getTriggerAxis(Hand.kRight);
    }
}
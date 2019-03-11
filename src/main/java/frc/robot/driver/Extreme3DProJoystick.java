package frc.robot.driver;

import edu.wpi.first.wpilibj.Joystick;

public class Extreme3DProJoystick extends Joystick {
    public Extreme3DProJoystick(int port) {
        super(port);
    }

    public boolean getPovUp() {
        return getPOV() == 0;
    }

    public boolean getPovDown() {
        return getPOV() == 180;
    }

    public boolean getPovLeft() {
        return getPOV() == 270;
    }

    public boolean getPovRight() {
        return getPOV() == 90;
    }

    public boolean getSideThumbButton() {
        return getRawButton(2);
    }

    public boolean getButton7() {
        return getRawButton(7);
    }

    public boolean getButton8() {
        return getRawButton(8);
    }

    public boolean getButton9() {
        return getRawButton(9);
    }

    public boolean getButton10() {
        return getRawButton(10);
    }

    public boolean getButton11() {
        return getRawButton(11);
    }

    public boolean getButton12() {
        return getRawButton(12);
    }
}
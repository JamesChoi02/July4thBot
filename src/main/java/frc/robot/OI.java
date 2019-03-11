package frc.robot;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.command.DriveStraight;
import frc.robot.command.InvertDriveTrain;
import frc.robot.command.MoveLifter;
import frc.robot.command.MoveLifterTo;
import frc.robot.command.RotateArticulator;
import frc.robot.command.SpinBackCams;
import frc.robot.command.SpinGrabber;
import frc.robot.command.TankDrive;
import frc.robot.driver.Extreme3DProJoystick;
import frc.robot.driver.Xbox360Controller;
import frc.robot.subsystem.Articulator;
import frc.robot.subsystem.BackCams;
import frc.robot.subsystem.DriveTrain;
import frc.robot.subsystem.Grabber;
import frc.robot.subsystem.Lifter;

public class OI {
    public static Xbox360Controller xboxController = new Xbox360Controller(0);
    public static Extreme3DProJoystick joystick = new Extreme3DProJoystick(1);
    private static List<Trigger> binds = new LinkedList<>();

    static {
        if (DriveTrain.isEnabled()) {
            bind(() -> Math.abs(xboxController.getLeftY()) > 0.1 || Math.abs(xboxController.getRightY()) > 0.1,
                    new TankDrive(xboxController::getLeftY, xboxController::getRightY), true);
            bind(() -> xboxController.getRightTrigger() > 0.1, new DriveStraight(xboxController::getRightTrigger),
                    true);
            bind(() -> xboxController.getLeftTrigger() > 0.1, new DriveStraight(() -> -xboxController.getLeftTrigger()),
                    true);
            bind(xboxController::getAButton, new InvertDriveTrain(), false);
        }

        if (BackCams.isEnabled()) {
            bind(() -> xboxController.getLeftBumper() || xboxController.getRightBumper(), new SpinBackCams(() -> 2.0),
                    true);
        }

        if (Lifter.isEnabled()) {
            bind(joystick::getPovUp, new MoveLifter(() -> 0.5), true);
            bind(joystick::getPovDown, new MoveLifter(() -> -0.25), true);

            bind(joystick::getButton7, new MoveLifterTo(2), false);
            bind(joystick::getButton9, new MoveLifterTo(1.5), false);
            bind(joystick::getButton11, new MoveLifterTo(1), false);

            bind(joystick::getButton8, new MoveLifterTo(2), false);
            bind(joystick::getButton10, new MoveLifterTo(1.5), false);
            bind(joystick::getButton12, new MoveLifterTo(1), false);
        }

        if (Articulator.isEnabled()) {
            bind(() -> joystick.getY() > 0.1, new RotateArticulator(() -> 0.35), true);
            bind(() -> joystick.getY() < -0.1, new RotateArticulator(() -> -0.35), true);
        }

        if (Grabber.isEnabled()) {
            bind(joystick::getTrigger, new SpinGrabber(() -> 0.35), true);
            bind(joystick::getSideThumbButton, new SpinGrabber(() -> -0.35), true);
        }
    }

    private static void bind(Supplier<Boolean> condition, Command command, boolean continuous) {
        Trigger trigger = new Trigger() {
            @Override
            public boolean get() {
                return condition.get();
            }
        };

        if (continuous)
            trigger.whileActive(command);
        else
            trigger.whenActive(command);

        binds.add(trigger);
    }
}
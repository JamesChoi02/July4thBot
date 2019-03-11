package frc.robot;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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
import frc.robot.subsystem.Articulator;
import frc.robot.subsystem.BackCams;
import frc.robot.subsystem.DriveTrain;
import frc.robot.subsystem.Grabber;
import frc.robot.subsystem.Lifter;

public class OI {
    public static XboxController xboxController = new XboxController(0);
    public static Joystick joystick = new Joystick(1);
    private static List<Trigger> binds = new LinkedList<>();

    static {
        if (DriveTrain.isEnabled()) {
            bind(() -> Math.abs(xboxController.getY(Hand.kLeft)) > 0.1
                    || Math.abs(xboxController.getY(Hand.kRight)) > 0.1,
                    new TankDrive(() -> xboxController.getY(Hand.kLeft), () -> xboxController.getY(Hand.kRight)), true);
            bind(() -> xboxController.getTriggerAxis(Hand.kRight) > 0.1,
                    new DriveStraight(() -> xboxController.getTriggerAxis(Hand.kRight)), true);
            bind(() -> xboxController.getTriggerAxis(Hand.kLeft) > 0.1,
                    new DriveStraight(() -> -xboxController.getTriggerAxis(Hand.kLeft)), true);
            bind(xboxController::getAButton, new InvertDriveTrain(), false);
        }

        if (BackCams.isEnabled()) {
            bind(() -> xboxController.getBumper(Hand.kLeft) || xboxController.getBumper(Hand.kRight),
                    new SpinBackCams(() -> 2.0), true);
        }

        if (Lifter.isEnabled()) {
            bind(() -> joystick.getPOV() == 0, new MoveLifter(() -> 0.5), true);
            bind(() -> joystick.getPOV() == 180, new MoveLifter(() -> -0.25), true);

            bind(() -> joystick.getRawButton(7), new MoveLifterTo(2), false);
            bind(() -> joystick.getRawButton(9), new MoveLifterTo(1.5), false);
            bind(() -> joystick.getRawButton(11), new MoveLifterTo(1), false);

            bind(() -> joystick.getRawButton(8), new MoveLifterTo(2), false);
            bind(() -> joystick.getRawButton(10), new MoveLifterTo(1.5), false);
            bind(() -> joystick.getRawButton(12), new MoveLifterTo(1), false);
        }

        if (Articulator.isEnabled()) {
            bind(() -> joystick.getY() > 0.1, new RotateArticulator(() -> 0.35), true);
            bind(() -> joystick.getY() < -0.1, new RotateArticulator(() -> -0.35), true);
        }

        if (Grabber.isEnabled()) {
            bind(joystick::getTrigger, new SpinGrabber(() -> 0.35), true);
            bind(() -> joystick.getRawButton(2), new SpinGrabber(() -> -0.35), true);
        }
    }

    private static void bind(Supplier<Boolean> shouldRun, Command command, boolean continuous) {
        Trigger trigger = new Trigger() {
            @Override
            public boolean get() {
                return shouldRun.get();
            }
        };

        if (continuous)
            trigger.whileActive(command);
        else
            trigger.whenActive(command);

        binds.add(trigger);
    }
}
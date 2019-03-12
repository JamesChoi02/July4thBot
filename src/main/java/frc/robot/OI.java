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
import frc.robot.subsystem.Lifter.Position;
import frc.robot.util.Logger;

public class OI {
    public static Xbox360Controller xboxController = new Xbox360Controller(0, 0.1, 0.1);
    public static Extreme3DProJoystick joystick = new Extreme3DProJoystick(1);
    private static List<Trigger> binds = new LinkedList<>();

    static {
        Logger.addLoggable(xboxController);
        Logger.addLoggable(joystick);

        if (DriveTrain.isEnabled()) {
            runWhile(() -> xboxController.getLeftYActive() || xboxController.getRightYActive(),
                    new TankDrive(xboxController::getLeftY, xboxController::getRightY));
            runWhile(xboxController::getRightTriggerPulled, new DriveStraight(() -> xboxController.getRightTrigger()));
            runWhile(xboxController::getLeftTriggerPulled, new DriveStraight(() -> -xboxController.getLeftTrigger()));
            runWhile(xboxController::getAButton, new InvertDriveTrain());
        }

        if (BackCams.isEnabled()) {
            Supplier<Double> backCamSpeed = () -> 1.0;

            runWhile(() -> xboxController.getLeftBumper() || xboxController.getRightBumper(),
                    new SpinBackCams(backCamSpeed));
        }

        if (Lifter.isEnabled()) {
            Supplier<Double> raiseLifterSpeed = () -> 0.5;
            Supplier<Double> lowerLifterSpeed = () -> -0.2;

            runWhile(joystick::getPovUp, new MoveLifter(raiseLifterSpeed));
            runWhile(joystick::getPovDown, new MoveLifter(lowerLifterSpeed));

            runWhen(joystick::getButton7, new MoveLifterTo(Position.HATCH_HIGH));
            runWhen(joystick::getButton9, new MoveLifterTo(Position.HATCH_MIDDLE));
            runWhen(joystick::getButton11, new MoveLifterTo(Position.HATCH_LOW));

            runWhen(joystick::getButton8, new MoveLifterTo(Position.BALL_HIGH));
            runWhen(joystick::getButton10, new MoveLifterTo(Position.BALL_MIDDLE));
            runWhen(joystick::getButton12, new MoveLifterTo(Position.BALL_LOW));
        }

        if (Articulator.isEnabled()) {
            double joystickDeadband = 0.1;
            double articulateUpMaxSpeed = 0.35;
            double articulateDownMaxSpeed = -0.35;

            runWhile(() -> joystick.getY() > joystickDeadband,
                    new RotateArticulator(() -> Math.min(joystick.getY(), articulateUpMaxSpeed)));
            runWhile(() -> joystick.getY() < -joystickDeadband,
                    new RotateArticulator(() -> Math.max(joystick.getY(), articulateDownMaxSpeed)));
        }

        if (Grabber.isEnabled()) {
            Supplier<Double> grabInSpeed = () -> 0.35;
            Supplier<Double> shootOutSpeed = () -> -0.35;

            runWhile(joystick::getTrigger, new SpinGrabber(grabInSpeed));
            runWhile(joystick::getSideThumbButton, new SpinGrabber(shootOutSpeed));
        }
    }

    private static void runWhen(Supplier<Boolean> condition, Command command) {
        bind(condition, command, false);
    }

    private static void runWhile(Supplier<Boolean> condition, Command command) {
        bind(condition, command, true);
    }

    private static void bind(Supplier<Boolean> condition, Command command, boolean continuous) {
        Trigger trigger = new Trigger() {
            @Override
            public boolean get() {
                Logger.log("Trigger polled for " + command.getClass().getSimpleName());
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
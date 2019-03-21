package frc.robot;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.command.ChangeCameraView;
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
import frc.robot.filter.RangeFilter;
import frc.robot.subsystem.Articulator;
import frc.robot.subsystem.BackCams;
import frc.robot.subsystem.Cameras;
import frc.robot.subsystem.DriveTrain;
import frc.robot.subsystem.Grabber;
import frc.robot.subsystem.Lifter;
import frc.robot.subsystem.Lifter.Position;
import frc.robot.log.Logger;

/**
 * Represents all of the driver controls of the robot
 */
public class OI {
    public Xbox360Controller xboxController;
    public Extreme3DProJoystick joystick;
    private List<Trigger> binds;

    public OI() {
        Logger.log("Initializing Bindings");
        xboxController = new Xbox360Controller(0, 0.1, 0.1);
        joystick = new Extreme3DProJoystick(1);
        binds = new LinkedList<>();

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

        if (Cameras.isEnabled()) {
            runWhen(xboxController::getBButton, new ChangeCameraView());
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

            runWhen(joystick::getTopBackLeftButton, new MoveLifterTo(Position.GROUND));
        }

        if (Articulator.isEnabled()) {
            double joystickDeadband = 0.1;
            double articulateUpMaxSpeed = 0.35;
            double articulateDownMaxSpeed = -0.35;

            runWhile(() -> joystick.getY() > joystickDeadband || joystick.getY() < -joystickDeadband,
                    new RotateArticulator(
                            new RangeFilter(joystick::getY, articulateDownMaxSpeed, articulateUpMaxSpeed)));
        }

        if (Grabber.isEnabled()) {
            Supplier<Double> grabInSpeed = () -> 0.35;
            Supplier<Double> shootOutSpeed = () -> -0.35;

            runWhile(joystick::getTrigger, new SpinGrabber(grabInSpeed));
            runWhile(joystick::getSideThumbButton, new SpinGrabber(shootOutSpeed));
        }
    }

    /**
     * Convience method for a bind that should execute the command once directly
     * after the condition switches to true
     * 
     * @param condition the condition to be checked
     * @param command   the command to be run
     */
    private void runWhen(Supplier<Boolean> condition, Command command) {
        bind(condition, command, false);
    }

    /**
     * Convience method for a bind that should execute the command continuously
     * while the condition is true
     * 
     * @param condition the condition to be checked
     * @param command   the command to be run
     */
    private void runWhile(Supplier<Boolean> condition, Command command) {
        bind(condition, command, true);
    }

    /**
     * Binds a command to a condition such that whenever the condition is true, the
     * command is run
     * 
     * @param condition  the boolean expression that must be true in order for the
     *                   command to run
     * @param command    the command to be run when the condition is true
     * @param continuous whether the command should be run continuously while the
     *                   condition is true or just once when the condition switches
     *                   to true
     */
    private void bind(Supplier<Boolean> condition, Command command, boolean continuous) {
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
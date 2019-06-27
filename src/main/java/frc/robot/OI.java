package frc.robot;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.command.SpinWheels;
import frc.robot.driver.Extreme3DProJoystick;
import frc.robot.driver.Xbox360Controller;
import frc.robot.filter.RangeFilter;
import frc.robot.subsystem.Wheels;
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
        // joystick = new Extreme3DProJoystick(1);
        binds = new LinkedList<>();

        if (Wheels.isEnabled()) {
            Supplier<Double> grabInSpeed = () -> 1.0;
            Supplier<Double> shootOutSpeed = () -> -1.0;

            runWhile(joystick::getTrigger, new SpinWheels(grabInSpeed));
            runWhile(joystick::getSideThumbButton, new SpinWheels(shootOutSpeed));
        }
    }

    /**
     * Convience method for a bind that should execute the command once directly after the condition
     * switches to true
     *
     * @param condition the condition to be checked
     * @param command   the command to be run
     */
    private void runWhen(Supplier<Boolean> condition, Command command) {
        Trigger trigger = new Trigger(condition);
        binds.add(trigger);
        trigger.whenActive(command);
    }

    /**
     * Convience method for a bind that should execute the command continuously while the condition
     * is true
     *
     * @param condition the condition to be checked
     * @param command   the command to be run
     */
    private void runWhile(Supplier<Boolean> condition, Command command) {
        Trigger trigger = new Trigger(condition);
        binds.add(trigger);
        trigger.whileActive(command);
    }
}

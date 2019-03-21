package frc.robot.subsystem;

import java.lang.reflect.Field;
import org.junit.Test;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.log.AutoLoggerFactory;
import frc.robot.log.Loggable;
import frc.robot.log.Logger;

/**
 * This is the class that every subsystem should subclass
 */
public abstract class BadSubsystem extends Subsystem implements Loggable {
    /**
     * Make sure this is called in subclass constructors.
     */
    public BadSubsystem() {
        super();
        initComponents();
        verifyFieldInitialization();
        initLogging();
    }

    /**
     * Initialize the motors, sensors, etc for a subsystem
     */
    public abstract void initComponents();

    /**
     * Verify that all of the subsystem's fields are initialized. This should be called after
     * {@link #initComponents()}.
     */
    private void verifyFieldInitialization() {
        for (Field field : getClass().getDeclaredFields()) {
            try {
                if (field.get(this) == null) {
                    Logger.log("Field " + field.getName() + " was not initialized");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initialize the fields that are sent to the SmartDashboard/Shuffleboard. This method is
     * automatically called by WPILIB.
     */
    @Override
    public abstract void initSendable(SendableBuilder builder);

    /**
     * Initialize logging for all of the subsystem's components. All components should be
     * initialized before this is called. By default this method will try to log a component using
     * {@link AutoLoggerFactory#log(String, String, Object)}.
     * 
     * @see badlog.lib.BadLog
     * @see AutoLoggerFactory
     */
    @Override
    public void initLogging() {
        String subsystemName = getName();
        Object obj;
        String deviceName;

        for (Field field : getClass().getDeclaredFields()) {
            try {
                obj = field.get(this);
                deviceName = field.getName();
                AutoLoggerFactory.log(subsystemName, deviceName, obj);
            } catch (NoClassDefFoundError e) {
                Logger.log(e.getMessage());
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Override
    protected void initDefaultCommand() {
        System.out.println("No Default Command Set For " + getName());
    }

    /**
     * This should stop all physical movement of the subsystem
     */
    public abstract void stop();

    /**
     * Release all of the resources that are used by this subsystem. This should only be called by
     * WPILIB.
     */
    @Override
    public abstract void close();

    /**
     * This should test all of the methods in this subsystem and attept to validate the effects
     * using JUnit assertions. {@link #stop()} should be called at the end of the test.
     */
    @Test
    public abstract void test();

    /**
     * Convience method for {@link Timer#delay(double)}.
     * 
     * @param seconds duration of sleep
     */
    protected void sleep(double seconds) {
        Timer.delay(seconds);
    }

    /**
     * This must be overridden in every subclass, as it is disabled by default
     * 
     * @return whether or not the subsystem should be initialized
     */
    public static boolean isEnabled() {
        return false;
    }
}

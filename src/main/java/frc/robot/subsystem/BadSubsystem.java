package frc.robot.subsystem;

import java.lang.reflect.Field;

import org.junit.Test;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import frc.robot.util.AutoLogger;

public abstract class BadSubsystem extends Subsystem {
    public BadSubsystem() {
        super();
        initComponents();
        initLogging();
    }

    public abstract void initComponents();

    public void initLogging() {
        String subsystemName = getName();
        Object obj;
        String deviceName;

        for (Field field : getClass().getDeclaredFields()) {
            try {
                obj = field.get(this);
                deviceName = field.getName();
                AutoLogger.log(subsystemName, deviceName, obj);
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Override
    public abstract void initSendable(SendableBuilder builder);

    @Override
    protected void initDefaultCommand() {
        System.out.println("No Default Command Set For " + getName());
    }

    public abstract void stop();

    @Override
    public abstract void close();

    @Test
    public abstract void test();

    protected void sleep(double seconds) {
        Timer.delay(seconds);
    }

    public static boolean isEnabled() {
        return false;
    }
}
package frc.robot;

import java.util.ConcurrentModificationException;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.subsystem.Articulator;
import frc.robot.subsystem.BackCams;
import frc.robot.subsystem.Cameras;
import frc.robot.subsystem.DriveTrain;
import frc.robot.subsystem.Grabber;
import frc.robot.subsystem.Lifter;
import frc.robot.log.Logger;

/**
 * Represents all of the robots subsystems and provides access to them for commands. The VM is
 * configured to automatically run this class, and to call the functions corresponding to each mode,
 * as described in the TimedRobot documentation. If you change the name of this class or the package
 * after creating this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends TimedRobot {
    public static DriveTrain driveTrain;
    public static BackCams backCams;
    public static Lifter lifter;
    public static Articulator articulator;
    public static Grabber grabber;
    public static Cameras cameras;
    public static OI oi;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code
     */
    @Override
    public void robotInit() {
        Logger.startInitialization();

        if (DriveTrain.isEnabled()) {
            driveTrain = new DriveTrain();
            LiveWindow.add(driveTrain);
        }

        if (BackCams.isEnabled()) {
            backCams = new BackCams();
            // LiveWindow.add(backCams);
        }

        if (Lifter.isEnabled()) {
            lifter = new Lifter();
            // LiveWindow.add(lifter);
        }

        if (Articulator.isEnabled()) {
            articulator = new Articulator();
            // LiveWindow.add(articulator);
        }

        if (Grabber.isEnabled()) {
            grabber = new Grabber();
            // LiveWindow.add(grabber);
        }

        if (Cameras.isEnabled()) {
            cameras = new Cameras();
            // LiveWindow.add(cameras);
        }

        oi = new OI();

        Logger.finishInitialization();
    }

    /**
     * This method is called 50 times per second (once every 20ms) and should contain code that is
     * run continuously
     */
    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        // Logger.update();
        // LiveWindow.updateValues();
    }
}

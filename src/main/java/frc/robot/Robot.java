/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.subsystem.Articulator;
import frc.robot.subsystem.BackCams;
import frc.robot.subsystem.Cameras;
import frc.robot.subsystem.DriveTrain;
import frc.robot.subsystem.Grabber;
import frc.robot.subsystem.Lifter;
import frc.robot.util.Logger;

/**
 * Represents all of the robots subsystems and provides access to them for
 * commands. The VM is configured to automatically run this class, and to call
 * the functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
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
     * This function is run when the robot is first started up and should be used
     * for any initialization code
     */
    @Override
    public void robotInit() {
        Logger.startInitialization();

        if (DriveTrain.isEnabled()) {
            driveTrain = new DriveTrain();
        }

        if (BackCams.isEnabled()) {
            backCams = new BackCams();
        }

        if (Lifter.isEnabled()) {
            lifter = new Lifter();
        }

        if (Articulator.isEnabled()) {
            articulator = new Articulator();
        }

        if (Grabber.isEnabled()) {
            grabber = new Grabber();
        }

        if (Cameras.isEnabled()) {
            cameras = new Cameras();
            LiveWindow.add(cameras);
        }

        oi = new OI();

        Logger.finishInitialization();
    }

    /**
     * This method is called 50 times per second (once every 20ms) and should
     * contain code that is run continuously
     */
    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
        Logger.update();
        LiveWindow.updateValues();
    }
}

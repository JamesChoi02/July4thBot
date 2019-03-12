/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.robot.subsystem.Articulator;
import frc.robot.subsystem.BackCams;
import frc.robot.subsystem.DriveTrain;
import frc.robot.subsystem.Grabber;
import frc.robot.subsystem.Lifter;
import frc.robot.util.LoggerThread;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
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

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        if (DriveTrain.isEnabled()) {
            driveTrain = new DriveTrain();
            LoggerThread.addLoggable(driveTrain);
        }

        if (BackCams.isEnabled()) {
            backCams = new BackCams();
            LoggerThread.addLoggable(backCams);
        }

        if (Lifter.isEnabled()) {
            lifter = new Lifter();
            LoggerThread.addLoggable(lifter);
        }

        if (Articulator.isEnabled()) {
            articulator = new Articulator();
            LoggerThread.addLoggable(articulator);
        }

        if (Grabber.isEnabled()) {
            grabber = new Grabber();
            LoggerThread.addLoggable(grabber);
        }

        new LoggerThread(50).start();
    }

    /**
     * This function is run once each time the robot enters autonomous mode.
     */
    @Override
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        teleopPeriodic();
    }

    /**
     * This function is called once each time the robot enters teleoperated mode.
     */
    @Override
    public void teleopInit() {
    }

    /**
     * This function is called periodically during teleoperated mode.
     */
    @Override
    public void teleopPeriodic() {

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}

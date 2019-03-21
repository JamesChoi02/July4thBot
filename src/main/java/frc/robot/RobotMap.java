package frc.robot;

/**
 * Represents all of the physical address of the robot's hardware such as motor
 * CAN ids or sensor DIO positions
 */
public class RobotMap {
    /**
     * Drivetrain Motor CAN Ids
     */
    public static final int FRONT_LEFT_MOTOR = 2;
    public static final int FRONT_RIGHT_MOTOR = 3;
    public static final int BACK_LEFT_MOTOR = 4;
    public static final int BACK_RIGHT_MOTOR = 1;

    /**
     * Aux Motor CAN Ids
     */
    public static final int LIFTER_MOTOR = 12;
    public static final int CAM_MOTOR = 23;
    public static final int ARTICULATOR_MOTOR = 25;
    public static final int GRABBER_MOTOR = 21;
}
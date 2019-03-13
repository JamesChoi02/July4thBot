package frc.robot.util;

public abstract class AutoLogger<T> implements Loggable {
    static {
        register();
    }

    public static void register() {}

    protected final String subsystemName;
    protected final String combo;
    protected final T device;

    public AutoLogger(String subsystemName, String deviceName, T device) {
        this.subsystemName = subsystemName;
        this.combo = subsystemName + "/" + deviceName + " ";
        this.device = device;
    }
}
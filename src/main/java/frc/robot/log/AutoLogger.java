package frc.robot.log;

public interface AutoLogger<T> {
    public abstract void log(String subsystemName, String combo, T device);
}
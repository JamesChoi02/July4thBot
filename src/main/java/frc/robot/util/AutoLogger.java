package frc.robot.util;

public interface AutoLogger<T> {
    public abstract void log(String subsystemName, String combo, T device);
}
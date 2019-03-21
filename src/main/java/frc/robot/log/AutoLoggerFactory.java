package frc.robot.log;

import java.util.HashMap;
import java.util.Map;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import frc.robot.driver.CANTalonSRX;

public class AutoLoggerFactory {
    private static Map<Class<?>, AutoLogger<?>> loggers = new HashMap<>();

    static {
        register(CANSparkMax.class, new CANSparkMaxAutoLogger());
        register(CANTalonSRX.class, new CANTalonSRXAutoLogger());
        register(AHRS.class, new AHRSAutoLogger());
    }

    public static <T> void log(String subsystemName, String deviceName, T device) {
        AutoLogger<T> logger = (AutoLogger<T>) loggers.get(device.getClass());

        if (logger == null)
            throw new NoClassDefFoundError("No AutoLogger<" + device.getClass().getSimpleName() + "> was registered");
        else
            logger.log(subsystemName, subsystemName + "/" + deviceName + " ", device);
    }

    private static void register(Class<?> deviceType, AutoLogger<?> logger) {
        loggers.put(deviceType, logger);
    }
}
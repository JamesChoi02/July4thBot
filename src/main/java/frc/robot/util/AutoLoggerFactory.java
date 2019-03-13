package frc.robot.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AutoLoggerFactory {
    private static Map<Class<?>, Constructor<?>> constructors = new HashMap<>();

    public static <T> AutoLogger<T> getAutoLoggerFor(String subsystemName, String deviceName, T object) {
        Constructor<?> constructor = constructors.get(object.getClass());

        if (constructor == null) {
            throw new NoClassDefFoundError("No AutoLogger<" + object.getClass().getSimpleName() + "> was registered");
        } else {
            try {
                return (AutoLogger<T>) constructor.newInstance(new Object[] { subsystemName, deviceName, object });
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void registerAutoLogger(Class<?> deviceType, Constructor<?> constructor) {
        constructors.put(deviceType, constructor);
    }
}
package frc.robot.subsystem;

import static org.junit.Assert.assertTrue;

import badlog.lib.BadLog;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class Cameras extends BadSubsystem {
    public enum Camera {
        FRONT(0, new VideoMode(PixelFormat.kYUYV, 320, 180, 30)),
        BACK(1, new VideoMode(PixelFormat.kYUYV, 320, 180, 30));

        private final UsbCamera device;

        private Camera(int port, VideoMode videoMode) {
            device = new UsbCamera(name(), port);
            // device = CameraServer.getInstance().startAutomaticCapture(name(), port);
            device.setVideoMode(videoMode);
        }

        private UsbCamera getDevice() {
            return device;
        }
    }

    private VideoSink server;
    private Camera activeCamera;

    @Override
    public void initComponents() {
        server = CameraServer.getInstance().addSwitchedCamera("POV");
        setCamera(Camera.values()[0]);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("Camera Name", () -> getActiveCamera().getDevice().getName(), null);
        // builder.addDoubleProperty("Camera FPS", () ->
        // getActiveCamera().getDevice().getActualFPS(), null);
    }

    @Override
    public void initLogging() {
        // BadLog.createTopic("Camera FPS", "FPS", () ->
        // getActiveCamera().getDevice().getActualFPS());
        // BadLog.createTopic("Camera Data Rate", "Bytes/s", () ->
        // getActiveCamera().getDevice().getActualDataRate());
    }

    public void setCamera(Camera camera) {
        activeCamera = camera;
        server.setSource(camera.getDevice());
    }

    public void nextCamera() {
        setCamera(Camera.values()[(getActiveCamera().ordinal() + 1) % Camera.values().length]);
    }

    private Camera getActiveCamera() {
        return activeCamera;
    }

    @Override
    public void stop() {
        server.setSource(null);
    }

    @Override
    public void close() {
        for (Camera camera : Camera.values())
            camera.getDevice().close();
        server.close();
    }

    @Override
    public void test() {
        for (Camera camera : Camera.values())
            assertTrue(camera.name() + " Camera Valid", camera.getDevice().isValid());
    }

    public static boolean isEnabled() {
        return true;
    }
}
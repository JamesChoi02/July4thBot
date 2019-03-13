package frc.robot.subsystem;

import static org.junit.Assert.assertTrue;

import badlog.lib.BadLog;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class Cameras extends BadSubsystem {
    enum Camera {
        FRONT(0), BACK(1);

        private final UsbCamera device;

        private Camera(int port) {
            device = new UsbCamera(name(), port);
        }

        private UsbCamera getDevice() {
            return device;
        }
    }

    private VideoSink server;

    @Override
    public void initComponents() {
        server = CameraServer.getInstance().addServer("POV");
        setCamera(Camera.FRONT);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addStringProperty("Camera Name", () -> getActiveCamera().getDevice().getName(), null);
        builder.addDoubleProperty("Camera FPS", () -> getActiveCamera().getDevice().getActualFPS(), null);
    }

    @Override
    public void initLogging() {
        BadLog.createTopic("Camera FPS", "FPS", () -> getActiveCamera().getDevice().getActualFPS());
        BadLog.createTopic("Camera Data Rate", "Bytes/s", () -> getActiveCamera().getDevice().getActualDataRate());
    }

    public void setCamera(Camera camera) {
        server.setSource(camera.getDevice());
    }

    public void nextCamera() {
        server.setSource(Camera.values()[(getActiveCamera().ordinal() + 1) % Camera.values().length].getDevice());
    }

    private Camera getActiveCamera() {
        for (Camera camera : Camera.values())
            if (camera.getDevice() == server.getSource())
                return camera;

        return null;
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
}
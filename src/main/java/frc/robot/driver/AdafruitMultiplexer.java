package frc.robot.driver;

import edu.wpi.first.wpilibj.I2C;

public class AdafruitMultiplexer extends I2C {
    public AdafruitMultiplexer(Port port, int deviceAddress) {
        super(port, deviceAddress);
    }

    public void reset() {
        byte[] buffer = new byte[1];
        buffer[0] = 0;
        writeBulk(buffer);
    }

    public void selectChannel(int channel) {
        byte[] buffer = new byte[1];
        buffer[0] = (byte) (1 << channel);
        writeBulk(buffer);
    }

    public int getCurrentChannel() {
        byte[] buffer = new byte[1];
        readOnly(buffer, 1);
        return buffer[0];
    }
}
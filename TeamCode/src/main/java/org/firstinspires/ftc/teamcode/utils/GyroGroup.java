package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;

/**
 * Created by John on 1/13/2018.
 */

public class GyroGroup implements GyroSensor {
    private GyroSensor[] m_gyros;

    public GyroGroup(GyroSensor[] gyros) {
        m_gyros = gyros;
    }


    @Override
    public void calibrate() {
        for (GyroSensor gyro : m_gyros) {
            gyro.calibrate();
        }
    }

    @Override
    public boolean isCalibrating() {
        for (GyroSensor gyro : m_gyros) {
            if (gyro.isCalibrating()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getHeading() {
        float sum = 0;
        for (GyroSensor gyro : m_gyros) {
            sum += gyro.getHeading();
        }
        float len = m_gyros.length;
        return Math.round(sum / m_gyros.length);
    }

    @Override
    public double getRotationFraction() {
        double sum = 0;
        for (GyroSensor gyro : m_gyros) {
            sum += gyro.getRotationFraction();
        }
        double len = m_gyros.length;
        return Math.round(sum / m_gyros.length);
    }

    @Override
    public int rawX() {
        return 0;
    }

    @Override
    public int rawY() {
        return 0;
    }

    @Override
    public int rawZ() {
        return 0;
    }

    @Override
    public void resetZAxisIntegrator() {

    }

    @Override
    public String status() {
        return null;
    }

    @Override
    public Manufacturer getManufacturer() {
        return null;
    }

    @Override
    public String getDeviceName() {
        return null;
    }

    @Override
    public String getConnectionInfo() {
        return null;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {

    }

    @Override
    public void close() {

    }
}

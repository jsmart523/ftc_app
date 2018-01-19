package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.robotcore.external.Func;

import java.util.Map;

/**
 * Created by John on 1/13/2018.
 */

public class GyroGroup implements GyroSensor {

/*    private abstract class GGMapper<X> {
        public abstract X mapOne(GyroSensor gyro);

        public X map() {
            X sum;
            int count = 0;
            Void whatever;
            /// afdasfasfafdadsafdafdsjkl fadsjlfskda jfsdakl ;fdsajdfsakl;fsdajklfsd ;
            }
        }
    }*/

    private GyroSensor[] m_gyros;

/*    private void DoVoid(GGMapper<Void> mapper) {
        for (GyroSensor gyro : m_gyros) {
            mapper.map(gyro);
        }
    }


    private double DoDouble(GGMapper<Double> mapper) throws UnsupportedOperationException {
        int count = 0;
        double sum = 0;


        for (GyroSensor gyro : m_gyros) {
            try {
                sum += mapper.map(gyro);
                count++;
            }
            catch (UnsupportedOperationException e) {
                // do nothing
            }
        }

        if (count == 0) {
            throw new UnsupportedOperationException("All gyros in group threw UnsupportedOperationException");
        }
        else {
            return sum / count;
        }
    }*/

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

/*    class Mapper_RotationFraction implements GGMapper {

        @Override
        public Double map(GyroSensor gyro) {
            return gyro.getRotationFraction();
        }
    }
    private Mapper_RotationFraction mapper_FotationFraction = new Mapper_RotationFraction();*/

    @Override
    public double getRotationFraction() { throw new UnsupportedOperationException(); }

    @Override
    public int rawX() { throw new UnsupportedOperationException(); }

    @Override
    public int rawY() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int rawZ() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void resetZAxisIntegrator() { throw new UnsupportedOperationException(); }

    @Override
    public String status() {
        StringBuilder ret = new StringBuilder();

        for (GyroSensor gyro : m_gyros) {
            ret.append(gyro.status());
            ret.append(" | ");
        }

        return ret.toString();
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.Other;
    }

    @Override
    public String getDeviceName() {
        StringBuilder ret = new StringBuilder();

        for (GyroSensor gyro : m_gyros) {
            ret.append(gyro.status());
            ret.append(" | ");
        }
        return ret.toString();
    }

    @Override
    public String getConnectionInfo() {

        StringBuilder ret = new StringBuilder();

        for (GyroSensor gyro : m_gyros) {
            ret.append(gyro.getConnectionInfo());
            ret.append(" | ");
        }
        return ret.toString();
    }

    @Override
    public int getVersion() {

        return m_gyros[0].getVersion();
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
        for (GyroSensor gyro : m_gyros) {
            gyro.resetDeviceConfigurationForOpMode();
        }
    }

    @Override
    public void close() {
        for (GyroSensor gyro : m_gyros) {
            gyro.close();
        }
    }
}

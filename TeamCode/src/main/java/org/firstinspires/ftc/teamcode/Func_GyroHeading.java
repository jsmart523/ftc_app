package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.robotcore.external.Func;

/**
 * Created by John on 12/23/2017.
 */

public class Func_GyroHeading implements Func {
    GyroSensor m_gyro;

    public Func_GyroHeading(GyroSensor gyro) {
        m_gyro = gyro;
    }

    @Override
    public Object value() {
        return m_gyro.getHeading();
    }
}

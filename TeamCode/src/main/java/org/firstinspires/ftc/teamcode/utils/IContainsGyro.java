package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by John on 1/6/2018.
 */

public interface IContainsGyro {
    GyroSensor gyro = null;

    public double getHeadingDegreesActual();
}

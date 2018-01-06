package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.robotcore.external.Func;

/**
 * Created by John on 12/23/2017.
 */

public class Func_GyroHeading implements Func {

    IContainsGyro gyroContainer;

    public Func_GyroHeading(IContainsGyro robot) {
        gyroContainer = robot;
    }

    @Override
    public Object value() {
        return gyroContainer.getHeadingDegreesActual();
    }
}

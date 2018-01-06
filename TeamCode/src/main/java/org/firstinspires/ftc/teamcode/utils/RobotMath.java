package org.firstinspires.ftc.teamcode.utils;

/**
 * Created by John on 1/6/2018.
 */

public class RobotMath {
    public static double radians180(double radians) {
        if (radians > Math.PI) {
            return radians - 2 * Math.PI;
        }
        else if (radians < 0 - Math.PI) {
            return radians + 2 * Math.PI;
        } else return radians;
    }

    public static double degrees180(double degrees) {
        if (degrees > 180) {
            return degrees - 360;
        }
        else if (degrees < 0 - 180) {
            return degrees + 360;
        }
        else return degrees;
    }

    // given the stick_x and stick_y, get the angle, with zero being "forward" PI/2 being "left"
    public static double getRadiansFromStickValues(double x, double y) {
        return radians180(Math.atan2(-x, -y));
    }

}

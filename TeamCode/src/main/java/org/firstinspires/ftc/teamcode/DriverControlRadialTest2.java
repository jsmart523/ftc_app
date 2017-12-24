package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * From DriverControlRadialTest1
 * change to reset which direction is "zero" for the robot by
 * adding getHeadingRadiansActual and setHeadingRadiansActual, then changing the rest of the code to use that rather than access the gyro directly.
 */

@TeleOp(name = "RadialTest2", group = "Linear OpMode")
public class DriverControlRadialTest2 extends ALinearOpMode2 {

    private double m_gyroOffsetDegrees = 0;

    @Override
    void customLoopBody() {
        makeWheelsMove();
        rotateServos();
        moveLift();
        //       changePower();
    }

    public double getHeadingDegreesActual() {
        return degrees180(gyro.getHeading() + m_gyroOffsetDegrees);
    }

    public void setHeadingDegreesActual(double headingDegreesActual) {
        m_gyroOffsetDegrees = degrees180(headingDegreesActual - gyro.getHeading());
        telemetry.addData("gyroOffset", Math.round(m_gyroOffsetDegrees));
    }

    public double getHeadingRadiansActual() {
        return radians180(Math.toRadians(getHeadingDegreesActual()));
    }

    public void setHeadingRadiansActual(double headingRadiansActual) {
        setHeadingDegreesActual(Math.toDegrees(headingRadiansActual));
    }

    public double radians180(double radians) {
        if (radians > Math.PI) {
            return radians - 2 * Math.PI;
        }
        else if (radians < 0 - Math.PI) {
            return radians + 2 * Math.PI;
        } else return radians;
    }

    public double degrees180(double degrees) {
        if (degrees > 180) {
            return degrees - 360;
        }
        else if (degrees < 0 - 180) {
            return degrees + 360;
        }
        else return degrees;
    }

    // given the stick_x and stick_y, get the angle, with zero being "forward" PI/2 being "left"
    public double getRadiansFromStickValues(double x, double y) {
        double forward = convertStickToPower(x);
        double left = convertStickToPower(y);

        return radians180(Math.atan2(-forward, -left));
    }

    public void makeWheelsMove() {
        double left_stick_x = gamepad1.left_stick_x;
        double left_stick_y = gamepad1.left_stick_y;
        double headingAbsoluteRadians = getRadiansFromStickValues(left_stick_x, left_stick_y);
        double headingRelativeRadians = radians180(headingAbsoluteRadians - getHeadingRadiansActual());
        double v = convertStickToPower(Math.hypot(left_stick_x, left_stick_y));
        double turn;
        if (gamepad1.dpad_up) {
            setHeadingRadiansActual(getRadiansFromStickValues(gamepad1.right_stick_x, gamepad1.right_stick_y));
            turn = 0;
        }
        else {
            turn = getTurnVelocity();
        }

        telemetry.addLine()
                .addData("sd", Math.toDegrees(headingAbsoluteRadians))
                .addData("sv", v)
                .addData("t", turn);

        setRadialVelocity(headingRelativeRadians, v, turn);
    }

    public double getTurnVelocity() {
        final double minTurnThresholdRadians = Math.PI/60; //  3 degrees
        final double fullTurnThresholdRadians = Math.PI/6; // 30 degrees
        final double maxTurnValue = 0.75;

        double x = gamepad1.right_stick_x;
        double y = gamepad1.right_stick_y;

        if (x == 0 && y == 0) {
            return 0;
        }
        
        double headingRadiansDesired = getRadiansFromStickValues(x, y);
        double headingRadiansCorrection = radians180(headingRadiansDesired - getHeadingRadiansActual());
        double sign = 0 - Math.signum(headingRadiansCorrection);
        double absRadiansCorrection = Math.abs(headingRadiansCorrection);
        double turnEffortPercentage; // value from -1 to 1
        
        if (absRadiansCorrection < minTurnThresholdRadians) {
            turnEffortPercentage = 0;
        }
        else if (absRadiansCorrection > fullTurnThresholdRadians) {
            turnEffortPercentage = sign;
        }
        else {
            turnEffortPercentage = sign * (absRadiansCorrection / fullTurnThresholdRadians);
        }
        telemetry.addLine()
                .addData("hd", Math.round(Math.toDegrees(headingRadiansDesired)))
                .addData("ha", Math.round(getHeadingDegreesActual()))
                .addData("hc", Math.round(Math.toDegrees(headingRadiansCorrection)))
                .addData("hac", Math.round(Math.toDegrees(absRadiansCorrection)))
                .addData("t", turnEffortPercentage * maxTurnValue);
        return turnEffortPercentage * maxTurnValue;
    }


    public void rotateServos() { //Right Servo out = MAX, Left Servo out = MIN
        if (gamepad1.x) { //change to y and x
            left.setPosition(.69); //.63 with other arms
            right.setPosition(.24); //.3 with other arms
        }
        
        if (gamepad1.y) {
            left.setPosition(0);
            right.setPosition(1);
        }
    }

    public void moveLift() {
        if (gamepad1.a) {
            lift.setPower(1);
        }
        else if (gamepad1.b) {
            lift.setPower(-1);
        }
        else {
            lift.setPower(0);
        }
    }
/*
    public void changePower() {
        if (gamepad1.a) {
            if (!aPressed) {
                aPressed = true;
                powerFactor += 0.1;
                telemetry.addData("power", powerFactor);
            }
        }
        else {
            aPressed = false;
        }
        if (gamepad1.b) {
            if (!bPressed) {
                bPressed = true;
                powerFactor = Math.max(powerFactor - 0.1, 1);
                telemetry.addData("power", powerFactor);
            }
        }
        else {
            bPressed = false;
        }
    }
*/
}



package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eaganrobotics on 10/19/2017.
 */

@TeleOp(name = "RadialTest1", group = "Linear OpMode")
public class DriverControlRadialTest1 extends ALinearOpMode2 {

    boolean aPressed = false;
    boolean bPressed = false;

    @Override
    void customLoopBody() {
        makeWheelsMove();
        rotateServos();
        moveLift();
        //       changePower();
    }

    public double getDesiredRadiansFromStickValues(double x, double y) {
        double forward = 0 - convertStickToPower(x);
        double left = 0 - convertStickToPower(y);

        return Math.atan2(forward, left);
    }

    public void makeWheelsMove() {
        double left_stick_x = gamepad1.left_stick_x;
        double left_stick_y = gamepad1.left_stick_y;

        //double forward = 0 - convertStickToPower(gamepad1.left_stick_x);
        //double left = 0 - convertStickToPower(gamepad1.left_stick_y);


        double gyroHeadingDegrees = gyro.getHeading();

        // desired = actual + correction
        //
        double headingAbsoluteRadians = getDesiredRadiansFromStickValues(left_stick_x, left_stick_y);
        double gyroHeadingRadians = Math.toRadians(gyroHeadingDegrees);
        double headingRelativeRadians = getHeadingRadiansCorrection(headingAbsoluteRadians, gyroHeadingRadians);
        double v = convertStickToPower(Math.hypot(left_stick_x, left_stick_y));
        double turn = getTurnVelocity();

        telemetry.addLine()
                 .addData("sds", Math.toDegrees(headingAbsoluteRadians))
                 .addData("vs", v)
                 .addData("ts",turn);
        telemetry.addData("g", gyroHeadingDegrees);


        setRadialVelocity(headingRelativeRadians, v, turn);
    }

    public double getHeadingRadiansCorrection(double headingRadiansDesired) {
        return getHeadingRadiansCorrection(headingRadiansDesired, Math.toRadians(gyro.getHeading()));
    }

    public double getHeadingRadiansCorrection(double headingRadiansDesired, double headingRadiansActual) {
        // correction = desired - actual
        // desiredAbsolute = desiredRelative + actual
        // we have absolute, and we have actual
        // so solve for relative
        // desiredRelative = desiredAbsolute - actual
        // therefore, to get the relative heading, we need to call getHeadingRadiansCorrection(headingRadiansAbsolute, headingRadiansActual)
        double headingRadiansCorrection = headingRadiansDesired - headingRadiansActual;
        if (headingRadiansCorrection > Math.PI) {
            headingRadiansCorrection -= 2 * Math.PI;
        }
        else if (headingRadiansCorrection < 0 - Math.PI) {
            headingRadiansCorrection += 2 * Math.PI;
        }
        return headingRadiansCorrection;
    }

    public double getTurnVelocity() {
        final double minTurnThresholdRadians = Math.PI/18; // 10 degrees
        final double fullTurnThresholdRadians = Math.PI/4; // 45 degrees
        final double maxTurnValue = 0.75;

        double x = gamepad1.right_stick_x;
        double y = gamepad1.right_stick_y;

        if (x == 0 && y == 0) {
            return 0;
        }
        
        double headingRadiansDesired = getDesiredRadiansFromStickValues(x, y);
        double headingRadiansActual = Math.toRadians(gyro.getHeading());

        //double headingRadiansCorrection = getHeadingRadiansCorrection(headingRadiansDesired, headingRadiansActual);

        double headingRadiansCorrection = headingRadiansDesired - headingRadiansActual;
        if (headingRadiansCorrection > Math.PI) {
            headingRadiansCorrection -= 2 * Math.PI;
        }
        else if (headingRadiansCorrection < 0 - Math.PI) {
            headingRadiansCorrection += 2 * Math.PI;
        }

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
                .addData("hd", Math.floor(Math.toDegrees(headingRadiansDesired)))
                .addData("ha", Math.floor(Math.toDegrees(headingRadiansActual)))
                .addData("hc", Math.floor(Math.toDegrees(headingRadiansCorrection)))
                .addData("hac", Math.floor(Math.toDegrees(absRadiansCorrection)))
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

    public void turnVelocity() {
        double xpos = 0 - gamepad1.right_stick_x;
        double ypos = 0 - gamepad1.right_stick_y;

        double headingRadians = Math.atan2(xpos, ypos);
        telemetry.addData("rightStickAngle", Math.toDegrees(headingRadians));
        double robotHeading360 = gyro.getHeading();
        double tempHeading360 = 0;
        double desiredChange360 = Math.toDegrees(headingRadians) - robotHeading360;

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



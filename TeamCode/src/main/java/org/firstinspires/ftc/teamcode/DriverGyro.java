package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * From DriverControlRadialTest2
 * No code changes other than testing ALinearOpMode3 and moving code from this level into ALinearOpMode3
 */

@TeleOp(name = "Gyro", group = "Linear OpMode")
public class DriverGyro extends ALinearOpMode3 {

    @Override
    protected void customSetup() {
    }

    @Override
    void customLoopBody() {
        makeWheelsMove();
        rotateServos();
        moveLift();
        //       changePower();
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
        final double minTurnThresholdRadians = Math.PI/180; // 1 degrees
        final double fullTurnThresholdRadians = Math.PI/3; // 60 degrees
        final double maxTurnValue = 1;

        double x = gamepad1.right_stick_x;
        double y = gamepad1.right_stick_y;

        if (x == 0 && y == 0) {
            return 0;
        }
        
        double headingRadiansDesired = getRadiansFromStickValues(x, y);
        double headingRadiansCorrection = radians180(headingRadiansDesired - getHeadingRadiansActual());
        double sign = 0 - Math.signum(headingRadiansCorrection);
        double absRadiansCorrection = Math.abs(headingRadiansCorrection);
        double turnEffortPercentage = convertStickToPower(Math.hypot(x, y)) * maxTurnValue;// value from -1 to 1

        if (absRadiansCorrection < minTurnThresholdRadians) {
            turnEffortPercentage = 0;
        }
        else if (absRadiansCorrection > fullTurnThresholdRadians) {
            turnEffortPercentage *= sign;
        }
        else {
            turnEffortPercentage *= sign * (absRadiansCorrection / fullTurnThresholdRadians);
        }
        telemetry.addLine()
                .addData("hd", Math.round(Math.toDegrees(headingRadiansDesired)))
                .addData("hc", Math.round(Math.toDegrees(headingRadiansCorrection)))
                .addData("hac", Math.round(Math.toDegrees(absRadiansCorrection)))
                .addData("t", turnEffortPercentage);
        return turnEffortPercentage;
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
}



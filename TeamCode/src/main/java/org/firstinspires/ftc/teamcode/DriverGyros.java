package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * From DriverControlRadialTest2
 * No code changes other than testing ALinearOpMode3 and moving code from this level into ALinearOpMode3
 */

@TeleOp(name = "Gyros", group = "Linear OpMode")
public class DriverGyros extends ALinearOpMode4 {

    @Override
    protected void customSetup() {
    }

    @Override
    protected void customLoopBody() {
        makeWheelsMove();
        rotateServos();
        moveLift();
        //       changePower();
    }


    public void makeWheelsMove() {
        double left_stick_x = gamepad1.left_stick_x;
        double left_stick_y = gamepad1.left_stick_y;
        double right_stick_x = gamepad1.right_stick_x;
        double right_stick_y = gamepad1.right_stick_y;
        double headingAbsoluteRadians = getRadiansFromStickValues(left_stick_x, left_stick_y);
        double headingRelativeRadians = radians180(headingAbsoluteRadians - getHeadingRadiansActual());
        double v = convertStickToPower(Math.hypot(left_stick_x, left_stick_y));
        double turn;

        if (gamepad1.dpad_up) {
            setHeadingRadiansActual(getRadiansFromStickValues(right_stick_x, right_stick_y));
            turn = 0;
        }
        else {
            if (right_stick_x == 0 && right_stick_y == 0) {
                turn = 0;
            }
            else {
                double headingRadiansDesired = getRadiansFromStickValues(right_stick_x, right_stick_y);
                double turnEffortPercentage = convertStickToPower(right_stick_x, right_stick_y);// value from -1 to 1
                turn = getTurnVelocity(headingRadiansDesired) * turnEffortPercentage;
            }
        }

        telemetry.addLine()
                .addData("sd", Math.toDegrees(headingAbsoluteRadians))
                .addData("sv", v)
                .addData("t", turn);

        setRadialVelocity(headingRelativeRadians, v, turn);
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
            //lift.setPower(1);
        }
        else if (gamepad1.b) {
            lift.setPower(-1);
        }
        else {
            lift.setPower(0);
        }
    }
}



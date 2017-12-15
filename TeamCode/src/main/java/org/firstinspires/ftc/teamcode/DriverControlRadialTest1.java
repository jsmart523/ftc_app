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

    public void makeWheelsMove() {
        double forward = 0 - convertStickToPower(gamepad1.left_stick_x);
        double left = 0 - convertStickToPower(gamepad1.left_stick_y);

        double headingRadians = Math.atan2(forward, left);
        double v = convertStickToPower(Math.hypot(forward, left));
        double turn = getTurnVelocity();

        telemetry.addLine()
                 .addData("sds", Math.toDegrees(headingRadians))
                 .addData("vs", v)
                 .addData("ts",turn);
        telemetry.addData("g", gyro.getHeading());


        setRadialVelocity(headingRadians, v, turn);
    }

    public double getTurnVelocity() {
        final double fullTurnThresholdRadians = 0.2;
        final double maxTurnValue = 0.75;

        double forward = gamepad1.right_stick_x;
        double left = 0 - gamepad1.right_stick_y;
        
        double headingRadiansDesired = Math.atan2(forward, left);
        double headingRadiansActual = Math.toRadians(gyro.getHeading());

        double headingRadiansCorrection = headingRadiansDesired - headingRadiansActual;
        if (headingRadiansCorrection > Math.PI) {
            headingRadiansCorrection -= 2 * Math.PI;
        }
        else if (headingRadiansCorrection < 0 - Math.PI) {
            headingRadiansCorrection += 2 * Math.PI;
        }
        double sign = Math.signum(headingRadiansCorrection);
        double absRadiansCorrection = Math.abs(headingRadiansCorrection);
        double turnEffortPercentage; // value from -1 to 1;
        if ((absRadiansCorrection > fullTurnThresholdRadians) || (absRadiansCorrection == 0)) {
            turnEffortPercentage = sign;
        }
        else {
            turnEffortPercentage = sign * (absRadiansCorrection / fullTurnThresholdRadians);
        }
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



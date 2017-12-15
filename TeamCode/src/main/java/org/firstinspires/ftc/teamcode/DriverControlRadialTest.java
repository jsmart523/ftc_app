package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eaganrobotics on 10/19/2017.
 */

@TeleOp(name = "RadialTest", group = "Linear OpMode")
public class DriverControlRadialTest extends ALinearOpMode2 {

    boolean aPressed = false;
    boolean bPressed = false;

    @Override
    void customLoopBody() {
        makeWheelsMove();
        rotateServos();
        moveLift();
//        changePower();
    }

    public void makeWheelsMove() {
        double forward = 0 - convertStickToPower(gamepad1.left_stick_x);
        double left = 0 - convertStickToPower(gamepad1.left_stick_y);

        double headingRadians = Math.atan2(forward, left);
        double v = convertStickToPower(Math.hypot(forward, left));
        double turn = convertStickToPower(gamepad1.right_stick_x);

        telemetry.addLine()
                 .addData("sds", Math.toDegrees(headingRadians))
                 .addData("vs", v)
                 .addData("ts",turn);
        telemetry.addData("g", gyro.getHeading());


        setRadialVelocity(headingRadians, v, turn);
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



package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Same as DriverControl.java except that this one inherits from aLinearOpMode1
 */

@TeleOp(name = "DriverControl2", group = "Linear Opmode")
public class DriverControl2 extends ALinearOpMode1 {
    @Override
    void customLoopBody() {
        makeWheelsMove();
        rotateServos();
        moveLift();
    }

    public void makeWheelsMove() {
        if (!gamepad1.left_bumper && !gamepad1.right_bumper) {
            double l = gamepad1.left_stick_y;
            double r = gamepad1.right_stick_y;
            topLeft.setPower(l);
            topRight.setPower(r);
            bottomLeft.setPower(l);
            bottomRight.setPower(r);
        } else if (gamepad1.left_bumper) { //strafe left
            topLeft.setPower(-1);
            topRight.setPower(-1);
            bottomLeft.setPower(1);
            bottomRight.setPower(1);
        } else if (gamepad1.right_bumper) { //strafe right
            topLeft.setPower(1);
            topRight.setPower(1);
            bottomLeft.setPower(-1);
            bottomRight.setPower(-1);
        }
    }

    public void rotateServos() {
        if (gamepad2.x) { //change to y and x
            left.setPosition(Servo.MAX_POSITION);
            right.setPosition(Servo.MAX_POSITION);
        }
        if (gamepad2.y) {
            left.setPosition(Servo.MIN_POSITION);
            right.setPosition(Servo.MIN_POSITION);
        }
    }

    public void moveLift() {
        if (gamepad2.a) {
            lift.setPower(1);
        }
        else if (gamepad2.b) {
            lift.setPower(-1);
        }
        else {
            lift.setPower(0);
        }
    }
}

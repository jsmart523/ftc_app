package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Same as DriverControl.java except that this one inherits from aLinearOpMode1
 */

@TeleOp(name = "Tank", group = "Linear Opmode")
public class DriverTank extends ALinearOpMode3 {
    @Override
    protected void customLoopBody() {
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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Same as DriverControl.java except that this one inherits from aLinearOpMode1
 */

@TeleOp(name = "Tank", group = "Linear Opmode")
public class DriverTank extends ALinearOpMode3 {
    public final double TRIGGER_THRESHOLD = 0.25;

    @Override
    protected void customLoopBody() {
        makeWheelsMove();
        rotateServos();
        moveLift();
        telemetry.addData("Motor Position topLeft =", topLeft.getCurrentPosition());
    }

    public void makeWheelsMove() {
        //setWheelPowers(double powerBottomLeft, double powerBottomRight, double powerTopLeft, double powerTopRight)

        if (gamepad1.left_trigger > TRIGGER_THRESHOLD) { //strafe left
            //public void setVelocity(double speedForward, double speedRight, double speedTurnRight)
            setVelocity(0, -1, 0);
/*          topLeft.setPower(-1);
            topRight.setPower(-1);
            bottomLeft.setPower(1);
            bottomRight.setPower(1);*/
        } else if (gamepad1.right_trigger > TRIGGER_THRESHOLD) { //strafe right
            //public void setVelocity(double speedForward, double speedRight, double speedTurnRight)
            setVelocity(0, 1, 0);
/*            topLeft.setPower(1);
            topRight.setPower(1);
            bottomLeft.setPower(-1);
            bottomRight.setPower(-1);*/
        } else {
            double powerRight = convertStickToPower(gamepad1.left_stick_y);
            double powerLeft = convertStickToPower(gamepad1.right_stick_y);
            setWheelPowers(powerLeft, powerRight, powerLeft, powerRight);
/*          double l = gamepad1.left_stick_y;
            double r = gamepad1.right_stick_y;
            topLeft.setPower(l);
            topRight.setPower(r);
            bottomLeft.setPower(l);
            bottomRight.setPower(r);*/
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

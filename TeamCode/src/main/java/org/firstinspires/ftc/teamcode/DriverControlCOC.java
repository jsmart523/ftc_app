package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eaganrobotics on 10/19/2017.
 */

@TeleOp(name = "DCOD", group = "Linear OpMode")
public class DriverControlCOC extends ALinearOpMode1 {
    @Override
    void customLoopBody() {
        makeWheelsMove();
        rotateServos();
        moveLift();
    }

    public double convertStickToPower(double pos) {

        // or to the power of some other number besides 2
        double powerFactor = 1;
        return Math.pow(pos, powerFactor);
    }


    public void makeWheelsMove() {
        double forward = convertStickToPower(gamepad1.left_stick_y);
        double right = convertStickToPower(gamepad1.left_stick_x);
        double turn = convertStickToPower(gamepad1.right_stick_x);
        setVelocity(forward, right, turn);

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



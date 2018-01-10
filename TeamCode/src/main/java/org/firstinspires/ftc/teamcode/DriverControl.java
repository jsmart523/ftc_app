package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eaganrobotics on 10/19/2017.
 */

@TeleOp(name = "DriverControl", group = "Linear Opmode")
@Disabled
public class DriverControl extends LinearOpMode {

    private DcMotor bottomLeft = null;
    private DcMotor bottomRight = null;
    private DcMotor topLeft = null;
    private DcMotor topRight = null;
    private DcMotor lift = null;
    private Servo left = null;
    private Servo right = null;

    @Override
    public void runOpMode() {
        bottomLeft = hardwareMap.get(DcMotor.class, "bottomLeft");
        bottomRight = hardwareMap.get(DcMotor.class, "bottomRight");
        topLeft = hardwareMap.get(DcMotor.class, "topLeft");
        topRight = hardwareMap.get(DcMotor.class, "topRight");
        lift = hardwareMap.get(DcMotor.class, "lift");
        left = hardwareMap.get(Servo.class, "left");
        right = hardwareMap.get(Servo.class, "right");

        bottomLeft.setDirection(DcMotor.Direction.FORWARD);
        topLeft.setDirection(DcMotor.Direction.FORWARD);
        bottomRight.setDirection(DcMotor.Direction.REVERSE);
        topRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            makeWheelsMove();
            rotateServos();
            moveLift();

            telemetry.update();
        }
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

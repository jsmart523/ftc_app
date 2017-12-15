package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by eaganrobotics on 11/30/2017.
 * Updated from ALinearOpMode1 by adding the gyro
 */

public abstract class ALinearOpMode2 extends LinearOpMode {
    protected DcMotor bottomLeft = null;
    protected DcMotor bottomRight = null;
    protected DcMotor topLeft = null;
    protected DcMotor topRight = null;
    protected DcMotor lift = null;
    protected Servo left = null;
    protected Servo right = null;
    protected GyroSensor gyro = null;


    double powerFactor = 3.2;

    @Override
    public void runOpMode() {
        setStatus("Initializing");
        telemetry.update();

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

        setStatus("calibrating gyro...");
        telemetry.update();
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        while(gyro.isCalibrating()) {
            sleep(50);
        }
        setStatus("calibration done");

        setStatus("Ready");
        customSetup();
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            customLoopBody();
            telemetry.update();
        }

        customTearDown();
    }


    protected void customSetup() {
    }

    abstract void customLoopBody();

    protected void customTearDown() {
    }

    public void setStatus (String status) {
        telemetry.addData("Status", status);
    }

    /*
        If desired speeds would cause any wheel to have a power greater than 1, the speeds are proportionally scaled down.
        So (0.5, 0, 0) means go forward half power, but (80, 80, 0) will cause the same behavior as (0.5, 0.5, 0)
        To strafe left, call setVelocity(0, -1, 0)
        To turn left while moving forward,
    */
    public void setVelocity(double speedForward, double speedRight, double speedTurnRight) {
        double powerTopLeft = speedForward + speedRight + speedTurnRight;
        double powerTopRight = speedForward + speedRight - speedTurnRight;
        double powerBottomLeft = speedForward - speedRight + speedTurnRight;
        double powerBottomRight = speedForward - speedRight - speedTurnRight;

        setWheelPowers(powerBottomLeft, powerBottomRight, powerTopLeft, powerTopRight);
    }

    public void setVelocity(double speedForward, double speedRight) {
        setVelocity(speedForward, speedRight, 0);
    }

    public double convertStickToPower(double pos) {

        return Math.signum(pos) * Math.pow(Math.abs(pos), powerFactor);
    }

    public void setRadialVelocity(double headingRadians, double velocity, double speedTurnRight) {
        double speedForward = 0 - Math.cos(headingRadians)* velocity;
        double speedRight   = 0 - Math.sin(headingRadians)* velocity;
        setVelocity(speedForward, speedRight, speedTurnRight);
    }

    public void setVelocity() {
        setWheelPowers(0, 0, 0, 0);
    }

    public void setWheelPowers(double powerBottomLeft, double powerBottomRight, double powerTopLeft, double powerTopRight) {
        double max = Math.max(Math.abs(powerBottomLeft), Math.abs(powerBottomRight));
        max = Math.max(max, Math.abs(powerTopLeft));
        max = Math.max(max, Math.abs(powerTopRight));
        max = Math.max(max, 1); // if not full power, don't change it all to full power. Also avoids divide-by-zero errors.
        bottomLeft.setPower(powerBottomLeft / max);
        bottomRight.setPower(powerBottomRight / max);
        topLeft.setPower(powerTopLeft / max);
        topRight.setPower(powerTopRight / max);
    }

}


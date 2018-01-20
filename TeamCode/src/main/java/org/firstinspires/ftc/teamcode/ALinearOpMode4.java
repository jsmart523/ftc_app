package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.utils.Func_GyroHeading;
import org.firstinspires.ftc.teamcode.utils.GyroGroup;
import org.firstinspires.ftc.teamcode.utils.IContainsGyro;


/**
 * Created by eaganrobotics on 11/30/2017.
 * Updated from ALinearOpMode1 by adding the gyro
 */

public abstract class ALinearOpMode4 extends LinearOpMode implements IContainsGyro {
    protected DcMotor bottomLeft = null;
    protected DcMotor bottomRight = null;
    protected DcMotor topLeft = null;
    protected DcMotor topRight = null;
    protected DcMotor lift = null;
    protected Servo left = null;
    protected Servo right = null;
    protected GyroSensor gyro = null;
    protected ModernRoboticsI2cColorSensor color = null;
    protected DcMotor arm = null;

    double powerFactor = 3.2;
    private Func_GyroHeading func_GyroHeading;

    private double m_gyroOffsetDegrees = 0;

    @Override
    public void runOpMode() {
        standardSetup();
        while (opModeIsActive()) {
            customLoopBody();
            telemetry.update();
        }

        customTearDown();
    }

    // .69, .24
    // 0, 1


    protected void customSetup() {
    }

    protected void customLoopBody() {
    }

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

    public double convertStickToPower(double x, double y) {
        return convertStickToPower(Math.hypot(x, y));
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

    // RADIAL FUNCTIONS
    public double getHeadingDegreesActual() {
        return degrees180(gyro.getHeading() + m_gyroOffsetDegrees);
    }

    public void setHeadingDegreesActual(double headingDegreesActual) {
        m_gyroOffsetDegrees = degrees180(headingDegreesActual - gyro.getHeading());
        telemetry.addData("gyroOffset", Math.round(m_gyroOffsetDegrees));
    }

    public double getHeadingRadiansActual() {
        return radians180(Math.toRadians(getHeadingDegreesActual()));
    }

    public void setHeadingRadiansActual(double headingRadiansActual) {
        setHeadingDegreesActual(Math.toDegrees(headingRadiansActual));
    }

    public double radians180(double radians) {
        if (radians > Math.PI) {
            return radians - 2 * Math.PI;
        }
        else if (radians < 0 - Math.PI) {
            return radians + 2 * Math.PI;
        } else return radians;
    }

    public double degrees180(double degrees) {
        if (degrees > 180) {
            return degrees - 360;
        }
        else if (degrees < 0 - 180) {
            return degrees + 360;
        }
        else return degrees;
    }

    // given the stick_x and stick_y, get the angle, with zero being "forward", PI/2 being "left", PI being "backwards", -PI/2 being "right"
    // returns value between -PI and PI
    public double getRadiansFromStickValues(double x, double y) {
        return radians180(Math.atan2(-x, -y));
    }

    public void standardSetup() {
        setStatus("Initializing");
        telemetry.update();

        bottomLeft = hardwareMap.get(DcMotor.class, "bottomLeft");
        bottomRight = hardwareMap.get(DcMotor.class, "bottomRight");
        topLeft = hardwareMap.get(DcMotor.class, "topLeft");
        topRight = hardwareMap.get(DcMotor.class, "topRight");
        lift = hardwareMap.get(DcMotor.class, "lift");
        left = hardwareMap.get(Servo.class, "left");
        right = hardwareMap.get(Servo.class, "right");
        arm = hardwareMap.get(DcMotor.class, "arm");

        bottomLeft.setDirection(DcMotor.Direction.FORWARD);
        topLeft.setDirection(DcMotor.Direction.FORWARD);
        bottomRight.setDirection(DcMotor.Direction.REVERSE);
        topRight.setDirection(DcMotor.Direction.REVERSE);


        setStatus("calibrating gyro...");
        telemetry.update();

        GyroSensor gyroArray[] = {hardwareMap.gyroSensor.get("gyro"), hardwareMap.gyroSensor.get("gyro2")};
        gyro = new GyroGroup(gyroArray);
        func_GyroHeading = new Func_GyroHeading(this);
        gyro.calibrate();
        while(gyro.isCalibrating()) {
            sleep(50);
        }

        color = hardwareMap.get(ModernRoboticsI2cColorSensor.class, "color");

        setStatus("Ready");
        customSetup();
        telemetry.update();
        waitForStart();
        telemetry.addData("heading", new Func_GyroHeading(this));
        telemetry.addLine()
                .addData("left", left.getPosition())
                .addData("right", right.getPosition());
        left.setPosition(1);
        right.setPosition(0);
    }

    /**
     * This is not the current velocity of the robot, it's how hard the robot *should* be turning in order to get to the desired heading, as compared to this.getHeadingActual()
     * Example:
     *      double headingRadiansDesired = Math.PI;
     *      double w;
     *      while (w = getTurnVelocity(headingRadiansDesired)) != 0) {
     *          setVelocity(0, 0, w); // zero forward, zero strafe, w angular velocity
     *      }
     *
     * @param headingRadiansDesired
     * @return double between -1 and 1; which way and how hard the robot should turn towards the desired heading, compared to the actual heading of the robot.
     */
    public double getTurnVelocity(double headingRadiansDesired) {
        final double minTurnThresholdRadians = Math.PI/180; // 1 degrees
        final double fullTurnThresholdRadians = Math.PI/3; // 60 degrees

        double headingRadiansCorrection = radians180(headingRadiansDesired - getHeadingRadiansActual());
        double sign = 0 - Math.signum(headingRadiansCorrection);
        double absRadiansCorrection = Math.abs(headingRadiansCorrection);
        double ret;

        if (absRadiansCorrection < minTurnThresholdRadians) {
            ret = 0;
        }
        else if (absRadiansCorrection > fullTurnThresholdRadians) {
            ret = sign;
        }
        else {
            ret = sign * (absRadiansCorrection / fullTurnThresholdRadians);
        }
        telemetry.addLine()
                .addData("hd", Math.round(Math.toDegrees(headingRadiansDesired)))
                .addData("hc", Math.round(Math.toDegrees(headingRadiansCorrection)))
                .addData("hac", Math.round(Math.toDegrees(absRadiansCorrection)))
                .addData("t", ret);
        return ret;
    }
}


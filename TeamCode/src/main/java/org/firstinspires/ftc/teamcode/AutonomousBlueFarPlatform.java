package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Awos on 1/11/2018.
 */
@Autonomous(name = "AutonomousBlueFarPlatform", group = "Linear OpMode")
public class AutonomousBlueFarPlatform extends ALinearOpMode4 {
    ElapsedTime time = new ElapsedTime();
    private final double rps = 2.53;
    private final double gearRPS = rps * (1.5 / 2);
    private final double movementPerRevolution = (2 * 2 * Math.PI); //inches (radius * 2PI)
    private final double movementPerSecond = gearRPS * movementPerRevolution; //inches per second

    protected int target;
    protected double speed;

    //10 inches goes 7.8125 inches

    @Override
    public void runOpMode() {
        super.standardSetup();

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rotateServosOut();
        rotateServosIn();
        manipulateLift(1);
        setVelocity(0, -.3, 0);
        time.reset();
        while (time.time() < .3) {
            telemetry.update();
        }
        setVelocity(0, 0, 0);

        manipulateArm(false);

        time.reset();
        while (time.time() < 2) {
            telemetry.update();
        }

        boolean color = determineColor();

        time.reset();
        while (time.time() < 1) {
            telemetry.update();
        }

        if (color) {
            drive(3, .5);

            time.reset();
            while (time.time() < 2) {
                telemetry.update();
            }

            manipulateArm(true);

            time.reset();
            while (time.time() < 2) {
                telemetry.update();
            }

            drive(3, -.5);
        }
        else if (!color) {
            drive(3, -.5);

            time.reset();
            while (time.time() < 2) {
                telemetry.update();
            }

            manipulateArm(true);

            time.reset();
            while (time.time() < 2) {
                telemetry.update();
            }

            drive(3, .5);
        }

        time.reset();
        while (time.time() < 2) {
            telemetry.update();
        }

        drive(28, 1);

        time.reset();
        while (time.time() < 2) {
            telemetry.update();
        }

        turn(2, .2);

        time.reset();
        while (time.time() < 1) {
            telemetry.update();
        }

        drive(11, 1);

        time.reset();
        while (time.time() < 1) {
            telemetry.update();
        }

        turn(1.8, -.2);

        time.reset();
        while (time.time() < 1) {
            telemetry.update();
        }

        drive(3, .15);

        rotateServosOut();

        drive(1.5, -.15);
    }

    public void drive(double length, double forwardsBackwards) {
        //1 = forwards
        //-1 = backwards
        double secondsOfMovement = (length * (10 / 7.8125)) / movementPerSecond;
        secondsOfMovement = secondsOfMovement / Math.abs(forwardsBackwards);
        time.reset();
        while (time.time() < secondsOfMovement && opModeIsActive()) {
            bottomLeft.setPower(-forwardsBackwards);
            topLeft.setPower(-forwardsBackwards);
            bottomRight.setPower(-forwardsBackwards);
            topRight.setPower(-forwardsBackwards);
            telemetry.update();
        }
        bottomLeft.setPower(0);
        topLeft.setPower(0);
        bottomRight.setPower(0);
        topRight.setPower(0);
        telemetry.update();
    }

    public void turnWithGyro(double headingDegreesDesired) { //degrees
        double turnPower = 0;
        while (opModeIsActive() && !((headingDegreesDesired - gyro.getHeading()) < 2)) {
            double actualHeading = gyro.getHeading();
            if (actualHeading < 180) {
                actualHeading += 360;
            }
            double turnAmount = headingDegreesDesired - actualHeading;
            if (Math.abs(turnAmount) >= 180) {
                if (turnAmount >= 60) {
                    turnPower = .5;
                }
                else if (turnAmount < 60) {
                    turnPower = .3;
                }
                else if (turnAmount < 30) {
                    turnPower = .2;
                }
            }
            else {
                if (turnAmount >= 60) {
                    turnPower = -.5;
                }
                else if (turnAmount < 60) {
                    turnPower = -.3;
                }
                else if (turnAmount < 30) {
                    turnPower = -.2;
                }
            }
            bottomLeft.setPower(turnPower);
            topLeft.setPower(turnPower);
            bottomRight.setPower(-turnPower);
            topRight.setPower(-turnPower);
        }
        bottomLeft.setPower(0);
        topLeft.setPower(0);
        bottomRight.setPower(0);
        topRight.setPower(0);
    }

    public void turn(double seconds, double turnPower) {
        time.reset();
        while(time.time() < seconds) {
            bottomLeft.setPower(turnPower);
            topLeft.setPower(turnPower);
            bottomRight.setPower(-turnPower);
            topRight.setPower(-turnPower);
        }
        bottomLeft.setPower(0);
        topLeft.setPower(0);
        bottomRight.setPower(0);
        topRight.setPower(0);
    }

    public void manipulateArm(boolean a) {
        //false = goes down
        //true = goes up
        time.reset();
        while (time.time() < 1) {
            speed = 0;
            if (a) {
                target = 0;
            } else if (!a) {
                target = 455;
            }
            if (Math.abs(target - arm.getTargetPosition()) < 100) {
                speed = .1;
            } else {
                speed = .6;
            }

            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setPower(speed);
            arm.setTargetPosition(target);
        }
    }

    public boolean determineColor() {
        color.enableLed(true);

        if (color.blue() > color.red() && color.blue()> color.green()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void rotateServosOut() {
        left.setPosition(0);
        right.setPosition(1);
    }

    public void rotateServosIn() {
        left.setPosition(.69);
        right.setPosition(.24);
    }

    public void manipulateLift(int speed) {
         lift.setPower(speed);
        time.reset();
        while (time.time() < 1) {
            telemetry.update();
        }
        lift.setPower(0);
    }
}
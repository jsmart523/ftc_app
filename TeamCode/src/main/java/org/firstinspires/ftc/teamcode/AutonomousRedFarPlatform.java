package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ALinearOpMode3;

/**
 * Created by John on 1/11/2018.
 */
@Autonomous(name = "AutonomousRedFarPlatform", group = "Linear OpMode")
public class AutonomousRedFarPlatform extends ALinearOpMode4 {
    ElapsedTime time = new ElapsedTime();
    private final double rps = 2.53;
    private final double gearRPS = rps * (1.5 / 2);
    private final double movementPerRevolution = (2 * 2 * Math.PI); //inches (radius * 2PI)
    private final double movementPerSecond = gearRPS * movementPerRevolution; //inches per second

    //10 inches goes 7.8125 inches

    @Override
    public void runOpMode() {
        super.standardSetup();


        drive(10, 1);
        //manipulateArm(1);
        boolean color = determineColor();
        if (color) {
            drive(.8, 1);
            //manipulateArm(-1);
            drive(.8, -1);
        }
        else if (!color) {
            drive(.8, -1);
            //manipulateArm(-1);
            drive(.8, 1);
        }
        //manipulateArm(-1);
        turn(Math.PI);
    }

    public void drive(double length, int forwardsBackwards) {
        //1 = forwards
        //-1 = backwards
        double secondsOfMovement = (length * (10 / 7.8125)) / movementPerSecond;

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

    public void turn(double headingRadiansDesired) { //radians
        double v = 0;
        v = getTurnVelocity(headingRadiansDesired);
        while(v != 0) {
            setVelocity(0, 0, v);
            v = getTurnVelocity(headingRadiansDesired);
        }
    }

    public void manipulateArm(int direction) {
        //1 = goes down
        //-1 = goes up
        time.reset();
        while (time.time() < 1) {
            arm.setPower(direction);
            telemetry.update();
        }
        arm.setPower(0);
        telemetry.update();
    }

    public boolean determineColor() {
        color.enableLed(true);

        if (color.red() > color.blue() && color.red() > color.green()) {
            return true;
        }
        else {
            return false;
        }
    }
}
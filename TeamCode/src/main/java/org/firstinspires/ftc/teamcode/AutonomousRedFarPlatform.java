package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ALinearOpMode3;

/**
 * Created by John on 1/11/2018.
 */
@Autonomous(name = "AutonomousRedFarPlatform", group = "Linear OpMode")
public class AutonomousRedFarPlatform extends ALinearOpMode3 {
    ElapsedTime time = new ElapsedTime();
    private final double rps = 2.53;
    private final double gearRPS = 1.8975;
    private final double movementPerRevolution = (4 * Math.PI); //inches
    private final double movementPerSecond = gearRPS * movementPerRevolution * 1.46788990826; //inches per second lol

    //10 inches goes 6.8125 inches

    @Override
    public void runOpMode() {
        super.standardSetup();

        drive(10, 1);
    }

    public void drive(double length, int forwardsBackwards) {
        //1 = forwards
        //-1 = backwards
        double secondsOfMovement = length / movementPerSecond;

        time.reset();
        while (time.time() < secondsOfMovement && opModeIsActive()) {
            bottomLeft.setPower(-forwardsBackwards);
            topLeft.setPower(-forwardsBackwards);
            bottomRight.setPower(-forwardsBackwards);
            topRight.setPower(-forwardsBackwards);
        }
        bottomLeft.setPower(0);
        topLeft.setPower(0);
        bottomRight.setPower(0);
        topRight.setPower(0);
    }

    public void turn() {

    }


}
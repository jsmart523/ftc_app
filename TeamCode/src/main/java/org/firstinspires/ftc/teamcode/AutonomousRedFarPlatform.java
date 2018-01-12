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

    @Override
    public void runOpMode() {
        super.standardSetup();

        time.reset();
        while(time.seconds() < 300) {
            telemetry.addLine()
                    .addData("Time Elapsed", time.seconds());
            telemetry.update();
        }
    }
}

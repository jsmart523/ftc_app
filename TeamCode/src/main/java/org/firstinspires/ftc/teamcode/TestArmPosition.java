package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by John on 1/20/2018.
 */

@TeleOp(name = "TestArmPosition", group = "Linear Opmode")
public class TestArmPosition extends ALinearOpMode4 {
    @Override
    protected void customLoopBody() {
        telemetry.addData("Arm Encoder", arm.getCurrentPosition());
    }


}

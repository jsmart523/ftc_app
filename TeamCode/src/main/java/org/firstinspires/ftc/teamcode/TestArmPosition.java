package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by John on 1/20/2018.
 */

@TeleOp(name = "TestArmPosition", group = "Linear Opmode")
public class TestArmPosition extends ALinearOpMode4 {
    @Override
    protected void customSetup() {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(0.3);
    }

    @Override
    protected void customLoopBody() {
        telemetry.addData("Arm Encoder", arm.getCurrentPosition());
        if ()
    }

    protected static final int ARM_ENC_UP = 50;
    protected static final int ARM_ENC_DOWN = 100;



}

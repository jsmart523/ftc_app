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
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    protected void customLoopBody() {
        while (opModeIsActive()) {
            moveArm();
        }
    }

    public void moveArm() {
        if (gamepad1.a) {
            arm.setPower(.3);
            arm.setTargetPosition(413);
        }
        else if (gamepad1.b) {
            arm.setPower(.3);
            arm.setTargetPosition(0);
        }
    }

    protected static final int ARM_ENC_UP = 50;
    protected static final int ARM_ENC_DOWN = 100;



}

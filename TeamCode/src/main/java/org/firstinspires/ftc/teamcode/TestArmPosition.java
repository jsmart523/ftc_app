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
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    protected void customLoopBody() {
        while (opModeIsActive()) {
            motorTest();
        }
    }

    public void moveArm() {
        speed = 0;
        if (gamepad1.a) {
            target = 0;
        }
        else if (gamepad1.b) {
            target = 400;
        }
        if (Math.abs(target - arm.getTargetPosition()) < 100) {
            speed = .1;
        }
        else {
            speed = .6;
        }

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(speed);
        arm.setTargetPosition(target);
    }

    protected static final int ARM_ENC_UP = 50;
    protected static final int ARM_ENC_DOWN = 100;
    protected int target;
    protected double speed;

    public void motorTest() {
        arm.setPower(.5);
    }


}

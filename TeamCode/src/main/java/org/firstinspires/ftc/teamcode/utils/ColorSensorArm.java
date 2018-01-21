package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by John on 1/20/2018.
 *
 * public class TestArmPosition extends ALinearOpMode4 {
 ColorSensorArm colorSensorArm;

 @Override
 protected void customSetup() {
 colorSensorArm = new ColorSensorArm(arm);
 }

 @Override
 protected void customLoopBody() {
 telemetry.addData("Arm Encoder", arm.getCurrentPosition());
 if (gamepad1.a) {
 colorSensorArm.up();
 }
 else if (gamepad1.b) {
 colorSensorArm.down();
 }
 }
 }
 */

public class ColorSensorArm {
    private static final int ARM_ENC_UP = 0;
    private static final int ARM_ENC_DOWN = 413;
    private static final double ARM_POWER = 0.3;

    private DcMotor m_arm;

    public ColorSensorArm(DcMotor arm) {
        m_arm = arm;
    }

    public void moveArm(boolean a) {
        m_arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        double speed = 0;
        int target = 0;
        if (a) {
            target = 0;
        }
        else if (!a) {
            target = 400;
        }
        if (Math.abs(target - m_arm.getTargetPosition()) < 100) {
            speed = .1;
        }
        else {
            speed = .6;
        }

        m_arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_arm.setPower(speed);
        m_arm.setTargetPosition(target);
    }
}

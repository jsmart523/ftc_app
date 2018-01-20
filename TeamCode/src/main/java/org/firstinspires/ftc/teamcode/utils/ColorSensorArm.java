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
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(ARM_POWER);
        up();
    }

    public void up() {
        m_arm.setTargetPosition(ARM_ENC_UP);
    }

    public void down() {
        m_arm.setTargetPosition(ARM_ENC_DOWN);
    }

    public boolean isBusy() {
        return m_arm.isBusy();
    }
}

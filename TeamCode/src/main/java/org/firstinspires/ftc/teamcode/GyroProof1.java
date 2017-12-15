package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by John on 12/7/2017.
 */

@TeleOp(name = "GyroTest1", group = "Linear OpMode")
public class GyroProof1 extends ALinearOpMode1 {
    protected GyroSensor gyro = null;
    boolean apressed = false;

    @Override
    protected void customSetup() {
        setStatus("calibrating gyro...");
        telemetry.update();
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
        while(gyro.isCalibrating()) {
            sleep(50);
        }
        setStatus("calibration done");
    }

    @Override
    void customLoopBody() {
        if (gamepad1.a) {
            apressed = true;
        }

        if (apressed) {


            telemetry.addLine()
                    .addData("r", gyro.getHeading())
                    .addData("gstatus", gyro.status());
        } else {
            setStatus("press a to continue");
        }
    }
}

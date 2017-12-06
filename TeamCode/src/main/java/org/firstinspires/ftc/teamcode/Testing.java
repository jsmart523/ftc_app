package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ALinearOpMode1;

/**
 * For diagnostics
 */

@TeleOp(name = "Testing", group = "Linear Opmode")
public class Testing extends ALinearOpMode1 {
    private int testStep = 0;


    @Override
    protected void customSetup() {
        setStatus("PUT ON BLOCKS! Press A to continue");
        telemetry.update();
    }

    @Override
    void customLoopBody() {
        boolean wasPressed = false;

        if(gamepad1.a) {
            if (!wasPressed) {
                // button wasn't pressed and now it is
                wasPressed = true;
                if (++testStep == 1) {
                    setStatus("bottomLeft forward");
                    setWheelPowers(1, 0, 0, 0);
                }
                else if (testStep == 2) {
                    setStatus("bottomRight forward");
                    setWheelPowers(0, 1, 0, 0);
                }
                else if (testStep == 3) {
                    setStatus("topLeft forward");
                    setWheelPowers(0, 0, 1, 0);
                }
                else if (testStep == 4) {
                    setStatus("topRight forward");
                    setWheelPowers(0, 0, 0, 1);
                }
                else if (testStep == 5) {
                    setStatus("full forward");
                    setVelocity(1, 0);
                }
                else if (testStep == 6) {
                    setStatus("strafe right");
                    setVelocity(0, 1);
                }
                else if (testStep == 7) {
                    setStatus("spin right");
                    setVelocity(0, 0, 1);
                }
                else if (testStep == 8) {
                    setStatus("forward-right");
                    setVelocity(1, 1);
                }
                else {
                    setStatus("No more tests. Will start over on next press.");
                    testStep = 0;
                }
            }
        }
        else if (wasPressed) {
            // was pressed, and we just let go
            setStatus("Stopped. Press A for next test.");
            stop();
            wasPressed = false;
        }
    }
}

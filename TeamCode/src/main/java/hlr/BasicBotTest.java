package hlr;//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by JSmart523 for 10/28/2017.
 * Test 2!
 */
@TeleOp(name="hlr.BasicBotTest", group="Linear Opmode")
public class BasicBotTest extends LinearOpMode {
    Robot1 robot = null;
    TouchSensor touch = null;
    DcMotor left = null;
    DcMotor right = null;

    @Override
    public void runOpMode() {
        setStatus("Initializing...");
        robot = new Robot1(this);

        setStatus("Initialized. Waiting for start...");
        telemetry.update();
        boolean wasPressed = false;
        robot.stop();
        int testStep = 0;
        setStatus("PUT ON BLOCKS! Press A to continue");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.a) {
                if (!wasPressed) {
                    // button wasn't pressed and now it is
                    wasPressed = true;
                    if (++testStep == 1) {
                        setStatus("motorBL forward");
                        robot.setWheelPowers(1, 0, 0, 0);
                    }
                    else if (testStep == 2) {
                        setStatus("motorBR forward");
                        robot.setWheelPowers(0, 1, 0, 0);
                    }
                    else if (testStep == 3) {
                        setStatus("motorBR forward");
                        robot.setWheelPowers(0, 0, 1, 0);
                    }
                    else if (testStep == 4) {
                        setStatus("motorBR forward");
                        robot.setWheelPowers(0, 0, 0, 1);
                    }
                    else if (testStep == 5) {
                        setStatus("speed forward");
                        robot.setVelocity(1, 0);
                    }
                    else if (testStep == 6) {
                        setStatus("speed right");
                        robot.setVelocity(0, 1);
                    }
                    else if (testStep == 7) {
                        setStatus("turn right");
                        robot.setVelocity(0, 0, 1);
                    }
                    else if (testStep == 8) {
                        setStatus("forward-right");
                        robot.setVelocity(1, 1);
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
                robot.stop();
                wasPressed = false;
            }
            telemetry.update();
        }
    }

    public void setStatus (String status) {
        telemetry.addData("Testing", status);
    }
}

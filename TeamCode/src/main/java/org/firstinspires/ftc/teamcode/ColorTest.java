package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * From DriverGyro
 * Same, but implementing the color sensor
 */

@TeleOp(name = "ColorTest", group = "Linear OpMode")
public class ColorTest extends ALinearOpMode3 {

    @Override
    protected void customSetup() {
        color.enableLed(true);
    }

    @Override
    protected void customLoopBody() {
        color.writeCommand(ModernRoboticsI2cColorSensor.Command.ACTIVE_LED);
        telemetry.addLine()
                .addData("r",color.red())
                .addData("g",color.green())
                .addData("b",color.blue());

        telemetry.addLine()
                .addData("alpha", color.alpha())
                .addData("argb", color.argb());

        telemetry.addData("colorObject", colorOfObject());
    }

    private String colorOfObject() {
        String ret = null;
        int red = color.red(), blue = color.blue(), green = color.green();
        if (red > blue && red > green) {
            ret = "red";
        }
        else if (blue > green && blue > red) {
            ret = "blue";
        }
        else {
            ret = "green";
        }
        return ret;
    }

}



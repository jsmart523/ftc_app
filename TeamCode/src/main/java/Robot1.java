import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by JSmart523 on 10/28/2017.
 * This object encapsulates the robot itself.
 */

public class Robot1 {
    private DcMotor     motorBL     = null;
    private DcMotor     motorBR     = null;
    private DcMotor     motorFL     = null;
    private DcMotor     motorFR     = null;
    private DcMotor     motorLift   = null;
    private OpMode      opMode      = null;
    private Servo       servoL      = null;
    private Servo       servoR      = null;
    
    public Robot1(OpMode opModeArg) {
        opMode = opModeArg;
        HardwareMap hardwareMap = opMode.hardwareMap;
        motorBL     = hardwareMap.dcMotor.get("??");
        motorBR     = hardwareMap.dcMotor.get("??");
        motorFL     = hardwareMap.dcMotor.get("??");
        motorFR     = hardwareMap.dcMotor.get("??");
        motorLift   = hardwareMap.dcMotor.get("??");
        servoL      = hardwareMap.servo.get("??");
        servoR      = hardwareMap.servo.get("??");
    }


    public void setVelocity(double speedForward, double speedRight) {
        setVelocity(speedForward, speedRight, 0);
    }

/*
    If desired speeds would cause any wheel to have a power greater than 1, the speeds are proportionally scaled down.
    So (0.5, 0, 0) means go forward half power, but (80, 80, 0) will cause the same behavior as (0.5, 0.5, 0)
    To strafe left, call setVelocity(0, -1, 0)
    To turn left while moving forward,
*/
    public void setVelocity(double speedForward, double speedRight, double speedTurnRight) {
        double powerFL = speedForward + speedRight + speedTurnRight;
        double powerFR = speedForward + speedRight - speedTurnRight;
        double powerBL = speedForward - speedRight + speedTurnRight;
        double powerBR = speedForward - speedRight - speedTurnRight;

        setWheelPowers(powerBL, powerBR, powerFL, powerFR);
    }
    
    public void setWheelPowers(double powerBL, double powerBR, double powerFL, double powerFR) {
        double max = Math.max(Math.abs(powerBL), Math.abs(powerBR));
        max = Math.max(max, Math.abs(powerFL));
        max = Math.max(max, Math.abs(powerFR));
        max = Math.max(max, 1); // if not full power, don't change it all to full power. Also avoids divide-by-zero errors.
        motorBL.setPower(powerBL / max);
        motorBR.setPower(powerBR / max);
        motorFL.setPower(powerFL / max);
        motorFR.setPower(powerFR / max);
    }

    public void stop() {
        setWheelPowers(0, 0, 0, 0);
    }
}

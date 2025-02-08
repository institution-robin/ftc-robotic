import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class MyFIRSTJavaOpMode extends LinearOpMode {
    private Gyroscope imu;
    private DcMotor motorLeft;      // propu gauche
    private DcMotor motorRight;     // propu droite
    private DcMotor motor2;         // bras
    private DcMotor motor3;         // avant-bras
    private Servo pince;            // pince
    private Servo rouleau;          // rouleau
    private DigitalChannel digitalTouch;
    private DistanceSensor sensorColorRange;

    @Override
    public void runOpMode() {
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        motorLeft = hardwareMap.get(DcMotor.class, "motor0");
        motorRight = hardwareMap.get(DcMotor.class, "motor1");
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor3 = hardwareMap.get(DcMotor.class, "motor3");
        //digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        //sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        rouleau = hardwareMap.get(Servo.class, "servo0");
        pince = hardwareMap.get(Servo.class, "servo1");


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        double tgtPowerLeft = 0;
        double tgtPowerRight = 0;
        double tgtPower2 = 0;
        double tgtPower3 = 0;
        double tgtTurnLeft = 0;
        double tgtTurnRight = 0;
        while (opModeIsActive()) {
            motorRight.setDirection(DcMotor.Direction.REVERSE);

            tgtPowerLeft = -this.gamepad1.left_stick_y;
            tgtPowerRight = -this.gamepad1.left_stick_y;
            tgtTurnLeft = -this.gamepad1.left_stick_x;
            tgtTurnRight = this.gamepad1.left_stick_x;
            tgtPower2 = -this.gamepad1.right_stick_y;
            tgtPower3 = -this.gamepad1.right_stick_x;

            motorLeft.setPower(tgtPowerLeft-tgtTurnLeft);
            motorRight.setPower(tgtPowerRight-tgtTurnRight);
            motor2.setPower(tgtPower2);
            motor3.setPower(tgtPower3);
            
            if(gamepad1.y) {
                // move to 0 degrees.
                pince.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                // move to 90 degrees.
                pince.setPosition(0.5);
            } else if (gamepad1.a) {
                // move to 180 degrees.
                pince.setPosition(1);
            }

            if(gamepad1.dpad_up) {
                // move to 0 degrees.
                rouleau.setPosition(0);
            } else if (gamepad1.dpad_left || gamepad1.dpad_right) {
                // move to 90 degrees.
                rouleau.setPosition(0.5);
            } else if (gamepad1.dpad_down) {
                // move to 180 degrees.
                rouleau.setPosition(1);
            }

            telemetry.addData("Rouleau Position", rouleau.getPosition());
            telemetry.addData("Pince Position", pince.getPosition());
            telemetry.addData("Puissance théorique motorLeft", tgtPowerLeft);
            telemetry.addData("Puissance motorLeft", motorLeft.getPower());
            telemetry.addData("Puissance théorique motorRight", tgtPowerRight);
            telemetry.addData("Puissance motorRight", motorRight.getPower());
            telemetry.addData("Puissance théorique Bras", tgtPower2);
            telemetry.addData("Puissance théorique Avant-Bras", tgtPower3);
            telemetry.addData("Puissance Bras", motor2.getPower());
            telemetry.addData("Puissance Avant-Bras", motor3.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }

    }
}

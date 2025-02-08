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
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor bras;
    private DcMotor avantBras;
    private Servo pince;
    private Servo rouleau;
    private DigitalChannel digitalTouch;
    private DistanceSensor sensorColorRange;

    @Override
    public void runOpMode() {
        //imu = hardwareMap.get(Gyroscope.class, "imu");
        //digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");
        //sensorColorRange = hardwareMap.get(DistanceSensor.class, "sensorColorRange");
        motorLeft = hardwareMap.get(DcMotor.class, "motor0");
        motorRight = hardwareMap.get(DcMotor.class, "motor1");
        bras = hardwareMap.get(DcMotor.class, "motor2");
        avantBras = hardwareMap.get(DcMotor.class, "motor3");
        rouleau = hardwareMap.get(Servo.class, "servo0");
        pince = hardwareMap.get(Servo.class, "servo1");

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        double tgtPowerLeft = 0;
        double tgtPowerRight = 0;
        double tgtPower2 = 0;
        double tgtPower3 = 0;
        double tgtTurnLeft = 0;
        double tgtTurnRight = 0;

        // run until the end of the match (driver presses STOP)
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
            bras.setPower(tgtPower2);
            avantBras.setPower(tgtPower3);
            
            if(gamepad1.y) {
                // ouvert
                pince.setPosition(0);
            } else if (gamepad1.x || gamepad1.b) {
                // semi-fermé
                pince.setPosition(0.5);
            } else if (gamepad1.a) {
                // fermé
                pince.setPosition(1);
            }

            if(gamepad1.dpad_up) {
                // reployé
                rouleau.setPosition(0);
            } else if (gamepad1.dpad_left || gamepad1.dpad_right) {
                // semi-déployé
                rouleau.setPosition(0.5);
            } else if (gamepad1.dpad_down) {
                // déployé
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
            telemetry.addData("Puissance Bras", bras.getPower());
            telemetry.addData("Puissance Avant-Bras", avantBras.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();

        }

    }
}

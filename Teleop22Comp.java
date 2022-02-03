// THIS IS THE NEWEST CODE

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.Servo;
//HERE WE INITIALIZE OUR VARIABLES AND MOTORS
@TeleOp(name ="TeleOp22", group = "TeleOP")
public class Teleop22Comp extends OpMode {
    DcMotor frontRight;
    DcMotor frontLeft;
    DcMotor backRight;
    DcMotor backLeft;
    DcMotor raiseArm1;
    DcMotor raiseArm2;
    DcMotor extendArm;
    Servo claw1;
    Servo claw2;
    DcMotor intake;
    Servo wrist;
    boolean powerControl = false;
    double powerGiven =0;
    boolean clamp = false;
    int powerButton;
    CRServo drag1, drag2;

    double armPowerMultiplier = 0.5;


    public void init() {
        // Here we construct our hardware map for the phone --> lets the phone identify which variables are which

        //touchSense = hardwareMap.get(DigitalChannel.class, "sensor_digital");
        frontRight = hardwareMap.dcMotor.get("front right");
        frontLeft = hardwareMap.dcMotor.get("front left");
        backRight = hardwareMap.dcMotor.get("back right");
        backLeft = hardwareMap.dcMotor.get("back left");
        raiseArm1 = hardwareMap.dcMotor.get("raise arm");
        //raiseArm2 = hardwareMap.dcMotor.get("raise arm 2");
        extendArm = hardwareMap.dcMotor.get("extend arm");
        claw1 = hardwareMap.servo.get("claw 1");
        claw2 = hardwareMap.servo.get("claw 2");
        //drag1 = hardwareMap.crservo.get("drag front");
        //drag2 = hardwareMap.crservo.get("drag back");
        wrist = hardwareMap.servo.get("wrist");
        intake = hardwareMap.dcMotor.get("intake");

    }

    //sets power for our arm
    private void setRaiseArmPower(double armPower, double multiplier){
        raiseArm1.setPower(armPower*multiplier);
        //raiseArm2.setPower(armPower*multiplier);
        return;
    }

    public void loop() {
        //              -----STICK VARIABLES-----
        // For driving
        float move = -gamepad1.left_stick_y;
        float crabWalk = gamepad1.left_stick_x;
        float rotation = -gamepad1.right_stick_x;

        // For arm raising
        float rawRaiseValue = -gamepad2.left_stick_y;




        //              -----WHEEL LOGIC-----

        // Wheels: Holonomic drive formula uses values of gamestick position to move
        double fLeftPower = Range.clip(move + rotation + crabWalk, -1.0, 1.0);
        double bLeftPower = Range.clip(move + rotation - crabWalk, -1.0, 1.0);
        double fRightPower = Range.clip(move - rotation - crabWalk, -1.0, 1.0);
        double bRightPower = Range.clip(move - rotation + crabWalk, -1.0, 1.0);
        // Assignment of motor power in relation to wheels
        frontLeft.setPower(fLeftPower/powerButton);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeft.setPower(bLeftPower/powerButton);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight.setPower(fRightPower/powerButton);

        backRight.setPower(bRightPower/powerButton);

        raiseArm1.setDirection(DcMotorSimple.Direction.FORWARD);

        //raiseArm2.setDirection(DcMotorSimple.Direction.REVERSE);




        //          -----GAME PAD 1-----

        //              ###SPEED BOOST###
        if (gamepad1.right_trigger > 0.1)
            powerButton = 1;
        else
            powerButton = 2;




        //          -----GAME PAD 2-----

        //              ###CLAMPS###

        if (gamepad2.y) {

            intake.setPower(1);
        }

        else if (gamepad2.x) {

            intake.setPower(-1);
        }
        else{
            intake.setPower(0);
        }

        // claw1: 1=open, 0=closed
        // claw2: 0=open, 1=closed

        // Open
        /*
        if (gamepad2.y) {
            claw1.setPosition(0.6);
            claw2.setPosition(0.4);
        }
        // Close
        else if (gamepad2.x) {
            claw1.setPosition(0.4);
            claw2.setPosition(0.6);
        }
*/

        //              ###ARM EXTENSION###
        extendArm.setPower(-gamepad2.right_stick_y);


        //              ###WRIST###
        if (gamepad2.right_bumper){
            wrist.setPosition(0.65);
        }
        else if (gamepad2.left_bumper){
            wrist.setPosition(0.25);
        }
        else {
            wrist.setPosition(0.0);
        }


        //              ###ARM RAISING###

        // Fast raise arm mode
        if (gamepad2.right_trigger > 0) {
            // If the driver is trying to move the arm up:
            if (rawRaiseValue > 0) {
                setRaiseArmPower(rawRaiseValue, 0.6);
            }
            // If the driver is trying to move the arm down:
            else if (rawRaiseValue < 0) {
                setRaiseArmPower(-1.0, 0.35);
            }
            // If the driver is not moving the arm:
            else{
                setRaiseArmPower(0.0, 1);
            }
        }
        // Slow raise arm mode
        else {
            // If the driver is trying to move the arm up:
            if (rawRaiseValue > 0) {
                setRaiseArmPower(rawRaiseValue, 0.35);
            }
            // If the driver is trying to move the arm down:
            else if (rawRaiseValue < 0) {
                setRaiseArmPower(-0.25, 1);  // Maybe change armPower
            }
            // If the driver is not moving the arm:
            else {
                setRaiseArmPower(0.0, 1);
            }
        }


    }


}

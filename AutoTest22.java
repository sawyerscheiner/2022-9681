package org.firstinspires.ftc.teamcode;
//THIS IS FOR THE BLUE SIDE
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import java.util.ArrayList;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
/*
Parallel Lions 9681 Autonomous Blue Code
author: 9681 Software
GOALS: Place the wobble goal in the zone and put rings in lowest goal
DESCRIPTION: This code is used for our autonomous when we are located on the blue side
 */
@Autonomous(name="AutoTest22", group="Iterative Opmode")
public class AutoTest22 extends OpMode
{
    /*
    ---MOTORS---
     */
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack; //make sure these are the right motors
    DcMotor extendArm;
    Servo claw1;
    Servo claw2;
    DcMotor raiseArm2;
    DcMotor raiseArmMotor;
    DcMotor intake;
    ArrayList<DcMotor> motors = new ArrayList<DcMotor>();
    ArrayList<CRServo> servos = new ArrayList<CRServo>();
    private StateMachine machine;
    driveState strafeLeft;
    driveState forwards1;
    driveState turnLeft;
    extendArmState raiseArm1;
    extendArmState extendFirst;
    intakeState deposit; //new
    extendArmState extendBack;
    driveState getMoveBackwards1;
    driveState turnLeft2;
    driveState forward2;
    //CRServoState open1;
    driveState moveBackwards1;
    public void init() {
        /*
        ---HARDWARE MAP---
         */
        rightFront=hardwareMap.dcMotor.get("front right");
        leftFront = hardwareMap.dcMotor.get("front left");
        rightBack = hardwareMap.dcMotor.get("back right");
        leftBack = hardwareMap.dcMotor.get("back left");
        raiseArmMotor = hardwareMap.dcMotor.get("raise arm");
        extendArm = hardwareMap.dcMotor.get("extend arm");
        intake = hardwareMap.dcMotor.get("intake");

        claw1 = hardwareMap.servo.get("claw 1");
        claw2 = hardwareMap.servo.get("claw 2");
        //claw1 = hardwareMap.crservo.get("claw 1");
        //claw2 = hardwareMap.crservo.get("claw 2");
        //get the CRSERVO


        /*
        ---MOTOR DIRECTIONS---
         */
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        /*
        ---GROUPING---
         */
        motors.add(rightFront);
        motors.add(leftFront);
        motors.add(rightBack);
        motors.add(leftBack);
        //CRServos.add(claw1);
        //CRServos.add(claw2);
        //extend, CRServos
        //maybe add an extra time?????? it was in colorstone
        /*
        ---USING STATES---
         */
        strafeLeft = new driveState(27, 0.2, motors, "strafeLeft"); //first move left (but is actually right because our things arte switched
        //drive forward a little, then turn left 90 degrees, raise and extend arm. dispose rings, move backwards
        forwards1 = new driveState(25, 0.1, motors, "forwards");
        extendFirst = new extendArmState(100, 1.0, extendArm);
        deposit = new intakeState(1000, -1.0, intake);
        extendBack = new extendArmState(100, -1.0, extendArm);
        //  open1 = new CRServoState(2000, 1.0, 1.0, servos);//do this later
        moveBackwards1 = new driveState(5, 1.0, motors, "backwards");
        turnLeft = new driveState(20, 1.0, motors, "turnRight");
        forward2 = new driveState(100, 1.0, motors, "forwards");
        //open the claws
        //back up
        strafeLeft.setNextState(forwards1);
        forwards1.setNextState(null);
        /*
        extendFirst.setNextState(extendBack);
        extendBack.setNextState(moveBackwards1);
        moveBackwards1.setNextState(turnLeft);
        turnLeft.setNextState(forward2);
        forward2.setNextState(null);
*/
    }
    @Override
    public void start(){
        machine = new StateMachine(strafeLeft);
    }
    public void loop()  {
        machine.update();
    }
    public void wait(int time) {
        try {
            Thread.sleep(time * 1000);//milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

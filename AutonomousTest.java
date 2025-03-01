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
@Autonomous(name="AutoTest", group="Iterative Opmode")
public class AutonomousTest extends OpMode
{


    /*
    ---MOTORS---
     */
    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack; //make sure these are the right motors
    DcMotor extendArm;
    CRServo claw1, claw2;
    DcMotor raiseArm2;
    DcMotor raiseArmMotor;


    ArrayList<DcMotor> motors = new ArrayList<DcMotor>();
    ArrayList<CRServo> servos = new ArrayList<CRServo>();

    private StateMachine machine;

    driveState strafeLeft;
    driveState forward1;
    driveState turnLeft;
    extendArmState extendFirst;
    extendArmState extendBack;
    CRServoState open1;
    driveState moveBackwards1;
    driveState turnLeft2;
    driveState forward2;

    CRServoState open1;
    driveState moveBackwards1;



    public void init() {

        /*
        ---HARDWARE MAP---
         */
        rightFront=hardwareMap.dcMotor.get("right front");
        leftFront = hardwareMap.dcMotor.get("left front");
        rightBack = hardwareMap.dcMotor.get("right back");
        leftBack = hardwareMap.dcMotor.get("left back");
        raiseArmMotor = hardwareMap.dcMotor.get("raise arm");
        extendArm = hardwareMap.dcMotor.get("extend arm");

        claw1 = hardwareMap.crservo.get("claw 1");
        claw2 = hardwareMap.crservo.get("claw 2");
        //get the CRSERVO


        /*
        ---MOTOR DIRECTIONS---
         */
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
//could be wrong!!!! test
        /*
        ---GROUPING---
         */
        motors.add(rightFront);
        motors.add(leftFront);
        motors.add(rightBack);
        motors.add(leftBack);
        // CRServos.add(claw1);
        // CRServos.add(claw2);
        //extend, CRServos
        //maybe add an extra time?????? it was in colorstone
        /*
        ---USING STATES---
         */

        strafeLeft = new driveState(7, 1.0, motors, "strafeRight"); //first move left (but is actually right because our things arte switched
        //drive forward a little, then turn left 90 degrees, raise and extend arm. dispose rings, move backwards
        forwards1 = new driveState(40, 0.5, motors, "backwards");
        extendFirst = new extendArmState(100, 1.0, extendArm);
        extendBack = new extendArmState(-100, 1.0, extendArm);
        open1 = new CRServoState(2000, 1.0, 1.0, servos);//do this later
        moveBackwards1 = new driveState(5, 1.0, motors, "forwards");
        turnLeft2 = new driveState(20, 1.0, motors, "turnLeft");
        forward2 = new driveState(100, 1.0, motors, "backwards");
        //open the claws
        //back up



        strafeLeft.setNextState(forward1);
        forward1.setNextState(extendFirst);
        extendFirst.setNextState(extendBack);
        extendBack.setNextState(open1)
        open1.setNextState(moveBackwards1);
        moveBackwards1.setNextState(turnLeft2);
        turnLeft2.setNextState(forward2);
        forward2.setNextState(null);
        
      
        





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

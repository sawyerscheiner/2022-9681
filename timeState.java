package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.StateMachine; //necessary
import org.firstinspires.ftc.teamcode.StateMachine.State; //necessary
import java.util.ArrayList;
import com.qualcomm.robotcore.util.ElapsedTime;

public class timeState implements State {

    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;

    private double Power;
    private String Movement;

    private State NextState;

    private int Time;
    ElapsedTime mRuntime = new ElapsedTime();


    public timeState(int time, double power, ArrayList<DcMotor> motor, String movement) {
        Time = time;
        leftFront = motor.get(0);
        rightFront = motor.get(1);
        leftBack = motor.get(2);
        rightBack = motor.get(3);
        Movement = movement;
        Power = power;
        mRuntime.reset();

    }

    public void setNextState(State state) {
        NextState = state;

    }

    @Override
    public void start() {

    }

    @Override
    public State update() {


        while (mRuntime.milliseconds() < Time) {
            if (Movement == "forward") { //for some reason == worked
                leftFront.setPower(Power);
                rightFront.setPower(Power);
                leftBack.setPower(Power);
                rightBack.setPower(Power);
            }
            if (Movement == "backward") {
                leftFront.setPower(-Power);
                rightFront.setPower(-Power);
                leftBack.setPower(-Power);
                rightBack.setPower(-Power);
            }
            if (Movement == "turnLeft") {
                leftFront.setPower(-Power);
                rightFront.setPower(Power);
                leftBack.setPower(-Power);
                rightBack.setPower(Power);

            }
            if (Movement == "turnRight") {
                leftFront.setPower(Power);
                rightFront.setPower(-Power);
                leftBack.setPower(Power);
                rightBack.setPower(-Power);

            }
            return this;
        }
        if(Time<=mRuntime.milliseconds()){
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);
            // return NextState;
        }



        return NextState;
    }
}








//        public void wait(int time) {
//        try {
//            Thread.sleep(time * 100);//milliseconds
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


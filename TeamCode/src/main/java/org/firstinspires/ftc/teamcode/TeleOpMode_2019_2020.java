package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;
import com.vuforia.ar.pl.SensorController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.Math.*;

/**
 * Created by Caleb Schwartz and John Best on 9/11/2019.
 * <p>
 *
 */
@TeleOp(name = "TeleOp Mode_2019_2020", group = "Tele Operated")

public class TeleOpMode_2019_2020 extends LinearOpMode {

    private DcMotor motorFrontLeft;
    private DcMotor motorRearLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorRearRight;

    //Controller Configs
    private DcMotorController motorController1;
    private DcMotorController motorController2;
    private DcMotorController motorController3;

    private ServoController servoController1;

    //private SensorController sensorController1;

    @Override
    public void runOpMode() throws InterruptedException {

        // Hardware Map for Motors
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorRearLeft = hardwareMap.dcMotor.get("motorRearLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorRearRight = hardwareMap.dcMotor.get("motorRearRight");

        // Hardware Map for Servos

        // Sets default direction for motors
        motorFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRearLeft.setDirection(DcMotor.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorRearRight.setDirection(DcMotor.Direction.REVERSE);

        // Sets mode for motors
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Sets zero power behavior
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialization Complete!");
        telemetry.update();

        waitForStart(); //Stops here after Init, waits for start

        while (opModeIsActive()) {

            // CONTROLLER 1
            double Lpadxroot = normalizeVector(gamepad1.left_stick_x);
            double Lpadyroot = normalizeVector(gamepad1.left_stick_y);
            double Rpadxroot = normalizeVector(gamepad1.right_stick_x);
            double Rpadyroot = normalizeVector(gamepad1.right_stick_y);

            //  ****Slow Code****
            if (gamepad1.dpad_up) {
                setDirection(0, .3, 0);
            } else if (gamepad1.dpad_down) {
                setDirection(0, -.3, 0);
            } else if (gamepad1.dpad_left) {
                setDirection(.3, 0, 0);
            } else if (gamepad1.dpad_right) {
                setDirection(-.3, 0, 0);
            }
            //  ****Regular Movement Code****
            else {
                setMotorsFromJoystick(Lpadxroot, Lpadyroot, Rpadxroot, Rpadyroot);
                //motorFrontLeft.setPower(Math.atan(.7 * gamepad1.left_stick_y));
            }
        }
    }
    private void setMotorsFromJoystick ( double left_x_val, double left_y_val, double right_x_val, double right_y_val){

        double SPEED = 1;
        double SPEEDstrafe = 0.85;
        double bumperRightPressed;
        double bumperLeftPressed;

        if (gamepad1.right_bumper == true) {
            bumperRightPressed = .5;
        } else
            bumperRightPressed = 1;


        if (gamepad1.left_bumper == true) {
            bumperLeftPressed = .2;

        } else
            bumperLeftPressed = 1;

        motorFrontLeft.setPower((-SPEED * bumperLeftPressed * bumperRightPressed * left_y_val) + (-SPEED * bumperLeftPressed * bumperRightPressed * left_x_val) + -SPEED * bumperLeftPressed * bumperRightPressed * (right_x_val));
        motorRearLeft.setPower((-SPEED * bumperLeftPressed * bumperRightPressed * left_y_val) + (SPEEDstrafe * bumperLeftPressed * bumperRightPressed * left_x_val) + -SPEED * bumperLeftPressed * bumperRightPressed * (right_x_val));
        motorFrontRight.setPower((-SPEED * bumperLeftPressed * bumperRightPressed * left_y_val) + (SPEED * bumperLeftPressed * bumperRightPressed * left_x_val) + SPEED * bumperLeftPressed * bumperRightPressed * (right_x_val));
        motorRearRight.setPower((-SPEED * bumperLeftPressed * bumperRightPressed * left_y_val) + (-SPEEDstrafe * bumperLeftPressed * bumperRightPressed * left_x_val) + SPEED * bumperLeftPressed * bumperRightPressed * (right_x_val));


    }
    private void setDirection ( double RightLeftMotion, double ForwardBackMotion, double RotateRightLeft){
        setMotorsFromJoystick(RightLeftMotion, ForwardBackMotion, RotateRightLeft, 0);
    }


    // Normalizes the values of the joystick controls
    private double normalizeVector ( double vector){
        double normalizedVector;
        if (vector < 0)
            normalizedVector = -Math.sqrt(Math.abs(vector));
        else
            normalizedVector = Math.sqrt(vector);
        normalizedVector = Math.pow(normalizedVector, 3);
        return (normalizedVector);
    }}








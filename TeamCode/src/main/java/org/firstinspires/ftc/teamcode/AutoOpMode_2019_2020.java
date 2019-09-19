package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorREVColorDistance;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * AutoOp Mode_2019_2020 Created by Caleb Schwartz and John Best on 9/11/2019.
 * <p>
 */

/**
 * Base model created by ChristopherDeloglos on 12/3/2016.
 * Purpose: Educational model for robot programming
 */
@Autonomous(name = "AutoOp Mode_2019_2020", group = "Autonomous")
// Add this for default (Change name)

public class AutoOp Mode_2019_2020 extends LinearOpMode // Add this for default (Change AutoOpMode to match filename/classname)
{
    private static final double METERS_PER_5_ROTATIONS = 2.388;
    private static final double TICKS_PER_REV = 1680; // Number of ticks in one motor turn
    private static final double REVS_PER_METER = 5 / METERS_PER_5_ROTATIONS; // Number of times the wheel turns after the robot moves 1 meter
    private static final double GEAR_RATIO = 1; // Number of times the motor rotates for 1 rotation of the wheel
    private static final int TICKS_PER_360 = 14509;
    private static final int TICKS_PER_180 = 1814;
    private static final int TICKS_PER_90 = 907;
    private static final int TICKS_PER_45 = 453;
    //Motor Configs//

    private DcMotor motorFrontLeft;
    private DcMotor motorRearLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorRearRight;



    public void runOpMode() throws InterruptedException {// Add this for default exactly as is// Hardware Map for Motors
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorRearLeft = hardwareMap.dcMotor.get("motorRearLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorRearRight = hardwareMap.dcMotor.get("motorRearRight");

        // Sets default direction for motors
        motorFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        motorRearLeft.setDirection(DcMotor.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorRearRight.setDirection(DcMotor.Direction.REVERSE);

        // Sets mode for motors
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRearLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRearRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Sets zero power behavior
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialization Complete!");
        telemetry.update();

        /*
        Positive Sideways moves RIGHT
        Postive Forward moves BACKWARD
        */




        //*******************    CODE START    *******************\\


        waitForStart();

        Thread.sleep(1000);



        //*******************    CODE END    *******************\\
    }


    private void moveForward(int motorsTicks, double motorsPower) {
        //motorsTicks = motorsTicks*1;
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() + motorsTicks);
        motorRearLeft.setTargetPosition(motorRearLeft.getCurrentPosition() + motorsTicks);
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() + motorsTicks);
        motorRearRight.setTargetPosition(motorRearRight.getCurrentPosition() + motorsTicks);

        motorFrontLeft.setPower(motorsPower);
        motorRearLeft.setPower(motorsPower);
        motorFrontRight.setPower(motorsPower);
        motorRearRight.setPower(motorsPower);
        while (isNotInRange(motorFrontLeft.getCurrentPosition(), motorFrontLeft.getTargetPosition(), 15))
            ;
    }

    private void moveRotate(int motorsTicks, double motorsPower) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() + -motorsTicks);
        motorRearLeft.setTargetPosition(motorRearLeft.getCurrentPosition() + -motorsTicks);
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() + motorsTicks);
        motorRearRight.setTargetPosition(motorRearRight.getCurrentPosition() + motorsTicks);

        motorFrontLeft.setPower(motorsPower);
        motorRearLeft.setPower(motorsPower);
        motorFrontRight.setPower(motorsPower);
        motorRearRight.setPower(motorsPower);
        while (isNotInRange(motorFrontLeft.getCurrentPosition(), motorFrontLeft.getTargetPosition(), 15))
            ;


    }

    private void moveSideways(int motorsTicks, double motorsPower) {

        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() + -motorsTicks);
        motorRearLeft.setTargetPosition(motorRearLeft.getCurrentPosition() + motorsTicks);
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() + motorsTicks);
        motorRearRight.setTargetPosition(motorRearRight.getCurrentPosition() + -motorsTicks);

        motorFrontLeft.setPower(motorsPower);
        motorRearLeft.setPower(motorsPower);
        motorFrontRight.setPower(motorsPower);
        motorRearRight.setPower(motorsPower);
        while (isNotInRange(motorFrontLeft.getCurrentPosition(), motorFrontLeft.getTargetPosition(), 15))
            ;

    }



    private static boolean isNotInRange(double number, double compareTo, double range) {
        return (Math.abs(number -compareTo)<range);
        //return number >= compareTo+range || number <= compareTo-range;
    }

    public int GetTicksFromMeters(double Meters) {
        Double ticks = GEAR_RATIO * (TICKS_PER_REV * REVS_PER_METER * Meters);
        return (ticks.intValue());
    }

    public int GetTicksFromFeet(double Feet) {
        return (GetTicksFromMeters(Feet / 3.28084));
    }

    public int GetTicksFromInches(double Inches) {
        return (GetTicksFromMeters((Inches / 12) / 3.28084));
    }

    public double GetFeetFromTicks(int Ticks) {
        return (Ticks * 3.28084 / (GEAR_RATIO * (TICKS_PER_REV * REVS_PER_METER)));
    }

    public double GetMetersFromTicks(int Ticks) {
        return (Ticks / (GEAR_RATIO * (TICKS_PER_REV * REVS_PER_METER)));
    }

    public int GetInchesFromAngle(double Degrees) {
        double inches = (68 / 360) * Degrees;
        return (int) Math.round(inches);
    }}




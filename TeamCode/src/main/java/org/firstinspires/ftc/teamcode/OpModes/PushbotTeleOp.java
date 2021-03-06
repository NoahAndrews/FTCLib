package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.SimpleMotorImpl;
import com.arcrobotics.ftclib.util.Safety;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.HardwarePushbot;

@TeleOp(name="Sample OpMode for Pushbot")
@Disabled
public class PushbotTeleOp extends LinearOpMode {

    private DifferentialDrive dt;
    private SimpleMotorImpl left, right;

    private GamepadEx controller;

    HardwarePushbot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        left = new SimpleMotorImpl(hardwareMap, telemetry, "left", 383.6);
        right = new SimpleMotorImpl(hardwareMap, telemetry, "right", 383.6);

        dt = new DifferentialDrive(left, right);

        robot = new HardwarePushbot(dt);

        controller = new GamepadEx(gamepad1);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            robot.driveRobot(controller.getLeftY(), controller.getRightX());
        }

        stopRobot();
    }

    private void stopRobot() {
        try {
            robot.stop(Safety.BREAK);
        } catch (Exception e) {
            telemetry.addData("Error Thrown", e.getMessage());
            telemetry.update();
        }
    }

}
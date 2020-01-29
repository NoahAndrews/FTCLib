package com.arcrobotics.ftclib.gamepad;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;

/**
 * Class that reads the value of button states.
 * In order to get any values that depend on the previous state, you must call "readValue();" in a loop.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class ButtonReader implements KeyReader {

    /** Holds the last state of the button (true if pressed, false if not pressed) */
    private boolean lastState;

    /** Holds the last state of the button (true if pressed, false if not pressed) */
    private boolean currState;

    /** Telemetry reference for the button */
    private Telemetry telemetry = null;

    /** Name of the button */
    private String buttonName;

    /** State of the button as defined by a {@link java.util.function.BooleanSupplier} */
    private BooleanSupplier buttonState;

    /**
     * Defines a ButtonReader using the gamepad and button references
     * @param gamepad the gamepad the button to be read is on
     * @param button the button to be read
     */
    public ButtonReader(GamepadEx gamepad, GamepadKeys.Button button) {
        buttonState = () -> gamepad.getButton(button);
        currState = buttonState.getAsBoolean();
        lastState = currState;
    }

    /**
     * Defines a ButtonReader using the gamepad and button references and a {@link Telemetry} instance
     * to post button states to
     * @param gamepad the gamepad the button to be read is on
     * @param button the button to be read
     * @param tel the telemetry instance to post button states to
     */
    public ButtonReader(GamepadEx gamepad, GamepadKeys.Button button, Telemetry tel) {
        this(gamepad, button);
        telemetry = tel;
    }

    /**
     * Defines a ButtonReader using a {@link BooleanSupplier} instead of a gamepad. Useful for running
     * a ButtonReader in simulation
     * @param buttonValue the {@link BooleanSupplier} button value to be read
     */
    public ButtonReader(BooleanSupplier buttonValue) {
        buttonState = buttonValue;
        currState = buttonState.getAsBoolean();
        lastState = currState;
    }

    /**
     * Defines a ButtonReader using a {@link BooleanSupplier} instead of a gamepad. Useful for running
     * a ButtonReader in simulation. Can post telemetry
     * @param buttonValue the {@link BooleanSupplier} button value to be read
     * @param tel the telemetry instance to post button states to
     */
    public ButtonReader(BooleanSupplier buttonValue, Telemetry tel) {
        this(buttonValue);
        telemetry = tel;
    }

    /**
     * Read the value of the button and save it internally. If telemetry is in use, then we will also
     * publish the button state to it using:
     * <pre><code>
     *     if (telemetry != null) {
     *             telemetry.addData(String.format("Button %s state: ", buttonName), isDown() ? "pressed" : "not pressed");
     *             telemetry.update();
     *     }
     * </code></pre>
     */
    public void readValue() {
        lastState = currState;
        currState = buttonState.getAsBoolean();

        // Telemetry isn't guaranteed to be enabled
        if (telemetry != null) {
            telemetry.addData(String.format("Button %s state: ", buttonName), isDown() ? "pressed" : "not pressed");
            telemetry.update();
        }
    }

    /**
     * Check if the button is pressed
     * @return true if the button is pressed, false otherwise
     */
    public boolean isDown() {
        return buttonState.getAsBoolean();
    }

    /**
     * Check if the button's last state was not pressed and is now pressed
     * @return true if the last state is not pressed and is now pressed, and false otherwise
     */
    public boolean wasJustPressed() {
        return (lastState == false && currState == true);
    }
    public boolean wasJustReleased() {
        return (lastState == true && currState == false);
    }
    public boolean stateJustChanged() {
        return (lastState != currState);
    }
}

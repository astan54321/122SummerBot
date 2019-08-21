package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.maps.Controls;

public class DriverStation {

    private Joystick driver;
    private Joystick operator;

    public DriverStation() {
        driver = new Joystick(0);
        operator = new Joystick(1);
    }

    // Driver methods

    public double getGTASpeed() {
        return driver.getRawAxis(Controls.GTA_ACCEL) - driver.getRawAxis(Controls.GTA_DECELL);
    }

    public double getThrottle() {
        return driver.getRawAxis(Controls.DRIVE_SPEED_AXIS);
    }

    public double getTurn() {
        return driver.getRawAxis(Controls.DRIVE_TURN_AXIS);
    }

    public double getRightThrottle() {
        return driver.getRawAxis(Controls.DRIVE_TANK_RIGHT);
    }

    // Operator methods

    public double getManualArmMove() {
        return operator.getRawAxis(Controls.MANUAL_ARM_AXIS);
    }

    public boolean getCargoIntake() {
        return operator.getRawButton(Controls.CARGO_INTAKE);
    }

    public boolean getCargoEject() {
        return operator.getRawButton(Controls.CARGO_EJECT);
    }

    public boolean getArmUpOverride() {
        return operator.getRawButton(button)
    }

    public boolean getHatchIntake() {
        return operator.getRawButton(Controls.HATCH_INTAKE);
    }

    public boolean getHatchEject() {
        return operator.getRawButton(Controls.HATCH_EJECT);
    }

    public boolean getHatchRetract() {
        return operator.getRawButtonReleased(Controls.HATCH_EJECT);
    }

    // Misc. utils

}
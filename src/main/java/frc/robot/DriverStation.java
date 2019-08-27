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
    // ________________________________________________________________

    // Driver methods

    public double getGTASpeed() {
        return driver.getRawAxis(Controls.GTA_ACCEL) - driver.getRawAxis(Controls.GTA_DECELL);
    }
    // ________________________________________________________________

    public double getThrottle() {
        return driver.getRawAxis(Controls.DRIVE_SPEED_AXIS);
    }
    // ________________________________________________________________

    public double getTurn() {
        return driver.getRawAxis(Controls.DRIVE_TURN_AXIS);
    }
    // ________________________________________________________________

    public double getRightThrottle() {
        return driver.getRawAxis(Controls.DRIVE_TANK_RIGHT);
    }
    // ________________________________________________________________

    // Operator methods

    public double getManualArmMove() {
        return operator.getRawAxis(Controls.MANUAL_ARM_AXIS);
    }
    // ________________________________________________________________

    public boolean getCargoIntake() {
        return operator.getRawButton(Controls.CARGO_INTAKE);
    }
    // ________________________________________________________________

    public boolean getCargoEject() {
        return operator.getRawButton(Controls.CARGO_EJECT);
    }
    // ________________________________________________________________

    public boolean getHatchIntake() {
        return operator.getRawButton(Controls.HATCH_INTAKE);
    }
    // ________________________________________________________________

    public boolean getHatchEject() {
        return operator.getRawButton(Controls.HATCH_EJECT);
    }
    // ________________________________________________________________

    public boolean getHatchDeploy() {
        return operator.getRawButton(4);
    }
    // ________________________________________________________________

    public boolean getHatchRetract() {
        return operator.getRawButton(3);
    }
    // ________________________________________________________________

    public boolean getTopRolerIn() {
        return operator.getRawButton(5);
    }
    // ________________________________________________________________

    public boolean getTopRolerOut() {
        return operator.getRawButton(6);
    }
    // ________________________________________________________________

    public boolean getBottomRolerIn() {
        return operator.getRawAxis(2) > 0.05;
    }
    // ________________________________________________________________

    public boolean getBottomRolerOut() {
        return operator.getRawAxis(3) > 0.05;
    }

    // Misc. utils

}
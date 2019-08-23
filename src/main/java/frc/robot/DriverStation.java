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

    public boolean getHatchIntake() {
        return operator.getRawButton(Controls.HATCH_INTAKE);
    }

    public boolean getHatchEject() {
        return operator.getRawButton(Controls.HATCH_EJECT);
    }

    public boolean getHatchDeploy() {
        return operator.getRawButton(4);
    }

    public boolean getHatchRetract() {
        return operator.getRawButton(3);
    }

    // -----------------------------------

    public boolean getTopRolerIn() {
        return operator.getRawButton(5);
    }

    public boolean getTopRolerOut() {
        return operator.getRawButton(6);
    }

    public boolean getBottomRolerIn() {
        return operator.getRawAxis(2) > 0.05;
    }

    public boolean getBottomRolerOut() {
        return operator.getRawAxis(3) > 0.05;
    }

    // Misc. utils

}
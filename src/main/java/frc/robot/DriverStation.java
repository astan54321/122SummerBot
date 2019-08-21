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

    // Misc. utils

}
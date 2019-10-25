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

    public boolean getClimb() {
        return driver.getRawButtonPressed(Controls.CLIMB_CYLINDERS);
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
        return operator.getRawButton(Controls.LEFT_BUMPER);
    }

    /**
     * to intake the hatch
     * @return the right y-axis value multipied by -1
     */
    public double getHatchRollers() {
        return -1 * operator.getRawAxis(Controls.HATCH_AXIS); //-1 from testing
    }

    public boolean getHatchRetract() {
        return operator.getRawButton(Controls.RIGHT_BUMPER);
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

    /**
     * gets the value of the left trigger
     * @return whether the value of the left trigger is greater than 0.05 or not
     * @author Levi Walker
     */
    public boolean getRollersIn() {
        return operator.getRawAxis(2) > 0.05;
    }

    /**
     * gets the value of the right trigger
     * @return whether the value of the right trigger is greater than 0.05 or not
     * @author Levi Walker
     */
    public boolean getRollersOut() {
        return operator.getRawAxis(3) > 0.05;
    }

    public double getRollers() {
        return operator.getRawAxis(2) - operator.getRawAxis(3);
    }

    public boolean getOpOne() {
        return operator.getRawButton(1);
    }

    public boolean getOpTwo() {
        return operator.getRawButton(2);
    }

    public boolean getOpThree() {
        return operator.getRawButton(3);
    }

    public boolean getOpFour() {
        return operator.getRawButton(4);
    }


    // Misc. utils

}
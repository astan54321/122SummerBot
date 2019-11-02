package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Constants {
    // drive
    public static final double SPEED_MAX = 0.75;
    public static final double ROTATION_MAX = 0.6;

    // cargo mech
    public static final double CARGO_INTAKE_SPEED = 0;
    public static final double CARGO_EJECT_SPEED = 0;
    public static final double CARGO_STALL_SPEED = 0.125;
    public static final double ARM_SPEED = 0.6;
    public static final double ARM_STALL = -0.025;
    public static final boolean ARM_INVERTED = false;
    public static final boolean TOP_ROLLER_INVERTED = false;
    public static final boolean BOTTOM_ROLLER_INVERTED = false;

    public static final double CARGOSHIP_ANGLE = 840;
    public static final double ROCKET_ANGLE = 640;
    public static final double STOW_ANGLE = 1100;
    public static final double PICKUP_ANGLE = 230;
    public static final double HATCH_CAM_ANGLE = 1100;

    // hatch mech
    public static final double HATCH_INTAKE_SPEED = 0.7;
    public static final double HATCH_EJECT_SPEED = -0.7;
    public static final double HATCH_STALL_SPEED = 0;
    public static final boolean HATCH_INTAKE_INVERTED = false;
    public static final Value RETRACT = Value.kReverse;
    public static final Value DEPLOY = Value.kForward;
}
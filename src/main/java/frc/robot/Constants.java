package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Constants {
    // drive
    public static final double SPEED_MAX = 0.75;
    public static final double ROTATION_MAX = 0.6;

    // cargo mech
    public static final double CARGO_INTAKE_SPEED = 1;
    public static final double CARGO_EJECT_SPEED = -1;
    public static final double CARGO_STALL_SPEED = 0.05;
    public static final boolean ARM_INVERTED = false;
    public static final boolean TOP_ROLLER_INVERTED = false;
    public static final boolean BOTTOM_ROLLER_INVERTED = false;

    // hatch mech
    public static final double HATCH_INTAKE_SPEED = 0.7;
    public static final double HATCH_EJECT_SPEED = -0.7;
    public static final double HATCH_STALL_SPEED = 0;
    public static final boolean HATCH_INTAKE_INVERTED = false;
    public static final Value RETRACT = Value.kReverse;
    public static final Value DEPLOY = Value.kForward;
}
package frc.robot.maps;

public class RobotMap {

    // Drive
    public static final int FRONT_LEFT_MOTOR = 1;
    public static final int REAR_LEFT_MOTOR = 2;
    public static final int FRONT_RIGHT_MOTOR = 3;
    public static final int REAR_RIGHT_MOTOR = 4;

    public static final int LEFT_ENC_CHANNEL_A = 0;
    public static final int LEFT_ENC_CHANNEL_B = 0;
    public static final int RIGHT_ENC_CHANNEL_A = 0;
    public static final int RIGHT_ENC_CHANNEL_B = 0;

    // Cargo mech
    public static final int ARM_MASTER_MOTOR = 11;
    public static final int ARM_SLAVE_MOTOR = 12;
    public static final int TOP_ROLLER_MOTOR = 13;
    public static final int BOTTOM_ROLLER_MOTOR = 14;
    public static final int CARGO_COLLECTED_SWITCH = 0; //we forgot this one
    public static final int ARM_POSITION_POT = 1;
    public static final int MAX_ARM_POS = 2;
    public static final int BOT_ARM_POS = 1;

    // Hatch mech
    public static final int HATCH_INTAKE = 21;
    public static final int HATCH_INTAKE_HELPER = 0; //pwm VictorSP
    public static final int HATCH_COLLECTED_SWITCH = 0;

    // Pneumatics
    public static final int PCM_CHANNEL = 40;
    public static final int HATCH_CHANNEL_A = 0;
    public static final int HATCH_CHANNEL_B = 1;
    public static final int CLIMB_CHANNEL_A = 3;
    public static final int CLIMB_CHANNEL_B = 2;

}
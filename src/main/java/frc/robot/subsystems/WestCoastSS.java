package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.maps.RobotMap;

public class WestCoastSS extends Subsystem {

  private WPI_TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;
  private DifferentialDrive drive;
  private AHRS navx;
  private Encoder leftEnc, rightEnc;
  private double SPEED_MAX, ROTATION_MAX;
  private final double DISTANCE_PER_ENCODER_TICK = 0 * Math.PI; // update this before using duh
  private DriveMode DRIVE_MODE;

  // defaults to curveDrive
  public WestCoastSS() {
    initMotors();
    initIMU();
    // initEncoders(); // these bois not on the bot yet
  }
  // ________________________________________________________________

  public void setMaxSpeeds(double maxDrive, double maxRotation) {
    SPEED_MAX = maxDrive;
    ROTATION_MAX = maxRotation;
  }
  // ________________________________________________________________

  public void setMode(DriveMode mode) {
    DRIVE_MODE = mode;
  }
  // ________________________________________________________________

  public void setCoast() {
    leftMaster.setNeutralMode(NeutralMode.Coast);
    rightMaster.setNeutralMode(NeutralMode.Coast);
  }
  // ________________________________________________________________

  public void drive(double speedInput, double rotationInput) {
    double speed = speedInput * SPEED_MAX * -1;
    double rotation = rotationInput * ROTATION_MAX;
    switch (DRIVE_MODE) {
    case kArcade:
      drive.arcadeDrive(speed, rotation);
      break;
    case kCurve:
      drive.curvatureDrive(speed, rotation, true);
      break;
    }
  }
  // ________________________________________________________________

  public void setLeftRight(double leftSpeed, double rightSpeed) {
    drive.tankDrive(leftSpeed, rightSpeed);
  }
  // ________________________________________________________________

  public double getAngle() {
    return navx.getYaw();
  }
  // ________________________________________________________________

  public double getLeftEnc() {
    return leftEnc.getDistance();
  }
  // ________________________________________________________________

  public double getRightEnc() {
    return rightEnc.getDistance();
  }
  // ________________________________________________________________

  private void initMotors() {
    leftMaster = new WPI_TalonSRX(RobotMap.FRONT_LEFT_MOTOR);
    leftSlave = new WPI_TalonSRX(RobotMap.REAR_LEFT_MOTOR);
    rightMaster = new WPI_TalonSRX(RobotMap.FRONT_RIGHT_MOTOR);
    rightSlave = new WPI_TalonSRX(RobotMap.REAR_RIGHT_MOTOR);

    resetMotors();

    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    leftMaster.setNeutralMode(NeutralMode.Brake);
    rightMaster.setNeutralMode(NeutralMode.Brake);

    drive = new DifferentialDrive(leftMaster, rightMaster);
  }
  // ________________________________________________________________

  private void resetMotors() {
    leftMaster.configFactoryDefault();
    rightMaster.configFactoryDefault();
    leftSlave.configFactoryDefault();
    rightSlave.configFactoryDefault();
  }
  // ________________________________________________________________

  // add try/catch
  private void initIMU() {
    navx = new AHRS(SPI.Port.kMXP);
    navx.reset();
  }
  // ________________________________________________________________

  private void initEncoders() {
    leftEnc = new Encoder(RobotMap.LEFT_ENC_CHANNEL_A, RobotMap.LEFT_ENC_CHANNEL_B);
    rightEnc = new Encoder(RobotMap.RIGHT_ENC_CHANNEL_A, RobotMap.RIGHT_ENC_CHANNEL_B);

  }
  // ________________________________________________________________

  @Override
  public void initDefaultCommand() {
  }
  // ________________________________________________________________

  public enum DriveMode {
    kArcade, kCurve;
  }
}

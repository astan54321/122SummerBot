package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.maps.RobotMap;

public class WestCoastSS extends Subsystem {

  private WPI_TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;
  private DifferentialDrive drive;
  private AHRS navx;
  private double SPEED_MAX, ROTATION_MAX;
  private DriveMode DRIVE_MODE;
  private double driveStraightP = 0.7; // tweak this if needed

  // defaults to curveDrive
  public WestCoastSS() {
    initMotors();
    initIMU();
  }

  public void setMaxSpeeds(double maxDrive, double maxRotation) {
    SPEED_MAX = maxDrive;
    ROTATION_MAX = maxRotation;
  }

  public void setMode(DriveMode mode) {
    DRIVE_MODE = mode;
  }

  public void setCoast() {
    leftMaster.setNeutralMode(NeutralMode.Coast);
    rightMaster.setNeutralMode(NeutralMode.Coast);
  }

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

  public void driveStraight(double speedInput, double targetAngle){
    double errorCorrect = driveStraightP * (targetAngle - getAngle());
    drive(speedInput, errorCorrect);
  }

  public void setLeftRight(double leftSpeed, double rightSpeed) {
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  public double getAngle() {
    return navx.getYaw();
  }

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

  private void resetMotors() {
    leftMaster.configFactoryDefault();
    rightMaster.configFactoryDefault();
    leftSlave.configFactoryDefault();
    rightSlave.configFactoryDefault();
  }

  // add try/catch
  private void initIMU() {
    navx = new AHRS(SPI.Port.kMXP);
    navx.reset();
  }

  @Override
  public void initDefaultCommand() {
  }

  public enum DriveMode {
    kArcade, kCurve;
  }
}

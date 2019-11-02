
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.maps.RobotMap;

public class Arm extends Subsystem {

  private WPI_VictorSPX topRoller, bottomRoller;
  private WPI_TalonSRX armMaster, armSlave;
  private DigitalInput cargoCollected, topLimit, bottomLimit;
  private AnalogInput armPosition;
  private double cruiseVelocity = 0.5;

  private double kP = 0.99;

  public Arm() {
    initMotors();
    initSensors();
  }

  /*
   * UP is <0 (-) DOWN is >0 (+)
   */

  public void moveArmManual(double speed) {
    if (speed < 0 && getTop() || speed > 0 && getBottom())
      speed = 0;
    armMaster.set(speed);
  }

  public void topRollerManual(double intakeSpeed) {
    topRoller.set(intakeSpeed);
  }

  public void bottomRollerManual(double intakeSpeed) {
    bottomRoller.set(intakeSpeed);
  }

  /**
   * stops the top rollers on the arm
   * 
   * @author Levi Walker
   */
  public void topRollerCoast() {
    topRoller.setNeutralMode(NeutralMode.Coast);
  }

  /**
   * stops the bottom rollers on the arm
   * 
   * @author Levi Walker
   */
  public void bottomRollerCoast() {
    bottomRoller.setNeutralMode(NeutralMode.Coast);
  }

  public void stopIntake() {
    topRollerManual(0);
  }

  public boolean hasCargo() {
    return cargoCollected.get();
  }

  public boolean getTop() {
    return topLimit.get();
  }

  public boolean getBottom() {
    return !bottomLimit.get();
  }

  public double getAngle() {
    return armPosition.getValue();
  }

  private void resetMotors() {
    topRoller.configFactoryDefault();
    bottomRoller.configFactoryDefault();
    armMaster.configFactoryDefault();
    armSlave.configFactoryDefault();
  }

  private void initMotors() {
    topRoller = new WPI_VictorSPX(RobotMap.TOP_ROLLER_MOTOR);
    bottomRoller = new WPI_VictorSPX(RobotMap.BOTTOM_ROLLER_MOTOR);
    armMaster = new WPI_TalonSRX(RobotMap.ARM_MASTER_MOTOR);
    armSlave = new WPI_TalonSRX(RobotMap.ARM_SLAVE_MOTOR);

    resetMotors();

    // arm
    armSlave.setInverted(Constants.ARM_INVERTED);
    armMaster.setInverted(armSlave.getInverted());
    armSlave.follow(armMaster);
    armMaster.setNeutralMode(NeutralMode.Brake);

    

    // rollers
    topRoller.setInverted(Constants.TOP_ROLLER_INVERTED);
    topRoller.setNeutralMode(NeutralMode.Brake);
    bottomRoller.setInverted(Constants.BOTTOM_ROLLER_INVERTED);
    bottomRoller.setNeutralMode(NeutralMode.Coast);

  }

  private void initSensors() {
    // cargoCollected = new DigitalInput(RobotMap.CARGO_COLLECTED_SWITCH);
    armPosition = new AnalogInput(RobotMap.ARM_POSITION_POT);
    topLimit = new DigitalInput(RobotMap.MAX_ARM_POS);
    bottomLimit = new DigitalInput(RobotMap.BOT_ARM_POS);
  }

  @Override
  public void initDefaultCommand() {
  }

  /**
   * Aight lets do the PID stuff Want keepAngle(angle) to move the arm to that
   * angle and keep it there can try just P cause not that critical but adding D
   * shouldnt be hard? start with just P and go from there
   */

  public void keepAngle(ArmPosition position) {
    double target = position.getAngle();
    double error = target - getAngle();
    double output = (error / 700) * kP;
    if (Math.abs(output) > cruiseVelocity) {
      output = output > 0 ? cruiseVelocity : -cruiseVelocity;
    }
    SmartDashboard.putNumber("Output: ", -output);
    moveArmManual(-output);
  }

  public enum ArmPosition {
    CARGO(Constants.CARGOSHIP_ANGLE), ROCKET(Constants.ROCKET_ANGLE), PICKUP(Constants.PICKUP_ANGLE),
    STOW(Constants.STOW_ANGLE), HATCH_CAM(Constants.HATCH_CAM_ANGLE);

    private double angle;

    public double getAngle() {
      return angle;
    }

    private ArmPosition(double angle) {
      this.angle = angle;
    }
  }
}

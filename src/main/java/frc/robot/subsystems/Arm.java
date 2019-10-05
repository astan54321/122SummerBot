
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.maps.RobotMap;

public class Arm extends Subsystem {

  private WPI_VictorSPX topRoller, bottomRoller;
  private WPI_TalonSRX armMaster, armSlave;
  private DigitalInput cargoCollected, topLimit;
  // private AnalogInput armPosition;

  public Arm() {
    initMotors();
    topLimit = new DigitalInput(2);
    // TO BE ADDED TO ROBOT
    // initSensors();
  }

  /*
   * UP is _____ (-/+) DOWN is ____ (-/+)
   */

  public void moveArmManual(double speed) {
    if (speed < 0 && topLimit.get())
      speed = 0;
    armMaster.set(speed);
  }

  public void topRollerManual(double intakeSpeed) {
    topRoller.set(intakeSpeed);
  }

  public void bottomRollerManual(double intakeSpeed) {
    bottomRoller.set(intakeSpeed);
  }

  /*
   * Intakes if a button is pressed, AND with hasCargo() once limit switch
   * installed on bot
   */
  public void intake(boolean intake) {
    double intakeSpeed = intake ? Constants.CARGO_INTAKE_SPEED : Constants.CARGO_STALL_SPEED;
    topRollerManual(intakeSpeed);
  }

  public void eject(boolean eject) {
    double ejectSpeed = eject ? Constants.CARGO_EJECT_SPEED : Constants.CARGO_STALL_SPEED;
    topRollerManual(ejectSpeed);
  }

  public void intake() {
    intake(true);
  }

  public void eject() {
    eject(true);
  }

  public void stall() {
    intake(false);
    eject(false);
  }

  public void stopIntake() {
    topRollerManual(0);
  }

  public boolean hasCargo() {
    return cargoCollected.get();
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
    cargoCollected = new DigitalInput(RobotMap.CARGO_COLLECTED_SWITCH);
    armPosition = new AnalogInput(RobotMap.ARM_POSITION_POT);
  }

  @Override
  public void initDefaultCommand() {
  }
}

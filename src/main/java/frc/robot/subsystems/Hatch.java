
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.maps.RobotMap;

public class Hatch extends Subsystem {

  private WPI_VictorSPX intake;
  private DoubleSolenoid deployer;
  private DigitalInput hatchCollected;

  public Hatch() {
    initMotors();
    initPneumatics();
    // initSensors();
  }

  // ________________________________________________________________

  public void intakeManual(double speed) {
    intake.set(speed);
  }

  // ________________________________________________________________

  public void intake(boolean intake) {
    deploy();
    double intakeSpeed = intake ? Constants.HATCH_INTAKE_SPEED : Constants.HATCH_STALL_SPEED;
    intakeManual(intakeSpeed);
  }

  // ________________________________________________________________

  public void intake() {
    intake(true);
  }

  // ________________________________________________________________

  public void eject(boolean eject) {
    double ejectSpeed = eject ? Constants.HATCH_EJECT_SPEED : Constants.HATCH_STALL_SPEED;
    intakeManual(ejectSpeed);
  }

  // ________________________________________________________________

  public void eject() {
    eject(true);
  }

  // ________________________________________________________________

  public void stall() {
    intake(false);
    eject(false);
  }

  // ________________________________________________________________

  public void stopIntake() {
    intakeManual(0);
  }

  // ________________________________________________________________

  public void deploy() {
    deployer.set(Constants.DEPLOY);
  }

  // ________________________________________________________________

  public void retract() {
    deployer.set(Constants.RETRACT);
  }
  // ________________________________________________________________

  private boolean getDeployed() {
    return deployer.get() == Constants.DEPLOY;
  }
  // ________________________________________________________________

  public boolean hasHatch() {
    return hatchCollected.get();
  }
  // ________________________________________________________________

  private void initMotors() {
    intake = new WPI_VictorSPX(RobotMap.HATCH_INTAKE);

    resetMotors();

    intake.setNeutralMode(NeutralMode.Brake);
  }
  // ________________________________________________________________

  public void initPneumatics() {
    deployer = new DoubleSolenoid(RobotMap.PCM_CHANNEL, RobotMap.HATCH_CHANNEL_A, RobotMap.HATCH_CHANNEL_B);
  }
  // ________________________________________________________________

  private void initSensors() {
    hatchCollected = new DigitalInput(RobotMap.HATCH_COLLECTED_SWITCH);
  }
  // ________________________________________________________________

  private void resetMotors() {
    intake.configFactoryDefault();
  }
  // ________________________________________________________________

  @Override
  public void initDefaultCommand() {
  }
}

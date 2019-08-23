
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

  public void intakeManual(double speed) {
    intake.set(speed);
  }

  public void intake(boolean intake) {
    deploy();
    double intakeSpeed = intake ? Constants.HATCH_INTAKE_SPEED : Constants.HATCH_STALL_SPEED;
    intakeManual(intakeSpeed);
  }

  public void intake() {
    intake(true);
  }

  public void eject(boolean eject) {
    double ejectSpeed = eject ? Constants.HATCH_EJECT_SPEED : Constants.HATCH_STALL_SPEED;
    intakeManual(ejectSpeed);
  }

  public void eject() {
    eject(true);
  }

  public void stall() {
    intake(false);
    eject(false);
  }

  public void stopIntake() {
    intakeManual(0);
  }

  public void deploy() {
    deployer.set(Constants.DEPLOY);
  }

  public void retract() {
    deployer.set(Constants.RETRACT);
  }

  private boolean getDeployed() {
    return deployer.get() == Constants.DEPLOY;
  }

  public boolean hasHatch() {
    return hatchCollected.get();
  }

  private void initMotors() {
    intake = new WPI_VictorSPX(RobotMap.HATCH_INTAKE);

    resetMotors();

    intake.setNeutralMode(NeutralMode.Brake);
  }

  public void initPneumatics() {
    deployer = new DoubleSolenoid(RobotMap.PCM_CHANNEL, RobotMap.HATCH_CHANNEL_A, RobotMap.HATCH_CHANNEL_B);
  }

  private void initSensors() {
    hatchCollected = new DigitalInput(RobotMap.HATCH_COLLECTED_SWITCH);
  }

  private void resetMotors() {
    intake.configFactoryDefault();
  }

  @Override
  public void initDefaultCommand() {
  }
}


package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.maps.RobotMap;

public class Hatch extends Subsystem {

  private WPI_VictorSPX intakeMaster, intakeSlave;
  private DoubleSolenoid deployer;
  private DigitalInput hatchCollected;

  public Hatch() {
    initMotors();
    initPneumatics();
    // initSensors();
  }

  public void intakeManual(double speed) {
    deploy();
    intakeMaster.set(speed);
  }

  public void intake(boolean intake) {
    deploy();
    double intakeSpeed = intake ? Constants.HATCH_INTAKE_SPEED : Constants.HATCH_STALL_SPEED;
    intakeManual(intakeSpeed);
  }

  public void eject(boolean eject) {
    double ejectSpeed = eject ? Constants.HATCH_EJECT_SPEED : Constants.HATCH_STALL_SPEED;
    intakeManual(ejectSpeed);
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

  private void initMotors() {
    intakeMaster = new WPI_VictorSPX(RobotMap.INTAKE_MASTER);
    intakeSlave = new WPI_VictorSPX(RobotMap.INTAKE_SLAVE);

    resetMotors();

    intakeSlave.setInverted(Constants.HATCH_INTAKE_INVERTED);
    intakeMaster.setInverted(!(intakeSlave.getInverted()));

    intakeSlave.follow(intakeMaster);

    intakeMaster.setNeutralMode(NeutralMode.Brake);
  }

  public void initPneumatics() {
    deployer = new DoubleSolenoid(RobotMap.PCM_CHANNEL, RobotMap.HATCH_CHANNEL_A, RobotMap.HATCH_CHANNEL_B);
  }

  private void initSensors() {
    hatchCollected = new DigitalInput(RobotMap.HATCH_COLLECTED_SWITCH);
  }

  private void resetMotors() {
    intakeMaster.configFactoryDefault();
    intakeSlave.configFactoryDefault();
  }

  @Override
  public void initDefaultCommand() {
  }
}

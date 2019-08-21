
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Hatch;
import frc.robot.subsystems.WestCoastSS;
import frc.robot.subsystems.WestCoastSS.DriveMode;

public class Robot extends TimedRobot {

  private DriverStation ds;
  private WestCoastSS alphaChi;
  private Arm arm;
  private Hatch hatch;
  private final ControlMode CONTROL_MODE = ControlMode.kGTA;

  // publics
  // in-match control
  @Override
  public void robotInit() {
    initDS();
    initDriveSubsystem();
  }

  @Override
  public void robotPeriodic() {
    System.out.println(alphaChi.getAngle());
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    driveControl();
  }

  @Override
  public void disabledInit() {
    alphaChi.setCoast();
  }

  // privates
  // init subsystems
  private void initDriveSubsystem() {
    alphaChi = new WestCoastSS();
    alphaChi.setMode(DriveMode.kCurve);
    alphaChi.setMaxSpeeds(Constants.SPEED_MAX, Constants.ROTATION_MAX);
  }

  private void initCargoSubsystem() {
    arm = new Arm();
  }

  private void initHatchSubsystem() {
    hatch = new Hatch();
  }

  private void initDS() {
    ds = new DriverStation();
  }

  // control methods for individual subsystems
  private void driveControl() {
    switch (CONTROL_MODE) {
    case kGTA:
      alphaChi.drive(ds.getGTASpeed(), ds.getTurn());
      break;
    case kSticks:
      alphaChi.drive(ds.getThrottle(), ds.getTurn());
      break;
    case kDumb:
      alphaChi.setLeftRight(ds.getThrottle(), ds.getRightThrottle());
      break;
    }
  }

  /*
   * ONLY functional if NOT HAS_HATCH ||| hold INTAKE button: move arm to DOWN and
   * INTAKE (arm returns to UP position when HAS_CARGO) ||| hold EJECT button:
   * move arm to UP and EJECT
   */

  private void cargoControlMatch() {
    if (!hatch.hasHatch()) {

      if (arm.hasCargo()) {
        // UP position
        arm.eject(ds.getCargoEject());
      } else if (ds.getCargoIntake()) {
        // DOWN position
        arm.intake();
      } else {
        arm.stopIntake();
        if (ds.getCargoEject()) {
          // UP position
        }
      }

    }
    // T E M P O R A R Y until POT attached to arm and positional control added
    arm.moveArmManual(ds.getManualArmMove());
  }

  /**
   * ONLY functional if NOT HAS_CARGO ||| hold INTAKE button: DEPLOY and INTAKE
   * ||| hold EJECT button: EJECT (RETRACT when EJECT button released)
   */
  private void hatchControlMatch() {
    if (!arm.hasCargo()) {

      if (hatch.hasHatch()) {
        hatch.deploy();
        hatch.eject(ds.getHatchEject());
      } else if (ds.getHatchIntake()) {
        hatch.deploy();
        hatch.intake();
      } else {
        hatch.stopIntake();
        if (ds.getHatchRetract())
          hatch.retract();
      }

    }
  }

  // misc.
  private enum ControlMode {
    kGTA, kSticks, kDumb; // kDumb AKA kTank
  };

}


package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.WestCoastSS;
import frc.robot.subsystems.WestCoastSS.DriveMode;

public class Robot extends TimedRobot {

  DriverStation ds;
  WestCoastSS alphaChi;
  private final ControlMode CONTROL_MODE = ControlMode.kGTA;

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
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  private void initDriveSubsystem() {
    alphaChi = new WestCoastSS();
    alphaChi.setMode(DriveMode.kCurve);
    alphaChi.setMaxSpeeds(Constants.SPEED_MAX, Constants.ROTATION_MAX);
  }

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

  private void initDS() {
    ds = new DriverStation();
  }

  private enum ControlMode {
    kGTA, kSticks, kDumb; // kDumb AKA kTank
  };

}

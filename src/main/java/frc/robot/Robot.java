
package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.maps.RobotMap;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Hatch;
import frc.robot.subsystems.WestCoastSS;
import frc.robot.subsystems.WestCoastSS.DriveMode;

public class Robot extends TimedRobot {

  private DriverStation ds;
  private WestCoastSS alphaChi;
  private Arm arm;
  private Hatch hatch;
  private Climb climb;
  private final ControlMode CONTROL_MODE = ControlMode.kGTA;
  private Compressor compressor;
  private UsbCamera hatchCam;
  private final int resMod = 1;
  private final int width = 160 * resMod;
  private final int height = 120 * resMod;

  // publics
  // in-match control
  @Override
  public void robotInit() {
    initDS();
    initDriveSubsystem();
    initCargoSubsystem();
    initHatchSubsystem();
    initClimbSubsystem();
    initCamera();
    initCompressor(true);
  }

  @Override
  public void robotPeriodic() {
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
    cargoControlTest(); //newCargoTest();
    hatchControlTest(); //newHatchTest();
    climbControlTest();
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

  private void initClimbSubsystem() {
    climb = new Climb();
  }

  private void initDS() {
    ds = new DriverStation();
  }

  private void initCamera() {
    hatchCam = CameraServer.getInstance().startAutomaticCapture();
    // hatchCam.setExposureAuto();
    // hatchCam.setWhiteBalanceAuto();
    // hatchCam.setExposureHoldCurrent();
    hatchCam.setFPS(30);
    hatchCam.setResolution(width, height);
    // hatchCam.setWhiteBalanceHoldCurrent();
  }

  private void initCompressor(boolean enabled) {
    compressor = new Compressor(RobotMap.PCM_CHANNEL);
    compressor.setClosedLoopControl(enabled);
  }

  // control methods for individual subsystems
  private void driveControl() {
    switch (CONTROL_MODE) {
    case kGTA:
      alphaChi.drive(ds.getGTASpeed(), ds.getTurn());
      // if (climb.getDeployed() && ds.getGTASpeed() > 51)
      // {arm.bottomRollerManual(0.7);} //test w/o first
      break;
    case kSticks:
      alphaChi.drive(ds.getThrottle(), ds.getTurn());
      break;
    case kDumb:
      alphaChi.setLeftRight(ds.getThrottle(), ds.getRightThrottle());
      break;
    }
  }

  private void cargoControlTest() {
    arm.moveArmManual(ds.getManualArmMove() * Constants.ARM_SPEED + Constants.ARM_STALL);

    if (Math.abs(ds.getRollers()) < 0.05) {
      final double holdSpeed = Constants.CARGO_STALL_SPEED;
      arm.topRollerManual(-holdSpeed);
      arm.bottomRollerManual(holdSpeed);
    } else {
      arm.topRollerManual(ds.getRollers());
      arm.bottomRollerManual(ds.getRollers());
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

  private void hatchControlTest() {
    double speed = 0.7;
    if (ds.getHatchIntake()) {
      hatch.intakeManual(speed);
    } else if (ds.getHatchEject()) {
      hatch.intakeManual(-speed);
    } else {
      hatch.intakeManual(0);
    }
    
    if (ds.getHatchDeploy()) {
      hatch.deploy();
    } else if (ds.getHatchRetract()) {
      hatch.retract();
    }
    
  }
  
  private void climbControlTest() {
    if (ds.getClimb()) {
      if (!climb.getDeployed())
        climb.deploy();
      else
        climb.retract();
      // can combine with getDeployed and the drivetrain for climb
    }
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

  /************** NEEDS TO BE TESTED **************/
  // private void newHatchTest() {
  //   if (!arm.hasCargo()) {
  //     if (ds.getHatchRollers() > 0.05 || ds.getHatchRollers() < -0.05) //change based on testing
  //     {
  //       if (!hatch.getDeployed()) {
  //         hatch.deploy();
  //       }
  //       hatch.intakeManual(ds.getHatchRollers());
  //     } else if (ds.getHatchDeploy()) {
  //       if (!hatch.getDeployed()) {
  //         hatch.deploy();
  //       }  
  //     } else if (ds.getHatchRetract()) {
  //       if (hatch.getDeployed()) {
  //         hatch.retract();
  //       }
  //     }
  //   } else {
  //     hatch.stopIntake();
  //   }
  // }

  // private void newCargoTest() {
  //   if (!hatch.hasHatch()) {
  //     if (ds.getRollersIn()) {
  //       arm.topRollerManual(Constants.CARGO_INTAKE_SPEED);
  //       arm.bottomRollerCoast();

  //     } else if (ds.getRollersOut()) {
  //       arm.topRollerManual(Constants.CARGO_EJECT_SPEED);
  //       arm.bottomRollerManual(Constants.CARGO_EJECT_SPEED);

  //     } else {
  //       arm.topRollerManual(Constants.CARGO_STALL_SPEED);
  //       arm.bottomRollerManual(Constants.CARGO_STALL_SPEED);
  //     }
  //   }
  // }

  private void seperateIntakeTest() {
    double speed = 0.7;
    if (ds.getOpOne()) {
      hatch.regularIntake(speed);
    } else if (ds.getOpTwo()) {
      hatch.regularIntake(-speed);
    } else if (ds.getOpThree()) {
      hatch.intakeHelper(speed);
    } else if(ds.getOpFour()) {
      hatch.intakeHelper(-speed);
    } else {
      hatch.stopIntake();
    }
  }

  /************************************************
  ***************** UNTESTED CODE *****************
  ************************************************/

  // misc.
  private enum ControlMode {
    kGTA, kSticks, kDumb; // kDumb AKA kTank
  };

}

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.maps.RobotMap;

public class Climb extends Subsystem {
    
    DoubleSolenoid climb;

    public Climb() {
        initPneumatics();
    }

    public void deploy() {
        climb.set(Constants.DEPLOY);
    }

    public void retract() {
        climb.set(Constants.RETRACT);
    }

    public boolean getDeployed() {
        return climb.get() == Constants.DEPLOY;
    }

    private void initPneumatics() {
        climb = new DoubleSolenoid(RobotMap.PCM_CHANNEL, RobotMap.CLIMB_CHANNEL_A, RobotMap.CLIMB_CHANNEL_B);
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}
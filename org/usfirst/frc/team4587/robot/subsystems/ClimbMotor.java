package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import utility.LogDataSource;
import utility.RampedSpeedController;
import utility.ValueLogger;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class ClimbMotor extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_climbMotor;
	public void startClimb()
	{
		m_climbMotor.set(1);
	}
	public void stopClimb()
	{
		m_climbMotor.set(0);
	}

    public ClimbMotor()
    {    	
    	m_climbMotor = new VictorSP(RobotMap.MOTOR_CLIMB);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
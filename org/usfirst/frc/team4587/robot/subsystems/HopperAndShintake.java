package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.Robot;
import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.GearIntakeLEDs;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import utility.LogDataSource;
import utility.RampedSpeedController;
import utility.ValueLogger;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class HopperAndShintake extends Subsystem implements LogDataSource {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_hopperMotor;
	private SpeedController m_shintakeMotor;
	private Solenoid m_hopperPiston;
	
	public void hopperIn()
	{
		m_hopperPiston.set(false);
	}
	public void hopperOut()
	{
		m_hopperPiston.set(true);
	}
	public boolean getPiston()
	{
		return m_hopperPiston.get();
	}
	
    public void setHopperMotor(double x)
    {
    	m_hopperMotor.set(-1 * x);
    	//m_motorOn = !(Math.abs(x) < 0.01);
    	
    }

    public void setShintakeMotor(double x)
    {
    	m_shintakeMotor.set(-1 * x);
    	//m_motorOn = !(Math.abs(x) < 0.01);
    	
    }
    /*private boolean m_motorOn;
    public boolean motorOn()
    {
    	return m_motorOn;
    }*/

    

    public HopperAndShintake()
    {    	
        m_hopperMotor = new Spark(RobotMap.MOTOR_HOPPER);
        m_shintakeMotor = new Spark(RobotMap.MOTOR_SHINTAKE);
        m_hopperPiston = new Solenoid(RobotMap.SOLENOID_HOPPER);
        //m_motorOn = false;
    }
    
    public void initialize()
    {
    	//gearIsLoaded = false;

    }
    
    public void gatherValues( ValueLogger logger)
    {
    	//logger.logBooleanValue("Intake Switch", getGearIntakeSwitch());
    	//m_gearIntakeMotor.gatherValues(logger);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new GearIntakeLEDs());
    }
}


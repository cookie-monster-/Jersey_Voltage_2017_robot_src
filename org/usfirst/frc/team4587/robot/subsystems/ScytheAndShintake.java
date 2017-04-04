package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;

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
public class ScytheAndShintake extends Subsystem implements LogDataSource {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_scytheMotor;
	private SpeedController m_shintakeMotor;
    public void setScytheMotor(double x)
    {
    	m_scytheMotor.set(-1 * x);
    	//m_motorOn = !(Math.abs(x) < 0.01);
    	
    }

    public void setShintakeMotor(double x)
    {
    	m_shintakeMotor.set(-1 * x);
    }

    /*private boolean m_motorOn;
    public boolean motorOn()
    {
    	return m_motorOn;
    }*/

    public ScytheAndShintake()
    {    	
        m_scytheMotor = new Spark(RobotMap.MOTOR_SCYTHE);
        m_shintakeMotor = new Spark(RobotMap.MOTOR_SHINTAKE);
        //m_motorOn = false;
    }
    
    public void initialize()
    {

    }
    
    public void gatherValues( ValueLogger logger)
    {
    	//logger.logBooleanValue("Intake Switch", getGearIntakeSwitch());
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}


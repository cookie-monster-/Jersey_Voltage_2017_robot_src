package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunFlywheel extends Command {

	private double m_desiredVelocity;
	private double m_currentFlywheelVelocity;
	private double m_lastFlywheelVelocity;
	private double m_lastFlywheelEncoder;
    public RunFlywheel() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getFlywheel());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_desiredVelocity = 0.0;
    	m_lastFlywheelEncoder = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.getFlywheel().running())
    	{
    		m_desiredVelocity = SmartDashboard.getNumber("FlywheelVelocity", 0.0) / 50 / 60 * 256;
    		SmartDashboard.putNumber("ConvertedFlywheelVelocity", m_desiredVelocity);
    		m_currentFlywheelVelocity = Robot.getFlywheel().getVelocity(m_lastFlywheelEncoder);
    		SmartDashboard.putNumber("Flywheel Current Velocity", m_currentFlywheelVelocity);
    		if(Math.abs(m_currentFlywheelVelocity - m_desiredVelocity) > 0)
    		{
    			System.out.println("nope");
    				double error = (m_desiredVelocity - m_currentFlywheelVelocity) / 50;
    				Robot.getFlywheel().setSetpoint(Robot.getFlywheel().getSetpoint() + error);
        			m_lastFlywheelVelocity = m_currentFlywheelVelocity;
        			SmartDashboard.putNumber("Flywheel Setpoint", Robot.getFlywheel().getSetpoint() + error);
    			
    			/*double error = (m_desiredVelocity - m_currentVelocity) / 1;
				Robot.getFlywheel().setSetpoint(Robot.getFlywheel().getSetpoint() + error);
    			m_lastVelocity = m_currentVelocity;
    			SmartDashboard.putNumber("Flywheel Setpoint", Robot.getFlywheel().getSetpoint() + error);*/
    			SmartDashboard.putNumber("Error", error);
    		}
    	}
    	m_lastFlywheelEncoder = Robot.getFlywheel().getEncoder().get();
    	Robot.getFlywheel().m_lastEncoders = m_lastFlywheelEncoder;
    	SmartDashboard.putNumber("Last Encoder Flywheel", m_lastFlywheelEncoder);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

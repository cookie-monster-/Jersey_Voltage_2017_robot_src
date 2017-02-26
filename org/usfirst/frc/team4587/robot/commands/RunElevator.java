package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class RunElevator extends Command {

	private double m_desiredVelocity;
	private double m_currentElevatorVelocity;
	private double m_lastElevatorVelocity;
	private double m_lastElevatorEncoder;
    public RunElevator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getElevator());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_desiredVelocity = 0.0;
    	m_lastElevatorEncoder = 0.0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.getElevator().running())
    	{
    		System.out.println("Running");
    		m_desiredVelocity = SmartDashboard.getNumber("FlywheelVelocity", 0.0) / 50 / 60 * 256;
    		SmartDashboard.putNumber("ConvertedFlywheelVelocity", m_desiredVelocity);
    		m_currentElevatorVelocity = Robot.getElevator().getVelocity(m_lastElevatorEncoder);
    		SmartDashboard.putNumber("Elevator Current Velocity", m_currentElevatorVelocity);
    		//if(Math.abs(m_currentElevatorVelocity - m_desiredVelocity) >= 0)
    		//{
    			System.out.println("Yes");
    				double error = (m_desiredVelocity - m_currentElevatorVelocity) / 50;
    				Robot.getElevator().setSetpoint(Robot.getElevator().getSetpoint() + error);
        			m_lastElevatorVelocity = m_currentElevatorVelocity;
        			SmartDashboard.putNumber("Elevator Setpoint", Robot.getElevator().getSetpoint() + error);
    			
    			/*double error = (m_desiredVelocity - m_currentVelocity) / 1;
				Robot.getFlywheel().setSetpoint(Robot.getFlywheel().getSetpoint() + error);
    			m_lastVelocity = m_currentVelocity;
    			SmartDashboard.putNumber("Flywheel Setpoint", Robot.getFlywheel().getSetpoint() + error);*/
    			SmartDashboard.putNumber("Elevator Error", error);
    		//}
    	}

    	m_lastElevatorEncoder = Robot.getElevator().getEncoder().get();
    	Robot.getElevator().m_lastEncoders = m_lastElevatorEncoder;
    	SmartDashboard.putNumber("Last Encoder Elevator", m_lastElevatorEncoder);
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

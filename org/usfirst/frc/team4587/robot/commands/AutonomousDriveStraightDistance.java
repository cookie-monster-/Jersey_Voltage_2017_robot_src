package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonomousDriveStraightDistance extends Command {
	int m_startLeft = 0;
	double m_distanceInches;
	double m_speed;
	double m_distanceTraveled;
	

    public AutonomousDriveStraightDistance(double distanceInches, double speed) {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	m_distanceInches = distanceInches;
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_startLeft = Robot.getDriveBaseSimple().getEncoderLeft();
    	Robot.getDriveBaseSimple().arcadeDrive(m_speed, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	m_distanceTraveled = Robot.getDriveBaseSimple().getEncoderLeft() - m_startLeft;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putNumber("distancetraveled", m_distanceTraveled);
    	SmartDashboard.putNumber("startLeft", m_startLeft);
    	if(m_distanceInches > 0)
    	{
    		return m_distanceTraveled >= m_distanceInches;
    	}
    	else
    	{
    		return m_distanceTraveled <= m_distanceInches;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.getDriveBaseSimple().arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

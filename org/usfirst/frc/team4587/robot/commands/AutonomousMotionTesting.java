package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutonomousMotionTesting extends Command {
	double m_speed;
	int count;

    public AutonomousMotionTesting(double speed) {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBase());
    	m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.getDriveBase().arcadeDrive(m_speed, 0);
    	count ++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	/*SmartDashboard.putNumber("distancetraveled", Robot.getDriveBase().straightDistanceTraveled(m_startLeft, m_startRight));
    	SmartDashboard.putNumber("startLeft", m_startLeft);
    	SmartDashboard.putNumber("startRight", m_startRight);
    	if(m_distanceInches > 0)
    	{
    		return Robot.getDriveBase().straightDistanceTraveled(m_startLeft, m_startRight) >= m_distanceInches;
    	}
    	else
    	{
    		return Robot.getDriveBase().straightDistanceTraveled(m_startLeft, m_startRight) <= m_distanceInches;
    	}*/
    	return count >= 100;
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

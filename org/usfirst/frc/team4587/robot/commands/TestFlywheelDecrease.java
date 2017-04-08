package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestFlywheelDecrease extends Command {

    public TestFlywheelDecrease() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getFlywheel());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putNumber("FlywheelVelocity", SmartDashboard.getNumber("FlywheelVelocity", 0.0) - 100);
		double m_rpms;
		double m_motorLevel;
		m_rpms = SmartDashboard.getNumber("FlywheelVelocity", 0);
    	m_motorLevel = m_rpms / 6750 * 1.3;//6750 = max rpms
    	Robot.getFlywheel().setSetpoint(m_rpms);
    	Robot.getFlywheel().setExpectedMotorLevel(m_motorLevel);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

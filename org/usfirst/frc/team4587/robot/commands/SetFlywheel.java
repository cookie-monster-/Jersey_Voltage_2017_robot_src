package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class SetFlywheel extends Command {

	double m_rpms;
	double m_motorLevel;
    public SetFlywheel(double rpms, double motorLevel) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getFlywheel());
    	requires(Robot.getIndexer());
    	m_rpms = rpms;
    	m_motorLevel = motorLevel;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.getFlywheel().setSetpoint(m_rpms);
    	Robot.getFlywheel().setExpectedMotorLevel(m_motorLevel);
    	Robot.getIndexer().setSetpoint(m_rpms);
    	Robot.getIndexer().setExpectedMotorLevel(m_motorLevel);
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

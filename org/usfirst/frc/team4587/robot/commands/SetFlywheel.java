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
    public SetFlywheel(double rpms) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getFlywheel());
    	//requires(Robot.getIndexer());
    	m_rpms = rpms;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_motorLevel = m_rpms / 6750 * 1.3;//6750 = max rpms
    	Robot.getFlywheel().setSetpoint(m_rpms);
    	Robot.getFlywheel().setExpectedMotorLevel(m_motorLevel);
    	//SmartDashboard.putNumber("Flywheel RPM's set to: ", m_rpms);
    	SmartDashboard.putNumber("FlywheelVelocity", m_rpms);
    	//Robot.getIndexer().setSetpoint(m_rpms);
    	//Robot.getIndexer().setExpectedMotorLevel(m_motorLevel);
    	System.out.println(m_rpms + " " + m_motorLevel + " setpoint: " + Robot.getFlywheel().getSetpoint());
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

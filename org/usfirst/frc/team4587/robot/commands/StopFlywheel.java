package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class StopFlywheel extends Command {

	double m_rpms;
	boolean run;
    public StopFlywheel() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getFlywheel());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.getFlywheel().setRunning(false);
		Robot.getFlywheel().setSetpoint(0.0);
		Robot.getFlywheel().disable();
		System.out.println("StopFlywheel/initialize");
		SmartDashboard.putBoolean("Flywheel PID in Default Command",true);
  	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
		System.out.println("StopFlywheel/end");
		SmartDashboard.putBoolean("Flywheel PID in Default Command",false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

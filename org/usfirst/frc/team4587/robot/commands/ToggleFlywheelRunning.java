package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleFlywheelRunning extends Command {

    public ToggleFlywheelRunning() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getFlywheel());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.getFlywheel().setRunning(!Robot.getFlywheel().running());
    	//Robot.getElevator().setRunning(!Robot.getElevator().running());
    	if (Robot.getFlywheel().running() == true)
    	{
    		Robot.getFlywheel().setRunning(false);
    		Robot.getIndexer().setRunning(false);
    		Robot.getFlywheel().disable();
    		Robot.getIndexer().disable();
    	}
    	else
    	{
    		Robot.getFlywheel().initialize();
    		Robot.getFlywheel().enable();
    		Robot.getIndexer().enable();
    		Robot.getFlywheel().setRunning(true);
    		Robot.getIndexer().setRunning(true);
    	}
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

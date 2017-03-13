package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EjectGear extends Command {

	private boolean m_motorOn = false;
	private int count;

    public EjectGear() {
    	requires(Robot.getGearIntake());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	Robot.getGearIntake().gearIntakeDown();
    	count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	count++;
    	if(count > 5)
    	{
        	Robot.getGearIntake().setGearIntakeMotor(-1.0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return count>5;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

package org.usfirst.frc.team4587.robot.commands;

import java.io.BufferedReader;
import java.io.FileReader;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FollowPathTest extends Command {

	BufferedReader m_bufferedReader;
	boolean quit;
	int m_startEncoderLeft;
	int m_startEncoderRight;
    public FollowPathTest() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBase());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	quit = false;
    	try
    	{
    		m_bufferedReader = new BufferedReader(new FileReader("/home/lvuser/pathTest.csv"));
    		m_startEncoderLeft = Robot.getDriveBase().getEncoderLeft();
    		m_startEncoderRight = Robot.getDriveBase().getEncoderRight();
    	}
    	catch(Exception e)
    	{
    		
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try
    	{
        	String line = m_bufferedReader.readLine();
        	if(line == null)
        	{
        		quit = true;
        	}
        	else
        	{
        		String[] fields = line.split(",");
        		double left = Double.parseDouble(fields[0]);
        		if (left > 0.8)
        		{
        			left=0.8;
        		}
        		double right = Double.parseDouble(fields[2]);
        		if (right > 0.8)
        		{
        			right=0.8;
        		}
        		int leftEncoder = Integer.parseInt(fields[1]);
        		int rightEncoder = Integer.parseInt(fields[3]);
        		int realLeftEncoder = Robot.getDriveBase().getEncoderLeft();
        		int realRightEncoder = Robot.getDriveBase().getEncoderRight();
        		leftEncoder += m_startEncoderLeft;
        		rightEncoder += m_startEncoderRight;
        		
        		Robot.getDriveBase().getLeftController().setDesiredSetting(left - (realLeftEncoder - leftEncoder)*.01);
        		Robot.getDriveBase().getRightController().setDesiredSetting((right - (realRightEncoder - rightEncoder)*.01)*-1);
        		Robot.getDriveBase().getLeftController().updateMotorLevel();
        		Robot.getDriveBase().getRightController().updateMotorLevel();
        		SmartDashboard.putNumber("setLeftMotor", (realLeftEncoder - leftEncoder)*.01);
        		SmartDashboard.putNumber("setRightMotor", (realRightEncoder - rightEncoder)*.01);
        	}
    	}
    	catch(Exception e)
    	{
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return quit;
    }

    // Called once after isFinished returns true
    protected void end() {
    	try
    	{
    		m_bufferedReader.close();
    	}
    	catch(Exception e)
    	{
    		
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

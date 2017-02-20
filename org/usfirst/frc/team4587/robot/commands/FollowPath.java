package org.usfirst.frc.team4587.robot.commands;

import java.io.BufferedReader;
import java.io.FileReader;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Gyro;

/**
 *
 */
public class FollowPath extends Command {

	BufferedReader m_bufferedReader;
	boolean quit;
	int m_startEncoderLeft;
	int m_startEncoderRight;
	double m_startAngle;
	double Ka = 0.05;
	double Kv = 0.8/43;
	double Kp = 0.005;
	double Kg = 0.015;
    public FollowPath() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("hello");
    	quit = false;
    	try
    	{
    		m_bufferedReader = new BufferedReader(new FileReader("/home/lvuser/Path.csv"));
    		m_startEncoderLeft = Robot.getDriveBaseSimple().getEncoderLeft();
    		m_startEncoderRight = Robot.getDriveBaseSimple().getEncoderRight();
    		m_startAngle = Gyro.getYaw();
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
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
        		System.out.println("null");
        	}
        	else
        	{
        		String[] fields = line.split(",");
        		double aLeft = Double.parseDouble(fields[0]);
        		double vLeft = Double.parseDouble(fields[1]);
        		double xLeft = Double.parseDouble(fields[2]);
        		double aRight = Double.parseDouble(fields[3]);
        		double vRight = Double.parseDouble(fields[4]);
        		double xRight = Double.parseDouble(fields[5]);
        		double desiredAngle = Double.parseDouble(fields[6]);
        		double currentAngle = Gyro.getYaw();
        		desiredAngle += m_startAngle;
        		int realLeftEncoder = Robot.getDriveBaseSimple().getEncoderLeft();
        		int realRightEncoder = Robot.getDriveBaseSimple().getEncoderRight();
        		xLeft += m_startEncoderLeft;
        		xRight += m_startEncoderRight;
        		double leftMotorLevel = Ka * aLeft + Kv * vLeft - Kp * (realLeftEncoder - xLeft) - Kg * (currentAngle - desiredAngle);
        		double rightMotorLevel = Ka * aRight + Kv * vRight - Kp * (realRightEncoder - xRight) + Kg * (currentAngle - desiredAngle);
        		
        		Robot.getDriveBaseSimple().setLeftMotor(leftMotorLevel);
        		Robot.getDriveBaseSimple().setRightMotor(rightMotorLevel);
        		SmartDashboard.putNumber("left motor set to: ", leftMotorLevel);
        		SmartDashboard.putNumber("right motor set to: ", rightMotorLevel);
        	}
    	}
    	catch(Exception e)
    	{
    		System.out.println(e);
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

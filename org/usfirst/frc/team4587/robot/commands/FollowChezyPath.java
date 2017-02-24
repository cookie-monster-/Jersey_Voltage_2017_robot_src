package org.usfirst.frc.team4587.robot.commands;

import java.io.BufferedReader;
import java.io.FileReader;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Gyro;
import utility.Path;
import utility.TextFileDeserializer;
import utility.Trajectory;

/**
 *
 */
public class FollowChezyPath extends Command {

	BufferedReader m_bufferedReader;
	boolean quit;
	int m_startEncoderLeft;
	int m_startEncoderRight;
	double m_startAngle;
	double Ka = 0.05;
	double Kv = 0.8/43;
	double Kp = 0.005;
	double Kg = 0.015;
	Path m_path;
	int count = 0;
    public FollowChezyPath() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getDriveBaseSimple());
    	try{
    		m_bufferedReader = new BufferedReader(new FileReader("/home/lvuser/ChezyPath.txt"));
    		StringBuilder sb = new StringBuilder();
    		String line;
    		while((line = m_bufferedReader.readLine()) != null)
    		{
    			sb.append(line).append("\n");
    		}
    		m_bufferedReader.close();
    		m_path = (new TextFileDeserializer()).deserialize(sb.toString());
    	}catch(Exception e){
    		
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	quit = false;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(count >= m_path.getLeftWheelTrajectory().getNumSegments())
    	{
    		quit = true;
    	}
    	else
        	{
        		Trajectory.Segment left = m_path.getLeftWheelTrajectory().getSegment(count);
            	Trajectory.Segment right = m_path.getRightWheelTrajectory().getSegment(count);
        		double aLeft = left.acc * 12 / 0.049 / 1000 * 20 / 1000 * 20;
        		double vLeft = left.vel * 12 / 0.049 / 1000 * 20;
        		double xLeft = left.pos * 12 / 0.049;
        		double aRight = right.acc * 12 / 0.049 / 1000 * 20 / 1000 * 20;
        		double vRight = right.vel * 12 / 0.049 / 1000 * 20;
        		double xRight = right.pos * 12 / 0.049;
        		double desiredAngle = right.heading * 180 / Math.PI;
        		double currentAngle = Gyro.getYaw();
        		int realLeftEncoder = Robot.getDriveBaseSimple().getEncoderLeft();
        		int realRightEncoder = Robot.getDriveBaseSimple().getEncoderRight();
        		desiredAngle += m_startAngle;
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

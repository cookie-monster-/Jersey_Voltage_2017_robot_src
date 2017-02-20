package org.usfirst.frc.team4587.robot.subsystems;

import java.io.FileOutputStream;

import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.DriveSimpleWithJoysticks;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.LogDataSource;
import utility.ValueLogger;

/**
 *
 */
public class DriveBaseSimple extends Subsystem implements LogDataSource {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.\
	private SpeedController left0;//23
	private SpeedController left1;
	private SpeedController right0;//01
	private SpeedController right1;
	
	private RobotDrive drive0;
	private RobotDrive drive1;
	public Encoder m_encoderRight, m_encoderLeft;
	
	public DriveBaseSimple()
	{
		left0 = new Spark(1);
		left1 = new Spark(3);
		right0 = new Spark(0);
		right1 = new Spark(2);
		
		drive0 = new RobotDrive(left0, right0);
		drive1 = new RobotDrive(left1, right1);
		
		m_encoderLeft = new Encoder(RobotMap.ENCODER_LEFT_DRIVE_A, 
				RobotMap.ENCODER_LEFT_DRIVE_B);

		m_encoderRight = new Encoder(RobotMap.ENCODER_RIGHT_DRIVE_A, 
				 RobotMap.ENCODER_RIGHT_DRIVE_B);
	}
	
	public void resetEncoders()
	{
		m_encoderLeft.reset();
		m_encoderRight.reset();
	}
	
	public void setLeftMotor(double speed)
	{
		left0.set(speed);
		left1.set(speed);
	}
	
	public void setRightMotor(double speed)
	{
		right0.set(speed*-1);
		right1.set(speed*-1);
	}
	
	public int getEncoderLeft()
	{
		return m_encoderLeft.get() * -1;
	}
	
	public int getEncoderRight()
	{
		return m_encoderRight.get() * -1;
	}

	public void arcadeDrive(double drive, double turn)
	{
		drive0.arcadeDrive(drive, turn*-1);
		drive1.arcadeDrive(drive, turn*-1);
	}
	
	public void getValues(){
    	SmartDashboard.putNumber("Left Motor Value", left0.get());
    	SmartDashboard.putNumber("Right Motor Value", right0.get());
    	SmartDashboard.putNumber("Left Encoder Value", getEncoderLeft());
    	SmartDashboard.putNumber("Right Encoder Value", getEncoderRight());
    	/*SmartDashboard.putNumber("Right Encoder Delta Value", m_rightRingBuffer.getAverage());
    	SmartDashboard.putNumber("Left Encoder Delta Value", m_leftRingBuffer.getAverage());
    	SmartDashboard.putNumber("Delta Tolerance Value", m_tolerance);
    	SmartDashboard.putNumber("Delta Balance Value", m_balance);*/
    }
	
	public void gatherValues ( ValueLogger logger )
    {
    	logger.logDoubleValue ( "Left Motor Value",            left0.get() );
    	logger.logDoubleValue ( "Right Motor Value",           right0.get() );
    	logger.logIntValue    ( "Left Encoder Value",          getEncoderLeft() );
    	logger.logIntValue    ( "Right Encoder Value",         getEncoderRight() );
    	//logger.logDoubleValue ( "Distance Traveled",              straightDistanceTraveled() );
    }
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveSimpleWithJoysticks());
    }
}

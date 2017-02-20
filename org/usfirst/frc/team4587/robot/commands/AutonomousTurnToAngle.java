package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import utility.Gyro;

/**
 *
 */
public class AutonomousTurnToAngle extends Command {
	double m_startAngle;
	double m_desiredAngle;
	double m_speed;
	double m_tolerance;
	PIDController turnControl;
	myPIDSource m_myPIDSource;
	myPIDOutput m_myPIDOutput;
	int count;

    public AutonomousTurnToAngle(double angleDegrees, double speed, double tolerance) {
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	count = 0;
    	requires(Robot.getDriveBase());
    	m_desiredAngle = angleDegrees;
    	m_speed = speed;
    	m_tolerance = tolerance;
    	m_myPIDSource = new myPIDSource();
    	m_myPIDOutput = new myPIDOutput();
    	turnControl = new PIDController(0.03, 0.0, 0.0, m_myPIDSource, m_myPIDOutput);
    	turnControl.setAbsoluteTolerance(1);
    }

    class myPIDOutput implements PIDOutput{

		@Override
		public void pidWrite(double output) {
			// TODO Auto-generated method stub
			Robot.getDriveBase().getLeftController().setDesiredSetting(output); //.setLeftMotor(output);
			Robot.getDriveBase().getRightController().setDesiredSetting(output); //.setRightMotor(output*-1);
			Robot.getDriveBase().getLeftController().updateMotorLevel();
			Robot.getDriveBase().getRightController().updateMotorLevel();
		}
    	
    }
    
    class myPIDSource implements PIDSource{

		@Override
		public void setPIDSourceType(PIDSourceType pidSource) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public PIDSourceType getPIDSourceType() {
			// TODO Auto-generated method stub
			return PIDSourceType.kDisplacement;
		}

		@Override
		public double pidGet() {
			// TODO Auto-generated method stub
			return Gyro.getYaw();
		}
    	
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	turnControl.reset();
    	m_startAngle = Gyro.getYaw();
    	turnControl.setSetpoint(m_desiredAngle);
    	turnControl.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	/*double direction;
    	if (Math.abs(Gyro.getYaw() - m_desiredAngle) < m_tolerance)
    	{
    		direction = 0;
    	}
    	else
    	if (m_startAngle < m_desiredAngle)
    	{
    		direction = 1;
    	}
    	else
    	{
    		direction = -1;
    	}
    	Robot.getDriveBaseSimple().arcadeDrive(0.0, m_speed * direction);*/
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	/*if (m_startAngle < m_desiredAngle)
    	{
    		return Gyro.getYaw() >= m_desiredAngle - m_tolerance;
    	}
    	else
    	{
    		return Gyro.getYaw() <= m_desiredAngle + m_tolerance;
    	}*/
    	if(count > 3)
    	{
        	return turnControl.onTarget();
    	}
    	else if(turnControl.onTarget())
    	{
    		count ++;
    		return false;
    	}
    	else 
    	{
    		count = 0;
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	turnControl.disable();
    	//Robot.getDriveBaseSimple().arcadeDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.RunFlywheel;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FlywheelPID extends PIDSubsystem {

	private SpeedController m_flywheelMotor;
	private Encoder m_encoder;
	private double m_lastEncoders = 0.0;
	private long m_lastTime;
	private double m_expectedMotorLevel;
	public void setExpectedMotorLevel(double motorLevel)
	{
		m_expectedMotorLevel = motorLevel;
	}
	private double m_velocity;
	public void setLastEncoder(double lastEncoder)
	{
		m_lastEncoders = lastEncoder;
	}
	private static double m_kP = 0.001;
	private static double m_kI = 0.000;//0.0001;
	private static double m_kD = 0.001;//0.001;
	public double m_testSetPoint = 0.0;
	
	private boolean m_running = false;
	public boolean running()
	{
		return m_running;
	}
	public void setRunning(boolean running)
	{
		m_running = running;
	}
	
    // Initialize your subsystem here
    public FlywheelPID() {
    	super(m_kP,m_kI,m_kD);
    	setSetpoint(0.0);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	setAbsoluteTolerance(2.0);
    	m_flywheelMotor = new VictorSP(RobotMap.MOTOR_FLYWHEEL);
        m_encoder = new Encoder(RobotMap.ENCODER_FLYWHEEL_A, RobotMap.ENCODER_FLYWHEEL_B);
    }
    
    public double getVelocity()
    {
    	return m_encoder.get() - m_lastEncoders;
    	
    }
    
    public void setSetpoint(double setpoint)
    {
    	/*if (setpoint >= 0)
	    {
	    	setpoint %= 360;
	    }
	    else
	    {
	    	while(setpoint < 0)
	    	{
	    		setpoint += 360;
	    	}
	    }*/
    	super.setSetpoint(setpoint);
    }
    
    public void initialize()
    {
    	m_flywheelMotor.set(0.0);
    	setSetpoint(0.0);
    	m_lastTime = System.nanoTime();
    }
    
    public Encoder getEncoder()
    {
    	return m_encoder;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	//setDefaultCommand(new RunFlywheel());
    	
    }

    
    protected double returnPIDInput() {
    	double encoderNow = m_encoder.get();
    	long timeNow = System.nanoTime();
    	m_velocity = (encoderNow - m_lastEncoders) / (timeNow - m_lastTime) * 60 * 1000000000 / 256;
    	m_lastEncoders = encoderNow;
    	m_lastTime = timeNow;
    	if (Math.abs(m_velocity - getSetpoint()) < 0.05 * getSetpoint())
    	{
    		m_expectedMotorLevel = (m_expectedMotorLevel + m_flywheelMotor.get()) / 2;
    	}
    	return m_velocity;
    	//encoder delta (velocity)
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	if(getSetpoint() == 0.0)
    	{
        	m_flywheelMotor.set(0.0);
    	}
    	else
    	{
    		double motorLevel = m_expectedMotorLevel + output;
    		if(motorLevel < 0)
    		{
    			motorLevel = 0;
    		}
    		else if(motorLevel > 1)
    		{
    			motorLevel = 1;
    		}
        	m_flywheelMotor.set(motorLevel);
    	}
    	//m_flywheelMotor.set(0.8);
    	SmartDashboard.putNumber("flywheel actual motor level", m_flywheelMotor.get());
    	SmartDashboard.putNumber("flywheel setpoint", getSetpoint());
    	SmartDashboard.putNumber("flywheel pid input", m_velocity);
    	SmartDashboard.putNumber("flywheel expected motor level", m_expectedMotorLevel);
    }
}

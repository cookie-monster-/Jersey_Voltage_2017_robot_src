package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.Robot;
import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.Aim;
import org.usfirst.frc.team4587.robot.commands.EjectGear;
import org.usfirst.frc.team4587.robot.commands.RunElevator;
import org.usfirst.frc.team4587.robot.commands.RunFlywheel;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.RampedSpeedController;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class ElevatorPID extends PIDSubsystem {

	private SpeedController m_elevatorMotor;
	private Encoder m_encoder;
	private double m_startEncoders;
	private double m_nowEncoders;
	public double m_lastEncoders = 0.0;
	private static double m_kP = 0.001;
	private static double m_kI = 0.000;//0.0001;
	private static double m_kD = 0.00;//0.001;
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
    public ElevatorPID() {
    	super(m_kP,m_kI,m_kD);
    	setSetpoint(0.0);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	setAbsoluteTolerance(2.0);
    	m_elevatorMotor = new VictorSP(RobotMap.MOTOR_ELEVATOR);
        m_encoder = new Encoder(RobotMap.ENCODER_ELEVATOR_A, RobotMap.ENCODER_ELEVATOR_B);
        m_startEncoders = getEncoder().get();
        m_nowEncoders = getEncoder().get();
    }
    
    public double getVelocity(double lastEncoder)
    {
    	return m_encoder.get() - lastEncoder;
    	
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
    	m_encoder.reset();
    	
    }
    
    public Encoder getEncoder()
    {
    	return m_encoder;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new RunElevator());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return getVelocity(m_lastEncoders);
    	//encoder delta (velocity)
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	m_elevatorMotor.set(Math.abs(output));
    	SmartDashboard.putNumber("elevator pid output", output);
    	SmartDashboard.putNumber("elevator setpoint", getSetpoint());
    	SmartDashboard.putNumber("elevator pid input", returnPIDInput());
    }
}

package org.usfirst.frc.team4587.robot.subsystems;

import java.io.FileOutputStream;

import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.DriveSimpleWithJoysticks;
import org.usfirst.frc.team4587.robot.commands.TurretSimpleWithJoysticks;

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
public class TurretSimple extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.\
	private SpeedController m_turretMotor;
	private Encoder m_encoder;
	
	//private RobotDrive drive1;
	
	public TurretSimple()
	{
		m_turretMotor = new Spark(RobotMap.MOTOR_TURRET);
        m_encoder = new Encoder(RobotMap.ENCODER_TURRET_A, RobotMap.ENCODER_TURRET_B);
	}
	
	public void setMotor(double speed)
	{
		m_turretMotor.set(speed);
		//right1.set(speed*-1);
	}

    public int getEncoder()
    {
    	return m_encoder.get();
    }
    
    public void initialize()
    {
    	m_encoder.reset();
    }
    
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TurretSimpleWithJoysticks());
    }
}


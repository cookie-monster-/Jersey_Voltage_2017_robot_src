
package org.usfirst.frc.team4587.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.Bling;
import utility.GearCameraThread;
import utility.Gyro;
import utility.LogDataSource;
import utility.ValueLogger;

import java.io.FileOutputStream;

import org.usfirst.frc.team4587.robot.commands.AutoGearBayou;
import org.usfirst.frc.team4587.robot.commands.AutoGearCenter;
import org.usfirst.frc.team4587.robot.commands.AutoGearSide;
import org.usfirst.frc.team4587.robot.commands.AutoMobility;
import org.usfirst.frc.team4587.robot.commands.HopperAuto;
import org.usfirst.frc.team4587.robot.commands.SetScytheAndShintake;
import org.usfirst.frc.team4587.robot.commands.TurnTurretDegrees;
import org.usfirst.frc.team4587.robot.subsystems.DriveBaseSimple;
import org.usfirst.frc.team4587.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team4587.robot.subsystems.FlywheelPID;
import org.usfirst.frc.team4587.robot.subsystems.GearIntake;
import org.usfirst.frc.team4587.robot.subsystems.IndexerPID;
import org.usfirst.frc.team4587.robot.subsystems.Turret;
import org.usfirst.frc.team4587.robot.subsystems.TurretPID;
import org.usfirst.frc.team4587.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements LogDataSource {

	private static Robot m_robot;
	public static Robot getInstance()
	{
		return m_robot;
	}
	private static OI m_oi;
	public static OI getOI()
	{
		return m_oi;
	}

	private static TurretPID m_turret;
	public static TurretPID getTurret()
	{
		return m_turret;
	}
	
	private static FlywheelPID m_flywheel;
	public static FlywheelPID getFlywheel()
	{
		return m_flywheel;
	}
	/*private static FlywheelSimple m_flywheel;
	public static FlywheelSimple getFlywheel()
	{
		return m_flywheel;
	}*/
	private static ScytheAndShintake m_scytheAndShintake;
	public static ScytheAndShintake getScytheAndShintake()
	{
		return m_scytheAndShintake;
	}
	private static IndexerPID m_indexer;
	public static IndexerPID getIndexer()
	{
		return m_indexer;
	}
	
	private static GearIntake m_gearIntake;
	public static GearIntake getGearIntake()
	{
		return m_gearIntake;
	}
	private static BallIntake m_ballIntake;
	public static BallIntake getBallIntake()
	{
		return m_ballIntake;
	}
	
	private static DriveBaseSimple m_driveBaseSimple;
	public static DriveBaseSimple getDriveBaseSimple()
	{
		return m_driveBaseSimple;
	}
	private static ClimbMotor m_climbMotor;
	public static ClimbMotor getClimbMotor()
	{
		return m_climbMotor;
	}
	private static GearCameraThread m_gearCameraThread;
	public static GearCameraThread getGearCameraThread(){
		return m_gearCameraThread;
	}
	private static PowerDistributionPanel m_PDP;
	public static PowerDistributionPanel getPDP(){
		return m_PDP;
	}
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	private static ValueLogger  logger;
	private static SerialPort m_arduino;
	private FileOutputStream log;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_robot = this;
		m_turret = new TurretPID();
		m_flywheel = new FlywheelPID();
		m_scytheAndShintake = new ScytheAndShintake();
		//m_indexer = new IndexerPID();
		Compressor compressor = new Compressor(0);
		//compressor.start();
    	m_gearIntake = new GearIntake();
    	m_ballIntake = new BallIntake();
		//m_driveBase = new DriveBase();
		m_driveBaseSimple = new DriveBaseSimple();
		//m_gearCameraThread = new GearCameraThread();
		m_climbMotor = new ClimbMotor();
		m_PDP = new PowerDistributionPanel();
		Bling.initialize();
		try
		{
			//m_arduino = new SerialPort(9600, SerialPort.Port.kUSB);
			m_arduino = new SerialPort(9600, SerialPort.Port.kUSB);
		}
		catch(Exception e)
		{
			m_arduino = null;
			System.out.println("DEAD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		
		m_oi = new OI();
		logger = null;
        logger = new ValueLogger("/home/lvuser/dump",10);
        logger.registerDataSource(this);
        //logger.registerDataSource ( m_driveBase );
        logger.registerDataSource ( m_gearIntake );
        logger.registerDataSource ( m_driveBaseSimple );
        logger.registerDataSource(m_flywheel);
        //logger.registerDataSource(m_turret);
		/*chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);*/
        
	}
	static byte[] buffer = new byte [2];
	static int counter=0;
	public static void writeToArduino(byte mode)
	{
		if(m_arduino != null)
		{
			System.out.println("yep");
			buffer[0] = mode;
			buffer[1] = 10;
			m_arduino.write(buffer, 2);
			m_arduino.flush();
			counter++;
			SmartDashboard.putNumber("write to arduino count", counter);
			System.out.println(mode);
			SmartDashboard.putNumber("byte sent to arduino last:", mode);
		}
		else
		{
			System.out.println("nope");
		}
	}
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		initializeNewPhase(ValueLogger.DISABLED_PHASE);
		writeToArduino((byte)69);
		//m_turret.disable();
		Robot.getFlywheel().setRunning(false);
		Robot.getFlywheel().disable();
		Robot.getFlywheel().setSetpoint(0.0);
		//m_indexer.disable();
		//m_gearCameraThread.setRunning(false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		initializeNewPhase(ValueLogger.AUTONOMOUS_PHASE);
		//autonomousCommand = new AutoGearRight();
		//autonomousCommand = new AutoGearSimple("right");
		//autonomousCommand = new AutoGearSide("left");
		//autonomousCommand = new AutoGearCenter();
		autonomousCommand = new HopperAuto("red");
		//autonomousCommand = new AutoMobility();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		long start = System.nanoTime();
        Scheduler.getInstance().run();
		m_driveBaseSimple.getValues();
       // if ( logger != null ) logger.logValues(start);
		//m_driveBase.getValues(); //put driveBase info on SmartDashboard
	}

	@Override
	public void teleopInit() {
		System.out.println("init1");
		initializeNewPhase(ValueLogger.TELEOP_PHASE);
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		System.out.println("init");
		//m_turret.enable();
		//m_flywheel.enable();
		//m_indexer.enable();
		//m_driveBaseSimple.resetEncoders();
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		try
		{
			log = new FileOutputStream("log.csv");
		}
		catch(Exception e)
		{
			
		}
		//m_gearCameraThread.setRunning(true);
		//m_gearCameraThread.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		long start = System.nanoTime();
		boolean on = true;
    
		Scheduler.getInstance().run();
		if ( logger != null ) logger.logValues(start);
		/*SmartDashboard.putNumber("Turret Encoder", m_turret.getEncoder());
		SmartDashboard.putNumber("Turret Degrees", m_turret.getDegrees());
		SmartDashboard.putNumber("Turret Heading", m_turret.getHeading());
		SmartDashboard.putNumber("Turret Setpoint", m_turret.getSetpoint());*/
		//m_driveBase.getValues(); //put driveBase info on SmartDashboard
		m_driveBaseSimple.getValues();
		
		SmartDashboard.putNumber("Gyro Yaw",Gyro.getYaw());
		SmartDashboard.putBoolean("Is Running", m_flywheel.running());
		SmartDashboard.putNumber("Flywheel Encoder", m_flywheel.getEncoder().get());
		SmartDashboard.putBoolean("gear intake limit", m_gearIntake.getGearIntakeSwitch());
		//SmartDashboard.putNumber("Turret Motor", m_turret.getTurretMotorActual());
		/*if (m_gearIntake.getGearIntakeSwitch() == false)
    	{
    		m_gearIntake.setGearIsLoaded(true);put back
    	}*/
		SmartDashboard.putNumber("PDP voltage", m_PDP.getVoltage());
		SmartDashboard.putNumber("PDP port 4", m_PDP.getCurrent(4));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	private void initializeNewPhase ( String whichPhase )
    {
        if ( autonomousCommand != null ) {
            autonomousCommand.cancel();
            autonomousCommand = null;
        }
    	//if ( m_iAmARealRobot ) {
            //Robot.getDriveBase().initialize();
            //Robot.getIntake().initialize();
    	//}
        Gyro.reset();
        if ( logger != null ) logger.initializePhase(whichPhase);
    }
	
	public void gatherValues ( ValueLogger logger )
    {
		logger.logDoubleValue ( "Gyro Yaw", Gyro.getYaw() );
    	logger.logDoubleValue ( "Gyro Pitch", Gyro.getPitch() );
    	logger.logDoubleValue ( "Gyro Roll", Gyro.getRoll() );	
    	logger.logBooleanValue( "IMU_Connected", Gyro.IMU_Connected() );
    	logger.logBooleanValue( "IMU_IsCalibrating", Gyro.IMU_IsCalibrating() );
    }
}

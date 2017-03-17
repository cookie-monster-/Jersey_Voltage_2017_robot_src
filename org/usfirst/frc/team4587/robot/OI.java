package org.usfirst.frc.team4587.robot;

import org.usfirst.frc.team4587.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import utility.JoyButton;
import utility.LogDataSource;
import utility.ValueLogger;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI implements LogDataSource {
	Joystick  stick1;
	Button	  buttonA1, buttonB1, buttonX1, buttonY1, leftBumper1, rightBumper1;
	JoyButton leftTrigger1, rightTrigger1;
	Joystick  stick2;
	Button	  buttonA2, buttonB2, buttonX2, buttonY2, leftBumper2, rightBumper2;
	JoyButton leftTrigger2, rightTrigger2;
	
    public OI()
    {
    	stick1			= new Joystick(1);
    	buttonA1		= new JoystickButton(stick1, 1);
    	buttonB1		= new JoystickButton(stick1, 2);
    	buttonX1		= new JoystickButton(stick1, 3);
    	buttonY1		= new JoystickButton(stick1, 4);
    	leftBumper1 	= new JoystickButton(stick1, 5);
    	leftTrigger1	= new JoyButton(stick1, JoyButton.JoyDir.DOWN, 2);
    	rightBumper1	= new JoystickButton(stick1, 6);
    	rightTrigger1	= new JoyButton(stick1, JoyButton.JoyDir.DOWN, 3);
    	
    	stick2			= new Joystick(2);
    	buttonA2		= new JoystickButton(stick2, 1);
    	buttonB2		= new JoystickButton(stick2, 2);
    	buttonX2		= new JoystickButton(stick2, 3);
    	buttonY2		= new JoystickButton(stick2, 4);
    	leftBumper2 	= new JoystickButton(stick2, 5);
    	leftTrigger2	= new JoyButton(stick2, JoyButton.JoyDir.DOWN, 2);
    	rightBumper2	= new JoystickButton(stick2, 6);
    	rightTrigger2	= new JoyButton(stick2, JoyButton.JoyDir.DOWN, 3);

    	/*
    	//buttonA1.whenPressed(new testTurretIncrease());
    	buttonX1.whenPressed(new ToggleAimMode());
    	//buttonB1.whenPressed(new testTurretDecrease());
    	//buttonY1.whenPressed(new testTurretSetSetpoint());
    	leftBumper1.whenPressed(new ToggleGearIntakeMotors());
    	rightBumper1.whenPressed(new EjectGear());
    	buttonA1.whenPressed(new ChangeLEDMode((byte)64));
    	buttonY1.whenPressed(new ChangeLEDMode((byte)65));
    	buttonB1.whenPressed(new ChangeLEDMode((byte)66));
    	*/
    	//buttonA1.whenPressed(new PlanPathTest());
    	//buttonA1.whenPressed(new AutonomousMotionTesting(0.5));
    	/*
    	buttonX1.whenPressed(new AimFixedShooter());
		buttonB1.whenPressed(new DriveSimpleWithJoysticks());
		buttonY1.whenPressed(new AutonomousTurnToAngleSimple(135, 0.6, 2));
		leftBumper1.whenPressed(new PlanPathTest());
		rightBumper1.whenPressed(new FollowPath());
		*/
    	/*buttonA1.whenPressed(new TestFlywheelIncrease());
    	buttonB1.whenPressed(new TestFlywheelDecrease());
    	buttonX1.whenPressed(new ToggleFlywheelRunning());
    	buttonY1.whenPressed(new SetFlywheel(3000, 0.8));*/
    	
    	/*buttonX1.whenPressed(new ClimbMotorStart());
    	buttonY1.whenPressed(new ClimbMotorStop());
    	buttonB1.whenPressed(new ToggleGearIntakeMotors());
    	rightBumper1.whenPressed(new EjectGear());
    	leftBumper1.whenPressed(new ToggleGearIntakeUpDown());
    	//buttonB1.whenPressed(new DriveSimpleWithJoysticks());
    	buttonA1.whenPressed(new AutoGearIntakeMotors());*/
    	
    	//buttonA1.whenPressed(new AutoGearTest());
    	//buttonA1.whenPressed(new RunGearCameraThread());
    	//buttonX1.whenPressed(new AimGearNoPi());
    	buttonA1.whenPressed(new ChangeLEDMode((byte)64));
    	buttonB1.whenPressed(new ChangeLEDMode((byte)65));
    	buttonX1.whenPressed(new ChangeLEDMode((byte)66));
    	buttonY1.whenPressed(new ChangeLEDMode((byte)67));
    	
    	/*buttonA1.whenPressed(new AimGear());
    	buttonX1.whenPressed(new AimGearDrive());
    	buttonB1.whenPressed(new DriveSimpleWithJoysticks());
    	buttonY1.whenPressed(new AutonomousDriveStraightDistance(1000, 0.7));*/
    	
    	//  D R I V E R   C O N T R O L L E R

    	/*if ( Robot.iAmARealRobot()) {
    		buttonB1.whenPressed(new DriveWithJoysticks());
    		buttonA1.whenPressed(new RunCameraThread());
    		buttonX1.whenPressed(new AutomaticAim(0, 0.6));
	    	buttonY1.whenPressed(new ToggleFlashlight());
	    	leftBumper1.whenPressed(new ToggleArmPiston());
	    	leftTrigger1.whileHeld(new LowerAndHoldArm());
	    	rightBumper1.whenPressed(new LowShot());
    		rightTrigger1.whenPressed(new HighShot());
    	}
    	else 
    	{
    		buttonA1.whenPressed(new RunCameraThread());
    	}*/

    	//  I N T A K E   C O N T R O L L E R

    	/*if ( Robot.iAmARealRobot()) {
	    	//buttonA2.whenPressed(new AutoIntakeBall());
	    	buttonA2.whenPressed(new ToggleFlashlight());
	    	buttonB2.whenPressed(new StopIntakeMotors());
	    	buttonX2.whenPressed(new StartIntakeMotors(Parameters.getDouble("Intake Motor Eject Speed", -1.0)));
	    	buttonY2.whenPressed(new StartIntakeMotors(Parameters.getDouble("Intake Motor Input Speed", 1.0)));
	    	rightBumper2.whenPressed(new ToggleIntakePiston());
	    	leftBumper2.whenPressed(new PulseLowGoalSolenoid(1000));
    	}*/
    }
    
    public double getTurn()
    {
    	return stick1.getRawAxis(4);
    }
    
    public double getDrive()
    {
    	return stick1.getRawAxis(1) * -1;
    }
    
    public void rumble( float value )
    {
    	stick1.setRumble(Joystick.RumbleType.kRightRumble, value);
    }
    
    public void gatherValues ( ValueLogger logger )
    {
    	logger.logDoubleValue("Driving Joystick Value", getDrive());
    	logger.logDoubleValue("Turning Joystick Value", getTurn());
    }
}

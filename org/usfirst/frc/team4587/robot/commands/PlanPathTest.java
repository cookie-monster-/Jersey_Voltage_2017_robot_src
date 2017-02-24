package org.usfirst.frc.team4587.robot.commands;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.PathPlanner;

/**
 *
 */
public class PlanPathTest extends Command {

    public PlanPathTest() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	long start = System.currentTimeMillis();
		System.setProperty("java.awt.headless", "true"); //enable this to true to emulate roboRio environment


		//create waypoint path
		double[][] waypoints = new double[][]{

			{7,16},
			{11,16},
			{17,28},
			{23,28},
		}; 

		double totalTime = 1.0; //seconds
		double timeStep = 0.02; //period of control loop on Rio, seconds
		double robotTrackWidth = 2.25; //distance between left and right wheels, feet

		final PathPlanner path = new PathPlanner(waypoints);
		path.calculate(totalTime, timeStep, robotTrackWidth);

		System.out.println("smoothLeftVelocity: " + path.smoothLeftVelocity[0][0]);
		
		System.out.println("Time in ms: " + (System.currentTimeMillis()-start));
    	try{
    		FileWriter w = new FileWriter("/home/lvuser/Path.csv");
    		double prevLeft = 0;
    		double prevRight = 0;
    		double xLeft = 0;
    		double xRight = 0;
    		for (int i = 0; i<path.smoothLeftVelocity.length;i++)
    		{
    			double vLeft = path.smoothLeftVelocity[i][1] / 50;
    			double vRight = path.smoothRightVelocity[i][1] / 50;
    			double aLeft = vLeft-prevLeft;
    			double aRight = vRight-prevRight;
    			xLeft += (vLeft+prevLeft)/2;
    			xRight += (vRight+prevRight)/2;
    			double desiredAngle = (Math.asin((xLeft - xRight)/551)) * (180/Math.PI);
    			w.write(aLeft + "," + vLeft + "," + xLeft + ","+ path.leftPath[i][1] + "," +aRight + "," + vRight + "," + xRight + ","+ path.rightPath[i][1] + "," + desiredAngle +"\n");
    			prevLeft = vLeft;
    			prevRight = vRight;
    		}
    		w.close();
    	}catch(Exception e){
    		System.out.println(e);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

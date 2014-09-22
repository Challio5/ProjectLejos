package main;

import lejos.nxt.Motor;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;

public class Takel extends Thread implements FeatureListener{
	
	//takelhoogte
	private static int hoogte = 5000;
	
	//stop boolean voor thread
	private boolean stop;
	
	//constructor
	public Takel()
	{
		//threadnaam
		super("kraan");
		
		//motorsnelheid
		Motor.B.setSpeed(2000);
	}
	
	//main thread
	public void run()
	{
		//kraan beweegt op en neer om een object vast te grijpen
		while(!stop)
		{	
			Motor.B.rotate(hoogte);
			Motor.B.rotate(-hoogte);
		}
	}
	
	//Is actief wanneer er een object aan de kraan gedetecteerd is
	public void featureDetected(Feature feature, FeatureDetector detector)
	{
		//stop main thread en takel object omhoog en hou vast
		stop = true;
		Motor.B.rotate(10000);
		Motor.B.stop();
	}
}

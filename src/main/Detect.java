package main;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.objectdetection.Feature;
import lejos.robotics.objectdetection.FeatureDetector;
import lejos.robotics.objectdetection.FeatureListener;

public class Detect extends Thread implements FeatureListener{
	
	//lichtsensor
	private LightSensor lightSensor;
	
	//setup voor pilot
	private DifferentialPilot pilot;
	private static double wheelDiameter = 2.6;
	private static double trackWidth = 28.0;
	
	//feature waarden
	private float range;
	private float angle;
	
	//stop boolean voor thread
	private boolean stop;

	//constructor
	public Detect(){
		//threadnaam
		super("detector");
		
		//lichtsensor
		lightSensor = new LightSensor(SensorPort.S2);
		
		//hardware
		pilot = new DifferentialPilot(wheelDiameter, trackWidth, Motor.C, Motor.A);
		
	}
	
	//main thread
	public void run()
	{
		while (!stop)
		{
			// Als witte lijn wordt gedetecteerd
			// Naar achter en na 3 sec sturen
			if(lightSensor.getNormalizedLightValue() > 550)
			{			
				pilot.backward();
			
				try 
				{
					Thread.sleep(3000);
				} 
				catch (InterruptedException e) {}
			
				pilot.steer(75, 150);
			}
			// Anders vooruit
			else
			{
				pilot.forward();
			}
		}
	}
	
	//Is actief wanneer er een object in de ring gedetecteerd is
	public void featureDetected(Feature feature, FeatureDetector detector)
	{
		//featurewaarden worden geinitialiseerd
		range = feature.getRangeReading().getRange();
		angle = feature.getRangeReading().getAngle();
		
		//stop main thread en stuur bij na laatste positie object
		stop = true;
		pilot.rotate(angle);
		pilot.travel(range);
		stop = false;
	}
}


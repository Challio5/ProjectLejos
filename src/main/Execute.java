package main;

import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.RangeFeatureDetector;
import lejos.robotics.objectdetection.TouchFeatureDetector;

public class Execute{
	
	public static void main(String[] args)
	{	
		
		//scanwaarden
		int scanDistance = 50;
		int scanPeriod = 500;
		
		//klassen
		Detect takelauto = new Detect();
		Takel kraan = new Takel();
		
		//objectlistener
		UltrasonicSensor ultrasoon = new UltrasonicSensor(SensorPort.S1);
		RangeFeatureDetector detect = new RangeFeatureDetector(ultrasoon, scanDistance, scanPeriod);
		detect.addListener(takelauto);		
		
		//takellistener
		TouchSensor touch = new TouchSensor(SensorPort.S3);
		TouchFeatureDetector takel = new TouchFeatureDetector(touch);
		takel.addListener(kraan);
		
		//threads
		takelauto.start();
		kraan.start();
		
	}
}


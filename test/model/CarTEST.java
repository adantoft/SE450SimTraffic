package model;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parameters.ModelConfig;

public class CarTEST {

	//TODO Tests for properties
	//TODO Tests for front position
	//TODO Tests for velocity
	
	ModelConfig config = ModelConfig.createConfig();
	CarObj car1 = new CarObj();
		
	@Test 
	public void testConstructorAndAttributes() {

		Assert.assertEquals(car1.getTimeStep() , config.getSimTimeStep(), .01);
		Assert.assertTrue(car1.getMaxVelocity() >= config.getCarMaxVelocityMin());
		Assert.assertTrue(car1.getMaxVelocity() <= config.getCarMaxVelocityMax());
		Assert.assertTrue(car1.getBrakeDistance() >= config.getCarBrakeDistanceMin());
		Assert.assertTrue(car1.getBrakeDistance() <= config.getCarBrakeDistanceMax());
		Assert.assertTrue(car1.getStopDistance() >= config.getCarStopDistanceMin());
		Assert.assertTrue(car1.getStopDistance() <= config.getCarStopDistanceMax());
		Assert.assertTrue(car1.getLength() >= config.getCarLengthMin());
		Assert.assertTrue(car1.getLength() <= config.getCarLengthMax());
		Assert.assertEquals(car1.getFrontPosition(), 0.0, .01);
		Assert.assertEquals(car1.getRearPosition(), car1.getFrontPosition() - car1.getLength(), .01);

	}

	@Test
	public void testCarAndRoad() {
		Sink sink1 = new Sink();
		Road road1 = new Road(sink1);
		Assert.assertEquals(car1.getCurrentRoad(), null);
		road1.accept(car1, 0.0);
		Assert.assertEquals(car1.getCurrentRoad(), road1);
		Assert.assertEquals(0.0, car1.getFrontPosition(), .01);
		car1.setFrontPosition(5.0);
		Assert.assertEquals(5.0, car1.getFrontPosition(), .01);		
	}

	@Test
	public void testCarRoadToRoad() {
		car1 = new CarObj();
		Sink sink1 = new Sink();
		Road road2 = new Road(sink1);
		Road road1 = new Road(road2);
		road1.accept(car1, 0.0);
		double timeToRun = road1.getEndPosition()/car1.getMaxVelocity() + 1; //how many steps to finish the road
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car1.run(0);
		}
		Assert.assertEquals(car1.getCurrentRoad(), road2);
	}

	@Test
	public void testEquals() { 
		
	}

	@Test
	public void testCompareTo() { 
		
	}

	@Test
	public void testToString() { 

	}
	
}

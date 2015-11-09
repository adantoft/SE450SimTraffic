package model;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;
import parameters.ModelConfig;

public class CarTEST {

	//TODO Tests for properties
	//TODO Tests for front position
	//TODO Tests for velocity
	
	ModelConfig config = ModelConfig.createConfig();
	Car car1 = new CarObj();
	
	
	@Test 
	public void testConstructorAndAttributes() {

		Assert.assertTrue(car1.getMaxVelocity() >= config.getCarMaxVelocityMin());
		Assert.assertTrue(car1.getMaxVelocity() <= config.getCarMaxVelocityMax());
		Assert.assertTrue(car1.getBrakeDistance() >= config.getCarBrakeDistanceMin());
		Assert.assertTrue(car1.getBrakeDistance() <= config.getCarBrakeDistanceMax());
		Assert.assertTrue(car1.getStopDistance() >= config.getCarStopDistanceMin());
		Assert.assertTrue(car1.getStopDistance() <= config.getCarStopDistanceMax());
		Assert.assertTrue(car1.getLength() >= config.getCarLengthMin());
		Assert.assertTrue(car1.getLength() <= config.getCarLengthMax());
		Assert.assertEquals(car1.getFrontPosition(), 0.0, .01);
	}

	@Test
	public void testConstructorExceptionDirector() {
	
	}

	@Test
	public void testHashCode() {
		
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

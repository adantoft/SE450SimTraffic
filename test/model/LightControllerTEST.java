package model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import model.StaticFactory.Orientation;
import parameters.ModelConfig;

public class LightControllerTEST {

	//TODO Tests for properties
	//TODO Tests for front position
	//TODO Tests for velocity
	
	ModelConfig config = ModelConfig.createConfig();
	Light light1 = new LightController();
	
	@Test 
	public void testConstructorAndAttributes() {

		Assert.assertTrue(light1.getGreenDurationNS() <= config.getTrafficLightGreenTimeMax());
		Assert.assertTrue(light1.getGreenDurationNS() >= config.getTrafficLightGreenTimeMin());
		Assert.assertTrue(light1.getYellowDurationNS() <= config.getTrafficLightYellowTimeMax());
		Assert.assertTrue(light1.getYellowDurationNS() >= config.getTrafficLightYellowTimeMin());
		Assert.assertTrue(light1.getGreenDurationEW() <= config.getTrafficLightGreenTimeMax());
		Assert.assertTrue(light1.getGreenDurationEW() >= config.getTrafficLightGreenTimeMin());
		Assert.assertTrue(light1.getYellowDurationEW() <= config.getTrafficLightYellowTimeMax());
		Assert.assertTrue(light1.getYellowDurationEW() >= config.getTrafficLightYellowTimeMin());
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

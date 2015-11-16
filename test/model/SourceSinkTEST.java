package model;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

import model.StaticFactory.Orientation;
import parameters.ModelConfig;

public class SourceSinkTEST {

	ModelConfig config = ModelConfig.createConfig();
	Sink sink1 = new Sink();
	Road road1 = new Road(sink1, Orientation.NS);
	CarSourceObj source1 = new CarSourceObj(road1, Orientation.NS);

	@Test 
	public void testSourceConstructorAndAttributes() {
		Assert.assertTrue(source1.getProductionFrequency() <= config.getCarGenerationDelayMax());
		Assert.assertTrue(source1.getProductionFrequency() >= config.getCarGenerationDelayMin());

	}

	@Test
	public void testSourcingAndSinkingCar() {
		
		Assert.assertTrue(road1.getCars().isEmpty());
		source1.run(0);
		Assert.assertTrue(!road1.getCars().isEmpty());
		double timeToRun; 
		CarObj car1;
		for (Car c : road1.getCars()) {
			car1 = (CarObj) c;
			timeToRun = road1.getEndPosition()/c.getMaxVelocity() + 10; //how many steps to finish the road
			for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
				car1.run(0.0);
			}
		}
		Assert.assertTrue(road1.getCars().isEmpty());
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

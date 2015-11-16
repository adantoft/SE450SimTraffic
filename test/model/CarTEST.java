package model;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.StaticFactory.LightState;
import model.StaticFactory.Orientation;
import parameters.ModelConfig;

public class CarTEST {

	//TODO Car vs Light
	//TODO Car vs Light	vs Car
	//TODO make all classes private that I can

	
	ModelConfig config = ModelConfig.createConfig();
	CarObj car1 = new CarObj(Orientation.NS);
		
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
	public void testCarVsRoad() {
		Sink sink1 = new Sink();
		Road road1 = new Road(sink1, Orientation.NS);
		Assert.assertEquals(car1.getCurrentRoad(), null);
		road1.accept(car1, 0.0);
		Assert.assertEquals(car1.getCurrentRoad(), road1);
		Assert.assertEquals(0.0, car1.getFrontPosition(), .01);
		car1.setFrontPosition(5.0);
		Assert.assertEquals(5.0, car1.getFrontPosition(), .01);		
	}

	@Test
	public void testCarRoadToRoad() {
		car1 = new CarObj(Orientation.NS);
		Sink sink1 = new Sink();
		Road road2 = new Road(sink1, Orientation.NS);
		Road road1 = new Road(road2, Orientation.NS);
		road1.accept(car1, 0.0);
		double timeToRun = road1.getEndPosition()/car1.getMaxVelocity() + 10; //how many steps to finish the road
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car1.run(0);
		}
		Assert.assertEquals(car1.getCurrentRoad(), road2);
	}
	
	@Test
	public void testCarVsCar() {
		car1 = new CarObj(Orientation.NS);
		CarObj car2 = new CarObj(Orientation.NS);
		Sink sink1 = new Sink();
		Road road1 = new Road(sink1, Orientation.NS);
		Assert.assertEquals(car1.getCurrentRoad(), null);
		road1.accept(car1, 0.0);
		road1.accept(car2, road1.getEndPosition() - 50); //sets car2 50 from end of road
		Assert.assertEquals(car1.getCurrentRoad(), road1);
		Assert.assertEquals(car2.getCurrentRoad(), road1);
		double timeToRun = road1.getEndPosition()/car1.getMaxVelocity() + 10; //how many steps to finish the road
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car1.run(0);
		}	
		Assert.assertEquals(car1.getCurrentRoad(), road1);
		Assert.assertEquals(car2.getCurrentRoad(), road1);
		Assert.assertTrue(car1.getFrontPosition()<car2.getFrontPosition());
	}
	
	@Test
	public void testCarVsCarVsCar() {
		car1 = new CarObj(Orientation.NS);
		CarObj car2 = new CarObj(Orientation.NS);
		CarObj car3 = new CarObj(Orientation.NS);
		Sink sink1 = new Sink();
		Road road1 = new Road(sink1, Orientation.NS);
		Assert.assertEquals(car1.getCurrentRoad(), null);
		road1.accept(car1, 0);
		road1.accept(car2, road1.getEndPosition() - 10); //sets car2 10 from end of road
		road1.accept(car3, 50); //sets car3 middle of the road
		Assert.assertEquals(car1.getCurrentRoad(), road1);
		Assert.assertEquals(car2.getCurrentRoad(), road1);
		Assert.assertEquals(car3.getCurrentRoad(), road1);
		double timeToRun = road1.getEndPosition()/Math.min(car1.getMaxVelocity(), car2.getMaxVelocity()) + 10; //how many steps to finish the road
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car1.run(0);
		}	
		Assert.assertEquals(car1.getCurrentRoad(), road1);
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car3.run(0);
		}
		Assert.assertEquals(car3.getCurrentRoad(), road1);
		Assert.assertEquals(car1.getCurrentRoad(), road1);
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car2.run(0);
		}
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car3.run(0);
			car1.run(0);
		}
		Assert.assertTrue(road1.getCars(null).isEmpty());
	}
	
	
	@Test
	public void testCarVsRedLightGreenLight() {
		car1 = new CarObj(Orientation.NS);
		Sink sink1 = new Sink();
		Intersection intersect = new Intersection(sink1, Orientation.NS);
		Road road1 = new Road(intersect, Orientation.NS);
		road1.accept(car1, 0.0);
		Assert.assertEquals(road1, car1.getCurrentRoad());
		intersect.getLight().setLightState(LightState.EWGREEN_NSRED);
		Assert.assertEquals(LightState.EWGREEN_NSRED, intersect.getLightState());
		double timeToRun = road1.getEndPosition()/car1.getMaxVelocity() + 10; //how many steps to finish the road
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car1.run(0);
		}	
		Assert.assertEquals(road1, car1.getCurrentRoad());
		intersect.getLight().setLightState(LightState.NSGREEN_EWRED);
		Assert.assertEquals(LightState.NSGREEN_EWRED, intersect.getLightState());
		for (double x = 0; x <= 10 ; x+= config.getSimTimeStep()){
			car1.run(0);
		}
		Assert.assertEquals(intersect, car1.getCurrentRoad());
	}
	
	@Test
	public void testCarVsYellowLight() {
		config.setSimTimeStep(config.getCarBrakeDistanceMin());
		car1 = new CarObj(Orientation.NS);
		Sink sink1 = new Sink();
		Intersection intersect = new Intersection(sink1, Orientation.NS);
		Road road1 = new Road(intersect, Orientation.NS);
		road1.accept(car1, 0.0);
		Assert.assertEquals(road1, car1.getCurrentRoad());
		intersect.getLight().setLightState(LightState.NSYELLOW_EWRED);
		Assert.assertEquals(LightState.NSYELLOW_EWRED, intersect.getLightState());
		//System.out.println("Road1 Position: " + road1.getEndPosition());

		double timeToRun = road1.getEndPosition()/car1.getMaxVelocity() + 10; //how many steps to finish the road
		for (double x = 0; x <= timeToRun; x+= config.getSimTimeStep()){
			car1.run(0);
			//System.out.println("Car1 Position: " + car1.getFrontPosition());
		}	
		Assert.assertEquals(road1, car1.getCurrentRoad());
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

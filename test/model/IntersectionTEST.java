package model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import model.StaticFactory.Orientation;
import parameters.ModelConfig;

public class IntersectionTEST {

	//TODO Tests for properties
			//TODO Tests for front position
			//TODO Tests for velocity
			
			ModelConfig config = ModelConfig.createConfig();
			Sink sinkNS = new Sink();
			Sink sinkEW = new Sink();
			Intersection intersect = new Intersection(sinkNS, Orientation.NS);
			Road roadNS = new Road(intersect, Orientation.NS);
			Road roadEW = new Road(intersect, Orientation.NS);
			CarObj carNS = new CarObj(Orientation.NS);
			CarObj carEW = new CarObj(Orientation.EW);
			
			@Test 
			public void testConstructorAndAttributes() {

				Assert.assertTrue(intersect.getEndPosition() <= config.getIntersectionLengthMax());
				Assert.assertTrue(intersect.getEndPosition() >= config.getIntersectionLengthMin());
				Assert.assertTrue(intersect.getCars(Orientation.NS).isEmpty());
				Assert.assertTrue(intersect.getCars(Orientation.EW).isEmpty());
				//intersect.setNextRoad(sinkNS, Orientation.NS);
				intersect.setNextRoad(sinkEW, Orientation.EW);
				Assert.assertSame(sinkNS, intersect.getNextRoad(Orientation.NS));
				Assert.assertSame(sinkEW, intersect.getNextRoad(Orientation.EW));
			}

			@Test
			public void testAcceptor() {
				Assert.assertTrue(intersect.accept(carNS, 0.0));
				Assert.assertTrue(intersect.accept(carEW, 0.0));
				//TODO test when position is empty	
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

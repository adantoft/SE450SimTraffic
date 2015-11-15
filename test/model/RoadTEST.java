package model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import model.StaticFactory.Orientation;
import parameters.ModelConfig;

public class RoadTEST {

	//TODO Tests for properties
		//TODO Tests for front position
		//TODO Tests for velocity
		
		ModelConfig config = ModelConfig.createConfig();
		Sink sink1 = new Sink();
		Road road1 = new Road(sink1);
		CarObj car1 = new CarObj(Orientation.NS);
		
		@Test 
		public void testConstructorAndAttributes() {

			Assert.assertTrue(road1.getEndPosition() <= config.getRoadSegmentLengthMax());
			Assert.assertTrue(road1.getEndPosition() >= config.getRoadSegmentLengthMin());
			Assert.assertTrue(road1.getCars().isEmpty());
		}

		@Test
		public void testAcceptor() {
			
			Assert.assertTrue("Adding a car",road1.accept(car1, 100.0));
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

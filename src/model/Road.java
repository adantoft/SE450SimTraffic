package model;

import java.util.Set;
import parameters.ModelConfig;
import java.util.HashSet;

/**
 * A road holds CarObjs.
 */
final class Road implements CarAcceptor{


	private ModelConfig config = ModelConfig.createConfig();
	private Set<Car> cars;
	private double endPosition;
	CarAcceptor nextRoad;
	

	Road() { 
		
		this.endPosition = Math.max(config.getRoadSegmentLengthMax() * Math.random(), config.getRoadSegmentLengthMin());
		this.cars = new HashSet<Car>();
	}

	public Set<Car> getCars() {
		return cars;
	}
	@Override
	public boolean accept(CarObj c, double frontPosition) {
		cars.remove(c); //don't know if this is necesary
		if (frontPosition>endPosition){
			return nextRoad.accept(c, frontPosition-endPosition);
		}else{
			c.setFrontPosition(frontPosition);
			c.setCurrentRoad(this);
			cars.add(c);
			return true;
		}
	}
	
	public boolean remove(CarObj c){
		if (cars.contains(c)){
			cars.remove(c);
			return true;
		} else {
			return false;
		}
	}
	
	public double distanceToObstacle(double fromPosition) {
		double obstaclePosition = this.distanceToCarRear(fromPosition);
		if (obstaclePosition == Double.POSITIVE_INFINITY) {
			double distanceToEnd = this.endPosition - fromPosition;
			obstaclePosition = nextRoad.distanceToObstacle(0.0) + distanceToEnd;
			return obstaclePosition;
		}
		return obstaclePosition - fromPosition;
	}

	private Double distanceToCarRear(Double fromPosition) {
		double carRearPosition = Double.POSITIVE_INFINITY;
		for (Car c : cars)
			if (c.getRearPosition() >= fromPosition &&
			c.getRearPosition() < carRearPosition)
				carRearPosition = c.getRearPosition();
		return carRearPosition;
	}
	
	public double getEndPosition(){
		return endPosition;
	}
	public CarAcceptor getNextRoad(){
		return nextRoad;
	}
}

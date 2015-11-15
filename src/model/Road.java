package model;

import java.util.Set;

import model.StaticFactory.Orientation;
import parameters.ModelConfig;
import java.util.HashSet;

//TODO equals, tostring, compareTo

/**
 * A road holds CarObjs.
 */
final class Road implements CarAcceptor{

	private ModelConfig config = ModelConfig.createConfig();
	private Set<Car> cars;
	private double endPosition;
	private CarAcceptor nextRoad;
	private Orientation orientation;

	Road(CarAcceptor next) { 
		this.endPosition = Math.max(config.getRoadSegmentLengthMax() * Math.random(), config.getRoadSegmentLengthMin());
		this.cars = new HashSet<Car>();
		this.nextRoad = next;
	}
	/**
	 * Accepts new car on this road.
	 */
	@Override
	public boolean accept(CarObj car, double frontPosition) {
		cars.remove(car); //don't know if this is necessary
		if (frontPosition>endPosition){
			return nextRoad.accept(car, frontPosition-endPosition);
		}else{
			car.setCurrentRoad(this);
			car.setFrontPosition(frontPosition);
			cars.add(car);
			return true;
		}
	}
	@Override
	public boolean remove(CarObj c){
		if (cars.contains(c)){
			cars.remove(c);
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Finds distance to closest object from fromPosition on this road or next road.
	 */	
	@Override
	public double distanceToObstacle(double fromPosition, Orientation orientation) {
		double obstaclePosition = this.getClosestObjPosition(fromPosition);
		if (obstaclePosition == Double.POSITIVE_INFINITY) {
			double distanceToEnd = this.endPosition - fromPosition;
			obstaclePosition = nextRoad.distanceToObstacle(0.0, orientation) + distanceToEnd;
			return obstaclePosition;
		}
		return obstaclePosition - fromPosition;
	}
	/**
	 * Finds position to closest object from fromPosition on this road.
	 */
	
	private double getClosestObjPosition(double fromPosition) {
		double carRearPosition = Double.POSITIVE_INFINITY;
		for (Car c : cars)
			if (c.getRearPosition() >= fromPosition && c.getRearPosition() < carRearPosition)
				carRearPosition = c.getRearPosition();
		return carRearPosition;
	}
	public double getEndPosition(){
		return endPosition;
	}
	public CarAcceptor getNextRoad(Orientation orientation){
		return nextRoad;
	}
	public void setNextRoad(CarAcceptor nextRoad) {
		this.nextRoad = nextRoad;
	}
	public Set<Car> getCars() {
		return cars;
	}
}

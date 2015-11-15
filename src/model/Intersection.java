package model;

import java.util.HashSet;
import java.util.Set;

import model.StaticFactory.Orientation;
import parameters.ModelConfig;

public class Intersection implements CarAcceptor{

	private ModelConfig config = ModelConfig.createConfig();
	private Set<Car> carsNS;
	private Set<Car> carsEW;
	private double endPosition;
	private CarAcceptor nextRoadNS;
	private CarAcceptor nextRoadEW;

	Intersection() { //doesn't set next road in construction as there are two, both might not be ready
		this.endPosition = Math.max(config.getIntersectionLengthMax() * Math.random(), config.getIntersectionLengthMin());
		this.carsNS = new HashSet<Car>();
		this.carsEW = new HashSet<Car>();
	}
	/**
	 * Accepts new car on this road.
	 */
	@Override
	public boolean accept(CarObj car, double frontPosition) { //need to conform to carAcceptor		
		if(car.getOrientation() == Orientation.NS){
			if (frontPosition>endPosition){ //car will be in next road
				return nextRoadNS.accept(car, frontPosition-endPosition);
			}else{ //car remains this this road
				car.setCurrentRoad(this);
				car.setFrontPosition(frontPosition);
				carsNS.add(car);
				return true;
			}
		}else { //car is EW bound
			if (frontPosition>endPosition){ //car will be in next road
				return nextRoadEW.accept(car, frontPosition-endPosition);
			}else{//car remains this this road
				car.setCurrentRoad(this);
				car.setFrontPosition(frontPosition);
				carsEW.add(car);
				return true;
			}
		}	
	}

	public boolean remove(CarObj c){
		if (carsNS.contains(c)){
			carsNS.remove(c);
			return true;
		} else if (carsEW.contains(c)){
			carsEW.remove(c);
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Finds distance to closest object from fromPosition on this road or next road.
	 */	
	public double distanceToObstacle(double fromPosition, Orientation orientation) {
		if (orientation == Orientation.NS){
			double obstaclePosition = this.getClosestObjPosition(fromPosition, carsNS);
			if (obstaclePosition == Double.POSITIVE_INFINITY) {
				double distanceToEnd = this.endPosition - fromPosition;
				obstaclePosition = nextRoadNS.distanceToObstacle(0.0, orientation) + distanceToEnd;
				return obstaclePosition;
			}
			return obstaclePosition - fromPosition;
		}else{ //orientation is EW
			double obstaclePosition = this.getClosestObjPosition(fromPosition, carsEW);
			if (obstaclePosition == Double.POSITIVE_INFINITY) {
				double distanceToEnd = this.endPosition - fromPosition;
				obstaclePosition = nextRoadEW.distanceToObstacle(0.0, orientation) + distanceToEnd;
				return obstaclePosition;
			}
			return obstaclePosition - fromPosition;
		}
	}
	/**
	 * Finds position to closest object from fromPosition on this road.
	 */	
	private double getClosestObjPosition(double fromPosition, Set<Car> cars) {
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
		if (orientation == Orientation.NS){
			return nextRoadNS;
		}else{ //orientation is EW
			return nextRoadEW;
		}
	}
	public void setNextRoad(CarAcceptor nextRoad, Orientation orientation) {
		if (orientation == Orientation.NS){
			this.nextRoadNS = nextRoad;
		}else{ //orientation is EW
			this.nextRoadEW = nextRoad;
		}
	}
	public Set<Car> getCars(Orientation orientation) {
		if (orientation == Orientation.NS){
			return carsNS;
		}else{ //orientation is EW
			return carsEW;
		}
	}
}

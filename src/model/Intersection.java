package model;

import java.util.HashSet;
import java.util.Set;

import model.StaticFactory.LightState;
import model.StaticFactory.Orientation;
import parameters.ModelConfig;

public class Intersection implements CarAcceptor{

	private ModelConfig config = ModelConfig.createConfig();
	private Set<Car> carsNS;
	private Set<Car> carsEW;
	private double endPosition;
	private CarAcceptor nextRoadNS;
	private CarAcceptor nextRoadEW;
	private Light light;

	Intersection(CarAcceptor next, Orientation orientation) { //doesn't set next road in construction as there are two, both might not be ready
		this.endPosition = Math.max(config.getIntersectionLengthMax() * Math.random(), config.getIntersectionLengthMin());
		this.carsNS = new HashSet<Car>();
		this.carsEW = new HashSet<Car>();
		this.light = StaticFactory.makeLight();
		if (orientation == Orientation.NS){
			nextRoadNS = next;
		} else{
			nextRoadEW = next;
		}
	}
	/**
	 * Accepts new car on this intersection.
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
		double obstaclePosition;
		
		if (orientation == Orientation.NS){
			
			if (light.getLightState() == LightState.EWGREEN_NSRED || !carsEW.isEmpty()) {
				return 0.0; //not allowed into intersection as light is red or intersection is occupied by cars in opposite direction
			}
			obstaclePosition = this.getClosestObjPosition(fromPosition, carsNS);
			if (obstaclePosition == Double.POSITIVE_INFINITY) {
				double distanceToEnd = this.endPosition - fromPosition;
				obstaclePosition = nextRoadNS.distanceToObstacle(0.0, orientation) + distanceToEnd;
				return obstaclePosition;
			}
			return obstaclePosition - fromPosition;
		}else{ //orientation is EW
			if (light.getLightState() == LightState.NSGREEN_EWRED || !carsNS.isEmpty()) {
				return 0.0; //not allowed into intersection as light is red or intersection is occupied by cars in opposite direction
			}
			
			
			obstaclePosition = this.getClosestObjPosition(fromPosition, carsEW);
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
	@Override
	public Set<Car> getCars(Orientation orientation) {
		if (orientation == Orientation.NS){
			return carsNS;
		}else{ //orientation is EW
			return carsEW;
		}
	}
	@Override
	public LightState getLightState() {
		return light.getLightState();
	}
	@Override
	public Light getLight() {
		return light;
	}
}

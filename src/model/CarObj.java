package model;

import model.StaticFactory.Orientation;
import parameters.ModelConfig;
import timeserver.TimeServer;

//TODO probably dont need this detail
//TODO equals, tostring, compareTo
/**
 * A car remembers its position from the beginning of its road. 
 */
public class CarObj implements Agent, Car {

	private ModelConfig config = ModelConfig.createConfig();
	private TimeServer time;
	private double maxVelocity;
	private double brakeDistance;
	private double stopDistance;
	private double length;
	private double frontPosition;
	private CarAcceptor currentRoad;
	private double timeStep;
	private Orientation orientation;

	CarObj(Orientation orientation) { 

		this.maxVelocity = Math.max(config.getCarMaxVelocityMax() * Math.random(), config.getCarMaxVelocityMin());
		this.brakeDistance = Math.max(config.getCarBrakeDistanceMax() * Math.random(), config.getCarBrakeDistanceMin());
		this.stopDistance = Math.max(config.getCarStopDistanceMax() * Math.random(), config.getCarStopDistanceMin());
		this.length = Math.max(config.getCarLengthMax() * Math.random(), config.getCarLengthMin());
		this.time = config.getTimeServer();
		this.timeStep = config.getSimTimeStep();
		this.setOrientation(orientation);
	}

	public void run(double time) {
		setFrontPosition(getCurrentVelocity());
		this.time.enqueue(this.time.currentTime() + timeStep, this);
	}
	@Override
	public double getCurrentVelocity() {
		
		double distanceToObstacle = currentRoad.distanceToObstacle(frontPosition, orientation); //talks to road to grab next object after front position
		double velocity =  (maxVelocity / (brakeDistance - stopDistance))*(distanceToObstacle - stopDistance);
		velocity = Math.max(0.0, velocity);
		velocity = Math.min(maxVelocity, velocity);
		double nextFrontPosition = frontPosition + velocity * timeStep;
		return nextFrontPosition; //TODO could consolidate this code?
	}
	@Override
	public void setFrontPosition(double newPosition) {
		CarAcceptor road = this.currentRoad;
		if(newPosition > road.getEndPosition()){
			road.getNextRoad(orientation).accept(this, newPosition - road.getEndPosition());
			road.remove(this);
		}else {
			frontPosition = newPosition;
		}
	}
	@Override
	public double getMaxVelocity() {
		return maxVelocity;
	}
	@Override
	public double getBrakeDistance() {
		return brakeDistance;
	}
	@Override
	public double getStopDistance() {
		return stopDistance;
	}
	@Override
	public double getLength() {
		return length;
	}
	@Override
	public double getFrontPosition() {
		return frontPosition;
	}
	@Override
	public double getRearPosition() {
		return frontPosition - length;
	}
	public CarAcceptor getCurrentRoad() {
		return currentRoad;
	}
	public void setCurrentRoad(CarAcceptor currentRoad) {
		this.currentRoad = currentRoad;
	}
	public double getTimeStep() {
		return this.timeStep;
	}
	public Orientation getOrientation() { 	//getters and setters for car orientation not needed for this implementation
		return orientation;  				//they are kept in care car turning is ever implemented
	}
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}


}

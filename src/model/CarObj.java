package model;

import parameters.ModelConfig;
import timeserver.TimeServer;

//TODO probably dont need this detail
/**
 * A car remembers its position from the beginning of its road. 
 * Cars have random velocity and random movement pattern:
 * when reaching the end of a road, the dot either resets its position
 * to the beginning of the road, or reverses its direction.
 */
public class CarObj implements Agent, Car {

	private ModelConfig config = ModelConfig.createConfig();
	private TimeServer time;
	private double maxVelocity;
	private double brakeDistance;
	private double stopDistance;
	private double length;
	private double frontPosition;
	private double rearPosition;

	CarObj() { 

		this.maxVelocity = Math.max(config.getCarMaxVelocityMax() * Math.random(), config.getCarMaxVelocityMin());
		this.brakeDistance = Math.max(config.getCarBrakeDistanceMax() * Math.random(), config.getCarBrakeDistanceMin());
		this.stopDistance = Math.max(config.getCarStopDistanceMax() * Math.random(), config.getCarStopDistanceMin());
		this.length = Math.max(config.getCarLengthMax() * Math.random(), config.getCarLengthMin());
		this.time = config.getTimeServer();
	}

	public void run(double time) {
		if (_backAndForth) {
			if (((_position + _velocity) < 0) || ((_position + _velocity) > (30-5)))//TODO: Update
				_velocity *= -1;
		} else {
			if ((_position + _velocity) > (30-5))//TODO: Update
				_position = 0;
		}
		_position += _velocity;
	}

	@Override
	public double getCurrentVelocity() {
		double velocity =  (maxVelocity / (brakeDistance - stopDistance))*(distanceToObstacle - stopDistance);
		velocity = Math.max(0.0, velocity);
		velocity = Math.min(maxVelocity, velocity);
		nextFrontPosition = frontPosition + velocity * timeStep;
		// TODO Auto-generated method stub
		return 0;
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
	public void setFrontPosition(double position) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getRearPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

}

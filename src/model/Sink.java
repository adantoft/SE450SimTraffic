package model;

import java.util.Set;

import model.StaticFactory.LightState;
import model.StaticFactory.Orientation;

public class Sink implements CarAcceptor{

	//TODO add orientation
	
	@Override
	public boolean accept(CarObj c, double frontPosition) {
		return true; //Eats up car (does nothing)
	}

	@Override
	public double distanceToObstacle(double fromPosition, Orientation orientation) {
		return Double.POSITIVE_INFINITY;
	}

	@Override
	public double getEndPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CarAcceptor getNextRoad(Orientation orientation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(CarObj carObj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LightState getLightState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNextRoad(CarAcceptor next, Orientation orientation) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Light getLight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Car> getCars(Orientation orientation) {
		// TODO Auto-generated method stub
		return null;
	}
}
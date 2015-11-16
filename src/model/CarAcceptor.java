package model;

import java.util.Set;

import model.StaticFactory.LightState;
import model.StaticFactory.Orientation;

public interface CarAcceptor {
	public boolean accept(CarObj c, double frontPosition);
	public double distanceToObstacle(double fromPosition, Orientation orientation);
	public double getEndPosition();
	public CarAcceptor getNextRoad(Orientation orientation);
	public boolean remove(CarObj carObj);
	public LightState getLightState();
	public Light getLight();
	public void setNextRoad(CarAcceptor next, Orientation orientation);
	public Set<Car> getCars(Orientation orientation);
}

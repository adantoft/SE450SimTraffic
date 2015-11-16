package model;

import model.StaticFactory.LightState;
import model.StaticFactory.Orientation;

public interface CarAcceptor {
	public boolean accept(CarObj c, double frontPosition);
	public double distanceToObstacle(double fromPosition, Orientation orientation);
	public double getEndPosition();
	public CarAcceptor getNextRoad(Orientation orientation);
	public boolean remove(CarObj carObj);
	public LightState getLightState();
}

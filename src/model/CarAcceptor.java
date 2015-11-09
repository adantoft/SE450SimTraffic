package model;

public interface CarAcceptor {
	public boolean accept(CarObj c, double frontPosition);
	public double distanceToObstacle(double fromPosition);
}

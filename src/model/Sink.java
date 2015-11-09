package model;

public class Sink implements CarAcceptor{

	@Override
	public boolean accept(CarObj c, double frontPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double distanceToObstacle(double fromPosition) {
		return Double.POSITIVE_INFINITY;
	}
}

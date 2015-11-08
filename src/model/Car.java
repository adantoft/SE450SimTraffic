package model;

public interface Car {
	
	public double getMaxVelocity();
	public double getCurrentVelocity();
	public double getBrakeDistance(); 
	public double getStopDistance();
	public double getLength();
	public double getFrontPosition();
	public void setFrontPosition(double position);
	public double getRearPosition();
}

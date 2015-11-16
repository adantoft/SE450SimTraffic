package model;

import model.StaticFactory.LightState;

/**
 * A light has a boolean state.
 */
public interface Light extends Agent{

	public LightState getLightState(); 
	public void setLightState(LightState state);
	public double getGreenDurationNS();
	public void setGreenDurationNS(double duration);
	public double getGreenDurationEW();
	public void setGreenDurationEW(double duration);
	public double getYellowDurationNS();
	public void setYellowDurationNS(double duration);
	public double getYellowDurationEW();
	public void setYellowDurationEW(double duration);
	public boolean getState();

}



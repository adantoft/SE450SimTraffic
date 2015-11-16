package model;

import model.StaticFactory.LightState;
import parameters.ModelConfig;
import timeserver.TimeServer;

/**
 * A light has a boolean state.
 */
public class LightController implements Light, Agent {

	private ModelConfig config = ModelConfig.createConfig();
	private TimeServer time;
	private LightState state;
	private double greenDurationNS;
	private double yellowDurationNS;
	private double greenDurationEW;
	private double yellowDurationEW;


	LightController() {
		this.greenDurationNS  = Math.max(config.getTrafficLightGreenTimeMax() * Math.random(), config.getTrafficLightGreenTimeMin());
		this.yellowDurationNS = Math.max(config.getTrafficLightYellowTimeMax() * Math.random(), config.getTrafficLightYellowTimeMin());
		this.greenDurationEW  = Math.max(config.getTrafficLightGreenTimeMax() * Math.random(), config.getTrafficLightGreenTimeMin());
		this.yellowDurationEW = Math.max(config.getTrafficLightYellowTimeMax() * Math.random(), config.getTrafficLightYellowTimeMin());
		this.time = config.getTimeServer();
		this.state = (Math.random()>.5) ? LightState.NSGREEN_EWRED : LightState.EWGREEN_NSRED; //50% starting as green NS or EW
	} 

	@Override
	public void run(double duration) {
		// TODO Auto-generated method stub
	}
	@Override
	public LightState getLightState() {
		return state;
	}
	@Override
	public void setLightState(LightState state) {
		this.state = state;
	}
	@Override
	public double getGreenDurationNS() {
		return greenDurationNS;
	}
	@Override
	public void setGreenDurationNS(double duration) {
		this.greenDurationNS = duration;
	}
	@Override
	public double getGreenDurationEW() {
		return greenDurationEW;
	}
	@Override
	public void setGreenDurationEW(double duration) {
		this.greenDurationEW = duration;
	}
	@Override
	public double getYellowDurationNS() {
		return yellowDurationNS;
	}
	@Override
	public void setYellowDurationNS(double duration) {
		this.yellowDurationNS = duration;
	}
	@Override
	public double getYellowDurationEW() {
		return yellowDurationEW;
	}
	@Override
	public void setYellowDurationEW(double duration) {
		this.yellowDurationEW = duration;
	}
}



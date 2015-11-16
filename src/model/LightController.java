package model;

import model.StaticFactory.LightState;
import parameters.ModelConfig;
import timeserver.TimeServer;

/**
 * A light has a boolean lightState.
 */
public class LightController implements Light, Agent {

	private ModelConfig config = ModelConfig.createConfig();
	private TimeServer time;
	private LightState lightState;
	private boolean state;
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
		this.lightState = (Math.random()>.5) ? LightState.NSGREEN_EWRED : LightState.EWGREEN_NSRED; //50% starting as green NS or EW
		this.state = true;
	} 

	@Override
	public void run(double duration) {
		switch (lightState) {
		
			case NSGREEN_EWRED:		lightState = LightState.NSYELLOW_EWRED;
									time.enqueue(time.currentTime() + yellowDurationNS, this);
									break;
			case NSYELLOW_EWRED:	lightState = LightState.EWGREEN_NSRED;
									time.enqueue(time.currentTime() + greenDurationEW, this);
									state = !state;
									break;
			case EWGREEN_NSRED:		lightState = LightState.EWYELLOW_NSRED;
									time.enqueue(time.currentTime() + yellowDurationEW, this);
									break;
			case EWYELLOW_NSRED:	lightState = LightState.NSGREEN_EWRED;
									time.enqueue(time.currentTime() + greenDurationNS, this);
									state = !state;
									break;
			default:				lightState = LightState.EWGREEN_NSRED;
									time.enqueue(time.currentTime() + greenDurationEW, this);
									break;
		}
	}
	@Override
	public LightState getLightState() {
		return lightState;
	}
	@Override
	public void setLightState(LightState lightState) {
		this.lightState = lightState;
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

	@Override
	public boolean getState() {
		// TODO Auto-generated method stub
		return false;
	}
}



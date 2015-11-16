package model;

import parameters.ModelConfig;

public class StaticFactory {

	public enum Orientation {
		NS, EW
	}

	public enum LightState {
		NSGREEN_EWRED, NSYELLOW_EWRED, EWGREEN_NSRED, EWYELLOW_NSRED
	}

	public StaticFactory() {}
	
	static public final ModelConfig makeConfiguration() {
		return ModelConfig.createConfig();
	}
	
	static public final CarSource makeCarSource(CarAcceptor next, Orientation orientation) {
		return new CarSourceObj(next, orientation);
	}
	static public final CarObj makeCar(Orientation orientation) {
		return new CarObj(orientation);
	}
	static public final CarAcceptor makeRoad(CarAcceptor next, Orientation orientation) {
		return new Road(next, orientation);
	}
	static public final CarAcceptor makeIntersection(CarAcceptor next, Orientation orientation) {
		return new Intersection(next, orientation);
	}
	static public final CarAcceptor makeCarSink() {
		return new Sink();
	}
	static public final Light makeLight() {
		return new LightController();
	}
}

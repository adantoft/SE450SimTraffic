package model;

public class StaticFactory {

	public enum Orientation {
		NS, EW
	}

	public enum LightState {
		NSGREEN, NSYELLOW, EWGREEN, EWYELLOW
	}

	public StaticFactory() {}

	static public final CarObj makeCar() {
		return new CarObj();
	}

	static public final Road makeRoad(CarAcceptor next) {
		return new Road(next);
	}

	static public final Sink makeCarSink() {
		return new Sink();
	}

	static public final CarSource makeCarSource(CarAcceptor next) {
		return new CarSourceObj(next);
	}
}

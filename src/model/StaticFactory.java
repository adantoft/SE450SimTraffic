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

	static public final Road makeRoad() {
		return new Road();
	}

	static public final Sink makeCarSink() {
		return new Sink();
	}

	static public final CarSource makeCarSource() {
		return new CarSourceObj();
	}
}

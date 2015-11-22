package parameters;

import timeserver.TimeServer;
import timeserver.TimeServerLinked;

//TODO create tostring?

/**
 * Static class for model configuration and parameters.
 * All spatial units will be given as meters
 * All time units will be given in seconds
 */
public class ModelConfig {

	private static ModelConfig config = null;
	private double simTimeStep =0.1;
	private double simRunTime = 1000; 
	private int gridRows = 2;
	private int gridColumns = 3;
	private TrafficPattern trafficPattern = TrafficPattern.SIMPLE;
	private double roadSegmentLengthMin = 200;
	private double roadSegmentLengthMax = 500;
	private double intersectionLengthMin = 10;
	private double intersectionLengthMax = 15;
	private double carGenerationDelayMin = 2;
	private double carGenerationDelayMax = 25;
	private double carLengthMin = 5;
	private double carLengthMax = 10;
	private double carMaxVelocityMin = 10;
	private double carMaxVelocityMax = 30;
	private double carStopDistanceMin = 0.5;
	private double carStopDistanceMax = 5;
	private double carBrakeDistanceMin = 9;
	private double carBrakeDistanceMax = 10;			
	private double trafficLightGreenTimeMin = 30; 
	private double trafficLightGreenTimeMax = 180; 
	private double trafficLightYellowTimeMin = 4; 
	private double trafficLightYellowTimeMax = 5;
	private TimeServer tserver = new TimeServerLinked();

	public enum TrafficPattern{
		SIMPLE("simple"),
		ALTERNATING("alternating");

		private String str;

		TrafficPattern(String name) {
			str = name;
		}

		@Override
		public String toString() {
			return str;
		}	
	}
	
	private ModelConfig() {}
	
	public static ModelConfig createConfig(){
		if (config == null) config = new ModelConfig();
		return config;		
	}

	public TimeServer getTimeServer() {return tserver;}
	public void setTimeServer(TimeServer timeServer) {this.tserver = timeServer;}
	/**
	 * This indicates how the direction of roads should vary. The choices are simple or alternating.
	 */
	public TrafficPattern getTrafficPattern() {return trafficPattern;}
	public void setTrafficPattern(TrafficPattern trafficPattern) {this.trafficPattern = trafficPattern;}
	/**
	 * This indicates how much model time elapses between each simulation step. You can use this to adjust the granularity of the simulation.
	 */
	public Double getSimTimeStep() {return simTimeStep;}
	public void setSimTimeStep(Double simTimeStep) {this.simTimeStep = simTimeStep;}
	/**
	 * The length of the simulation in model seconds. When the user chooses Run simulation from the main menu, this indicates how long the simulation should run.
	 */
	public Double getSimRunTime() {return simRunTime;}
	public void setSimRunTime(Double simRunTime) {this.simRunTime = simRunTime;}

	public int getGridRows() {return gridRows;}
	public void setGridRows(int gridRows) {this.gridRows = gridRows;}

	public int getGridColumns() {return gridColumns;}
	public void setGridColumns(int gridColumns) {this.gridColumns = gridColumns;}

	public Double getCarLengthMin() {return carLengthMin;}
	public void setCarLengthMin(Double carLengthMin) {this.carLengthMin = carLengthMin;}

	public Double getCarLengthMax() {return carLengthMax;}
	public void setCarLengthMax(Double carLengthMax) {this.carLengthMax = carLengthMax;}

	public Double getCarMaxVelocityMin() {return carMaxVelocityMin;}
	public void setCarMaxVelocityMin(Double carMaxVelocityMin) {this.carMaxVelocityMin = carMaxVelocityMin;}

	public Double getCarMaxVelocityMax() {return carMaxVelocityMax;}
	public void setCarMaxVelocityMax(Double carMaxVelocityMax) {this.carMaxVelocityMax = carMaxVelocityMax;}

	public Double getRoadSegmentLengthMin() {return roadSegmentLengthMin;}
	public void setRoadSegmentLengthMin(Double roadSegmentLengthMin) {this.roadSegmentLengthMin = roadSegmentLengthMin;}

	public Double getRoadSegmentLengthMax() {return roadSegmentLengthMax;}
	public void setRoadSegmentLengthMax(Double roadSegmentLengthMax) {this.roadSegmentLengthMax = roadSegmentLengthMax;}

	public Double getIntersectionLengthMin() {return intersectionLengthMin;}
	public void setIntersectionLengthMin(Double intersectionLengthMin) {this.intersectionLengthMin = intersectionLengthMin;}

	public Double getIntersectionLengthMax() {return intersectionLengthMax;}
	public void setIntersectionLengthMax(Double intersectionLengthMax) {this.intersectionLengthMax = intersectionLengthMax;}

	public Double getCarGenerationDelayMin() {return carGenerationDelayMin;}
	public void setCarGenerationDelayMin(Double carGenerationDelayMin) {this.carGenerationDelayMin = carGenerationDelayMin;}

	public Double getCarGenerationDelayMax() {return carGenerationDelayMax;}
	public void setCarGenerationDelayMax(Double carGenerationDelayMax) {this.carGenerationDelayMax = carGenerationDelayMax;}

	public Double getCarStopDistanceMin() {return carStopDistanceMin;}
	public void setCarStopDistanceMin(Double carStopDistanceMin) {this.carStopDistanceMin = carStopDistanceMin;}

	public Double getCarStopDistanceMax() {return carStopDistanceMax;}
	public void setCarStopDistanceMax(Double carStopDistanceMax) {this.carStopDistanceMax = carStopDistanceMax;}

	public Double getCarBrakeDistanceMin() {return carBrakeDistanceMin;}
	public void setCarBrakeDistanceMin(Double carBrakeDistanceMin) {this.carBrakeDistanceMin = carBrakeDistanceMin;}

	public Double getCarBrakeDistanceMax() {return carBrakeDistanceMax;}
	public void setCarBrakeDistanceMax(Double carBrakeDistanceMax) {this.carBrakeDistanceMax = carBrakeDistanceMax;}

	public double getTrafficLightGreenTimeMin() {return trafficLightGreenTimeMin;}
	public void setTrafficLightGreenTimeMin(double trafficLightGreenTimeMin) {this.trafficLightGreenTimeMin = trafficLightGreenTimeMin;}

	public double getTrafficLightGreenTimeMax() {return trafficLightGreenTimeMax;}
	public void setTrafficLightGreenTimeMax(double trafficLightGreenTimeMax) {this.trafficLightGreenTimeMax = trafficLightGreenTimeMax;}

	public double getTrafficLightYellowTimeMin() {return trafficLightYellowTimeMin;}
	public void setTrafficLightYellowTimeMin(double trafficLightYellowTimeMin) {this.trafficLightYellowTimeMin = trafficLightYellowTimeMin;}

	public double getTrafficLightYellowTimeMax() {return trafficLightYellowTimeMax;}
	public void setTrafficLightYellowTimeMax(double trafficLightYellowTimeMax) {this.trafficLightYellowTimeMax = trafficLightYellowTimeMax;}

	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("Simulation time step (seconds)		[" + simTimeStep + "]\n");
		b.append("Simulation run time (seconds)	        [" + simRunTime + "]\n");
		b.append("Grid size (number of roads)	        [row=" + gridRows + ",column=" + gridColumns + "]\n");
		b.append("Traffic pattern		                [" + trafficPattern + "]\n");
		b.append("Car entry rate (seconds/car)	        [min=" + carGenerationDelayMin + ",max=" + carGenerationDelayMax + "]\n");
		b.append("Road segment length (meters)	        [min=" + roadSegmentLengthMin + ",max=" + roadSegmentLengthMax + "]\n");
		b.append("Intersection length (meters)	        [min=" + intersectionLengthMin + ",max=" + intersectionLengthMax + "]\n");
		b.append("Car length (meters)	                [min=" + carLengthMin + ",max=" + carLengthMax + "]\n");
		b.append("Car maximum velocity (meters/second)	[min=" + carMaxVelocityMin + ",max=" + carMaxVelocityMax + "]\n");
		b.append("Car stop distance (meters)	        [min=" + carStopDistanceMin + ",max=" + carStopDistanceMax + "]\n");
		b.append("Car brake distance (meters)	        [min=" + carBrakeDistanceMin + ",max=" + carBrakeDistanceMax + "]\n");
		b.append("Traffic light green time (seconds)	[min=" + trafficLightGreenTimeMin + ",max=" + trafficLightGreenTimeMax + "]\n");
		b.append("Traffic light yellow time (seconds)	[min=" + trafficLightYellowTimeMin + ",max=" + trafficLightYellowTimeMax + "]\n");
		return b.toString();
	}
} 



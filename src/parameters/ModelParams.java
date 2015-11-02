package parameters;

import timeserver.TimeServer;
import timeserver.TimeServerLinked;

/**
 * Static class for model parameters.
 * All spatial units will be given as meters
 * All time units will be given in seconds
 */
public class ModelParams {
	public enum TrafficPattern{
		SIMPLE, ALTERNATING
	}
	private TimeServer timeServer = new TimeServerLinked(); //TODO add getter and setter
	/**
	 * This indicates how much model time elapses between each simulation step. You can use this to adjust the granularity of the simulation.
	 */
	private double simTimeStep =0.1;
	/**
	 * The length of the simulation in model seconds. When the user chooses Run simulation from the main menu, this indicates how long the simulation should run.
	 */
	private double simRunTime = 1000; 
	private int gridRows = 2;
	private int gridColumns = 3;
	/**
	 * This indicates how the direction of roads should vary. The choices are simple or alternating.
	 */
	private TrafficPattern trafficPattern = TrafficPattern.ALTERNATING; //TODO add getter and setters for rest of below
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

	private ModelParams() {}

 

public Double getSimTimeStep() {return simTimeStep;}
public void setSimTimeStep(Double simTimeStep) {this.simTimeStep = simTimeStep;}

public Double getSimRunTime() {return simTimeStep;}
public void setSimRunTime(Double simRunTime) {this.simRunTime = simRunTime;}

public int getGridRows() {return gridRows;}
public void setGridRows(int gridRows) {this.gridRows = gridRows;}

public int getGridColumns() {return gridColumns;}
public void setgridColumns(int gridColumns) {this.gridColumns = gridColumns;}

public Double getCarLengthMin() {return carLengthMin;}
public void setcarLengthMin(Double carLengthMin) {this.carLengthMin = carLengthMin;}

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


} 
//TODO: implement getters and setters


package model;

import model.StaticFactory.Orientation;
import parameters.ModelConfig;
import timeserver.TimeServer;

public class CarSourceObj implements CarSource, Agent{

	private ModelConfig config = ModelConfig.createConfig();
	private double productionFrequency;
	private CarAcceptor nextRoad;
	private TimeServer time;
	private Orientation orientation;
	
	CarSourceObj(CarAcceptor next, Orientation orientation){ //constructor
		this.time = config.getTimeServer();
		this.setProductionFrequency(Math.max(config.getCarGenerationDelayMax() * Math.random(), config.getCarGenerationDelayMin()));
		this.time.enqueue(time.currentTime(), this);
		this.nextRoad = next;
		this.orientation = orientation;
	}
	
	@Override
	public void run(double time) {
		CarObj c = StaticFactory.makeCar(orientation);
		nextRoad.accept(c, 0.0);
		this.time.enqueue(this.time.currentTime() + config.getSimTimeStep(), c); //TODO make this part of CarObj?		
		this.time.enqueue(this.time.currentTime() + productionFrequency, this);
		// TODO add blocker in case too many cars on road?
	}
	@Override
	public void setNextRoad(CarAcceptor road) { //TODO might not need this section
		this.nextRoad = road;
	}

	public double getProductionFrequency() {
		return productionFrequency;
	}

	public void setProductionFrequency(double productionFrequency) {
		this.productionFrequency = productionFrequency;
	}
}

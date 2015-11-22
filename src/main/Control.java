package main;

import ui.UI;
import ui.UIMenu;
import ui.UIMenuAction;
import ui.UIMenuBuilder;
import ui.UIError;
import ui.UIForm;
import ui.UIFormTest;
import ui.UIFormBuilder;
import animation.Model;
import animation.SwingAnimatorBuilder;
import model.StaticFactory;
import parameters.ModelConfig;
import parameters.ModelConfig.TrafficPattern;

class Control {
	private static final int EXITED = 0;
	private static final int EXIT = 1;
	private static final int START = 2;
	private static final int EDIT = 3;
	private static final int EDITPATTERN = 4;
	private static final int NUMSTATES = 10;
	private UIMenu[] _menus;
	private int _state;
	private UIFormTest _numberTest;
	private ModelConfig config = StaticFactory.makeConfiguration();
	private UI _ui;

	Control(UI ui) {
		_ui = ui;
		_menus = new UIMenu[NUMSTATES];
		_state = START;
		addSTART(START);
		addEXIT(EXIT);
		addEDIT(EDIT);
		addEDITPATTERN(EDITPATTERN);

		_numberTest = new UIFormTest() {
			public boolean run(String input) { //all user input is a string
				try {
					Double.parseDouble(input);
					return true;
				} catch (NumberFormatException e) {
					return false;
				}
			}
		};
	}
	
	/**
	 * Makes UI form
	 * @param title 
	 * @return array of strings from user input
	 */
		//TODO add error checking on no input from user
		//TODO enforce max value is greater than min value
	private String[] makeFormGetResults (String title){
		UIFormBuilder form = new UIFormBuilder();
		form.add(title, _numberTest);
		UIForm _getForm = form.toUIForm(title);
		return _ui.processForm(_getForm);
	}
	
	private int buildIntegerForm(String title) {
		return Integer.parseInt(makeFormGetResults(title)[0]);
	}
	private double buildDoubleForm(String title) {
		return Double.parseDouble(makeFormGetResults(title)[0]);
	}	
	
	void run() { //All UIs have this loop; wait for input then act
		try {
			while (_state != EXITED) {
				_ui.processMenu(_menus[_state]);
			}
		} catch (UIError e) {
			_ui.displayError("UI closed");
		}
	}

	private void addSTART(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default",
				new UIMenuAction() {
			public void run() {
				_ui.displayError("doh!");
			}
		});
		m.add("Run Simulation",
				new UIMenuAction() {
			public void run() {
				Model model = new Model(new SwingAnimatorBuilder(), config.getGridRows(), config.getGridColumns());
				model.run(config.getSimRunTime());
				model.dispose(); //trashes the simulation
				_state = START;
			}
		});
		m.add("Change Simulation Parameters",
				new UIMenuAction() {
			public void run() {
				_state = EDIT;
			}
		});

		m.add("Exit",
				new UIMenuAction() {
			public void run() {
				_state = EXIT;
			}
		});


		_menus[stateNum] = m.toUIMenu("Alex's SimTraffic 2015!!\n");
	}
	private void addEXIT(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() { public void run() {} }); //invalid command entered
		m.add("Yes",
				new UIMenuAction() {
			public void run() {
				_state = EXITED;
			}
		});
		m.add("No",
				new UIMenuAction() {
			public void run() {
				_state = START;
			}
		});

		_menus[stateNum] = m.toUIMenu("Are you sure you want to exit?");
	}
	
	private void addEDIT(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() {
			public void run() {
				_ui.displayError("doh!");
			}
		});
		m.add("Show current values", new UIMenuAction() {
			public void run() {
				System.out.println(config.toString());
			}
		});
		m.add("Simulation time step", new UIMenuAction() {
			public void run() {
				double val = buildDoubleForm("Enter Simulation Time Step: ");
				config.setSimTimeStep(val);
			}


		});
		m.add("Simulation run time", new UIMenuAction() {
			public void run() {
				double val = buildDoubleForm("Enter Simulation Run Time");
				config.setSimRunTime(val);
			}
		});
		m.add("Grid size", new UIMenuAction() {
			public void run() {
				int valRow = buildIntegerForm("Enter Number of Grid Rows");
				config.setGridRows(valRow);

				int valCol = buildIntegerForm("Enter Number of Grid Columns");
				config.setGridColumns(valCol);
			}
		});
		m.add("Traffic pattern", new UIMenuAction() {
			public void run() {
				_state = EDITPATTERN;
			}
		});
		m.add("Car entry rate", new UIMenuAction() {
			public void run() {
				String title = "Car Entry Rate";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setCarGenerationDelayMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setCarGenerationDelayMin(valMin);	
			}
		});
		m.add("Road segment length", new UIMenuAction() {
			public void run() {
				String title = "Road Length";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setRoadSegmentLengthMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setRoadSegmentLengthMin(valMin);		
			}
		});
		m.add("Intersection length", new UIMenuAction() {
			public void run() {
				String title = "Intersection Length";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setIntersectionLengthMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setIntersectionLengthMin(valMin);			
			}
		});
		m.add("Car length", new UIMenuAction() {
			public void run() {
				String title = "Car Length";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setCarLengthMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setCarLengthMin(valMin);	
			}
		});
		m.add("Car maximum velocity", new UIMenuAction() {
			public void run() {
				String title = "Car Max Velocity";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setCarMaxVelocityMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setCarMaxVelocityMin(valMin);
			}
		});
		m.add("Car stop distance", new UIMenuAction() {
			public void run() {
				String title = "Car Stopping Distance";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setCarStopDistanceMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setCarStopDistanceMin(valMin);	
			}
		});
		m.add("Car brake distance", new UIMenuAction() {
			public void run() {
				String title = "Car Braking Distance";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setCarBrakeDistanceMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setCarBrakeDistanceMin(valMin);			
			}
		});
		m.add("Traffic light green time", new UIMenuAction() {
			public void run() {
				String title = "Traffic Light Green Duration";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setTrafficLightGreenTimeMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setTrafficLightYellowTimeMin(valMin);		
			}
		});
		m.add("Traffic light yellow time", new UIMenuAction() {
			public void run() {
				String title = "Traffic Light Yellow Duration";

				double valMax = buildDoubleForm("Enter " + title + " Max: ");
				config.setTrafficLightYellowTimeMax(valMax);

				double valMin = buildDoubleForm("Enter " + title + " Min: ");
				config.setTrafficLightYellowTimeMin(valMin);		
			}
		});
		m.add("Reset simulation and return to the main menu",
				new UIMenuAction() {
			public void run() {
				_state = START;
			}
		});
		_menus[stateNum] = m.toUIMenu("Settings Menu");
	}
	
	private void addEDITPATTERN(int stateNum) {
		UIMenuBuilder m = new UIMenuBuilder();

		m.add("Default", new UIMenuAction() { public void run() {} }); //invalid command entered
		m.add("Alternating",
				new UIMenuAction() {
			public void run() {
				config.setTrafficPattern(TrafficPattern.ALTERNATING);
				_state = EDIT;
			}
		});
		m.add("Simple",
				new UIMenuAction() {
			public void run() {
				config.setTrafficPattern(TrafficPattern.SIMPLE);
				_state = EDIT;
			}
		});

		_menus[stateNum] = m.toUIMenu("Edit Traffic Pattern");
	}
	
}

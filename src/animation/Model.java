package animation;

import java.util.List;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import animation.Animator;
import model.CarAcceptor;
import model.StaticFactory;
import model.StaticFactory.Orientation;
import parameters.ModelConfig;
import parameters.ModelConfig.TrafficPattern;
import timeserver.TimeServer;

/**
 * An example to model for a simple visualization.
 * The model contains roads organized in a matrix.
 * See {@link #Model(AnimatorBuilder, int, int)}.
 */
public class Model extends Observable {
  //private List<Agent> _agents;
  private Animator _animator;
  private boolean _disposed;
  private TimeServer _time;
  private ModelConfig config;

  /** Creates a model to be visualized using the <code>builder</code>.
   *  If the builder is null, no visualization is performed.
   *  The number of <code>rows</code> and <code>columns</code>
   *  indicate the number of {@link Light}s, organized as a 2D
   *  matrix.  These are separated and surrounded by horizontal and
   *  vertical {@link Road}s.  For example, calling the constructor with 1
   *  row and 2 columns generates a model of the form:
   *  <pre>
   *     |  |
   *   --@--@--
   *     |  |
   *  </pre>
   *  where <code>@</code> is a {@link Light}, <code>|</code> is a
   *  vertical {@link Road} and <code>--</code> is a horizontal {@link Road}.
   *  Each road has one {@link Car}.
   *
   *  <p>
   *  The {@link AnimatorBuilder} is used to set up an {@link
   *  Animator}.
   *  {@link AnimatorBuilder#getAnimator()} is registered as
   *  an observer of this model.
   *  <p>
   */
  public Model(AnimatorBuilder builder, int rows, int columns) {
	this.config = StaticFactory.makeConfiguration();
	this._time = config.getTimeServer();
    if (rows < 0 || columns < 0 || (rows == 0 && columns == 0)) {
      throw new IllegalArgumentException();
    }
    if (builder == null) {
      builder = new NullAnimatorBuilder();
    }
    //_agents = new ArrayList<Agent>();
    setup(builder, rows, columns);
    _animator = builder.getAnimator();
    super.addObserver(_animator);
    this._time.addObserver(_animator);
  }

  /**
   * Run the simulation for <code>duration</code> model seconds.
   */
  public void run(double duration) {
	  if (_disposed)
		  throw new IllegalStateException();
	  _time.run(config.getSimRunTime());
	  super.setChanged();
	  super.notifyObservers();
  }


  /**
   * Throw away this model.
   */
  public void dispose() {
    _animator.dispose();
    _disposed = true;
  }

  /**
   * Construct the model, establishing correspondences with the visualizer.
   */
  private void setup(AnimatorBuilder builder, int rows, int columns) {
    List<CarAcceptor> roads = new ArrayList<CarAcceptor>();
    CarAcceptor[][] intersections = new CarAcceptor[rows][columns];
    Boolean reverse;

    // Add Lights
    for (int i=0; i<rows; i++) {
      for (int j=0; j<columns; j++) {
        intersections[i][j] = StaticFactory.makeIntersection(null, null); //doesn't assign next streets to intersection
        builder.addLight(intersections[i][j], i, j);
        _time.enqueue(_time.currentTime(), intersections[i][j].getLight());
      }
    }

    // Add Horizontal Roads
    boolean eastToWest = false;
    for (int i=0; i<rows; i++) {
    	if (eastToWest) { //alternating mode
    		for (int j=0; j<=columns; j++) {
    			CarAcceptor rd;			
    			if (j == 0) { //at end, makes a sink
    				rd = StaticFactory.makeRoad(StaticFactory.makeCarSink(),Orientation.EW);
    				intersections[i][j].setNextRoad(rd, Orientation.EW);
    			} else if (j == columns) {
    				rd = StaticFactory.makeRoad(intersections[i][j-1],Orientation.EW);
    				StaticFactory.makeCarSource(rd, Orientation.EW);
    			}else {
    				rd = StaticFactory.makeRoad(intersections[i][j],Orientation.EW);
    				intersections[i][j].setNextRoad(rd, Orientation.EW);
    			}
    			builder.addHorizontalRoad(rd, i, j, eastToWest);
    			roads.add(rd);
    		}
    	}
    	else { //simple mode
    		for (int j=columns; j>=0; j--) {
    			CarAcceptor rd;			
    			if (j == columns) { //at end, makes a sink
    				rd = StaticFactory.makeRoad(StaticFactory.makeCarSink(),Orientation.EW);
    				intersections[i][j-1].setNextRoad(rd, Orientation.EW);
    			} else if (j == 0) {
    				rd = StaticFactory.makeRoad(intersections[i][j],Orientation.EW);
    				StaticFactory.makeCarSource(rd, Orientation.EW);
    			}else {
    				rd = StaticFactory.makeRoad(intersections[i][j],Orientation.EW);
    				intersections[i][j-1].setNextRoad(rd, Orientation.EW);
    			}
    			builder.addHorizontalRoad(rd, i, j, eastToWest);
    			roads.add(rd);
    		}
    	}
    	if (config.getTrafficPattern() == TrafficPattern.ALTERNATING) {
    		eastToWest = !eastToWest; //next iteration will be opposite direction
    	}
    }

    // Add Vertical Roads
    boolean southToNorth = false;
    for (int j=0; j< columns; j++) {
    	if (southToNorth) { //alternating mode
    		for (int i=0; i<=rows; i++) {
    			CarAcceptor rd;			
    			if (i == 0) { //at end, makes a sink
    				rd = StaticFactory.makeRoad(StaticFactory.makeCarSink(),Orientation.NS);
    				intersections[i][j].setNextRoad(rd, Orientation.NS);
    			} else if (i == rows) {
    				rd = StaticFactory.makeRoad(intersections[i][j-1],Orientation.NS);
    				StaticFactory.makeCarSource(rd, Orientation.NS);
    			}else {
    				rd = StaticFactory.makeRoad(intersections[i][j],Orientation.NS);
    				intersections[i][j].setNextRoad(rd, Orientation.NS);
    			}
    			builder.addVerticalRoad(rd, i, j, southToNorth);
    			roads.add(rd);
    		}
    	}
    	else { //simple mode
    		for (int i=rows; i>=0; i--) {
    			CarAcceptor rd;			
    			if (i == rows) { //at end, makes a sink
    				rd = StaticFactory.makeRoad(StaticFactory.makeCarSink(),Orientation.NS);
    				intersections[i-1][j].setNextRoad(rd, Orientation.NS);
    			} else if (i == 0) {
    				rd = StaticFactory.makeRoad(intersections[i][j],Orientation.NS);
    				StaticFactory.makeCarSource(rd, Orientation.NS);
    			}else {
    				rd = StaticFactory.makeRoad(intersections[i][j],Orientation.NS);
    				intersections[i-1][j].setNextRoad(rd, Orientation.NS);
    			}
    			builder.addVerticalRoad(rd, i, j, southToNorth);
    			roads.add(rd);
    		}
    	}
    	if (config.getTrafficPattern() == TrafficPattern.ALTERNATING) {
    		southToNorth = !southToNorth; //next iteration will be opposite direction
    	}
    }

  }
}

package model;

import java.util.List;

public interface TrafficSimulatorObserver {
	public void registered(int time, RoadMap map, List<Event> events);
	
	public void simulatorError(int time, RoadMap map, List<Event> events, SimulatorError e);
	
	public void advanced(int time, RoadMap map, List<Event> events);
	
	public void eventAdded(int time, RoadMap map, List<Event> events);
	
	public void reset(int time, RoadMap map, List<Event> events);	
}

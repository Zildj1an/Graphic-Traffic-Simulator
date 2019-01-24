package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Event {
	
	protected Integer _time;
	protected int _index;
		
	Event(){
		_time = 0;
	}
	
	public Event(Integer time) {
		_time = time;
	}
	
	public void setPosition(int index) {
		_index = index;
	}
	
	public void increaseIndexPosition() {
		_index++;
	}
	
	public int getPositionIndex() {
		return _index;
	}

	public int getScheduledTime() {
		return _time;
	}
	
	protected static List<Junction> parseListOfJunctions(RoadMap map, String[] itinerary) throws SimulatorError{
		try{
			List<Junction> junctions = new ArrayList<Junction>();
			for(int i = 0; i < itinerary.length ; i++){
				junctions.add(map.getJunction(itinerary[i]));
			}
			return junctions;
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem parsing the itinerary. " + e.getMessage());
		}
		
	}
	
	protected static List<Vehicle> parseListOfVehicles(RoadMap map, String[] str) throws SimulatorError{
		try {
			List<Vehicle> vehicles = new ArrayList<Vehicle>();
			for(int i = 0; i < str.length ; ++i){
				vehicles.add(map.getVehicle(str[i]));
			}
			return vehicles;
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem parsing the vehicles. " + e.getMessage());
		}
	}
	
	public abstract void execute(RoadMap map, int time) throws SimulatorError;

	public abstract String toString();
}
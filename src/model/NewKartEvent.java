package model;

import java.util.List;

import exceptions.ExecutionOfEventException;

public class NewKartEvent extends NewVehicleEvent {
	
	protected int _luck;

	public NewKartEvent(Integer time, String id, Integer maxSpeed, String[] itinerary, int luck) {
		super(time, id, maxSpeed, itinerary);
		_luck = luck;
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError {
		try{
			List<Junction> itinerary = Event.parseListOfJunctions(map, _itinerary);
			if(itinerary == null || itinerary.size() < 2)
				throw new ExecutionOfEventException("Invalid itinerary for the car " + _id + ": must have at least 2 junctions.");
			Kart k = new Kart(_id, _maxSpeed, itinerary, _luck);
			map.addVehicle(k);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_vehicle_event event. " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "New Kart " + _id;
	}

}

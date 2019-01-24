package model;

import java.util.List;

import exceptions.ExecutionOfEventException;

/**
 * This class is used in order to create a Bike object
 *
 */
public class NewBikeEvent extends NewVehicleEvent {
	public NewBikeEvent(int time, String id, Integer maxSpeed, String[] itinerary){
		super(time, id, maxSpeed, itinerary);
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError{
		try {
			List<Junction> itinerary = Event.parseListOfJunctions(map, _itinerary);
			if(itinerary == null || itinerary.size() < 2)
				throw new ExecutionOfEventException("Invalid itinerary for the bike " + _id + ": must have at least 2 junctions.");
			Bike b = new Bike(_id, _maxSpeed, itinerary);
			map.addVehicle(b);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_vehicle_event event. " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "New Bike " + _id;
	}
}

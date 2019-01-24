package model;

import java.util.List;

import exceptions.ExecutionOfEventException;

/**
 * This class is used in order to create a Vehicle object
 *
 */
public class NewVehicleEvent extends Event {
	
	protected String _id;
	protected Integer _maxSpeed;
	protected String[] _itinerary;
	
	public NewVehicleEvent(Integer time, String id, Integer maxSpeed, String[] itinerary){
		super(time);
		_id = id;
		_itinerary = itinerary;
		_maxSpeed = maxSpeed;
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError{	
		try{
			List<Junction> itinerary = Event.parseListOfJunctions(map, _itinerary);
			if(itinerary == null || itinerary.size() < 2)
				throw new ExecutionOfEventException("Invalid itinerary for the vehicle " + _id + ": must have at least 2 junctions.");
			Vehicle v = new Vehicle(_id, _maxSpeed, itinerary);
			map.addVehicle(v);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_vehicle_event event for Vehicle " + _id + ". " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "New Vehicle " + _id;
	}
}

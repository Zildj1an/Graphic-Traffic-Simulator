package model;

import java.util.List;

import exceptions.ExecutionOfEventException;

/**
 * This class is used in order to create a Car object
 *
 */
public class NewCarEvent extends NewVehicleEvent {
	
	protected Integer _maxFaultDuration;
	protected Integer _resistance;
	protected Double _faultProbability;
	protected Long _seed;
	
	
	public NewCarEvent(int time, String id, Integer maxFaultDuration, String[] itinerary, Integer resistance, Integer maxSpeed, Double faultProbability, long seed){
		super(time, id, maxSpeed, itinerary);
		_maxFaultDuration = maxFaultDuration;
		_resistance = resistance;
		_faultProbability = faultProbability;
		_seed = seed;
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError {
		try{
			List<Junction> itinerary = Event.parseListOfJunctions(map, _itinerary);
			if(itinerary == null || itinerary.size() < 2)
				throw new ExecutionOfEventException("Invalid itinerary for the car " + _id + ": must have at least 2 junctions.");
			Car c = new Car(_id, _resistance, _maxSpeed, _faultProbability, _seed, _maxFaultDuration, itinerary);
			map.addVehicle(c);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_vehicle_event event. " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "New Car " + _id;
	}
}

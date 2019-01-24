package model;

import java.util.List;
import exceptions.UnexistingObjectException;

/**
 * This class is used in order to make a Vehicle faulty
 *
 */
public class MakeVehicleFaultyEvent extends Event {
	
	protected Integer _duration;
	protected String[] _vehicles;
	
	public MakeVehicleFaultyEvent(int time, Integer duration, String[] vehicles){
		super(time);
		_duration = duration;
		_vehicles = vehicles;
	}
	public void execute(RoadMap map, int time) throws SimulatorError {
		try {
			List<Vehicle> vehicles = Event.parseListOfVehicles(map, _vehicles);
			for(Vehicle v: vehicles) {
				v.makeFaulty(_duration);
			}
		}
		catch(UnexistingObjectException e) {
			throw new SimulatorError("Problem executing a make_vehicle_faulty event. " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		String veh = "";
		veh += "Break Vehicles [";
		for(String s: _vehicles){
			veh += s + ",";
		}
	    veh = veh.substring(0, veh.length() - 1);
	    veh += "]";
		return veh;
	}
	
}

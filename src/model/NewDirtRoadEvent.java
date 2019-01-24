package model;

/**
 * This class is used in order to create a DirtRoad object
 *
 */
public class NewDirtRoadEvent extends NewRoadEvent {
	public NewDirtRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length) {
		super(time, id, src, dest, maxSpeed, length);
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError {
		try{
			Junction src = map.getJunction(_sourceJunctionId);
			Junction dest = map.getJunction(_destinyJunctionId);
			DirtRoad road = new DirtRoad(_id, _length, _maxSpeed, src, dest);
			map.addRoad(road);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_car_event event. " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "New Dirt Road " + _id;
	}
}
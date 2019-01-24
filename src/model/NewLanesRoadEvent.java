package model;

/**
 * This class is used in order to create a LanesRoad object
 *
 */
public class NewLanesRoadEvent extends NewRoadEvent {
	
	protected int _numLanes;
	
	public NewLanesRoadEvent(int time, String id, String src, String dest, int maxSpeed, int length, int lanes) {
		super(time, id, src, dest, maxSpeed, length);
		_numLanes = lanes;
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError {		
		try {
			Junction src = map.getJunction(_sourceJunctionId);
			Junction dest = map.getJunction(_destinyJunctionId);
			LanesRoad road = new LanesRoad(_id, _length, _maxSpeed, _numLanes, src, dest);
			map.addRoad(road);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_road_event event. " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "New Lanes Road " + _id;
	}
}
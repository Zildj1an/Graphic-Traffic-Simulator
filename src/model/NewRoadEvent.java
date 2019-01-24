package model;

/**
 * This class is used in order to create a Road object
 *
 */
public class NewRoadEvent extends Event {
	
	protected String _id;
	protected Integer _maxSpeed;
	protected Integer _length;
	protected String _sourceJunctionId;
	protected String _destinyJunctionId;
	
	
	public NewRoadEvent(int time, String id, String sourceJunctionId, String destinyJunctionId, int maxSpeed, int length) {
		super(time);
		_id = id;
		_maxSpeed = maxSpeed;
		_length = length;
		_sourceJunctionId = sourceJunctionId;
		_destinyJunctionId = destinyJunctionId;
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError{
		try {
			Junction src = map.getJunction(_sourceJunctionId);
			Junction dest = map.getJunction(_destinyJunctionId);
			Road road = new Road(_id, _length, _maxSpeed, src, dest);
			map.addRoad(road);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_road_event event. " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "New Road " + _id;
	}
}
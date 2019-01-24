package model;

/**
 * This class is used in order to create a Junction object
 *
 */
public class NewJunctionEvent extends Event {
	
	protected String _id;
	
	public NewJunctionEvent(int time, String id){
		super(time);
		_id = id;
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError{
		try{
			map.addJunction(new Junction(_id));
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_junction_event event. " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "New Junction " + _id;
	}
}
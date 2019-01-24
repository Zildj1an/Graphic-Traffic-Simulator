package model;

/**
 * This class is used in order to create a RoundRobinJunction object
 *
 */
public class NewRoundRobinJunctionEvent extends NewJunctionEvent {
	
	protected int _maxTimeSlice;
	protected int _minTimeSlice;
	
	public NewRoundRobinJunctionEvent(int time, String id, int maxTimeSlice, int minTimeSlice) {
		super(time, id);
		_maxTimeSlice = maxTimeSlice;
		_minTimeSlice = minTimeSlice;
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError {
		try{
			RoundRobinJunction rr = new RoundRobinJunction(_id, _minTimeSlice, _maxTimeSlice);
			map.addJunction(rr);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_junction_event event. " + e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "New Round Robin Junction " + _id;
	}
}

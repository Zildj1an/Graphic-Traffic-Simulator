package model;

/**
 * This class is used in order to create a MostCrowdedJunction object
 *
 */
public class NewMostCrowdedJunctionEvent extends NewJunctionEvent {
	public NewMostCrowdedJunctionEvent(int time, String id) {
		super(time, id);
	}
	
	public void execute(RoadMap map, int time) throws SimulatorError {
		try {
		MostCrowdedJunction mc = new MostCrowdedJunction(_id);
		map.addJunction(mc);
		}
		catch(SimulatorError e) {
			throw new SimulatorError("Problem executing a new_junction_event event. " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return "New Most Crowded Junction " + _id;
	}
}

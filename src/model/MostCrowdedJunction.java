package model;

import ini.IniSection;

/**
 * This class represents a MostCrowdedJunction, i.e., a type of Junction that will
 * turn on first the lights of the most crowded IncomingRoad.
 *
 */
public class MostCrowdedJunction extends JunctionWithTimeSlice{

	public MostCrowdedJunction(String id) {
		super(id);
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		IncomingRoadWithTimeSlice roadSlice = new IncomingRoadWithTimeSlice(road);
		roadSlice.setTimeSlice(0);
		roadSlice.setUsedTimeUnits(0);
		return roadSlice;
	}
	
	void advance() throws SimulatorError {
		boolean found = false;
		int i = 0;
		while(!found && i < _roads.size()){
			found = (_roads.get(i).hasGreenLight());
			i++;
		}
		if(found)
			_roads.get(i - 1).advanceFirstVehicle();
		this.switchLights();
	}
	
	protected void switchLights() {
		boolean found = false;
		int i = 0;
		while(!found && i < _roads.size()){
			found = _roads.get(i).hasGreenLight();
			i++;
		}
		if(found){
			if(((IncomingRoadWithTimeSlice)_roads.get(i - 1)).getUsedTimeUnits() == ((IncomingRoadWithTimeSlice)_roads.get(i - 1)).getTimeSlice()) {
				turnLightOff((IncomingRoadWithTimeSlice)_roads.get(i - 1));
				((IncomingRoadWithTimeSlice)_roads.get(i - 1)).setUsedTimeUnits(0);
				turnLightOn(i - 1);
			}
		}
		else
			turnLightOn(-1);
	}
	
	protected void turnLightOff(IncomingRoadWithTimeSlice road) {
		road.setGreen(false);
	}
	
	protected void turnLightOn(int differentIndex) {		
		int i = 0;
		int newGreenIndex = 0, longerQueue = -1;
		if(_roads.size() > 1) {
			while(i < _roads.size()){
				if(_roads.get(i)._queue.size() > longerQueue && i != differentIndex) {
					longerQueue = _roads.get(i)._queue.size();
					newGreenIndex = i;
				}
				i++;
			}
		}
		((IncomingRoadWithTimeSlice)_roads.get(newGreenIndex)).setGreen(true);
		int newTimeSlice = Integer.max((_roads.get(newGreenIndex)._queue.size() / 2), 1);
		((IncomingRoadWithTimeSlice)_roads.get(newGreenIndex)).setTimeSlice(newTimeSlice);
	}
	
	protected void fillReportDetails(IniSection is) {
		super.fillReportDetails(is);		
		is.setValue("type", "mc");
	}
}

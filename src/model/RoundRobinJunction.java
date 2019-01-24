package model;

import ini.IniSection;

/**
 * This class represents a RoundRobinJunction, i.e., a type of Junction that keeps green lights on for longer intervals of time.
 *
 */
public class RoundRobinJunction extends JunctionWithTimeSlice{

	protected int _minTimeSlice;
	protected int _maxTimeSlice;
	private int _lightIndex;
	
	public RoundRobinJunction(String id, int minTimeSlice, int maxTimeSlice) {
		super(id);
		_minTimeSlice = minTimeSlice;
		_maxTimeSlice = maxTimeSlice;
		_lightIndex = -1;
	}
	
	public int getLightIndex() {
		return _lightIndex;
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		IncomingRoadWithTimeSlice roadSlice = new IncomingRoadWithTimeSlice(road);
		roadSlice.setTimeSlice(_maxTimeSlice);
		return roadSlice;
	}
	
	protected void switchLights() {
		if(_lightIndex != -1){
			if(((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).getUsedTimeUnits() == ((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).getTimeSlice()) {
				turnLightOff((IncomingRoadWithTimeSlice)_roads.get(_lightIndex));
				if(((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).isFullyUsed()) {
					int newTimeSlice = ((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).getTimeSlice() + 1;
					if(_maxTimeSlice < newTimeSlice)
						newTimeSlice = _maxTimeSlice;
					((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).setTimeSlice(newTimeSlice);
				}
				else if(!((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).isUsed()) {
					int newTimeSlice = ((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).getTimeSlice() - 1;
					if(_minTimeSlice > newTimeSlice)
						newTimeSlice = _minTimeSlice;
					((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).setTimeSlice(newTimeSlice);
				}
				((IncomingRoadWithTimeSlice)_roads.get(_lightIndex)).setUsedTimeUnits(0);
				if(_lightIndex == _roads.size() - 1)
					_lightIndex = 0;
				else
					_lightIndex++;
				turnLightOn((IncomingRoadWithTimeSlice)_roads.get(_lightIndex));
			}
		}
		else {
			if(_roads.size() > 0) {
				_roads.get(0).setGreen(true);
				_lightIndex = 0;
			}
		}
	}
	
	protected void turnLightOff(IncomingRoadWithTimeSlice road) {
		road.setGreen(false);
	}
	
	protected void turnLightOn(IncomingRoadWithTimeSlice road) {
		road.setGreen(true);
	}
	
	protected void fillReportDetails(IniSection is) {
		super.fillReportDetails(is);	
		is.setValue("type", "rr");
	}
}

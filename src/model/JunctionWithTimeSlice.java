package model;

import ini.IniSection;

/**
 * This class represents a JunctionWithTimeSlice, i.e., a type of Junction which 
 * traffic light can be turned on a longer time.
 *
 */
public abstract class JunctionWithTimeSlice extends Junction{

	public JunctionWithTimeSlice(String id){
		super(id);
	}
	
	protected void fillReportDetails(IniSection is) {
		String queue = "";
		int i = 0;
		for(; i < _roads.size(); i++) {
			queue += _roads.get(i).toString();
			if(i != _roads.size() - 1)
				queue += ",";	
		}
		is.setValue("queues", queue);
	}
	
	static protected class IncomingRoadWithTimeSlice extends IncomingRoad{
		
		int _timeSlice;
		int _usedTimeUnits;
		boolean _fullyUsed;
		boolean _used;
		
		protected IncomingRoadWithTimeSlice(Road road) {
			super(road);
			_usedTimeUnits = 0;
			_used = false;
			_fullyUsed = false;
		}
		
		protected void advanceFirstVehicle() throws SimulatorError {
			_usedTimeUnits++;
			if(_queue.isEmpty()) {
				_fullyUsed = false;
				_used = false;
			}
			else {
				_used = true;
				if(_usedTimeUnits == _timeSlice)
					_fullyUsed = true;
				super.advanceFirstVehicle();
			}
		}
		
		public int getTimeSlice() {
			return _timeSlice;
		}
		
		protected void setTimeSlice(int timeSlice) {
			_timeSlice = timeSlice;
		}
		
		public int getUsedTimeUnits() {
			return _usedTimeUnits;
		}
		
		protected void setUsedTimeUnits(int usedTimeUnits) {
			_usedTimeUnits = usedTimeUnits;
		}
		
		public boolean isFullyUsed() {
			return _fullyUsed;
		}
		
		protected void setFullyUsed(boolean fullyUsed) {
			_fullyUsed = fullyUsed;
		}
		
		public boolean isUsed() {
			return _used;
		}
		
		protected void setUsed(boolean used) {
			_used = used;
		}
		
		public String toString() {
			String queue = "";
			String light = "";
			queue += "(" + _road.getId() + ",";
			if(hasGreenLight()) {
				light = "green:";
				int remainingTimeUnits = _timeSlice - _usedTimeUnits;
				light += remainingTimeUnits;
			}
			else
				light = "red";
			queue += light + ",[";
			queue += printQueue();
			queue += "])";
			return queue;
		}
	}
}

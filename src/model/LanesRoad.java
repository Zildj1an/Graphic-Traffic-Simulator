package model;

import ini.IniSection;

/**
 * This class represents a LanesRoad, i.e., a type of Road with certain singularities.
 *
 */
public class LanesRoad extends Road{
	
	protected int _numLanes;

	public LanesRoad(String id, int length, int maxSpeed, int numLanes, Junction src, Junction dest) {
		super(id, length, maxSpeed, src, dest);
		_numLanes = numLanes;
	}
	
	public int getNumLanes() {
		return _numLanes;
	}
	
	protected int calculateBaseSpeed() {
		int baseSpeed = _maxSpeed;
		int max = 1;
		if(_vehicles.size() > max)
			max = _vehicles.size();
		int division = (_maxSpeed * getNumLanes()) / max;
		division++;
		if(division < baseSpeed)
			baseSpeed = division;
		return baseSpeed;		
	}
	
	protected int reduceSpeedFactor(int obstacles) {
		if(getNumLanes() > obstacles)
			return 1;
		else
			return 2;
	}
	
	protected void fillReportDetails(IniSection is) {
		is.setValue("type", "lanes");
		super.fillReportDetails(is);
	}
}

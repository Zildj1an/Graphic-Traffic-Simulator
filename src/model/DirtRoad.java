package model;

import ini.IniSection;

/**
 * This class represents a DirtRoad, i.e., a type of Road with certain singularities.
 *
 */
public class DirtRoad extends Road{
	
	public DirtRoad(String id, int length, int maxSpeed, Junction src, Junction dest) {
		super(id, length, maxSpeed, src, dest);
	}
	
	protected int calculateBaseSpeed() {
		return _maxSpeed;
	}
	
	protected int reduceSpeedFactor(int obstacles) {
		return obstacles + 1;
	}
	
	protected void fillReportDetails(IniSection is) {
		is.setValue("type", "dirt");
		super.fillReportDetails(is);
	}
}

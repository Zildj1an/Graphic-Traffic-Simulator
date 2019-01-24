package model;

import java.util.List;

import exceptions.UnexistingObjectException;
import ini.IniSection;

/**
 * This class represents a Bike, i.e., a type of Vehicle with certain singularities.
 *
 */
public class Bike extends Vehicle{

	public Bike(String id, int maxSpeed, List<Junction> itinerary) {
		super(id, maxSpeed, itinerary);
	}
	
	void makeFaulty(int number) throws UnexistingObjectException {
		if(_currentSpeed > (_maxSpeed / 2))
			super.makeFaulty(number);
	}
	
	protected void fillReportDetails(IniSection is) {
		is.setValue("type", "bike");
		super.fillReportDetails(is);
	}
}

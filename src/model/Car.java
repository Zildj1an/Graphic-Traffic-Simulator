package model;

import java.util.List;
import java.util.Random;

import exceptions.UnexistingObjectException;
import ini.IniSection;

/**
 * This class represents a Car, i.e., a type of Vehicle with certain singularities.
 *
 */
public class Car extends Vehicle{
	
	protected int _resistance;
	protected int _maxFaultDuration;
	protected double _probability;
	protected Random _random;

	public Car(String id, int resistance, int maxSpeed, double faultProbability, long seed, int maxFaultDuration, List<Junction> itinerary) {
		super(id, maxSpeed, itinerary);
		_resistance = resistance;
		_probability = faultProbability;
		_random = new Random(seed);
		_maxFaultDuration = maxFaultDuration;
	}
	
	void advance() throws UnexistingObjectException {
		if((_faultyTime == 0) && ((_kilometrage - _last_faulty_km) >= _resistance))
			if (_random.nextDouble() < _probability)
				makeFaulty(_random.nextInt(_maxFaultDuration) + 1);
		super.advance();
	}
	
	protected void fillReportDetails(IniSection is) {
		is.setValue("type", "car");
		super.fillReportDetails(is);
	}
}

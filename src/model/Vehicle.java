package model;

import java.util.List;

import exceptions.UnexistingObjectException;
import ini.IniSection;

/**
 * This class represents a Vehicle, i.e., an object that follows a given itinerary of Junctions through different Roads.
 *
 */
public class Vehicle extends SimulatedObject {	
	
	protected int _maxSpeed;
	protected int _last_faulty_km;
	protected int _currentSpeed;
	protected Road _road;
	protected int _location;
	protected List<Junction> _itinerary;
	protected int _kilometrage;
	protected int _faultyTime;
	protected boolean _atJunction;
	protected boolean _arrived;
	protected int _itineraryIndex;
	protected int _positionIndex; // Represents the vehicle position on its road
	
	public Vehicle(String id, int maxSpeed, List<Junction> itinerary) {
		super(id);
		_maxSpeed = maxSpeed;
		_itinerary = itinerary;
		_atJunction = true;
		_arrived = false;
		_kilometrage = 0;
		_faultyTime = 0;
		_location = 0;
		_road = null;
		_last_faulty_km = 0;
		_itineraryIndex = 0;
	}
	
	public boolean getAtJunction() {
		return _atJunction;
	}
	
	public int getPositionIndex() {
		return _positionIndex;
	}
	
	public void decreasePositionIndex() { // Higher number -> worse position
		 _positionIndex++;
	}
	
	public void increasePositionIndex() { // Lower number -> better position
		 _positionIndex--;
	}
	
	public void setPositionIndex(int index) {
		_positionIndex = index;
	}
	
	public Road getRoad() {
		return _road;
	}
	
	public int getMaxSpeed() {
		return _maxSpeed;
	}
	
	public int getSpeed() {
		return _currentSpeed;
	}
	
	public int getLocation() {
		return _location;
	}
	
	public int getKilometrage() {
		return _kilometrage;
	}
	
	public int getFaultyTime() {
		return _faultyTime;
	}
	
	public boolean atDestination() {
		return _arrived;
	}
	
	public List<Junction> getItinerary(){
		return _itinerary;
	}
	
	void makeFaulty(int faultyTime) throws UnexistingObjectException {
		if(_road == null)
			throw new UnexistingObjectException("The vehicle is not in any road!");
		_faultyTime += faultyTime;
		_currentSpeed = 0;
		_last_faulty_km = _kilometrage;
	}
	
	void setSpeed(int speed) {
		if(_faultyTime != 0)
			_currentSpeed = 0;
		else if(speed < 0)
			_currentSpeed = 0;
		else if (speed > _maxSpeed)
			_currentSpeed = _maxSpeed;
		else
			_currentSpeed = speed;
	}
	
	void advance() throws UnexistingObjectException {
		if(_faultyTime != 0)
			_faultyTime--;
		else if (!_atJunction){
			_location += _currentSpeed;
			_kilometrage += _currentSpeed;
			if(_location >= _road.getLength()) {
				_kilometrage -= (_location - _road.getLength());
				_location = _road.getLength();
				_atJunction = true;
				_currentSpeed = 0;
				_road.getDestination().enter(_road.getId(), this);
				_itineraryIndex++;
			}
		}
	}
	
	/**
	 * 	When adding a vehicle to the road map, this
	 *	method should be called so the vehicle enter
	 *	the first road — see the RoadMap class. It will
	 *	also be called by Junction to tell the vehicle to
	 *	move to the next road
	 * @throws SimulatorError 
	 *
	 */
	void moveToNextRoad() throws SimulatorError {
		if(_road != null) {
			_road.exit(this);
		}
		_location = 0;
		_currentSpeed = 0;
		
		if(!_arrived && _itineraryIndex == _itinerary.size() - 1) {
			_arrived = true;
			_road = null;
		}
		else {
			//Road nextRoad = _itinerary.get(_itineraryIndex + 1).roadFrom(_itinerary.get(_itineraryIndex)); // Option 1
			Road nextRoad = _itinerary.get(_itineraryIndex).roadTo(_itinerary.get(_itineraryIndex + 1)); // Option 2
			if(nextRoad != null) {
				nextRoad.enter(this);
				_road = nextRoad;
				_atJunction = false;
			}
			else
				throw new UnexistingObjectException("Road from " + _itinerary.get(_itineraryIndex).getId() +
						" to " + _itinerary.get(_itineraryIndex + 1).getId() + " not found!");
		}
	}
	
	protected String getReportSectionTag() {
		return "vehicle_report";
	}
	
	protected void fillReportDetails(IniSection is) {
		is.setValue("speed", this.getSpeed());
		is.setValue("kilometrage", this.getKilometrage());
		is.setValue("faulty", this.getFaultyTime());
		String location;
		if(_arrived)
			location = "arrived";
		else
			location = "(" + this.getRoad().getId() + "," + this.getLocation() + ")";
		is.setValue("location", location);
	}
	
	public String getItineraryString(){
		String itinerary = "[";
		for(Junction j : _itinerary){
			itinerary += j.getId() + ", ";
		}
		itinerary = itinerary.substring(0, itinerary.length() - 2);
		itinerary += "]";
		return itinerary;
	}
	
	public int getItineraryIndex() {
		return _itineraryIndex;
	}
}

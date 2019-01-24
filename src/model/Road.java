package model;

import ini.IniSection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import exceptions.UnexistingObjectException;

/**
 * This class represents a Road. The Vehicles will used in order to follow their itinerary.
 * They go from one Junction to another.
 *
 */
public class Road extends SimulatedObject{
	
	protected Junction _srcJunc;
	protected Junction _destJunc;
	protected ArrayList<Vehicle> _vehicles;
	protected int _length;
	protected int _maxSpeed;
	protected Comparator<Vehicle> _comparator;
	protected int _numVehicles;

	public Road(String id, int length, int maxSpeed, Junction src, Junction dest) {
		super(id);
		_destJunc = dest;
		_srcJunc = src;
		_length = length;
		_maxSpeed = maxSpeed;
		_vehicles = new ArrayList<Vehicle>();
		_numVehicles = 0;
		_comparator = new Comparator<Vehicle>() {
			public int compare(Vehicle v1, Vehicle  v2) {
					if(v1.getLocation() > v2.getLocation())
						return -1;
					else if (v1.getLocation() < v2.getLocation())
						return 1;
					else {
						if(v1.getPositionIndex() < v2.getPositionIndex())
							return -1;
						else
							return 1;
					}
			}
		};
	}
	
	public Junction getSource() {
		return _srcJunc;
	}
	
	public Junction getDestination() {
		return _destJunc;
	}
	
	public int getLength() {
		return _length;
	}
	
	public int getMaxSpeed() {
		return _maxSpeed;
	}
	
	public List<Vehicle> getVehicles(){
		return _vehicles;
	}
	
	void advance() throws UnexistingObjectException {
		int obstacles = 0;
		int nextObstacles = 0;
		int speed = calculateBaseSpeed();
		Vehicle v;
		for(int i = 0; i < _vehicles.size(); i++) {
			v = _vehicles.get(i);
			if(!v.getAtJunction()) {
				int currentSize = _vehicles.size();
				if(v._faultyTime > 0)
					nextObstacles++;
				if(i - 1 >= 0 && v.getLocation() < _vehicles.get(i - 1).getLocation()) {
					obstacles = obstacles + nextObstacles;
					nextObstacles = 0;
				}
				v.setSpeed(speed / reduceSpeedFactor(obstacles));
				v.advance();
				for(int j = i - 1; (j >= 0) && (_vehicles.get(j).getLocation() < v.getLocation()); j--) {
					_vehicles.get(j).decreasePositionIndex();
					v.increasePositionIndex();
				}
				_vehicles.sort(_comparator);
				int newCurrentSize = _vehicles.size();
				if(newCurrentSize < currentSize)
					i--;
			}
		}
	}
	
	void enter(Vehicle v) throws SimulatorError {
		if(!_vehicles.contains(v)) {
			_vehicles.add(v);
			_numVehicles++;
			v.setPositionIndex(_numVehicles);
			_vehicles.sort(_comparator);
		}
		else
			throw new SimulatorError("Error trying to enter the road: The vehicle " + v.getId() + " is already in the road!");
	}
	
	void exit(Vehicle v) throws UnexistingObjectException {
		if(_vehicles.contains(v)) {
			_vehicles.remove(v);
			_numVehicles--;
			for(Vehicle veh : _vehicles)
				veh.increasePositionIndex();
			_vehicles.sort(_comparator);
		}
		else
			throw new UnexistingObjectException("Error trying to exit the road: The vehicle " + v.getId() + " is not in the road!");
	}
	
	protected String getReportSectionTag() {
		return "road_report";
	}
	
	protected void fillReportDetails(IniSection is) {
		String vehicles = "";
		int i = 0;
		for(; i < _vehicles.size(); i++) {
			vehicles += "(" + _vehicles.get(i).getId() + "," + _vehicles.get(i).getLocation() + ")";
			if(i != _vehicles.size() - 1)
				vehicles += ",";
		}
		is.setValue("state", vehicles);
	}
	
	public String getVehiclesString(){
		String vehicles = "[";
		for(Vehicle v : _vehicles){
			vehicles += v.getId() + ", ";
		}
		if(!_vehicles.isEmpty())
			vehicles = vehicles.substring(0, vehicles.length() - 2);
		vehicles += "]";
		return vehicles;
	}
	
	protected int calculateBaseSpeed() {
		int baseSpeed = _maxSpeed;
		int max = 1;
		if(_vehicles.size() > max)
			max = _vehicles.size();
		int division = _maxSpeed / max;
		division++;
		if(division < baseSpeed)
			baseSpeed = division;
		return baseSpeed;		
	}
	
	protected int reduceSpeedFactor(int obstacles) {
		if(obstacles > 0)
			return 2;
		else
			return 1;
	}
}

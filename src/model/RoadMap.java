package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.SameIdException;
import exceptions.UnexistingObjectException;

public class RoadMap {
	
	private ArrayList<Junction> _junctions; // Ordered by addition
	private ArrayList<Road> _roads; // Ordered by addition
	private ArrayList<Vehicle> _vehicles; // Ordered by addition
	private Map<String, Vehicle> _vehiclesMap;
	private Map<String, Road> _roadsMap;
	private Map<String, Junction> _junctionsMap;
	
	private int totalVehicles;
	
	public RoadMap(){
		totalVehicles = 0;
		_junctions = new ArrayList<Junction>();
		_roads = new ArrayList<Road>(); 
		_vehicles = new ArrayList<Vehicle>();
		_vehiclesMap = new HashMap<String, Vehicle>();
		_roadsMap = new HashMap<String, Road>();
		_junctionsMap = new HashMap<String, Junction>();
	}
	
	public Vehicle getVehicle(String id) throws UnexistingObjectException {
		if (_vehiclesMap.get(id) == null)
			throw new UnexistingObjectException("A vehicle with the ID: " + id + " does not exist.");
		return _vehiclesMap.get(id);
	}
	
	public Road getRoad(String id) throws UnexistingObjectException {
		if (_roadsMap.get(id) == null)
			throw new UnexistingObjectException("A road with the ID: " + id + " does not exist.");
		return _roadsMap.get(id);
	}
	
	public Junction getJunction(String id) throws UnexistingObjectException {
		if (_junctionsMap.get(id) == null)
			throw new UnexistingObjectException("A junction with the ID: " + id + " does not exist.");
		return _junctionsMap.get(id);
	}
	
	public List<Vehicle> getVehicles(){
		return _vehicles;
	}
	
	public List<Road> getRoads(){
		return _roads;
	}
	
	public List<Junction> getJunctions(){
		return _junctions;
	}
	
	void addJunction(Junction junction) throws SameIdException {
		if(!_junctionsMap.containsKey(junction.getId())) {
			_junctions.add(junction);
			_junctionsMap.put(junction.getId(), junction);
		}
		else {
			throw new SameIdException("A junction with the ID: " + junction.getId() + " already exists.");
		}
	}
	
	void addVehicle(Vehicle vehicle) throws SimulatorError {
		if(!_vehiclesMap.containsKey(vehicle.getId())) {
			totalVehicles++;
			_vehicles.add(vehicle);
			_vehiclesMap.put(vehicle.getId(), vehicle);
			vehicle.moveToNextRoad();
		}
		else {
			throw new SameIdException("A vehicle with the ID: " + vehicle.getId() + " already exists.");
		}
	}
	
	void addRoad(Road road) throws SameIdException {
		if(!_roadsMap.containsKey(road.getId())) {
			_roads.add(road);
			_roadsMap.put(road.getId(), road);
			road.getSource().addOutGoingRoad(road);
			road.getDestination().addIncommingRoad(road);
		}
		else {
			throw new SameIdException("A road with the ID: " + road.getId() + " already exists.");
		}
	}
	
	void clear() {
		_vehicles.clear();
		_roads.clear();
		_junctions.clear();
		_junctionsMap.clear();
		_vehiclesMap.clear();
		_roadsMap.clear();
		totalVehicles = 0;
	}
	
	public String generateReport(int time) {
		String report = "";
		for(Junction j: _junctions)
			report += j.generateReport(time) + "\n";
		for(Road r: _roads)
			report += r.generateReport(time) + "\n";
		for(Vehicle v: _vehicles)
			report += v.generateReport(time) + "\n";
		return report;
	}
	
	public String generateSelectedReports(int time, Vehicle[] vehicles, Road[] roads, Junction[] junctions) {
		String report = "";
		for(Junction j: junctions)
			report += j.generateReport(time) + "\n";
		for(Road r: roads)
			report += r.generateReport(time) + "\n";
		for(Vehicle v: vehicles)
			report += v.generateReport(time) + "\n";
		return report;
	}
	
	public int getTotalVehicles() {
		return totalVehicles;
	}
}
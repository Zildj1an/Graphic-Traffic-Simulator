package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.UnexistingObjectException;
import ini.IniSection;

/**
 * This class represents a Junction. Junctions are used in order to connect different Roads.
 * Because of this, they will have IncomingRoads, OutgoingRoads or both.
 * Each one of the IncomingRoads will have a traffic light, and only one of them will be turned on.
 *
 */
public class Junction extends SimulatedObject{

	protected ArrayList<IncomingRoad> _roads;
	protected Map<String, IncomingRoad> _incomingRoadsString;
	protected Map<Junction, Road> _incomingRoadsJunction;
	private int _lightIndex;
	protected Map<Junction, Road> _outgoingRoads;
	
	protected int arrived;
	
	public Junction(String id) {
		super(id);
		arrived = 0;
		_roads = new ArrayList<IncomingRoad>();
		_incomingRoadsString = new HashMap<String, IncomingRoad>();
		_incomingRoadsJunction = new HashMap<Junction, Road>();
		_outgoingRoads = new HashMap<Junction, Road>();
		_lightIndex = -1;
	}
	
	void addOutGoingRoad(Road road) {
		_outgoingRoads.put(road.getDestination(), road);
	}
	
	public Road roadTo(Junction nextJunction){
		return _outgoingRoads.get(nextJunction);
	}
	
	public Map<Junction, Road> getIncomingRoadsMap(){
		return _incomingRoadsJunction;
	}
	
	public Map<Junction, Road> getOutgoingRoadsMap(){
		return _outgoingRoads;
	}
	
	void addIncommingRoad(Road road) {
		IncomingRoad entrante = createIncomingRoadQueue(road);
		_roads.add(entrante);
		_incomingRoadsString.put(road.getId(), entrante);
		_incomingRoadsJunction.put(road.getSource(), road);
	}
	
	public Road roadFrom(Junction previousJunction) {
		return _incomingRoadsJunction.get(previousJunction);
	}
	
	public List<IncomingRoad> getRoadsInfo(){
		return _roads;
	}
	
	void enter(String idRoad, Vehicle v) throws UnexistingObjectException {
		if(_incomingRoadsString.get(idRoad) == null)
			throw new UnexistingObjectException("The vehicle " + v.getId() + " had a problem trying to enter the junction");
		else {
			_incomingRoadsString.get(idRoad).addVehicle(v);
		}
	}
	
	void advance() throws SimulatorError {
		if(getLightIndex() != -1)
			_roads.get(getLightIndex()).advanceFirstVehicle();
		this.switchLights();
	}
	
	public int getLightIndex() {
		return _lightIndex;
	}
	
	protected void switchLights() {
		if(_lightIndex != -1){
			_roads.get(_lightIndex).setGreen(false);
			if(_lightIndex == _roads.size() - 1)
				_lightIndex = 0;
			else
				_lightIndex++;
			_roads.get(_lightIndex).setGreen(true);
		}
		else
			if(_roads.size() > 0) {
				_roads.get(0).setGreen(true);
				_lightIndex = 0;
			}
	}
	
	protected IncomingRoad createIncomingRoadQueue(Road road) {
		return new IncomingRoad(road);
	}
	
	protected String getReportSectionTag() {
		return "junction_report";
	}
	
	protected void fillReportDetails(IniSection is) {
		is.setValue("queues", createQueuesString(0));
	}
	
	protected String createQueuesString(int restriction) {
		String queue = "";
		if(restriction == 1 && _lightIndex != -1)
			queue = _roads.get(_lightIndex).toString();
		else {
			for(IncomingRoad r : _roads) {
				switch(restriction) {
					case 0:	// All incoming roads
						queue += r.toString() + ",";
						break;
					case 1:	// Only green incoming roads
						if(r.hasGreenLight())
							queue += r.toString() + ",";
						break;
					case -1:// Only red incoming roads
						if(!r.hasGreenLight())
							queue += r.toString() + ",";
						break;
					default:
						break;
				}	
			}
			if(queue.length() != 0)
				queue = queue.substring(0, queue.length() - 1);
		}
		return queue;
	}
	
	/**
	 * This inner class is used in order to store the Vehicles that are waiting in a Junction.
	 * It will have a traffic light, which can be green or red, a list of vehicles and a Road. 
	 *
	 */
	static public class IncomingRoad{
		
		protected Road _road;
		protected ArrayList<Vehicle> _queue;
		protected boolean _green;
		
		protected IncomingRoad(Road road) {
			_queue = new ArrayList<Vehicle>();
			_green = false;
			_road = road;
		}
		
		public Road getRoad() {
			return _road;
		}

		public boolean hasGreenLight() {
			return _green;
		}
		
		public String getId(){
			return _road.getId();
		}
		
		protected void setGreen(boolean green) {
			_green = green;
		}
		
		protected void advanceFirstVehicle() throws SimulatorError {
			if(_queue.size() > 0) {
				_queue.get(0).moveToNextRoad();
				if(_queue.get(0).atDestination())
					_queue.get(0).getItinerary().get(_queue.get(0).getItineraryIndex()).increaseArrivals();
				_queue.remove(0);
			}
		}
		
		protected void addVehicle(Vehicle v) {
			_queue.add(v);
		}
		
		protected String printQueue() {
			String queue = "";
			for(Vehicle v : _queue) {
				queue += v.getId() + ",";
			}
			if(!_queue.isEmpty())
				queue = queue.substring(0, queue.length() - 1);
			return queue;
		}
		
		public String toString() {
			String queue = "";
			String light = "";
			queue += "(" + _road.getId() + ",";
			light = hasGreenLight() ? "green" : "red";
			queue += light + ",[";
			queue += printQueue();
			queue += "])";
			return queue;
		}
		
	}

	public String getReds() {
			String reds = "[";
			reds += createQueuesString(-1);
			reds += "]";
			return reds;
	}

	public String getGreens() {
			String greens = "[";
			greens += createQueuesString(1);
			greens += "]";
			return greens;
	}
	
	public int getArrivedVehicles() {
		return arrived;
	}
	
	public void increaseArrivals() {
		arrived++;
	}
}

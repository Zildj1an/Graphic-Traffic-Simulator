package model;

import java.io.IOException;
import java.util.Comparator;

public class RacingSimulator extends TrafficSimulator {
	
	protected Comparator<Vehicle> _kartsComparator;
	private int arrivedVehicles;
	
	public RacingSimulator() {
		super();
		_kartsComparator = new Comparator<Vehicle>() {
			public int compare(Vehicle v1, Vehicle  v2) {
				if (v1.getItineraryIndex() > v2.getItineraryIndex())
					return -1;
				else if (v1.getItineraryIndex() < v2.getItineraryIndex())
					return 1;
				else {
					if (v1.getAtJunction() && !v2.getAtJunction())
						return 1;
					else if (!v1.getAtJunction() && v2.getAtJunction())
						return -1;
					else {
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
				}
			}
		};
	}
	
	@Override
	public void run(int ticks) throws IOException, SimulatorError {
		int limit = _time + ticks - 1;
		while(_time <= limit) {
			arrivedVehicles = 0;
			while (!_events.isEmpty() && _events.get(0).getScheduledTime() == _time) {
				_events.get(0).execute(_map, _time);
				_events.remove(0);
			}
			for(Road r: _map.getRoads())
				r.advance();
			for(Junction j: _map.getJunctions()) {
				j.advance();
				arrivedVehicles += j.getArrivedVehicles();
			}
			_map.getVehicles().sort(_kartsComparator);
			for(int i = 0; i < _map.getVehicles().size(); i++)
				((Kart) _map.getVehicles().get(i)).setRacePosition(i + 1);
			_time++;
			notifyAdvanced();
			if(_outStream != null)
				_outStream.write(_map.generateReport(_time).getBytes());
		}
	}
	
	@Override
	public void reset() {
		_numEvents = 0;
		_time = 0;
		_events.clear();
		_map.clear();
		notifyReset();
		arrivedVehicles = 0;
	}
	
	public int getArrivedVehicles() {
		return arrivedVehicles;
	}
}

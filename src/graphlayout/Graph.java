package graphlayout;

import java.util.ArrayList;
import java.util.List;

import model.Junction;
import model.Road;

public class Graph {
	private List<Road> _roads;
	private List<Junction> _junctions;
	
	public Graph() {
		_roads = new ArrayList<>();
		_junctions = new ArrayList<>();
	}
	
	public void addEdge(Road r) {
		_roads.add(r);
	}
	
	public void addNode(Junction j) {
		_junctions.add(j);
	}
	
	public List<Road> getEdges() {
		return _roads;
	}
	
	public List<Junction> getNodes() {
		return _junctions;
	}
}

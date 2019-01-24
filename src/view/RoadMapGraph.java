package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import graphlayout.Graph;
import graphlayout.GraphComponent;
import model.Event;
import model.Junction;
import model.Road;
import model.RoadMap;
import model.SimulatorError;
import model.TrafficSimulatorObserver;

public class RoadMapGraph extends JPanel implements TrafficSimulatorObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected GraphComponent _graph;
	protected RoadMap _map;
	
	protected boolean all;
	protected Junction filteredJunction;
	protected JPopupMenu _graphPopupMenu;
	protected JMenu subMenu;
	
	public RoadMapGraph() {
		initGUI();
	}
	
	@SuppressWarnings("deprecation")
	protected void initGUI() {
		this.setLayout(new BorderLayout());	
		_graph = new GraphComponent();
		all = true;
		this.add(_graph, BorderLayout.CENTER);
		
		_graphPopupMenu = new JPopupMenu();
		subMenu = new JMenu("Filter");			
		_graphPopupMenu.add(subMenu);

		// connect the popup menu to the graph
		this.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger() && _graphPopupMenu.isEnabled()) {
					_graphPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		_graphPopupMenu.setEnabled(false);
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		_map = map;
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, SimulatorError e) {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		if(_map.getJunctions().isEmpty())
			_graphPopupMenu.setEnabled(false);
		else
			_graphPopupMenu.setEnabled(true);
		
		subMenu.removeAll();
		JMenuItem menuItemAll = new JMenuItem("All");
		menuItemAll.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				all = true;
				_graph.setFocus(false);
				generateGraph();
			}
		});
		
		subMenu.add(menuItemAll);
		subMenu.addSeparator();

		for (Junction j : _map.getJunctions()) {
			JMenuItem menuItem = new JMenuItem(j.toString());
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					all = false;
					filteredJunction = j;
					_graph.setFocus(true);
					_graph.setFocusedJunction(filteredJunction);
					generateGraph(filteredJunction);
				}
			});
			subMenu.add(menuItem);
		}
		_graph.setAvanzado(true);
		if(all)
			generateGraph();
		else
			generateGraph(filteredJunction);
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		_graph.reset();
		all = true;
		filteredJunction = null;
		subMenu.removeAll();
		generateGraph();
		_graphPopupMenu.disable();
	}
	
	protected void generateGraph(Junction j) {
		Graph g = new Graph();
		
		g.addNode(j);
		for(Junction j1 : j.getIncomingRoadsMap().keySet())
			g.addNode(j1);
		for(Junction j2 : j.getOutgoingRoadsMap().keySet())
			g.addNode(j2);
		for(Road r1 : j.getIncomingRoadsMap().values())
			g.addEdge(r1);
		for(Road r2 : j.getOutgoingRoadsMap().values())
			g.addEdge(r2);
		
		_graph.setGraph(g);
	}

	protected void generateGraph() {
		Graph g = new Graph();
		
		for(Junction j : _map.getJunctions())
			g.addNode(j);
		
		for (Road r : _map.getRoads())
			g.addEdge(r);
		
		_graph.setGraph(g);
	}
}

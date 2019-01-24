package view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Event;
import model.RoadMap;
import model.SimulatorError;
import model.TrafficSimulatorObserver;

public class StateBarPanel extends JPanel implements TrafficSimulatorObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel infoExecution;
	
	public StateBarPanel() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		infoExecution = new JLabel("Welcome to the simulator!");
		this.add(infoExecution);
		this.setBorder(BorderFactory.createBevelBorder(1));
	}
	
	public void setMessage(String str) {
		infoExecution.setText(str);
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, SimulatorError e) {
		infoExecution.setText("Error with the simulator!");
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		infoExecution.setText("Simulator reseted...");
	}
}

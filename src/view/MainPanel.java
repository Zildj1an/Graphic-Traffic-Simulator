package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.*;

import control.Controller;

public class MainPanel extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected RunThread thread;
	
	protected Controller _control;
	protected File _inFile;
	protected OutputStream _outputStream;
	protected int _steps;
	
	protected JPanel _mainPanel;
	protected MenuBar _menuBar;
	protected ToolBar _toolBar;
	protected StateBarPanel _stateBar;
	
	// Top Panel
	protected JPanel _topPanel;
		// Events Editor
	protected EventsEditorPanel _eventsEditor;
		// Events Queue
	protected EventsQueueTable _eventsQueue;
		//Reports Area
	protected ReportsAreaPanel _reportsArea;
	// Down Panel
	protected JPanel _downPanel;
		// Down Left Panel
	protected JPanel _downLeftPanel;
			// Vehicles Table
	protected VehiclesTable _vehiclesTable;
			// Roads Table
	protected RoadsTable _roadsTable;
			// Junctions Table
	protected JunctionsTable _junctionsTable;
		// Down Right Panel
	protected JPanel _downRightPanel;
	protected RoadMapGraph _roadmapGraph;
	
	public MainPanel(String inFile, Controller control, int steps) throws IOException {
		super("Traffic Simulator");
		thread = null;
		_steps = steps;
		_control = control;
		_inFile = inFile == null ? null : new File(inFile);
		_outputStream = new OutputStream() {
			@Override
			public void write(byte[] b) throws IOException {
				_reportsArea.insert(new String(b));
			}

			@Override
			public void write(int b) throws IOException {
			}
		};
		initGUI();
		this.setMinimumSize(new Dimension(1000, 1000));
		this.setPreferredSize(new Dimension(1000, 1000));
		this.setMaximumSize(new Dimension(1000, 1000));
		this.setResizable(false);
	}

	protected void initGUI() throws IOException {
		this.setTitle("Traffic Simulator");
			
		// Main Panel
		createMainPanel();
		this.setContentPane(_mainPanel);
				
		// Menu Bar
		createMenuBar();
		this.setJMenuBar(_menuBar);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	protected void createMenuBar() {
		_menuBar = new MenuBar(this, _eventsEditor, _reportsArea, _toolBar, _control);
	}
	
	protected void createMainPanel() throws IOException {
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
		
		createStateBar();
		
		createTopPanel();
		
		createDownPanel();
		
		createToolBar();
		
		_mainPanel.add(_toolBar);
		
		_mainPanel.add(_topPanel);
		
		_mainPanel.add(_downPanel);
		
		_mainPanel.add(_stateBar);
	}
	
	private void createStateBar() {
		_stateBar = new StateBarPanel();
		_control.addObserver(_stateBar);
	}
	
	protected void createToolBar() {
		_toolBar = new ToolBar(this, _eventsEditor, _reportsArea, _steps);
		_control.addObserver(_toolBar);
	}
	
	protected void createDownPanel() {
		_downPanel = new JPanel();
		_downPanel.setLayout(new BoxLayout(_downPanel, BoxLayout.X_AXIS));
		
		createDownLeftPanel();
		_downPanel.add(_downLeftPanel);
		
		createDownRightPanel();
		_downPanel.add(_downRightPanel);		
	}
	
	private void createDownRightPanel() {
		_downRightPanel = new JPanel();	
		_downRightPanel.setLayout(new BorderLayout());
		
		createRoadMapGraph();
		_downRightPanel.add(_roadmapGraph, BorderLayout.CENTER);
	}
	
	protected void createRoadMapGraph() {
		_roadmapGraph = new RoadMapGraph();
		_control.addObserver(_roadmapGraph);
	}
	
	protected void createDownLeftPanel() {
		_downLeftPanel = new JPanel();
		_downLeftPanel.setLayout(new BoxLayout(_downLeftPanel, BoxLayout.Y_AXIS));
		
		// Vehicles Table
		createVehiclesTable();
		_downLeftPanel.add(_vehiclesTable);
		
		// Roads Table
		createRoadsTable();
		_downLeftPanel.add(_roadsTable);
		
		// Junctions Table
		createJunctionsTable();
		_downLeftPanel.add(_junctionsTable);
	}
	
	protected void createVehiclesTable() {
		_vehiclesTable = new VehiclesTable();
		_control.addObserver(_vehiclesTable);
		_vehiclesTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Vehicles"));
	}
	
	private void createRoadsTable() {
		_roadsTable = new RoadsTable();
		_control.addObserver(_roadsTable);
		_roadsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Roads"));
	}
	
	private void createJunctionsTable() {
		_junctionsTable = new JunctionsTable();
		_control.addObserver(_junctionsTable);
		_junctionsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Junctions"));
	}
	
	protected void createTopPanel() throws IOException {
		_topPanel = new JPanel();
		_topPanel.setMinimumSize(new Dimension(1000, 200));
		_topPanel.setPreferredSize(new Dimension(1000, 200));
		_topPanel.setMaximumSize(new Dimension(1000, 200));
		_topPanel.setLayout(new BoxLayout(_topPanel, BoxLayout.X_AXIS));
		
		// Events Editor
		createEventsEditor();
		_topPanel.add(_eventsEditor);
		
		// Events Queue
		createEventsQueue();
		_topPanel.add(_eventsQueue);
		
		// Reports Area
		createReportsArea();
		_topPanel.add(_reportsArea);
	}
	
	protected void createEventsEditor() throws IOException {
		_eventsEditor = new EventsEditorPanel("Events: ", "", true, _inFile, _control, _stateBar);
	}
	
	private void createEventsQueue() {
		_eventsQueue = new EventsQueueTable();
		_control.addObserver(_eventsQueue);
		_eventsQueue.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Events Queue"));
	}
	
	private void createReportsArea() {
		_reportsArea = new ReportsAreaPanel(_stateBar, this, "Reports", false);
		_control.addObserver(_reportsArea);
		_control.setOutputStream(_outputStream);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		switch (str){
			case "RESET":
				_control.reset();
				_reportsArea.clear();
				break;
			case "RUN":
				try {
					if(thread == null || !thread.isAlive()) {
						thread = new RunThread(_control, _toolBar, _menuBar);
						thread.start();
					}
					_stateBar.setMessage(_toolBar.getTime() + " steps advanced!");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "PAUSE":
				if(thread != null && !thread.isInterrupted()) {
					thread.interrupt();
					thread = null;
				}
				break;
			case "QUIT":
				System.exit(0);
				break;
			case "REDIRECT":
				JCheckBoxMenuItem redirect = (JCheckBoxMenuItem)e.getSource();
				if(redirect.isSelected())
					_control.setOutputStream(_outputStream);
				else
					_control.setOutputStream(null);
				break;
			default:
				break;
		}
	}
}

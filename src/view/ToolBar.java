package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import model.Event;
import model.RoadMap;
import model.SimulatorError;
import model.TrafficSimulatorObserver;

public class ToolBar extends JToolBar implements TrafficSimulatorObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	protected JButton loadEventsButton;
	protected JButton saveEventsButton;
	protected JButton clearEventsButton;
	protected JButton checkInEventsButton;
	protected JButton runButton;
	protected JButton pauseButton;
	protected JButton resetButton;
	protected JLabel delayLabel;
	protected JSpinner delaySpinner;
	protected JLabel stepsLabel;
	protected JSpinner stepsSpinner;
	protected JLabel timeLabel;
	protected JTextField timeTextField;
	protected JButton generateReportsButton;
	protected JButton clearReportsAreaButton;
	protected JButton saveReportsButton;
	protected JButton quitButton;
	protected JLabel authors;
	
	protected MainPanel _mainPanel;
	protected ActionListener _eventsListener;
	protected ActionListener _reportsListener;
	protected int _steps;
	
	public ToolBar(MainPanel mainPanel, ActionListener eventsListener, ActionListener reportsListener, int steps) {
		super();
		_mainPanel = mainPanel;
		_eventsListener = eventsListener;
		_reportsListener = reportsListener;
		_steps = steps;
		initGUI();
	}
	
	public void able(boolean b) {
		loadEventsButton.setEnabled(b);
		saveEventsButton.setEnabled(b);
		clearEventsButton.setEnabled(b);
		checkInEventsButton.setEnabled(b);
		runButton.setEnabled(b);
		resetButton.setEnabled(b);
		delaySpinner.setEnabled(b);
		stepsSpinner.setEnabled(b);
		generateReportsButton.setEnabled(b);
		clearReportsAreaButton.setEnabled(b);
		saveReportsButton.setEnabled(b);
		quitButton.setEnabled(b);
	}

	protected void initGUI() {
		// Events
		loadEventsButton = new JButton();
		createGenericButton(loadEventsButton, "LOAD", _eventsListener, "/icons/open.png", "Load events file");
			
		saveEventsButton = new JButton();
		createGenericButton(saveEventsButton, "SAVE", _eventsListener, "/icons/save.png", "Save events in a file");
			
		clearEventsButton = new JButton();
		createGenericButton(clearEventsButton, "CLEAR", _eventsListener, "/icons/clear.png", "Clear events editor");
			
		checkInEventsButton = new JButton();
		createGenericButton(checkInEventsButton, "CHECK IN", _eventsListener, "/icons/events.png", "Check in events");
			
		// Simulator
		runButton = new JButton();
		createGenericButton(runButton, "RUN", _mainPanel, "/icons/play.png", "Run");
		
		pauseButton = new JButton();
		createGenericButton(pauseButton, "PAUSE", _mainPanel, "/icons/pause.png", "Pause");
			
		resetButton = new JButton();
		createGenericButton(resetButton, "RESET", _mainPanel, "/icons/reset.png", "Reset");
		
		delayLabel = new JLabel("Delay: ");
		
		delaySpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		delaySpinner.setMinimumSize(new Dimension(50, 50));
		delaySpinner.setPreferredSize(new Dimension(50, 50));
		delaySpinner.setMaximumSize(new Dimension(50, 50));
			
		stepsLabel = new JLabel("Steps: ");
		
		stepsSpinner = new JSpinner(new SpinnerNumberModel(_steps, 0, null, 1));
		stepsSpinner.setMinimumSize(new Dimension(50, 50));
		stepsSpinner.setPreferredSize(new Dimension(50, 50));
		stepsSpinner.setMaximumSize(new Dimension(50, 50));
			
		timeLabel = new JLabel(" Time: ");
		
		timeTextField = new JTextField();
		timeTextField.setEditable(false);
		timeTextField.setMinimumSize(new Dimension(50, 50));
		timeTextField.setPreferredSize(new Dimension(50, 50));
		timeTextField.setMaximumSize(new Dimension(50, 50));
		
		this.addSeparator();
		
		// Reports
		generateReportsButton = new JButton();
		createGenericButton(generateReportsButton, "GENERATE", _reportsListener, "/icons/report.png", "Generate reports");
				
		clearReportsAreaButton = new JButton();
		createGenericButton(clearReportsAreaButton, "CLEAR", _reportsListener, "/icons/delete_report.png", "Clear reports area");
		
		saveReportsButton = new JButton();
		createGenericButton(saveReportsButton, "SAVE", _reportsListener, "/icons/save_report.png", "Save reports in a file");	
		
		// Exit
		quitButton = new JButton();
		createGenericButton(quitButton, "QUIT", _mainPanel, "/icons/exit.png", "Exit");
		
		authors = new JLabel("Álvaro & Carlos ");
		
		addComponents();
		
		setMinimumSize(new Dimension(1000, 50));
		setPreferredSize(new Dimension(1000, 50));
		setMaximumSize(new Dimension(1000, 50));
	}
	
	protected void addComponents() {
		this.add(loadEventsButton);
		this.add(saveEventsButton);
		this.add(clearEventsButton);		
		this.addSeparator();
		this.add(checkInEventsButton);
		this.add(runButton);
		this.add(pauseButton);
		this.add(resetButton);
		this.addSeparator();
		this.add(delayLabel);
		this.add(delaySpinner);
		this.addSeparator();
		this.add(stepsLabel);
		this.add(stepsSpinner);
		this.addSeparator();
		this.add(timeLabel);
		this.add(timeTextField);
		this.addSeparator();
		this.add(generateReportsButton);
		this.add(clearReportsAreaButton);
		this.add(saveReportsButton);
		this.addSeparator();
		this.add(quitButton);
		this.addSeparator();
		this.addSeparator();
		this.addSeparator();
		this.add(authors);
	}
	
	protected void createGenericButton(JButton button, String actionCommand, ActionListener actionListener, String path, String toolTip) {
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTip);
		button.addActionListener(actionListener);
		ImageIcon icon = new ImageIcon(this.getClass().getResource(path));
		icon.setImage(icon.getImage().getScaledInstance(32, 32, 1));
		button.setIcon(icon);
	}
	
	public int getTime(){
		return (Integer)stepsSpinner.getValue();
	}
	
	public int getDelay(){
		return (Integer)delaySpinner.getValue();
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		timeTextField.setText("0");
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, SimulatorError e) {
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		timeTextField.setText("" + time);
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		timeTextField.setText("0");
	}
}

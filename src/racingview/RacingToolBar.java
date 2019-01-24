package racingview;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import view.MainPanel;
import view.ReportsAreaPanel;
import view.ToolBar;

public class RacingToolBar extends ToolBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JButton playMusicButton;
	protected JButton stopMusicButton;
	protected JButton randomMusicButton;
	protected JComboBox<String> playList;
	protected JLabel itemBoxLabel;
	protected JButton itemBoxButton;
	protected boolean itemBoxActivated;
	protected JLabel lapsLabel;
	protected JSpinner lapsSpinner;
	
	private String[] _songs;

	public RacingToolBar(MainPanel mainPanel, ActionListener imagesPanel, ReportsAreaPanel reportsArea, int steps) {
		super(mainPanel, imagesPanel, reportsArea, steps);
	}
	
	public void raceFinished() {
		runButton.setEnabled(false);
		stepsSpinner.setEnabled(false);
		itemBoxButton.setEnabled(false);
	}
	
	public void reset() {
		runButton.setEnabled(true);
		stepsSpinner.setEnabled(true);
	}
	
	public void able(boolean b) {
		playMusicButton.setEnabled(b);
		stopMusicButton.setEnabled(b);
		randomMusicButton.setEnabled(b);
		itemBoxButton.setEnabled(b);
		runButton.setEnabled(b);
		resetButton.setEnabled(b);
		delaySpinner.setEnabled(b);
		stepsSpinner.setEnabled(b);
		lapsSpinner.setEnabled(b);
		playList.setEnabled(b);
		quitButton.setEnabled(b);
	}
	
	@Override
	protected void initGUI() {
		_songs = ((RacingPanel)_mainPanel).getSelectedSongs();
		
		// Simulator
		runButton = new JButton();
		createGenericButton(runButton, "RUN", _mainPanel, "/icons/play.png", "Run");
		
		pauseButton = new JButton();
		createGenericButton(pauseButton, "PAUSE", _mainPanel, "/icons/pause.png", "Pause");
			
		resetButton = new JButton();
		createGenericButton(resetButton, "RESET", _mainPanel, "/icons/reset.png", "Reset");
			
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
		
		// Music
		playMusicButton = new JButton();
		createGenericButton(playMusicButton, "PLAY", _mainPanel, "/icons/play_music.png", "Play music");
				
		stopMusicButton = new JButton();
		createGenericButton(stopMusicButton, "STOP", _mainPanel, "/icons/stop_music.png", "Pause music");
				
		randomMusicButton = new JButton();
		createGenericButton(randomMusicButton, "RANDOM", _mainPanel, "/icons/random_music.png", "Random song");	
		
		playList = new JComboBox<String>(_songs);
		playList.setToolTipText("Select a song");
		playList.setSelectedIndex(0);
		playList.setActionCommand("PLAYLIST");
		playList.addActionListener(_mainPanel);
		playList.setMinimumSize(new Dimension(170, 50));
		playList.setPreferredSize(new Dimension(170, 50));
		playList.setMaximumSize(new Dimension(170, 50));
		
		itemBoxLabel = new JLabel("Item Box ");
		itemBoxButton = new JButton();
		createGenericButton(itemBoxButton, "ITEM BOX", _mainPanel, "/icons/itembox.png", "Item Box");
		itemBoxButton.setEnabled(false);
		itemBoxActivated = true;
		
		// Delay		
		delayLabel = new JLabel("Delay: ");
		
		delaySpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		delaySpinner.setMinimumSize(new Dimension(50, 50));
		delaySpinner.setPreferredSize(new Dimension(50, 50));
		delaySpinner.setMaximumSize(new Dimension(50, 50));
		
		//Laps
		lapsLabel = new JLabel("Laps: ");
		
		lapsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, null, 1));
		lapsSpinner.setMinimumSize(new Dimension(50, 50));
		lapsSpinner.setPreferredSize(new Dimension(50, 50));
		lapsSpinner.setMaximumSize(new Dimension(50, 50));
		
		// Exit
		quitButton = new JButton();
		createGenericButton(quitButton, "QUIT", _mainPanel, "/icons/exit.png", "Exit");
		
		addComponents();
		
		setMinimumSize(new Dimension(1000, 50));
		setPreferredSize(new Dimension(1000, 50));
		setMaximumSize(new Dimension(1000, 50));
	}
	
	public int getLaps() {
		return (Integer)lapsSpinner.getValue();
	}
	
	@Override
	protected void addComponents() {
		this.add(runButton);
		this.add(pauseButton);
		this.add(resetButton);
		this.add(delayLabel);
		this.add(delaySpinner);
		this.add(stepsLabel);
		this.add(stepsSpinner);
		this.add(timeLabel);
		this.add(timeTextField);
		this.addSeparator();
		this.add(playMusicButton);
		this.add(stopMusicButton);
		this.add(randomMusicButton);
		this.add(playList);
		this.addSeparator();
		this.add(itemBoxLabel);
		this.add(itemBoxButton);
		this.addSeparator();
		this.add(lapsLabel);
		this.add(lapsSpinner);
		this.addSeparator();
		this.add(quitButton);
	}

	public JComboBox<String> getComboBox() {
		return playList;
	}
	
	public void setLapsSpinnerEnabled(boolean b) {
		lapsSpinner.setEnabled(b);
	}
	
	public void setItemBoxEnabled(boolean b) {
		itemBoxButton.setEnabled(b);
	}
	
	public boolean getItemBoxActivated() {
		return itemBoxActivated;
	}
	
	public void setItemBoxActivated(boolean b) {
		itemBoxActivated = b;;
	}
}

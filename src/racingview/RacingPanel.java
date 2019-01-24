package racingview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Controller;
import model.Kart;
import model.Observable;
import model.RacingSimulator;
import model.RacingSimulatorObserver;
import model.SimulatorError;
import model.TrafficSimulator;
import model.Vehicle;
import music.Music;
import view.MainPanel;

public class RacingPanel extends MainPanel implements Observable<RacingSimulatorObserver>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum Universe{
		STARWARS("starwars"), MARIOKART("mariokart");
		
		String str;
		
		private Universe(String str) {
			this.str = str;
		}
		
		public String toString() {
			return str;
		}
		
	}
	
	private static Universe universe = Universe.MARIOKART;
	
	private static final Integer[] starwars_speeds = {35, 25, 50, 45, 35, 20, 10, 15};
	private static final Integer[] starwars_lucks = {80, 50, 50, 20, 60, 80, 100, 35};
	private static final String[] starwars_ids = {"yoda", "vader", "solo", "boba", "luke", "leia", "r2d2", "chewbacca"};
	private static final String[] starwars_songs = {"Cantina",
			"ThroneRoom",
			"ImperialMarch"};
	private static final String[][] starwars_junctions = {{"Coruscant", "Hoth", "Kamino", "Mustafar"}, // Mushroom cup
			{"Naboo", "Tatooine", "Yavin", "Endor"}, // Flower cup
			{"Death Star", "Starkiller", "Cantina", "Jabba's Palace"}, // Star cup
			{"Galactic Senate", "Emperor's Lair", "Alderaan", "Bespin"}, // Special cup
			{"Corellia", "Crait", "Jakku", "Geonosis"}, // Shell cup
			{"Polis Massa", "Dagobah", "Ahch-To", "Kashyyyk"}, // Banana cup
			{"Canto Bight", "Scarif", "Yavin 4", "Takodana"}, // Leaf cup
			{"Lah'mu", "Utapau", "Felucia", "Cato Neimoidia"}}; // Lightning cup
	private static final Integer[] mario_speeds = {35, 35, 10, 40, 15, 25, 40, 30};
	private static final Integer[] mario_lucks = {75, 70, 90, 50, 40, 90, 30, 60};
	private static final String[] mario_ids = {"mario", "luigi", "bowser", "peach", "donkey", "toad", "yoshi", "koopa"};
	private static final String[][] mario_junctions = {{"Mario Kart Stadium", "Water Park", "Sweet Sweet Canyon", "Thwomp Ruins"}, // Mushroom cup
			{"Mario Circuit", "Toad Harbor", "Twisted Mansion", "Shy Guy Falls"}, // Flower cup
			{"Sunshine Airport", "Dolphin Shoals", "Electrodrome", "Mount Wario"}, // Star cup
			{"Cloudtop Cruise", "Bone-Dry Dunes", "Bowser's Castle", "Rainbow Road"}, // Special cup
			{"Moo Moo Meadows", "Mario Circuit", "Cheep Cheep Beach", "Toad's Turnpike"}, // Shell cup
			{"Dry Dry Desert", "Donut Plains 3", "Royal Raceway", "DK Jungle"}, // Banana cup
			{"Wario Stadium", "Sherbet Land", "Music Park", "Yoshi Valley"}, // Leaf cup
			{"Tick-Tock Clock", "Piranha Plant Slide", "Grumble Volcano", "Rainbow Road"}}; // Lightning cup
	private static final String[] mario_songs = {"MooMooFarm",
			"Kirby",
			"BowserCastle",
			"MountWario",
			"SuperMarioWorldAthletic",
			"SweetSweetCanyon",
			"ToadHarbor",
			"WarioGoldMine",
			"YoshiCircuit"};
	
	private static String[] circuits = {"Mushroom",
			"Flower",
			"Star",
			"Special",
			"Shell",
			"Banana",
			"Leaf",
			"Lightning"};
	
	private String[] ids;
	private Integer[] speeds;
	private Integer[] lucks;
	private String[] songs;
	private String[][] junctions;
	
	private List<RacingSimulatorObserver> _observers;
	
	private Music _music;
	private boolean playingMusic;
	private CharacterChooserPanel _characterChooserPanel;
	private ImagesPanel _imagesPanel;
	private CupChooserPanel _circuitChooserPanel;
	private SelectedCharactersTable _selectedCharactersTable;
	private JPanel _selectedCupPanel;
	private SelectedCupTable _selectedCupTable;
	private JPanel _selectedCupImage;
	private ClassificationTable _classificationTable;

	public RacingPanel(String inFile, Controller control, int steps) throws IOException {
		super(inFile, control, steps);
		playingMusic = false;
		this.setMinimumSize(new Dimension(1000, 1000));
		this.setPreferredSize(new Dimension(1000, 1000));
		this.setMaximumSize(new Dimension(1000, 1000));
		this.setResizable(false);
	}
	
	public String[] getSelectedSongs() {
		return songs;
	}
	
	@SuppressWarnings("static-access")
	private void selectUniverse() {
		_music = new Music("src/music/start_simulator.wav");
		_music.play();
		
		JOptionPane option = new JOptionPane();
		
		JPanel select = new JPanel();
		
		JButton buttonMK = new JButton();
		createSelectUniverseButton(buttonMK, "mariokart", Universe.MARIOKART, "Mario Kart");
	
		JButton buttonSW = new JButton();
		createSelectUniverseButton(buttonSW, "starwars", Universe.STARWARS, "Star Wars");
		buttonSW.setToolTipText("Star Wars");
		
		select.setLayout(new GridLayout(2, 2));
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/welcome.gif"));
		icon.setImage(icon.getImage().getScaledInstance(300, 150, 1));
		JLabel hello = new JLabel("Select the universe you want to race in...");
		hello.setHorizontalAlignment(JLabel.CENTER);
		select.add(new JLabel(icon));
		select.add(hello);
		select.add(buttonMK);
		select.add(buttonSW);
		select.setVisible(true);
		select.setMinimumSize(new Dimension(600, 300));
		select.setPreferredSize(new Dimension(600, 300));
		select.setMaximumSize(new Dimension(600, 300));
		
		option.showMessageDialog(this, select, "Welcome!", JOptionPane.NO_OPTION);
		_music.stop();
		_music = null;
	}
	
	private void createSelectUniverseButton(JButton button, String path, Universe universe, String toolTip) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/" + path + ".png"));
		icon.setImage(icon.getImage().getScaledInstance(300, 150, 1));
		button.setIcon(icon);
		button.setToolTipText(toolTip);
		button.setPreferredSize(new Dimension(300, 150));
		button.setMaximumSize(new Dimension(300, 150));
		button.setMinimumSize(new Dimension(300, 150));
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RacingPanel.universe = universe;
			}
		});
	}
	
	@Override
	protected void initGUI() throws IOException {
		selectUniverse();
		switch(universe) {
		case STARWARS:
			ids = starwars_ids;
			speeds = starwars_speeds;
			lucks = starwars_lucks;
			songs = starwars_songs;
			junctions = starwars_junctions;
			break;
		case MARIOKART:
			ids = mario_ids;
			speeds = mario_speeds;
			lucks = mario_lucks;
			songs = mario_songs;
			junctions = mario_junctions;
			break;
		default:
			break;
		}
		_music = new Music("src/music/" + songs[0] + ".wav");
		_observers = new ArrayList<>();
		
		this.setTitle("Racing Simulator");
		
		createMainPanel();
		this.setContentPane(_mainPanel);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	protected void createMainPanel() throws IOException {
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
		
		createTopPanel();
		
		createDownPanel();
		
		createToolBar();
		
		_mainPanel.add(_toolBar);
		
		_mainPanel.add(_topPanel);
		
		_mainPanel.add(_downPanel);
	}
	
	@Override
	protected void createTopPanel() throws IOException {
		_topPanel = new JPanel();
		_topPanel.setMinimumSize(new Dimension(1000, 150));
		_topPanel.setPreferredSize(new Dimension(1000, 150));
		_topPanel.setMaximumSize(new Dimension(1000, 150));
		_topPanel.setLayout(new BoxLayout(_topPanel, BoxLayout.X_AXIS));
		
		createImagesPanel();
		createCharacterChooserPanel();
		createCircuitChooserPanel();
	
		_topPanel.add(_characterChooserPanel);
		_topPanel.add(_imagesPanel);
		_topPanel.add(_circuitChooserPanel);	
	}
	
	private void createCharacterChooserPanel() {
		_characterChooserPanel = new CharacterChooserPanel(ids, this, _imagesPanel, speeds, lucks);
		_characterChooserPanel.setMinimumSize(new Dimension(300, 150));
		_characterChooserPanel.setPreferredSize(new Dimension(300, 150));
		_characterChooserPanel.setMaximumSize(new Dimension(300, 150));
	}
	
	private void createImagesPanel() {
		_imagesPanel = new ImagesPanel("/images/" + universe.toString() + ".png", _control);
		_imagesPanel.setLayout(new BoxLayout(_imagesPanel, BoxLayout.X_AXIS));
		_imagesPanel.setMinimumSize(new Dimension(400, 150));
		_imagesPanel.setPreferredSize(new Dimension(400, 150));
		_imagesPanel.setMaximumSize(new Dimension(400, 150));
	}
	
	private void createClassificationTable() {
		_classificationTable = new ClassificationTable();
		_control.addObserver(_classificationTable);
		_classificationTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Clasification: "));
	}
	
	private void createCircuitChooserPanel() {
		_circuitChooserPanel = new CupChooserPanel(circuits, this, _imagesPanel, junctions);
		_circuitChooserPanel.setMinimumSize(new Dimension(300, 150));
		_circuitChooserPanel.setPreferredSize(new Dimension(300, 150));
		_circuitChooserPanel.setMaximumSize(new Dimension(300, 150));
	}
	
	@Override
	protected void createDownLeftPanel() {
		_downLeftPanel = new JPanel();
		_downLeftPanel.setLayout(new BoxLayout(_downLeftPanel, BoxLayout.Y_AXIS));
		_downLeftPanel.setMinimumSize(new Dimension(440, 850));
		_downLeftPanel.setPreferredSize(new Dimension(440, 850));
		_downLeftPanel.setMaximumSize(new Dimension(440, 850));
		
		createSelectedCupPanel();
		createSelectedCharactersTable();
		
		_downLeftPanel.add(_selectedCharactersTable);
		_downLeftPanel.add(_selectedCupPanel);
	}
	
	private void createSelectedCupPanel(){
		_selectedCupPanel = new JPanel();
		_selectedCupPanel.setMinimumSize(new Dimension(420, 380));
		_selectedCupPanel.setPreferredSize(new Dimension(420, 380));
		_selectedCupPanel.setMaximumSize(new Dimension(420, 380));
		_selectedCupPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), _circuitChooserPanel.getSelectedCup() + " Cup"));
		
		createSelectedCupImage();
		createSelectedCupTable();
		
		_selectedCupPanel.add(_selectedCupImage);
		_selectedCupPanel.add(_selectedCupTable);
	}
	
	private void createSelectedCupImage() {
		_selectedCupImage = new JPanel();
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/" + _circuitChooserPanel.getSelectedCup() + "trophy" + ".jpg"));
		icon.setImage(icon.getImage().getScaledInstance(390, 230, 1));
		_selectedCupImage.add(new JLabel(icon));
	}
	
	private void createSelectedCharactersTable() {
		_selectedCharactersTable = new SelectedCharactersTable();
		addObserver(_selectedCharactersTable);
		_selectedCharactersTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Pilots"));
		_selectedCharactersTable.setMinimumSize(new Dimension(420, 385));
		_selectedCharactersTable.setPreferredSize(new Dimension(420, 385));
		_selectedCharactersTable.setMaximumSize(new Dimension(420, 385));
	}
	
	private void createSelectedCupTable() {
		_selectedCupTable = new SelectedCupTable();
		addObserver(_selectedCupTable);
		_selectedCupTable.setMinimumSize(new Dimension(410, 100));
		_selectedCupTable.setPreferredSize(new Dimension(410, 100));
		_selectedCupTable.setMaximumSize(new Dimension(410, 100));
	}
	
	@Override
	protected void createToolBar() {
		_toolBar = new RacingToolBar(this, _imagesPanel, _reportsArea, _steps);
		_control.addObserver(_toolBar);
	}
	
	@Override
	protected void createRoadMapGraph() {
		_roadmapGraph = new RacingRoadMapGraph();
		_control.addObserver(_roadmapGraph);
	}
	
	public void raceFinished() {
		if(playingMusic)
			_music.stop();
		Music temp = _music;
		_music = new Music("src/music/CourseClear.wav");
		_music.play();
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/finish.gif"));
		JOptionPane.showMessageDialog(this, "The race has finished!", "CONGRATULATIONS", JOptionPane.INFORMATION_MESSAGE, icon);
		_music.stop();
		_music = null;
		_music = temp;
		if(playingMusic)
			_music.loop();
	}
	
	private void raceStarted() {
		((RacingToolBar) _toolBar).setItemBoxEnabled(true);
		((RacingToolBar) _toolBar).setLapsSpinnerEnabled(false);
		_downLeftPanel.removeAll();
		createClassificationTable();
		_downLeftPanel.add(_classificationTable);
		_downLeftPanel.repaint();
		_downLeftPanel.updateUI();
		
		if(playingMusic)
			_music.stop();
		Music temp = _music;
		_music = new Music("src/music/start_race.wav");
		_music.play();
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/lakitu.gif"));
		JOptionPane.showMessageDialog(this, "READY\n\nSET\n\nGO!", "The race is starting!", JOptionPane.INFORMATION_MESSAGE, icon);
		_music.stop();
		_music = null;
		_music = temp;
		if(playingMusic)
			_music.loop();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		switch (str){
			case "ITEM BOX":
				ImageIcon itemboxicon = new ImageIcon(this.getClass().getResource("/icons/itembox.png"));
				itemboxicon.setImage(itemboxicon.getImage().getScaledInstance(150, 150, 1));
				String activated = !((RacingToolBar) _toolBar).getItemBoxActivated() ? "enabled!\n(Each pilot will get one"
						+ " when he/she starts a new lap)" : "disabled!";				
				JOptionPane.showMessageDialog(this, "Item Boxes are now " + activated, "Item Box", JOptionPane.INFORMATION_MESSAGE, itemboxicon);
				((RacingToolBar) _toolBar).setItemBoxActivated(!((RacingToolBar) _toolBar).getItemBoxActivated());
				for(Vehicle v : _control.getRoadMap().getVehicles())
					((Kart)v).changeItemBox();
				break;
			case "RESET":
				((RacingToolBar) _toolBar).setLapsSpinnerEnabled(true);
				((RacingToolBar) _toolBar).setItemBoxEnabled(false);
				((RacingToolBar) _toolBar).setItemBoxActivated(true);
				((RacingToolBar) _toolBar).reset();
				notifyReset();
				_control.reset();
				_characterChooserPanel.reset();
				_circuitChooserPanel.reset();
				_downLeftPanel.removeAll();
				createSelectedCharactersTable();
				createSelectedCupPanel();
				_downLeftPanel.add(_selectedCharactersTable);
				_downLeftPanel.add(_selectedCupPanel);
				_downLeftPanel.repaint();
				_downLeftPanel.updateUI();
				break;
			case "RUN":
				boolean begin = true;
				if(_control.getTime() == 0) {
					_circuitChooserPanel.setSelectedCup(((RacingToolBar) _toolBar).getLaps());
					try {
						begin = _imagesPanel.checkIn(_circuitChooserPanel.getSelectedCupJunctions(), 
								_circuitChooserPanel.getSelectedCupItinerary(), _characterChooserPanel.getSpeedMap(), 
								_characterChooserPanel.getLuckMap(), _characterChooserPanel.getSelectedCharacters());
					} catch (SimulatorError e1) {
						JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				if(begin) {
					try {
						if(_control.getTime() == 0)
							raceStarted();					
						if(thread == null || !thread.isAlive()) {
							thread = new RacingRunThread(_control, _toolBar, null, (RacingSimulator)_control, this);
							thread.start();
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
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
			case "PLAY":
				_music.loop();
				playingMusic = true;
				break;
			case "STOP":
				_music.stop();
				playingMusic = false;
				break;
			case "RANDOM":
				_music.stop();
				Random rnd = new Random();
				int selected = rnd.nextInt(songs.length);
				_music = null;
				_music = new Music("src/music/" + songs[selected] + ".wav");
				((RacingToolBar)_toolBar).getComboBox().setSelectedIndex(selected);
				_music.loop();
				playingMusic = true;
				break;
			case "PLAYLIST":
				@SuppressWarnings("unchecked") JComboBox<String> comboBox = (JComboBox<String>)e.getSource();
				_music.stop();
				_music = null;
				_music = new Music("src/music/" + (String)comboBox.getSelectedItem() + ".wav");
				_music.loop();
				playingMusic = true;
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

	public void addObserver(RacingSimulatorObserver obs) {
		if(obs != null && !_observers.contains(obs)) {
			_observers.add(obs);
			notifyRegistered(obs);
		}
	}
	
	public void removeObserver(RacingSimulatorObserver obs) {
		if(obs != null && _observers.contains(obs))
			_observers.remove(obs);
	}
	
	private void notifyRegistered(RacingSimulatorObserver obs) {
		obs.registered(_characterChooserPanel, _imagesPanel, _circuitChooserPanel, _selectedCupImage, _selectedCupPanel);
	}
	
	public void notifyCupSelected() {
		for(RacingSimulatorObserver observer : _observers)
			observer.cupSelected();
	}

	public void notifyCharacterAdded() {
		for(RacingSimulatorObserver observer : _observers)
			observer.characterAdded();
	}
	
	private void notifyReset() {
		for(RacingSimulatorObserver observer : _observers)
			observer.reset();
	}
	
	public Music getMusic() {
		return _music;
	}
}

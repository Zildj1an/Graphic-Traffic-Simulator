package model;

import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import exceptions.UnexistingObjectException;
import ini.IniSection;
import music.Music;

public class Kart extends Vehicle {
	
	protected int _luck;
	protected Random _random;
	protected boolean _newLap;
	protected int _lap;
	protected boolean _itembox;
	protected int _racePosition;

	public Kart(String id, int maxSpeed, List<Junction> itinerary, int luck) {
		super(id, maxSpeed, itinerary);
		_luck = luck;
		_random = new Random();
		_newLap = false;
		_lap = 0;
		_itembox = true;
	}
	
	public int getLap() {
		return _lap;
	}
	
	public void lapMessage(ImageIcon flagicon) {
		JOptionPane.showMessageDialog(null, "LAP " + _lap, "New Lap", JOptionPane.INFORMATION_MESSAGE, flagicon);
	}

	void advance() throws UnexistingObjectException {
		if(_itinerary.get(_itineraryIndex) == _itinerary.get(0) && !_atJunction && _itineraryIndex != 0 && _newLap) {
			_newLap = false;
			_lap++;
			if(_racePosition == 1) {
				ImageIcon flagicon = new ImageIcon(this.getClass().getResource("/images/newlap.png"));
				flagicon.setImage(flagicon.getImage().getScaledInstance(300, 200, 1));
				Music music = new Music("src/music/lap.wav");
				music.play();
				lapMessage(flagicon);
				music.stop();
				music = null;
			}
			if(_itembox) {
				if (_random.nextInt(101) < _luck) {
					_currentSpeed = _currentSpeed * 2;
					ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/boost.gif"));
					Music music = new Music("src/music/itemboxboost.wav");
					music.play();
					JOptionPane.showMessageDialog(null, _id + " found an Item Box...\nEXTRA BOOST!", "Item Box", JOptionPane.INFORMATION_MESSAGE, icon);
					music.stop();
					music = null;
				}
				else {
					ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/boo.gif"));
					Music music = new Music("src/music/itemboxboo.wav");
					music.play();
					JOptionPane.showMessageDialog(null, _id + " found an Item Box...\nBOO!", "Item Box", JOptionPane.WARNING_MESSAGE, icon);
					music.stop();
					music = null;
					makeFaulty(1);
				}
			}
		}
		int previous = _itineraryIndex;
		super.advance();
		if(_itineraryIndex != previous)
			_newLap = true;
	}
	
	protected void fillReportDetails(IniSection is) {
		is.setValue("type", "kart");
		super.fillReportDetails(is);
	}
	
	public void changeItemBox() {
		_itembox = !_itembox;
	}
	
	public void setRacePosition(int position) {
		_racePosition = position;
	}
}

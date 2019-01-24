package model;

import javax.swing.JPanel;

import racingview.CharacterChooserPanel;
import racingview.CupChooserPanel;
import racingview.ImagesPanel;

public interface RacingSimulatorObserver {
	public void registered(CharacterChooserPanel characterChooserPanel, ImagesPanel imagesPanel, CupChooserPanel cupChooserPanel, JPanel selectedCupImage, JPanel selectedCupPanel);
	
	public void cupSelected();
	
	public void characterAdded();
	
	public void reset();	
}

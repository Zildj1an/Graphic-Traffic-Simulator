package racingview;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CupChooserPanel extends ChooserPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String[]> _cupsMap;
	private String _selectedCup;
	private String[] _selectedCupItinerary;

	public CupChooserPanel(String[] items, RacingPanel racingPanel, ImagesPanel imagesPanel, String[][] junctions) {
		super(items, racingPanel, imagesPanel);
		_selectedCupItinerary = new String[0];
		_selectedCup = _items[0];
		_cupsMap = new HashMap<String, String[]>();
		_cupsMap.put(_items[0], junctions[0]);
		_cupsMap.put(_items[1], junctions[1]);
		_cupsMap.put(_items[2], junctions[2]);
		_cupsMap.put(_items[3], junctions[3]);
		_cupsMap.put(_items[4], junctions[4]);
		_cupsMap.put(_items[5], junctions[5]);
		_cupsMap.put(_items[6], junctions[6]);
		_cupsMap.put(_items[7], junctions[7]);
	}
	
	public Map<String, String[]> getCupsMap(){
		return _cupsMap;
	}
	
	public String getSelectedCup() {
		return _selectedCup;
	}
	
	public void setSelectedCup(int laps) {
		String[] selectedCupJunctions = _cupsMap.get(_selectedCup);
		_selectedCupItinerary = new String[(selectedCupJunctions.length * laps) + 1];
		for(int i = 0; i < (selectedCupJunctions.length * laps) + 1; i++)
			_selectedCupItinerary[i] = selectedCupJunctions[i % selectedCupJunctions.length];
	}
	
	public String[] getSelectedCupJunctions() {
		return _cupsMap.get(_selectedCup);
	}
	
	public String[] getSelectedCupItinerary() {
		return _selectedCupItinerary;
	}

	@Override
	protected void setMouseListener(JButton button, String path) {
		button.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				_selectedCup = path;
				_racingPanel.notifyCupSelected();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				_imagesPanel.removeAll();
				JPanel temp1 = new JPanel();
				temp1.setPreferredSize(new Dimension(205, 150));
				temp1.setMaximumSize(new Dimension(205, 150));
				temp1.setMinimumSize(new Dimension(205, 150));
				ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/" + path + "trophy" + ".jpg"));
				icon.setImage(icon.getImage().getScaledInstance(205, 150, 1));
				temp1.add(new JLabel(icon));
				_imagesPanel.add(temp1);
				JPanel temp2 = new JPanel();
				temp2.setPreferredSize(new Dimension(195, 150));
				temp2.setMaximumSize(new Dimension(195, 150));
				temp2.setMinimumSize(new Dimension(195, 150));
				temp2.setLayout(new GridLayout(_cupsMap.get(path).length, 1));
				for(int i = 0; i < _cupsMap.get(path).length; i++) {
					JLabel label = new JLabel("  " + (i + 1) + " - " + _cupsMap.get(path)[i]);
					label.setFont(new Font("Sheriff", Font.BOLD, 15));
					temp2.add(label);
				}
				_imagesPanel.add(temp2);
				_imagesPanel.repaint();
				updateUI();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				_imagesPanel.removeAll();
				JLabel _image = new JLabel();
				ImageIcon icon = new ImageIcon(this.getClass().getResource(_imagesPanel.getPath()));
				icon.setImage(icon.getImage().getScaledInstance(400, 150, 1));
				_image.setIcon(icon);
				_imagesPanel.add(_image);
				_imagesPanel.repaint();
				updateUI();
				
			}
		});
	}
	
	public void reset() {
		_selectedCup = "Mushroom";
		_selectedCupItinerary = new String[0];
	}
}

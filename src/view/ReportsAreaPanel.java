package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Event;
import model.RoadMap;
import model.SimulatorError;
import model.TrafficSimulatorObserver;

public class ReportsAreaPanel extends TextAreaPanel implements TrafficSimulatorObserver, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RoadMap _map;
	private int _time;
	private DialogWindow _dialog;
	private MainPanel _mainPanel;
	private StateBarPanel _stateBarPanel;
	protected JFileChooser _fc;
	
	public ReportsAreaPanel(StateBarPanel stateBarPanel, MainPanel mainPanel, String title, boolean editable){
		super(title, editable);
		_stateBarPanel = stateBarPanel;
		_mainPanel = mainPanel;
		_fc = new JFileChooser();
		_fc.setCurrentDirectory(new File("."));
		_fc.setMultiSelectionEnabled(false);
		_fc.setFileFilter(new FileNameExtensionFilter("Archivos OUT", "out"));
		
		initGUI();
	}
	
	private void initGUI(){
		_dialog = new DialogWindow(_mainPanel);		
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		_map = map;
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, SimulatorError e) {
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		_time = time;
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		switch(str){
			case "GENERATE":
				_dialog.setData(_map.getVehicles(), _map.getRoads(), _map.getJunctions());
				int status = _dialog.open();
				if (status != 0) {
					insert(_map.generateSelectedReports(_time, _dialog.getSelectedVehicles(), _dialog.getSelectedRoads(), _dialog.getSelectedJunctions()));
					_stateBarPanel.setMessage("Reports generated");
				}
				break;
			case "CLEAR":
				clear();
				_stateBarPanel.setMessage("Reports area cleared");
				break;
			case "SAVE":
				try {
					saveFile();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				break;
			default:
				break;
		}
		
	}

	private void saveFile() throws FileNotFoundException {
		int returnVal = _fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			String path = file.getAbsolutePath();
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.print(getText());
			printWriter.close();
			if(!path.endsWith(".out")) {
				File temp = new File(path + ".out");
				file.renameTo(temp);
				_stateBarPanel.setMessage("Reports saved in " + temp.getName());
			}
			else
				_stateBarPanel.setMessage("Reports saved in " + file.getName());
		}
	}
}

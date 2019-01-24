package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;

import model.Junction;
import model.Road;
import model.Vehicle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

class DialogWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	private MyListModel<Vehicle> _vehiclesListModel;
	private MyListModel<Road> _roadsListModel;
	private MyListModel<Junction> _junctionsListModel;

	private int _status;
	private JList<Vehicle> _vehiclesList;
	private JList<Road> _roadsList;
	private JList<Junction> _junctionsList;

	static final private char _clearSelectionKey = 'c';
	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	public DialogWindow(Frame parent) {
		super(parent, true);
		initGUI();
	}

	private void initGUI() {

		_status = 0;

		setTitle("Generate Reports");
		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		JPanel vehiclesPanel = new JPanel(new BorderLayout());
		JPanel roadsPanel = new JPanel(new BorderLayout());
		JPanel junctionsPanel = new JPanel(new BorderLayout());

		contentPanel.add(vehiclesPanel);
		contentPanel.add(roadsPanel);
		contentPanel.add(junctionsPanel);

		vehiclesPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Vehicles", TitledBorder.LEFT, TitledBorder.TOP));
		roadsPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Roads", TitledBorder.LEFT, TitledBorder.TOP));
		
		junctionsPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Junctions", TitledBorder.LEFT, TitledBorder.TOP));

		vehiclesPanel.setMinimumSize(new Dimension(100, 100));
		roadsPanel.setMinimumSize(new Dimension(100, 100));
		junctionsPanel.setMinimumSize(new Dimension(100, 100));

		_vehiclesListModel = new MyListModel<>();
		_roadsListModel = new MyListModel<>();
		_junctionsListModel = new MyListModel<>();

		_vehiclesList = new JList<>(_vehiclesListModel);
		_roadsList = new JList<>(_roadsListModel);
		_junctionsList = new JList<>(_junctionsListModel);

		addCleanSelectionListener(_vehiclesList);
		addCleanSelectionListener(_roadsList);
		addCleanSelectionListener(_junctionsList);

		vehiclesPanel.add(new JScrollPane(_vehiclesList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		roadsPanel.add(new JScrollPane(_roadsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		
		junctionsPanel.add(new JScrollPane(_junctionsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);


		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton okButton = new JButton("Generate");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				DialogWindow.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				DialogWindow.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		mainPanel.add(infoPanel, BorderLayout.PAGE_START);

		infoPanel.add(new JLabel("Select items for which you want to generate reports."));
		infoPanel.add(new JLabel("Use '" + _clearSelectionKey + "' to deselect all."));
		infoPanel.add(new JLabel("Use Ctrl+A to select all"));
		infoPanel.add(new JLabel(" "));

		setContentPane(mainPanel);
		setMinimumSize(new Dimension(100, 100));
		setVisible(false);
	}

	private void addCleanSelectionListener(JList<?> list) {
		list.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == _clearSelectionKey) {
					list.clearSelection();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

	}

	public void setData(List<Vehicle> vehicles, List<Road> roads, List<Junction> junctions) {
		_vehiclesListModel.setList(vehicles);
		_roadsListModel.setList(roads);
		_junctionsListModel.setList(junctions);
	}

	public Vehicle[] getSelectedVehicles() {
		int[] indices = _vehiclesList.getSelectedIndices();
		Vehicle[] vehicles = new Vehicle[indices.length];
		for(int i=0; i < vehicles.length; i++) {
			vehicles[i] = _vehiclesListModel.getElementAt(indices[i]);
		}
		return vehicles;
	}

	public Road[] getSelectedRoads() {
		int[] indices = _roadsList.getSelectedIndices();
		Road[] roads = new Road[indices.length];
		for(int i=0; i < roads.length; i++) {
			roads[i] = _roadsListModel.getElementAt(indices[i]);
		}
		return roads;
	}
	
	public Junction[] getSelectedJunctions() {
		int[] indices = _junctionsList.getSelectedIndices();
		Junction[] junctions = new Junction[indices.length];
		for(int i=0; i < junctions.length; i++) {
			junctions[i] = _junctionsListModel.getElementAt(indices[i]);
		}
		return junctions;
	}


	public int open() {
		setLocation(getParent().getLocation().x + 50, getParent().getLocation().y + 50);
		pack();
		setVisible(true);
		return _status;
	}

}

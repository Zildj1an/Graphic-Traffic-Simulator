package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import control.Controller;

public class MenuBar extends JMenuBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainPanel _mainPanel;
	private EventsEditorPanel _eventsEditorPanel;
	private ReportsAreaPanel _reportsAreaPanel;
	
	private JMenu file;
	private JMenu reports;
	private JMenu simulator;
	
	public MenuBar(MainPanel mainPanel, EventsEditorPanel eventsEditorPanel, ReportsAreaPanel reportsAreaPanel, ToolBar toolBar, Controller control){
		super();
		_mainPanel = mainPanel;
		_reportsAreaPanel = reportsAreaPanel;
		_eventsEditorPanel = eventsEditorPanel;
		initGUI();
	}
	
	private void initGUI() {
		this.add(createFileMenu());
		this.add(createSimulatorMenu());
		this.add(createReportsMenu());
	}
	
	public void able(boolean b) {
		file.setEnabled(b);
		reports.setEnabled(b);
		simulator.setEnabled(b);
	}
	
	public JMenu createFileMenu() {
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		file.add(createJMenuItem("Load Events", "LOAD", _eventsEditorPanel, KeyEvent.VK_L));
		file.add(createJMenuItem("Save Events", "SAVE", _eventsEditorPanel, KeyEvent.VK_S));
		file.add(createJMenuItem("Check-in Events", "CHECK IN", _eventsEditorPanel, KeyEvent.VK_C));
		file.addSeparator();
		file.add(createJMenuItem("Save Reports", "SAVE", _reportsAreaPanel, KeyEvent.VK_R));
		file.addSeparator();
		file.add(createJMenuItem("Exit", "QUIT", _mainPanel, KeyEvent.VK_E));
		
		return file;
	}
	
	private JMenuItem createJMenuItem(String title, String actionCommand, ActionListener actionListener, int mnemonic) {
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(actionListener);
		menuItem.setMnemonic(mnemonic);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(mnemonic, ActionEvent.ALT_MASK));
		return menuItem;
	}
	
	private JMenu createReportsMenu() {
		reports = new JMenu("Reports");
		reports.setMnemonic(KeyEvent.VK_R);
		
		reports.add(createJMenuItem("Generate", "GENERATE", _reportsAreaPanel));
		reports.add(createJMenuItem("Clear", "CLEAR", _reportsAreaPanel));
		
		return reports;
	}
	
	private JMenuItem createJMenuItem(String title, String actionCommand, ActionListener actionListener) {
		JMenuItem menuItem = new JMenuItem(title);
		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(actionListener);
		return menuItem;
	}

	public JMenu createSimulatorMenu() {
		simulator = new JMenu("Simulator");
		simulator.setMnemonic(KeyEvent.VK_S);
		
		simulator.add(createJMenuItem("Run", "RUN", _mainPanel));
		simulator.add(createJMenuItem("Reset", "RESET", _mainPanel));
		
		JCheckBoxMenuItem redirect = new JCheckBoxMenuItem("Redirect Output");
		redirect.setActionCommand("REDIRECT");
		redirect.addActionListener(_mainPanel);
		redirect.setSelected(true);
		simulator.add(redirect);
		
		return simulator;
	}
}

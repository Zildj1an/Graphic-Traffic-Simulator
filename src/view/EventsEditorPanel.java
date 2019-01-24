package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import control.Controller;
import control.EventBuilder;
import model.SimulatorError;

public class EventsEditorPanel extends TextAreaPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StateBarPanel _stateBarPanel;
	private Controller _control;
	private File _inFile;
	protected JFileChooser _fc;

	public EventsEditorPanel(String title, String text, boolean editable, File inFile, Controller control, StateBarPanel stateBarPanel) throws IOException{
		super(title, editable);
		
		_stateBarPanel = stateBarPanel;
		_control = control;
		_fc = new JFileChooser();
		_fc.setCurrentDirectory(new File("."));
		_fc.setMultiSelectionEnabled(false);
		_inFile = inFile;
		if(_inFile != null)
			setText(readFile());	
		
		initGUI();
	}
	
	private void initGUI() {

		JPopupMenu _editorPopupMenu = new JPopupMenu();

		JMenu subMenu = new JMenu("Add Template");

		for (EventBuilder eb : _control.getEventBuilders()) {
			JMenuItem menuItem = new JMenuItem(eb.toString());
			menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EventsEditorPanel.this.insert("\n" + eb.template() + "\n");
				}
			});
			subMenu.add(menuItem);
		}
				
		_editorPopupMenu.add(subMenu);
		_editorPopupMenu.addSeparator();
		_editorPopupMenu.add(createMenuItem("Load", "LOAD", this));
		_editorPopupMenu.add(createMenuItem("Save", "SAVE", this));
		_editorPopupMenu.add(createMenuItem("Clear", "CLEAR", this));
		

		// connect the popup menu to the text area _editor
		textArea.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger() && _editorPopupMenu.isEnabled()) {
					_editorPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
	
	private JMenuItem createMenuItem(String title, String actionCommand, ActionListener actionListener) {
		JMenuItem menuItem = new JMenuItem(title);
		
		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(actionListener);
		
		return menuItem;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		switch (str) {
			case "LOAD":
				loadFile();
				break;
			case "SAVE":
				try {
					saveFile();
				} catch (FileNotFoundException e2) {
					JOptionPane.showMessageDialog(this, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "CHECK IN":
				_control.setInputStream(new ByteArrayInputStream(this.getText().getBytes()));
				try {
					_control.loadEvents();
					_stateBarPanel.setMessage("Events loaded into the simulator!");
				} catch (SimulatorError e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case "CLEAR":
				clear();
				_stateBarPanel.setMessage("Events editor cleared");
				break;
			default:
				break;
		}
	}

	private void loadFile() {
		_fc.setFileFilter(new FileNameExtensionFilter("Archivos INI", "ini"));
		int returnVal = this._fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			_inFile = this._fc.getSelectedFile();
			try {
				setText(readFile());
				_stateBarPanel.setMessage("File '" + _inFile.getName() + "' loaded");
			}
			catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Problems loading file", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void setText(String text) {
		textArea.setText(text);
		setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createLineBorder(Color.BLACK), "Events: " + _inFile.getName()));
	}

	private String readFile() throws IOException {
		FileReader fr = new FileReader(_inFile);
		BufferedReader br = new BufferedReader(fr);
		String str = "";
		String finalStr = "";
		str = br.readLine();
		while(str != null) {
			if(!finalStr.equals(""))
				finalStr += "\n";
			finalStr = finalStr + str;
			str = br.readLine();
		}
		br.close();
		return finalStr;
	}

	private void saveFile() throws FileNotFoundException {
		_fc.setFileFilter(new FileNameExtensionFilter("Archivos OUT", "out"));
		int returnVal = _fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = _fc.getSelectedFile();
			String path = file.getAbsolutePath();
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.print(getText());
			printWriter.close();
			if(!path.endsWith(".ini")) {
				File temp = new File(path + ".ini");
				file.renameTo(temp);
				_stateBarPanel.setMessage("Events saved in " + temp.getName());
			}
			else
				_stateBarPanel.setMessage("Events saved in " + file.getName());
		}
	}

}

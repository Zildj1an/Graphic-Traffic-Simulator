package view;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import control.Controller;
import model.SimulatorError;

public class RunThread extends Thread {
	
	protected Controller _control;
	protected ToolBar _toolBar;
	protected MenuBar _menuBar;
	
	public RunThread(Controller control, ToolBar toolBar, MenuBar menuBar) {
		_control = control;
		_toolBar = toolBar;
		_menuBar = menuBar;
	}
	
	@Override
	public void run() {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				_toolBar.able(false);
				_menuBar.able(false);				
			}
		});
		for (int i = 0; i < _toolBar.getTime() && !Thread.currentThread().isInterrupted(); i++) {
			try {
				_control.run(1);
				Thread.sleep(_toolBar.getDelay() * 1000);
			} catch (IOException | SimulatorError e1) {
				JOptionPane.showMessageDialog(null, "Error running the simulator", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				_toolBar.able(true);
				_menuBar.able(true);				
			}
		});
	}
}

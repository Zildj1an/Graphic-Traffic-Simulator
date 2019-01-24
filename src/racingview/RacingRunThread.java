package racingview;

import java.io.IOException;

import javax.swing.SwingUtilities;

import control.Controller;
import model.RacingSimulator;
import model.SimulatorError;
import view.MenuBar;
import view.RunThread;
import view.ToolBar;

public class RacingRunThread extends RunThread {
	
	private RacingSimulator _model;
	private RacingPanel _racingPanel;
	private boolean _interrupted;

	public RacingRunThread(Controller control, ToolBar toolBar, MenuBar menuBar, RacingSimulator model, RacingPanel racingPanel) {
		super(control, toolBar, menuBar);
		_model = model;
		_racingPanel = racingPanel;
		_interrupted = false;
	}

	@Override
	public void run() {
		_toolBar.able(false);
		for (int i = 0; !RacingRunThread.this._interrupted && i < _toolBar.getTime(); i++) {
			// Runs inside of the Swing UI thread
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
					_control.run(1);
					if(_model.getTotalVehicles() == ((RacingSimulator)_model).getArrivedVehicles()) {
						_racingPanel.raceFinished();
						((RacingToolBar)_toolBar).raceFinished();
						_interrupted = true;
					}
					} catch (IOException | SimulatorError e) {}
				}
			});
			try {
				Thread.sleep(_toolBar.getDelay() * 1000);
			} catch (InterruptedException e) {
				_interrupted = true;
			}
		}
		_toolBar.able(true);
	}
}

package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import ini.Ini;
import ini.IniSection;
import model.Event;
import model.Observable;
import model.SimulatorError;
import model.TrafficSimulator;
import model.TrafficSimulatorObserver;

/**
 * This class is in charge of loading the Events and pass them to the TrafficSimulator.
 * They are loaded using the IniSections and the parse methods of the different EventBuilders, 
 * which are stored in an array previously provided.
 * This class also runs the program by calling the run method of the TrafficSimulator.
 *
 */
public class Controller implements Observable<TrafficSimulatorObserver>{
	
	protected TrafficSimulator _sim;
	EventBuilder[] _eventBuilders = {};
	protected InputStream _inputStream;
	protected int _ticks;


	public Controller(TrafficSimulator simulator, int ticks, InputStream inStream) {
		_sim = simulator;
		_inputStream = inStream;
		_ticks = ticks;
	}
	
	public Controller(TrafficSimulator simulator, OutputStream outStream) {
		_sim = simulator;
		setOutputStream(outStream);
	}
	
	public void setEventBuilders(EventBuilder[] eventBuilders) {
		_eventBuilders = eventBuilders;
	}
	
	public EventBuilder[] getEventBuilders() {
		return _eventBuilders;
	}
	
	public void run() throws IOException, SimulatorError {
		_sim.run(_ticks);
	}
	
	public void reset() {
		_sim.reset();
	}
	
	public void setInputStream(InputStream is) {
		_inputStream = is;
	}
	
	public void setOutputStream(OutputStream outStream) {
		_sim.setOutputStream(outStream);
	}
	
	public void run(int ticks) throws IOException, SimulatorError {
		_sim.run(ticks);
	}
	
	public void addObserver(TrafficSimulatorObserver obs) {
		_sim.addObserver(obs);
	}
	
	public void removeObserver(TrafficSimulatorObserver obs) {
		_sim.removeObserver(obs);
	}
	
	public void loadEvents() throws SimulatorError {
		Ini ini;
		boolean parsed = false;
		try {
			ini = new Ini(_inputStream);
		}
		catch(IOException e) {
			throw new SimulatorError("Error while reading the events: " + e);
		}
		for(IniSection sec : ini.getSections()) {
			try{
				Event e = null;
				parsed = false;
				for(EventBuilder eb : _eventBuilders) {
					e = eb.parse(sec);
					if(e != null) {
						_sim.addEvent(e);
						parsed = true;
					}
				}
				if (!parsed)
					throw new SimulatorError("Unknown event: " + sec.getTag());
			}
			catch(SimulatorError e) {
				throw e;
			}
		}
	}
}

package launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import control.Controller;
import control.EventBuilder;
import control.MakeVehicleFaultyEventBuilder;
import control.NewBikeEventBuilder;
import control.NewCarEventBuilder;
import control.NewDirtRoadEventBuilder;
import control.NewJunctionEventBuilder;
import control.NewLanesRoadEventBuilder;
import control.NewMostCrowdedJunctionEventBuilder;
import control.NewRoadEventBuilder;
import control.NewRoundRobinJunctionEventBuilder;
import control.NewVehicleEventBuilder;
import ini.Ini;
import model.RacingSimulator;
import model.SimulatorError;
import model.TrafficSimulator;
import racingview.RacingPanel;
import view.MainPanel;

public class Main {
	
	public enum ExecutionMode{
		BATCH("batch"), GUI("gui"), RACING("race");
		
		private String modeDescription;
		private static ExecutionMode[] modes = ExecutionMode.values();
		
		private ExecutionMode(String modeDescription) {
			this.modeDescription = modeDescription;
		}
		
		private String getModelDescription() {
			return modeDescription;
		}
		
		public static ExecutionMode parse(String value) {
			for (ExecutionMode em : modes) {
				if (em.getModelDescription().equalsIgnoreCase(value))
					return em;
			}
			return null;
		}
	}
	
	private final static Integer _timeLimitDefaultValue = 10;
	private static Integer _timeLimit = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static ExecutionMode _mode = null;
	
	private static EventBuilder[] _events = {new NewMostCrowdedJunctionEventBuilder(), 
			new NewRoundRobinJunctionEventBuilder(),
			new NewJunctionEventBuilder(),
			new NewLanesRoadEventBuilder(),
			new NewDirtRoadEventBuilder(),
			new NewRoadEventBuilder(),
			new NewBikeEventBuilder(),
			new NewCarEventBuilder(),
			new NewVehicleEventBuilder(),
			new MakeVehicleFaultyEventBuilder()};

	private static void parseArgs(String[] args) throws SimulatorError {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseModeOption(line);
			parseOutFileOption(line);
			parseStepsOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			
			// new Piece(...) might throw GameError exception
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("'batch' for batch mode and "
				+ "'gui' for GUI mode (default value is 'batch'").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg()
				.desc("Ticks to execute the simulator's main loop (default value is " + _timeLimitDefaultValue + ").")
				.build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && _mode == ExecutionMode.BATCH) {
			throw new ParseException("An events file is missing");
		}
	}
	
	private static void parseModeOption(CommandLine line) throws SimulatorError {
		_mode = ExecutionMode.parse(line.getOptionValue("m"));
		if (_mode == null) {
			if(line.getOptionValue("m") == null)
				_mode = ExecutionMode.BATCH;
			else
				throw new SimulatorError("Unknown mode");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		_outFile = line.getOptionValue("o");
	}

	private static void parseStepsOption(CommandLine line) throws ParseException {
		String t = line.getOptionValue("t", _timeLimitDefaultValue.toString());
		try {
			if(_mode == ExecutionMode.BATCH)
				_timeLimit = Integer.parseInt(t);
			else {
				t = line.getOptionValue("t", "0");
				_timeLimit = Integer.parseInt(t);
			}
			assert (_timeLimit < 0);
		} catch (Exception e) {
			throw new ParseException("Invalid value for time limit: " + t);
		}
	}

	/**
	 * This method run the simulator on all files that ends with .ini if the given
	 * path, and compares that output to the expected output. It assumes that for
	 * example "example.ini" the expected output is stored in "example.ini.eout".
	 * The simulator's output will be stored in "example.ini.out"
	 * @throws IOException
	 * @throws SimulatorError
	 */
	@SuppressWarnings("unused")
	private static void test(String path) throws IOException, SimulatorError {

		File dir = new File(path);

		if ( !dir.exists() ) {
			throw new FileNotFoundException(path);
		}
		
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});

		for (File file : files) {
			test(file.getAbsolutePath(), file.getAbsolutePath() + ".out", file.getAbsolutePath() + ".eout", 10);
		}

	}

	private static void test(String inFile, String outFile, String expectedOutFile, int timeLimit) throws IOException, SimulatorError {
		_outFile = outFile;
		_inFile = inFile;
		_timeLimit = timeLimit;
		startBatchMode();
		boolean equalOutput = (new Ini(_outFile)).equals(new Ini(expectedOutFile));
		System.out.println("Result for: '" + _inFile + "' : "
				+ (equalOutput ? "OK!" : ("not equal to expected output +'" + expectedOutFile + "'")));
	}

	/**
	 * Run the simulator in batch mode
	 * @throws SimulatorError 
	 * @throws IOException 
	 */
	private static void startBatchMode() throws IOException, SimulatorError {
		InputStream is = new FileInputStream(new File(_inFile));
		OutputStream os;
		os = _outFile == null ? System.out : new FileOutputStream(new File(_outFile));
		TrafficSimulator sim = new TrafficSimulator();
		Controller control = new Controller(sim, _timeLimit, is);
		control.setOutputStream(os);
		control.setEventBuilders(_events);
		control.loadEvents();
		control.run();
		is.close();
		System.out.println("Done!");
		os.close();		
	}
	
	private static void startGUIMode() throws IOException, InvocationTargetException, InterruptedException{
		InputStream is = null;
		if (_inFile != null)
			is = new FileInputStream(new File(_inFile));
		TrafficSimulator sim = new TrafficSimulator();	
		Controller control = new Controller(sim, _timeLimit, is);
		control.setEventBuilders(_events);
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				try {
					new MainPanel(_inFile, control, _timeLimit);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void startRACINGMode() throws IOException, InvocationTargetException, InterruptedException{
		InputStream is = null;
		if (_inFile != null)
			is = new FileInputStream(new File(_inFile));
		TrafficSimulator sim = new RacingSimulator();
		Controller control = new Controller(sim, _timeLimit, is);
		control.setEventBuilders(_events);
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				try {
					new RacingPanel(_inFile, control, _timeLimit);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void start(String[] args) throws SimulatorError, IOException, InvocationTargetException, InterruptedException  {
		parseArgs(args);
		if (_mode == ExecutionMode.BATCH)
			startBatchMode();
		else if (_mode == ExecutionMode.GUI) {
			startGUIMode();
		}
		else if (_mode == ExecutionMode.RACING)
			startRACINGMode();
		else
			throw new SimulatorError("Unknown mode.");
	}

	public static void main(String[] args) throws IOException, SimulatorError, InvocationTargetException, InterruptedException {
		/*
		 -i resources/examples/events/basic/ex1.ini
		 -i resources/examples/events/basic/ex1.ini -o ex1.out
		 -i resources/examples/events/basic/ex1.ini -t 20
		 -i resources/examples/events/basic/ex1.ini -o ex1.out -t 20
		 --help
		
	    	test("resources/examples/events/basic");
		
		
		test("resources/ini1.ini", "resources/output.ini.out", "resources/ini1.ini.eout", 10);
		test("resources/err");
		test("resources/advanced");*/
		
		start(args);
	}

}

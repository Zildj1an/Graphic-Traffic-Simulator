package exceptions;
import model.SimulatorError;

/**
 * This is a general purpose exception used by some others that inherit from it and is related to errors during the execution (execute method) of an Event 
 */
public class ExecutionOfEventException extends SimulatorError {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String message = "Error during the execution of an event!";
	
	public ExecutionOfEventException() {
		super(message);
	}
	
	public ExecutionOfEventException(String msg) {
		super(msg);
	}
}
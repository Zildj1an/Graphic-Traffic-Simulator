package exceptions;
import model.SimulatorError;

/**
 * This is an exception thrown when some arguments provided by the users (through files) are not correct 
 *
 */
public class InvalidParametersException extends SimulatorError {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String message = "Reference to an object that does not exist!";
		
	public InvalidParametersException() {
		super(message);
	}
	
	public InvalidParametersException(String msg) {
		super(msg);
	}
}
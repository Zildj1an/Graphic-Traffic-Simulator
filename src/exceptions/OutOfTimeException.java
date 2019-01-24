package exceptions;
import model.SimulatorError;

/**
 * This is a exception thrown when the time of execution is greater than the expected time
 *
 */
public class OutOfTimeException extends SimulatorError{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String message = "There is no time left!";
	
	public OutOfTimeException() {
		super(message);
	}
	
	public OutOfTimeException(String msg) {
		super(msg);
	}
}
package exceptions;

/**
 * This exception is thrown when we try to find an object that does not exist (using for instance its ID)
 *
 */
public class UnexistingObjectException extends ExecutionOfEventException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String message = "Reference to an object that does not exist!";
	
	public UnexistingObjectException() {
		super(message);
	}
	
	public UnexistingObjectException(String msg) {
		super(msg);
	}
}
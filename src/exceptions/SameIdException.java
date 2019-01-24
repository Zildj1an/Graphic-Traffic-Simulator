package exceptions;

/**
 * This exception is thrown when the user -using the file- tries to create an object with the same ID of an existing object
 *
 */
public class SameIdException extends ExecutionOfEventException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String message = "There is an object with the same id!";
	
	public SameIdException() {
		super(message);
	}
	
	public SameIdException(String msg) {
		super(msg);
	}
}
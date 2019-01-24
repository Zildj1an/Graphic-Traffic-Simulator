package model;

/**
 * All the exceptions inherit from this class, each of them for an specific purpose or situation:
 *  - UnexistingObjectException
 *  - SameIdException
 *  - InvalidParametersException
 *  - ExecutionOfEventException
 *  - OutOfTimeException
 * 
 */
public class SimulatorError extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SimulatorError(String str){
		super(str);
	}
}

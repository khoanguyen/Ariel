package eid.ariel.exception;

public class InvalidParamException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidParamException() {
		super("Invalid parameter");
	}

	public InvalidParamException(String message) {
		super(message);
	}
}

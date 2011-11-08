package eid.ariel.exception;

public class AccessDeniedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccessDeniedException(){
		super("Access denied");
	}
}

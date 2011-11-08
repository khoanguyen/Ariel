package eid.ariel.core;

public class CryptographyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CryptographyException(Exception inner){
		super(inner);
	}
}

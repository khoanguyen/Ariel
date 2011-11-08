package eid.ariel.atlas.exception;

public class FailedLoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMessage, accountId;

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getAccountId() {
		return accountId;
	}

	public FailedLoginException(int errorCode, String errorMessage,
			String accountId) {
		super("Login failed");
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.accountId = accountId;
	}
}

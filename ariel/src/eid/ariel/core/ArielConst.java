package eid.ariel.core;

public class ArielConst {

	public static final String RESULT_KEY = "result";
	public static final String ERROR_CODE_KEY = "errorCode";
	public static final String ERROR_MESSAGE_KEY = "errorMessage";
	
	public static final String HEADER_USER_ID_KEY = "Ariel-UserId";
	public static final String HEADER_TOKEN_KEY = "Ariel-Token";
	
	public static final Integer ERROR_INVALID_PARAMETER_CODE = 1;
	public static final String ERROR_INVALID_PARAMETER_MESSAGE = "Invalid input parameters";
	
	public static final Integer ERROR_ACCESS_DENIED_CODE = 2;
	public static final String ERROR_ACCESS_DENIED_MESSAGE = "Access denied";
	
	public static final int ERROR_RECORD_NOT_EXIST_CODE = 3;
	public static final String ERROR_RECORD_NOT_EXIST_MESSAGE = "does not exist";
	
	private ArielConst() {
	}
}

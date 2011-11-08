package eid.ariel.atlas;

public final class AtlasConst {

	public static final String ID_KEY = "_id";	
	public static final String LOGINID_KEY = "loginId";
	public static final String PASSPHRASE_KEY = "passphrase";
	public static final String TOKEN_KEY = "token";

	public static final String PASSTYPE_PLAIN = "plain";
	public static final String PASSTYPE_SHA = "sha";
	
	public static final int ERROR_USER_NOTFOUND_CODE = 0x10001;
	public static final String ERROR_USER_NOTFOUND_MESSAGE = "User not found";

	public static final int ERROR_INVALID_PASSWORD_CODE = 0x10002;
	public static final String ERROR_INVALID_PASSWORD_MESSAGE = "Invalid password";

	public static final int ERROR_USER_LOCKED_CODE = 0x10003;
	public static final String ERROR_USER_LOCKED_MESSAGE = "User is locked";
	
	public static final int ERROR_RECORD_NOT_EXIST_CODE = 0x10004;
	public static final String ERROR_RECORD_NOT_EXIST_MESSAGE = "does not exist";
	
	private AtlasConst() {
	}

}

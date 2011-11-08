package eid.ariel.schema;

public final class SecurityUser {
	
	public final static String COLLECTION = "users";
	
	public final static String FIELD_ID = "_id";
	public final static String FIELD_LASTLOGIN = "lastlogin";	
	public final static String FIELD_LOGINID = "loginid";
	public final static String FIELD_PASSPHRASE = "passphrase";
	public final static String FIELD_PASSTYPE = "passtype";
	public final static String FIELD_TOKEN = "token";
	public final static String FIELD_STATUS = "status";
	public final static String FIELD_PASSPHRASE_SALT = "salt";
	public final static String FIELD_PASSPHRASE_HASH = "hash";
	
	private SecurityUser(){
	}
}

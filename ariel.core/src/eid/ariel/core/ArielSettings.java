package eid.ariel.core;

public class ArielSettings {

	public final static String fileSeparator = System.getProperty("file.separator");
	public final static String ROOT = System.getProperty("eid.ariel.root");	
	public final static String EXTENSION_DIR = fromRoot("ext");
	public final static String CONFIG_DIR = fromRoot("conf");
	public final static String CONFIG_FILE_PATH = join(CONFIG_DIR, "appconfig.yaml");
	
	public final static String ENV_KEY = "env";
	public final static String ENV_DEV_VALUE = "development";
	public final static String ENV_PROD_VALUE = "production";
	public final static String ENV_TEST_VALUE = "test";
	
	public final static String DB_KEY = "databases";
	public final static String DB_NAME_KEY = "name";
	public final static String DB_HOSTS_KEY = "hosts";
	public final static String DB_HOST_KEY = "host";
	public final static String DB_DBNAME_KEY = "dbname";
	public final static String DB_PORT_KEY = "port";
	public final static String DB_USER_KEY = "user";
	public final static String DB_PASSWORD_KEY = "password";
	public final static String DB_SLAVE_OK_KEY = "slaveOk";
	
	public final static String SECURITY_DBNAME = "eid_security";
	public final static String DATA_DBNAME = "eid_data";
	
	private ArielSettings(){
		
	}
	
	private static String fromRoot(String tail) {
		if (ROOT == null) throw new RuntimeException("eid.ariel.root is missing");
		return join(ROOT, tail);
	}
	
	private static String join(String path1, String path2){
		return trimEnd(path1, fileSeparator) + fileSeparator + path2;
	}
	
	private static String trimEnd(String source, String separator){
		String result = source;
		while (result.endsWith(separator)) 
			result = result.substring(0, result.lastIndexOf(separator));
		return result;
	}
	
}

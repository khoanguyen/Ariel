package eid.ariel.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ArielConfig {
	private static ArielConfig instance;
	Map<String, ?> envConfig;
	String environment;
	DbConfigProvider securityDbConfigProvider;

	private ArielConfig() throws FileNotFoundException {
		InputStream stream = new FileInputStream(new File(
				ArielSettings.CONFIG_FILE_PATH));
		Yaml yaml = new Yaml();
		Map<String, ?> config = (Map<String, ?>) yaml.load(stream);
		environment = (String) config.get(ArielSettings.ENV_KEY);
		envConfig = (Map<String, ?>) config.get(environment);
	}

	public DbConfigProvider getSecurityDbConfigProvider() {
		if (securityDbConfigProvider == null) {
			securityDbConfigProvider = new DefaultDbConfigProvider(
					getSecurityDatabaseConfig());
		}
		return securityDbConfigProvider;
	}

	public static ArielConfig getInstance() throws FileNotFoundException {
		if (instance == null) {
			instance = new ArielConfig();
		}
		return instance;
	}

	public String getEnvironment() {
		return environment;
	}

	public Map<String, ?> getEnvConfig() {
		return envConfig;
	}

	public void setSecurityDbConfigProviderClass(Class<?> providerClass)
			throws IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException {
		Constructor<?> con = providerClass.getConstructor(Map.class);
		this.securityDbConfigProvider = (DbConfigProvider) con
				.newInstance(getSecurityDatabaseConfig());
	}

	private ArrayList<Map<String, ?>> getDatabaseConfig() {
		return (ArrayList<Map<String, ?>>) envConfig
				.get(ArielSettings.DB_KEY);
	}

	private Map<String, ?> getSecurityDatabaseConfig() {
		for (Map<String, ?> map : getDatabaseConfig()) {
			if (map.get(ArielSettings.DB_NAME_KEY).toString()
					.equals(ArielSettings.SECURITY_DBNAME)) {
				return map;
			}
		}
		return null;
	}

}

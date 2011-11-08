package eid.ariel.core;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import com.mongodb.ServerAddress;

class DefaultDbConfigProvider extends DbConfigProvider {

	public DefaultDbConfigProvider(Map<String, ?> config) throws UnknownHostException {
		super(config);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void parseConfig(Map<String, ?> config) throws UnknownHostException {
		// TODO Auto-generated method stub
		setConfigName((String) config.get(ArielSettings.DB_NAME_KEY));
		setDbName((String) config.get(ArielSettings.DB_DBNAME_KEY));

		if (config.containsKey(ArielSettings.DB_USER_KEY))
			setUser((String) config.get(ArielSettings.DB_USER_KEY));

		if (config.containsKey(ArielSettings.DB_PASSWORD_KEY))
			setPassword((String) config.get(ArielSettings.DB_PASSWORD_KEY));

		if (config.containsKey(ArielSettings.DB_SLAVE_OK_KEY))
			setSlaveOk((Boolean) config.get(ArielSettings.DB_SLAVE_OK_KEY));
		
		setHosts(parseHosts((ArrayList<Map<String, ?>>) config
				.get(ArielSettings.DB_HOSTS_KEY)));
	}

	private ArrayList<ServerAddress> parseHosts(
			ArrayList<Map<String, ?>> hosts) throws UnknownHostException {
		ArrayList<ServerAddress> result = new ArrayList<ServerAddress>();

		for (Map<String, ?> host : hosts) {
			ServerAddress serverAddress = null;
			if (host.containsKey(ArielSettings.DB_PORT_KEY)) {
				serverAddress = new ServerAddress(
						(String) host.get(ArielSettings.DB_HOST_KEY),
						(Integer) host.get(ArielSettings.DB_PORT_KEY));
			}else{
				serverAddress = new ServerAddress(
						(String) host.get(ArielSettings.DB_HOST_KEY));
			} 
			result.add(serverAddress);
		}

		return result;
	}
}

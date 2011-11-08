package eid.ariel.core;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import com.mongodb.ServerAddress;

public abstract class DbConfigProvider {
	
	private Map<String, ?> config;
	private String configName;
	private String dbName;
	private String user = null;
	private String password = null;
	private Boolean slaveOk = false;
	private ArrayList<ServerAddress> hosts;
	private Boolean secured = false;
	
	public DbConfigProvider(Map<String, ?> config) throws UnknownHostException{
		this.config = config;
		parseConfig(config);
	}
	
	public Map<String, ?> getConfig() {
		return config;
	}
	
	public Boolean isSecured(){
		return secured;
	}
	
	public Boolean getSlaveOk() {
		return slaveOk;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public String getConfigName() {
		return configName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUser() {
		return user;
	}
	
	public ArrayList<ServerAddress> getHosts() {
		return hosts;
	}
	
	protected void setConfigName(String configName) {
		this.configName = configName;
	}
	
	protected void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	protected void setHosts(ArrayList<ServerAddress> hosts) {
		this.hosts = hosts;
	}
	
	protected void setPassword(String password) {
		this.password = password;
	}
	
	protected void setUser(String user) {
		this.user = user;
		this.secured = true;
	}
	
	protected void setSlaveOk(Boolean slaveOk) {
		this.slaveOk = slaveOk;
	}
	
	protected abstract void parseConfig(Map<String, ?> config) throws UnknownHostException;
}

package eid.ariel.data;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import eid.ariel.core.ArielConfig;
import eid.ariel.core.DbConfigProvider;

public class MongoDbManager {
	
	private static MongoDbManager instance;
	
	public static MongoDbManager getInstance() {
		if (instance == null) instance = new MongoDbManager();
		return instance;
	}
	
	private MongoDbManager() {
	}

	public DBCollection getDbCollection(String name) throws UnknownHostException, MongoException, FileNotFoundException {
		DBCollection result = getDb().getCollection(name);
		return result;
	}

	public DBCollection getSecurityDbCollection(String name) throws UnknownHostException, MongoException, FileNotFoundException {
		DBCollection result = getSecurityDb().getCollection(name);
		return result;
	}

	public DB getDb() throws UnknownHostException, MongoException, FileNotFoundException {
		DB result = getDb(ArielConfig.getInstance().getDatabDbConfigProvider());
		return result;
	}

	public DB getSecurityDb() throws UnknownHostException, MongoException, FileNotFoundException {
		DB result = getDb(ArielConfig.getInstance().getSecurityDbConfigProvider());
		return result;
	}

	public static MongoDataAdapter dataAdapter() throws UnknownHostException, MongoException, FileNotFoundException{
		return new MongoDataAdapter(MongoDbManager.getInstance().getDb());
	}
	
	public static MongoDataAdapter securityAdapter() throws UnknownHostException, MongoException, FileNotFoundException{
		return new MongoDataAdapter(MongoDbManager.getInstance().getSecurityDb());
	}
	
	private DB getDb(DbConfigProvider configProvider) throws UnknownHostException, MongoException, FileNotFoundException {
		Mongo mongo = getMongoConnection(configProvider);
		DB result = mongo.getDB(configProvider.getDbName());
		
		if (configProvider.isSecured()){
			result.authenticate(configProvider.getUser(), 
					configProvider.getPassword().toCharArray());
		}
		
		return result;
	}
	
	private Mongo getMongoConnection(DbConfigProvider configProvider) throws FileNotFoundException{
		Mongo result = null;
		
		result = new Mongo(configProvider.getHosts());
		//if (configProvider.getSlaveOk()) result.;
		
		return result;
	}
}

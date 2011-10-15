package eid.ariel.data;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDbManager {
	private MongoDbManager() {
	}

	public static DBCollection getDbCollection(String name) {
		DBCollection result = getDb().getCollection(name);
		return result;
	}

	public static DBCollection getSecurityDbCollection(String name) {
		DBCollection result = getSecurityDb().getCollection(name);
		return result;
	}

	public static DB getDb() {
		DB result = null;
		return result;
	}

	public static DB getSecurityDb() {
		DB result = null;
		return result;
	}

	private static DB getDbFromName(String dbName) throws UnknownHostException, MongoException {
		Mongo mongo;
		DB result = null;

		mongo = new Mongo("localhost");
		result = mongo.getDB(dbName);

		return result;
	}
}

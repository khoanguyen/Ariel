package eid.ariel.data;


import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;


public class MongoDataAdapter implements IDataAdapter<Map<String, Object>> {

	private DB db;
	
	public MongoDataAdapter(DB db) {
		this.db = db;
	}

	
	@Override
	public IDataCollection<Map<String, Object>> from(String collectionName) {
		String[] tokens = collectionName.split("\\.");
		DBCollection collection = null;
		for (int i = 0; i < tokens.length; i++) {
			if (i == 0)
				collection = this.db.getCollection(tokens[i]);
			else
				collection = collection.getCollection(tokens[i]);
		}
		return new MongoCollection(collection);
	}

}

package eid.ariel.data;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class TestMongo {
	public String getUserId() {
		Mongo mongo;
		try {
			mongo = new Mongo("localhost");
			DB db = mongo.getDB("eid_security_dev");
			DBCollection collection = db.getCollection("users");
			DBCursor cursor = collection.find();
			if (cursor.hasNext()) {
				DBObject obj = cursor.next();
				return obj.get("_id").toString() + " - " + obj.get("loginid").toString();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "error";
	}
}

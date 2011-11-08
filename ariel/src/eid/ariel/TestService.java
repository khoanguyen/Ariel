package eid.ariel;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import eid.ariel.annotation.LoginRequired;
import eid.ariel.core.ArielConfig;
import eid.ariel.data.IDataAdapter;
import eid.ariel.data.MongoDataAdapter;
import eid.ariel.data.MongoDbManager;

@Path("/test")
public class TestService {
	
	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DBObject> getAllUsers() throws UnknownHostException, MongoException, FileNotFoundException{
		Mongo mongo = new Mongo("127.0.0.1");
		DBCollection collection = mongo.getDB("eid_security_dev").getCollection("users");
		DBCursor cur = collection.find();
		List<DBObject> result = new ArrayList<DBObject>();
		while (cur.hasNext()){
			
			result.add(cur.next());
		}
		return result;
	}
	
	@GET
	@Path("/conf")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getConf() throws UnknownHostException, FileNotFoundException{
		return (Map<String, Object>) ArielConfig.getInstance().getSecurityDbConfigProvider().getConfig();
	}

}

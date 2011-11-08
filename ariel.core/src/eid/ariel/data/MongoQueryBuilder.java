package eid.ariel.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoQueryBuilder {
	private Map<String, Object> query;
	private Map<String, Object> currentMap;
	private Stack<Map<String, Object>> stack;
	
	public Map<String, Object> getQuery() {
		return query;
	}
	
	public MongoQueryBuilder(){
		reset();
	}
	
	public void put(String key, Object value){
		currentMap.put(key, value);
	}
	
	public void beginMap(String key){
		Map<String, Object> newMap = new HashMap<String, Object>();
		currentMap.put(key, newMap);
		stack.push(currentMap);
		currentMap = newMap;
	}
	
	public void endMap(){
		if (!stack.isEmpty()) currentMap = stack.pop();
	}
	
	public void reset(){
		query = new HashMap<String, Object>();
		currentMap = query;
		stack = new Stack<Map<String,Object>>();
	}
	
	public DBObject getDbObject(){
		return new BasicDBObject(getQuery());
	}
}

package eid.ariel.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoCollection implements IDataCollection<Map<String, Object>> {

	public final static String FETCH_SORTED_FIELDS_KEY = "sortedFields";
	public final static String FETCH_START_INDEX_KEY = "startIndex";
	public final static String FETCH_LIMIT_KEY = "limit";

	private DBCollection collection;

	public MongoCollection(DBCollection collection) {
		this.collection = collection;
	}

	private Map<String, Object> dbobjectToMap(DBObject dbObject) {
		Stack<DBObject> dboStack = new Stack<DBObject>();
		Stack<Map<String, Object>> mapStack = new Stack<Map<String, Object>>();
		Stack<Iterator<String>> keyStack = new Stack<Iterator<String>>();

		Map<String, Object> result = new HashMap<String, Object>();
		dboStack.push(dbObject);
		mapStack.push(result);
		keyStack.push(dbObject.keySet().iterator());

		while (!keyStack.isEmpty()) {
			Iterator<String> iter = keyStack.peek();
			DBObject dbo = dboStack.peek();
			Map<String, Object> map = mapStack.peek();
			if (iter.hasNext()) {
				String key = iter.next();
				if (dbo.get(key) instanceof DBObject) {
					DBObject obj = (DBObject) dbo.get(key);
					Map<String, Object> newMap = new HashMap<String, Object>();
					keyStack.push(obj.keySet().iterator());
					dboStack.push(obj);
					mapStack.push(newMap);
					map.put(key, newMap);
				} else {
					map.put(key, dbo.get(key));
				}
			} else {
				keyStack.pop();
				dboStack.pop();
				mapStack.pop();
			}
		}

		return result;
	}

	private List<Map<String, Object>> cursorToList(DBCursor cursor) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		while (cursor.hasNext()) {
			result.add(dbobjectToMap(cursor.next()));
		}
		return result;
	}

	@Override
	public void save(Map<String, Object> target) {
		collection.save(new BasicDBObject(target));
	}

	@Override
	public List<Map<String, Object>> all() {
		// TODO Auto-generated method stub
		DBCursor cursor = collection.find();
		return cursorToList(cursor);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> query) {
		BasicDBObject qObj = new BasicDBObject(query);
		DBCursor cursor = collection.find(qObj);
		return cursorToList(cursor);
	}

	@Override
	public Map<String, Object> findScalar(Map<String, Object> query) {
		List<Map<String, Object>> list = find(query);
		if (list.size() > 1)
			throw new RuntimeException(
					"Scalar query return more than one result");
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		MongoQueryBuilder builder = new MongoQueryBuilder();
		builder.put("_id", id);
		collection.remove(new BasicDBObject(builder.getQuery()));
	}

	@Override
	public void insert(Map<String, Object> target) {
		// TODO Auto-generated method stub
		collection.insert(new BasicDBObject(target));
	}

	@Override
	public List<Map<String, Object>> fetch(Map<String, Object> fetch) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Map<String, Object> sortedFields = (Map<String, Object>) fetch
				.get(FETCH_SORTED_FIELDS_KEY);
		int startIndex = (Integer) fetch.get(FETCH_START_INDEX_KEY);
		int limit = (Integer) fetch.get(FETCH_LIMIT_KEY);
		DBCursor cursor = collection.find()
				.sort(new BasicDBObject(sortedFields)).skip(startIndex)
				.limit(limit);
		return cursorToList(cursor);
	}

	@Override
	public List<Map<String, Object>> query(Map<String, Object> query,
			Map<String, Object> fetch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyFetch(Map<String, Object> fetch) {
		// TODO Auto-generated method stub
		return fetch.containsKey(FETCH_SORTED_FIELDS_KEY) &&
				fetch.containsKey(FETCH_START_INDEX_KEY) &&
				fetch.containsKey(FETCH_LIMIT_KEY) &&
				(fetch.get(FETCH_SORTED_FIELDS_KEY) instanceof Map) &&
				(fetch.get(FETCH_START_INDEX_KEY) instanceof Integer) &&
				(fetch.get(FETCH_LIMIT_KEY) instanceof Integer);
	}

	@Override
	public boolean verifyQuery(Map<String, Object> query) {
		// TODO Auto-generated method stub
		return false;
	}

}

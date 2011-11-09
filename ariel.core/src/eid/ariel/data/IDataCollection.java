package eid.ariel.data;

import java.util.List;
import java.util.Map;


public interface IDataCollection<E> {
	List<E> all();
	List<E> find(Map<String, Object> query);
	E findScalar(Map<String, Object> query);
	void insert(Map<String, Object> target);
	void save(Map<String, Object> target);
	void delete(String id);
	List<E> fetch(Map<String, Object> fetch);
	List<E> query(Map<String, Object> query, Map<String, Object> fetch);
	boolean verifyFetch(Map<String, Object> fetch);
	boolean verifyQuery(Map<String, Object> query);
}

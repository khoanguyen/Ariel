package eid.ariel.data;

import java.util.List;
import java.util.Map;


public interface IDataCollection<E> {
	List<E> all();
	List<E> find(Map<String, Object> query);
	E findScalar(Map<String, Object> query);
	void insert(Map<String, Object> target);
	void save(Map<String, Object> target);
	void delete(Map<String, Object> target);
	List<Map<String, Object>> fetch(Map<String, Object> sortedFields, int startIndex, int limit);
	List<Map<String, Object>> fetch(Map<String, Object> query, Map<String, Object> sortedFields, int limit);
}

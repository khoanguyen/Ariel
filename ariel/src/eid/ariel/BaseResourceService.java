package eid.ariel;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.MongoException;

import eid.ariel.exception.RecordDoesNotExistException;
import eid.ariel.core.ArielUtils;
import eid.ariel.data.IDataCollection;
import eid.ariel.data.MongoDbManager;
import eid.ariel.data.MongoQueryBuilder;

public abstract class BaseResourceService {

	protected abstract String getCollectionName();

	protected abstract String getIdField();

	protected abstract String getResourceType();

	public BaseResourceService() {
	}

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> allItems() throws UnknownHostException,
			MongoException, FileNotFoundException {
		return getCollection(getCollectionName()).all();
	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createItem(Map<String, Object> school)
			throws UnknownHostException, MongoException, FileNotFoundException {
		school.put(getIdField(), UUID.randomUUID().toString());
		getCollection(getCollectionName()).insert(school);
		return ArielUtils.fixJsonString((String) school.get(getIdField()));
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> findItem(@PathParam("id") String id)
			throws UnknownHostException, MongoException, FileNotFoundException {
		MongoQueryBuilder builder = new MongoQueryBuilder();
		builder.put(getIdField(), id);
		Map<String, Object> school = getCollection(getCollectionName())
				.findScalar(builder.getQuery());
		if (school == null)
			throw new RecordDoesNotExistException(getResourceType());
		return school;
	}

	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateSchool(Map<String, Object> item)
			throws UnknownHostException, MongoException, FileNotFoundException {
		Map<String, Object> record = findItem((String) item.get(getIdField()));
		if (record == null)
			throw new RecordDoesNotExistException(getResourceType());
		getCollection(getCollectionName()).save(item);
	}

	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void removeItem(@PathParam("id") String id)
			throws UnknownHostException, MongoException, FileNotFoundException {
		getCollection(getCollectionName()).delete(id);
	}

	@GET
	@Path("fetch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> fetchItems(Map<String, Object> criteria)
			throws UnknownHostException, MongoException, FileNotFoundException {
		IDataCollection<Map<String, Object>> collection = getCollection(getCollectionName());
		if (!collection.verifyFetch(criteria))
			throw new InvalidParameterException("Invalid fetch criteria.");
		return collection.fetch(criteria);
	}

	private IDataCollection<Map<String, Object>> getCollection(String name)
			throws UnknownHostException, MongoException, FileNotFoundException {
		return MongoDbManager.dataAdapter().from(name);
	}
}

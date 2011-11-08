package eid.ariel.atlas;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
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

import eid.ariel.annotation.LoginRequired;
import eid.ariel.atlas.exception.RecordDoesNotExistException;
import eid.ariel.atlas.shcema.School;
import eid.ariel.core.ArielUtils;
import eid.ariel.data.IDataCollection;
import eid.ariel.data.MongoDbManager;
import eid.ariel.data.MongoQueryBuilder;

@Path("/schools")
@LoginRequired
public class SchoolService {

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> allSchools() throws UnknownHostException,
			MongoException, FileNotFoundException {
		return getCollection(School.COLLECTION).all();
	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createSchool(Map<String, Object> school)
			throws UnknownHostException, MongoException, FileNotFoundException {
		school.put(School.FIELD_ID, UUID.randomUUID().toString());
		getCollection(School.COLLECTION).insert(school);
		return ArielUtils.fixJsonString((String) school.get(School.FIELD_ID));
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> findSchool(@PathParam("id") String id)
			throws UnknownHostException, MongoException, FileNotFoundException {
		MongoQueryBuilder builder = new MongoQueryBuilder();
		builder.put(School.FIELD_ID, id);
		Map<String, Object> school = getCollection(School.COLLECTION).findScalar(builder.getQuery());
		if (school == null) throw new RecordDoesNotExistException("School");
		return school;
	}

	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateSchool(Map<String, Object> school)
			throws UnknownHostException, MongoException, FileNotFoundException {
		Map<String, Object> record = findSchool((String) school
				.get(School.FIELD_ID));
		if (record == null)
			throw new RecordDoesNotExistException("School");
		getCollection(School.COLLECTION).save(school);
	}

	@DELETE
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void removeSchool(Map<String, Object> school)
			throws UnknownHostException, MongoException, FileNotFoundException {
		getCollection(School.COLLECTION).delete(school);
	}

	private IDataCollection<Map<String, Object>> getCollection(String name)
			throws UnknownHostException, MongoException, FileNotFoundException {
		return MongoDbManager.dataAdapter().from(name);
	}
}

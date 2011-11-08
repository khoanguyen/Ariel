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
import eid.ariel.atlas.shcema.User;
import eid.ariel.core.ArielUtils;
import eid.ariel.data.IDataCollection;
import eid.ariel.data.MongoDbManager;
import eid.ariel.data.MongoQueryBuilder;

@Path("/users")
@LoginRequired
public class UserService {

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> allUsers() throws UnknownHostException,
			MongoException, FileNotFoundException {
		return getCollection(User.COLLECTION).all();
	}

	@POST
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createUser(Map<String, Object> user)
			throws UnknownHostException, MongoException, FileNotFoundException {
		user.put(User.FIELD_ID, UUID.randomUUID().toString());
		getCollection(User.COLLECTION).insert(user);
		return ArielUtils.fixJsonString((String) user.get(User.FIELD_ID));
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> findUser(@PathParam("id") String id)
			throws UnknownHostException, MongoException, FileNotFoundException {
		MongoQueryBuilder builder = new MongoQueryBuilder();
		builder.put(User.FIELD_ID, id);
		Map<String, Object> user = getCollection(User.COLLECTION).findScalar(builder.getQuery());
		if (user == null) throw new RecordDoesNotExistException("User");
		return user;
	}

	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateUser(Map<String, Object> user)
			throws UnknownHostException, MongoException, FileNotFoundException {
		Map<String, Object> record = findUser((String) user.get(User.FIELD_ID));
		if (record == null)
			throw new RecordDoesNotExistException("User");
		getCollection(User.COLLECTION).save(user);
	}

	@DELETE
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void removeUser(Map<String, Object> user)
			throws UnknownHostException, MongoException, FileNotFoundException {
		getCollection(User.COLLECTION).delete(user);
	}

	private IDataCollection<Map<String, Object>> getCollection(String name)
			throws UnknownHostException, MongoException, FileNotFoundException {
		return MongoDbManager.dataAdapter().from(name);
	}
}

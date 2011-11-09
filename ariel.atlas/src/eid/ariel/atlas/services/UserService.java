package eid.ariel.atlas.services;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mongodb.MongoException;

import eid.ariel.BaseResourceService;
import eid.ariel.annotation.LoginRequired;
import eid.ariel.atlas.shcema.User;
import eid.ariel.data.MongoDbManager;

@Path("/users")
@LoginRequired
public class UserService extends BaseResourceService {

	@Override
	protected String getCollectionName() {
		// TODO Auto-generated method stub
		return User.COLLECTION;
	}

	@Override
	protected String getIdField() {
		// TODO Auto-generated method stub
		return User.FIELD_ID;
	}

	@Override
	protected String getResourceType() {
		// TODO Auto-generated method stub
		return "User";
	}

	@Override
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void removeItem(@PathParam("id") String id) throws UnknownHostException,
			MongoException, FileNotFoundException {
		// TODO Auto-generated method stub
		super.removeItem(id);
		MongoDbManager.securityAdapter().from(getCollectionName()).delete(id);
	}
}

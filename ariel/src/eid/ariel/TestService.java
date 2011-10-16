package eid.ariel;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import eid.ariel.data.TestMongo;

@Path("/test")
public class TestService {
	
	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String hello(@HeaderParam("Ariel-UserId") String userId) {
		TestMongo mongo = new TestMongo();
		return new TestMongo().getUserId();
	}
	
	@POST
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public String helloPost() {
		return "Post";
	}
}

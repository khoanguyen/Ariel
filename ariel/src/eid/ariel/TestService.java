package eid.ariel;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class TestService {
	
	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> hello() {
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("test1", "value1");
		result.put("test2", 2);
		
		return result;
	}
	
}

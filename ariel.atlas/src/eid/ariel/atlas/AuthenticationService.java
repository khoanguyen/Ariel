package eid.ariel.atlas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.mongodb.MongoException;

import eid.ariel.core.ArielConst;
import eid.ariel.core.CryptographyException;
import eid.ariel.exception.InvalidParamException;
import eid.ariel.annotation.LoginRequired;
import eid.ariel.atlas.logic.AuthenticationLogic;

@Path("/authentication")
public class AuthenticationService {
	private AuthenticationLogic logic = new AuthenticationLogic();

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String, Object> login(Map<String, Object> params)
			throws MongoException, CryptographyException, IOException,
			InterruptedException {
		if (!verifyLoginParam(params)) {
			throw new InvalidParamException();
		}

		return logic.authenticate(params);
	}

	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@LoginRequired
	public void logout(@HeaderParam(ArielConst.HEADER_USER_ID_KEY) String userId)
			throws UnknownHostException, MongoException, FileNotFoundException {
		logic.unauthenticate(userId);
	}

	private boolean verifyLoginParam(Map<String, Object> params) {
		return params.containsKey(AtlasConst.LOGINID_KEY)
				&& params.containsKey(AtlasConst.PASSPHRASE_KEY);
	}

}

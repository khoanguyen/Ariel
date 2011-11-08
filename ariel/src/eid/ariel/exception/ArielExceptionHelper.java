package eid.ariel.exception;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import eid.ariel.core.ArielConst;

public final class ArielExceptionHelper {
		
	private ArielExceptionHelper(){
	}
	
	public static Map<String, Object> generateErrorMap(){
		HashMap<String, Object> messageBody = new HashMap<String, Object>();
		messageBody.put(ArielConst.RESULT_KEY, 1);
		return messageBody;
	}
	
	public static Response createErrorResponse(Map<String, Object> messageBody){
		Response response = Response.status(506).entity(messageBody).build();
		return response;
	}
}

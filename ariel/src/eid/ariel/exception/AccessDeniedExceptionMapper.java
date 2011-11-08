package eid.ariel.exception;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import eid.ariel.core.ArielConst;

public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

	@Override
	public Response toResponse(AccessDeniedException arg0) {
		// TODO Auto-generated method stub
		Map<String, Object> messageBody = ArielExceptionHelper.generateErrorMap();
		messageBody.put(ArielConst.ERROR_CODE_KEY, ArielConst.ERROR_ACCESS_DENIED_CODE);
		messageBody.put(ArielConst.ERROR_MESSAGE_KEY, ArielConst.ERROR_ACCESS_DENIED_MESSAGE);
		return ArielExceptionHelper.createErrorResponse(messageBody);
	}

}

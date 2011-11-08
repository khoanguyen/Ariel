package eid.ariel.atlas.exception;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import eid.ariel.atlas.AtlasConst;
import eid.ariel.core.ArielConst;
import eid.ariel.exception.ArielExceptionHelper;

@Provider
public class FailedLoginExceptionMapper implements ExceptionMapper<FailedLoginException> {

	@Override
	public Response toResponse(FailedLoginException exception) {
		// TODO Auto-generated method stub
		Map<String, Object> messageBody = ArielExceptionHelper.generateErrorMap();
		messageBody.put(AtlasConst.ID_KEY, exception.getAccountId());
		messageBody.put(ArielConst.ERROR_CODE_KEY, exception.getErrorCode());
		messageBody.put(ArielConst.ERROR_MESSAGE_KEY, exception.getErrorMessage());
		return ArielExceptionHelper.createErrorResponse(messageBody);
	}
}

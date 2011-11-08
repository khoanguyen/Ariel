package eid.ariel.exception;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import eid.ariel.core.ArielConst;

@Provider
public class InvalidParamExceptionMapper implements ExceptionMapper<InvalidParamException>{

	@Override
	public Response toResponse(InvalidParamException arg0) {
		// TODO Auto-generated method stub
		Map<String, Object> messageBody = ArielExceptionHelper.generateErrorMap();
		messageBody.put(ArielConst.ERROR_CODE_KEY, ArielConst.ERROR_INVALID_PARAMETER_CODE);
		messageBody.put(ArielConst.ERROR_MESSAGE_KEY, ArielConst.ERROR_INVALID_PARAMETER_MESSAGE);
		return ArielExceptionHelper.createErrorResponse(messageBody);
	}
}

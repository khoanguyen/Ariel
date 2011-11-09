package eid.ariel.exception;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import eid.ariel.core.ArielConst;

@Provider
public class RecordDoesNotExistExeptionMapper implements ExceptionMapper<RecordDoesNotExistException> {

	@Override
	public Response toResponse(RecordDoesNotExistException exception) {
		// TODO Auto-generated method stub
		Map<String, Object> messageBody = ArielExceptionHelper.generateErrorMap();
		messageBody.put(ArielConst.ERROR_CODE_KEY, ArielConst.ERROR_RECORD_NOT_EXIST_CODE);
		messageBody.put(ArielConst.ERROR_MESSAGE_KEY, exception.getMessage());
		return ArielExceptionHelper.createErrorResponse(messageBody);
	}
}

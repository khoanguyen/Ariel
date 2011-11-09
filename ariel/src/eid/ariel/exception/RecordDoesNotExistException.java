package eid.ariel.exception;

import eid.ariel.core.ArielConst;

public class RecordDoesNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recordType;
	
	public String getRecordType() {
		return recordType;
	}
	
	public RecordDoesNotExistException(String recordType){
		super(recordType + " " + ArielConst.ERROR_RECORD_NOT_EXIST_MESSAGE);
		this.recordType = recordType;
	}
}

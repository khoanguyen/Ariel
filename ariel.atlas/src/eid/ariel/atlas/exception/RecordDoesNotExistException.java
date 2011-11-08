package eid.ariel.atlas.exception;

import eid.ariel.atlas.AtlasConst;

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
		super(recordType + " " + AtlasConst.ERROR_RECORD_NOT_EXIST_MESSAGE);
		this.recordType = recordType;
	}
}

package eid.ariel.atlas.services;

import javax.ws.rs.Path;

import eid.ariel.BaseResourceService;
import eid.ariel.annotation.LoginRequired;
import eid.ariel.atlas.shcema.School;

@Path("/schools")
@LoginRequired
public class SchoolService extends BaseResourceService{

	@Override
	protected String getCollectionName() {
		// TODO Auto-generated method stub
		return School.COLLECTION;
	}

	@Override
	protected String getIdField() {
		// TODO Auto-generated method stub
		return School.FIELD_ID;
	}

	@Override
	protected String getResourceType() {
		// TODO Auto-generated method stub
		return "School";
	}
}

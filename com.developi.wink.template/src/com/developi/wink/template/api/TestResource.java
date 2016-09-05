package com.developi.wink.template.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.commons.util.io.json.JsonJavaObject;
import com.ibm.domino.osgi.core.context.ContextInfo;

import lotus.domino.NotesException;
import lotus.domino.Session;

@Path("/test")
public class TestResource {

	@GET
	public Response test(){
		JsonJavaObject resp = new JsonJavaObject();
		
		try {
			resp.put("message", "Hello " + getUserSession().getEffectiveUserName());
			return Response.ok().type(MediaType.APPLICATION_JSON).entity(resp.toString()).build();
		} catch (NotesException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
				
	}

	private Session getUserSession() {
		return ContextInfo.getUserSession();
	}
	
	
}

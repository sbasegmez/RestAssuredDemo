package com.developi.wink.demo.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ibm.domino.osgi.core.context.ContextInfo;

import lotus.domino.NotesException;
import lotus.domino.Session;

@Path("/test")
public class TestResource {

	@GET
	public Response test(){
		try {
			String userName = getUserSession().getEffectiveUserName();
			
			if("Anonymous".equals(userName)) {
				// If connected through a browser, this response will signal for authentication and redirected to the login form. 
				// However, be careful with 40x responses. If session authentication is enabled for the server,
				// The consumer might receive the login form and 200-OK response. This would be a code-breaker.
				return Response.status(Status.UNAUTHORIZED).build();
			} else {
				String message = "<b>Hello " + userName + "</b>";
				return Response.ok().type(MediaType.TEXT_HTML).entity(message).build();
			}
		} catch (NotesException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
				
	}

	private Session getUserSession() {
		return ContextInfo.getUserSession();
	}
	
	
}

package com.developi.wink.demo.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.wink.common.annotations.Parent;

@Parent(VersionRoot.class)
@Path("/ping")
public class PingResource {

	@GET
	public Response ping() {
		return Response.ok("<h1>Hello World Version 1!</h1>", MediaType.TEXT_HTML).build();
	}
	
}

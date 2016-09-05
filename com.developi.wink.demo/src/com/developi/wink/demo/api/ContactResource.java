package com.developi.wink.demo.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.wink.common.model.multipart.BufferedInMultiPart;

import com.developi.wink.demo.data.DominoAccessor;
import com.developi.wink.demo.model.Contact;
import com.developi.wink.demo.model.ModelUtils;
import com.ibm.domino.osgi.core.context.ContextInfo;

@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

	// DominoAccessor is a class to access Domino data
	private DominoAccessor accessor = new DominoAccessor(ContextInfo.getUserSession());
	
	@GET()
	public Response getContactList(	@DefaultValue("1") @QueryParam("start") int start, @QueryParam("count") int count) {
		
		List<Contact> contactList = accessor.pullContacts(start, count);
		String result = ModelUtils.toJson(contactList).toString();
		
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}
	
	@Path("/search")
	@GET()
	public Response searchContactList(@QueryParam("q") String searchString) {
		List<Contact> contactList = accessor.searchContacts(searchString);
		String result = ModelUtils.toJson(contactList).toString();
		
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@Path("/{id}")
	@GET()
	public Response getContact(@PathParam("id") String id) {
		Contact contact = accessor.findContact(id);

		if(null == contact) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {
			String result = ModelUtils.toJson(contact).toString();
			return Response.ok(result, MediaType.APPLICATION_JSON).build();
		}
	}

	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postContactJson(String body) {
		Contact contact = ModelUtils.buildContactfromJson(body);
		accessor.saveNewContact(contact);
		
		String result = ModelUtils.toJson(contact).toString();
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

	@POST()
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response postContactForm(BufferedInMultiPart formData) {
		Contact contact = ModelUtils.buildContactfromMultipart(formData);
		accessor.saveNewContact(contact);
		
		String result = ModelUtils.toJson(contact).toString();
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}
	
}

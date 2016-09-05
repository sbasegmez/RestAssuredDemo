package com.developi.wink.demo.model;

import java.util.List;

import org.apache.wink.common.model.multipart.BufferedInMultiPart;

import com.ibm.commons.util.io.json.JsonException;
import com.ibm.commons.util.io.json.JsonJavaArray;
import com.ibm.commons.util.io.json.JsonJavaFactory;
import com.ibm.commons.util.io.json.JsonJavaObject;
import com.ibm.commons.util.io.json.JsonParser;

public class ModelUtils {

	public static JsonJavaObject toJson(Contact contact) {
		JsonJavaObject cj = new JsonJavaObject();
		
		cj.put("number", contact.getNumber());
		cj.put("firstName", contact.getFirstName());
		cj.put("middle", contact.getMiddle());
		cj.put("lastName", contact.getLastName());
		cj.put("city", contact.getCity());
		cj.put("state", contact.getState());
		cj.put("zip", contact.getZip());
		cj.put("country", contact.getCountry());
		cj.put("emailAddress", contact.getEmail());
		
		return cj;
	}

	public static JsonJavaObject toJson(List<Contact> contacts) {
		
		JsonJavaObject cj = new JsonJavaObject();
		JsonJavaArray arr = new JsonJavaArray(contacts.size());
		
		cj.put("listOfContacts", arr);
		cj.put("count", contacts.size());
		
		for(Contact c: contacts) {
			arr.add(toJson(c));
		}
		
		return cj;
		
	}

	public static Contact buildContactfromJson(String jsonStr) {

		Object jsonObjSafe = null;

		try {
			jsonObjSafe = JsonParser.fromJson(JsonJavaFactory.instanceEx, jsonStr);
		} catch (JsonException e) {
			throw new RuntimeException("Unable to parse incoming message body", e);
		}
		
		if(! (jsonObjSafe instanceof JsonJavaObject)) {
			throw new RuntimeException("Invalid format in the message. Json Object expected");
		}
		
		JsonJavaObject cj = (JsonJavaObject) jsonObjSafe;
		
		Contact contact = new Contact();
		
		contact.setFirstName(cj.getAsString("firstName"));
		contact.setMiddle(cj.getAsString("middle"));
		contact.setLastName(cj.getAsString("lastName"));
		contact.setCity(cj.getAsString("city"));
		contact.setState(cj.getAsString("state"));
		contact.setZip(cj.getAsString("zip"));
		contact.setCountry(cj.getAsString("country"));
		contact.setEmail(cj.getAsString("emailAddress"));
		
		return contact;
	}

	public static Contact buildContactfromMultipart(BufferedInMultiPart formData) {
		Contact contact = new Contact();

		// Not Implemented yet...
				
		return contact;
	}

}

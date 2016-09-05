package com.developi.wink.demo.data;

import com.developi.wink.demo.model.Contact;

import lotus.domino.Document;
import lotus.domino.NotesException;

public class DataUtils {

	public static Contact toContact(Document doc) throws NotesException {
		Contact c = new Contact();
		
		c.setCity(doc.getItemValueString("city"));
		c.setCountry(doc.getItemValueString("country"));
		c.setEmail(doc.getItemValueString("email"));
		c.setFirstName(doc.getItemValueString("firstname"));
		c.setLastName(doc.getItemValueString("lastname"));
		c.setMiddle(doc.getItemValueString("middle"));
		c.setNumber(doc.getItemValueString("number"));
		c.setState(doc.getItemValueString("state"));
		c.setZip(doc.getItemValueString("zip"));		
		
		return c;
	}

	public static void fillDoc(Document doc, Contact contact) throws NotesException {

		doc.replaceItemValue("number", contact.getNumber());

		doc.replaceItemValue("city", contact.getCity());
		doc.replaceItemValue("country", contact.getCountry());
		doc.replaceItemValue("email", contact.getEmail());
		doc.replaceItemValue("firstname", contact.getFirstName());
		doc.replaceItemValue("lastname", contact.getLastName());
		doc.replaceItemValue("middle", contact.getMiddle());
		doc.replaceItemValue("state", contact.getState());
		doc.replaceItemValue("zip", contact.getZip());		

		
	}
	
}

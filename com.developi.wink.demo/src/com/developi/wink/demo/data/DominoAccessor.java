package com.developi.wink.demo.data;

import java.util.ArrayList;
import java.util.List;

import com.developi.wink.demo.model.Contact;

import lotus.domino.Database;
import lotus.domino.Document;
import lotus.domino.DocumentCollection;
import lotus.domino.NotesException;
import lotus.domino.Session;
import lotus.domino.View;

public class DominoAccessor {

	public static final String CONTACTSDB = "RestAssured/fakenames.nsf";
	public static final String CONTACTSVIEW = "ByName";
	public static final String CONTACTSIDVIEW = "ById";
	
	public static final int PAGELIMIT = 100;
	public static final int MAXLIMIT = 1000;
	
	private transient final Session session;
	
	public DominoAccessor(Session session) {
		this.session = session;
	}
	
	public Contact findContact(String number) {
		Database db = null;
		View view = null;
		Document doc = null;
		
		try { 
			db = session.getDatabase("", CONTACTSDB);
			view = db.getView(CONTACTSIDVIEW);
			doc = view.getDocumentByKey(number, true);

			if(null==doc) {
				return null;
			} else {
				return DataUtils.toContact(doc);
			}
			
		} catch (NotesException e) {
			throw new RuntimeException(e);
		} finally {
			DominoUtils.recycleObjects(db, view, doc);
		}

	}
	
	public List<Contact> pullContacts(int start, int size) {
		if(size==0) {
			size = PAGELIMIT;
		} else if(size>MAXLIMIT) {
			size = MAXLIMIT;
		}

		if(start == 0) {
			start = 1;
		}
		
		List<Contact> contacts = new ArrayList<Contact>(size);
		
		Database db = null;
		View view = null;
		Document doc = null;
		
		try { 
			db = session.getDatabase("", CONTACTSDB);
			view = db.getView(CONTACTSVIEW);
			view.setAutoUpdate(false);

			int index = 1;
			
			// This is a slow method, but this is just a demo!
			doc = view.getNthDocument(start);
			
			while(null!=doc) {
				index++;
				
				contacts.add(DataUtils.toContact(doc));

				if(index>size) {
					break;
				}
				
				Document nextDoc = view.getNextDocument(doc);
				DominoUtils.recycleObject(doc);
				doc = nextDoc;
			}
			
		} catch (NotesException e) {
			throw new RuntimeException(e);
		} finally {
			DominoUtils.recycleObjects(db, view, doc);
		}

		return contacts;
	}
	
	public List<Contact> searchContacts(String searchString) {
		List<Contact> contacts = new ArrayList<Contact>(PAGELIMIT);

		Database db = null;
		DocumentCollection docs = null;
		Document doc = null;
		
		try { 
			db = session.getDatabase("", CONTACTSDB);
			docs = db.FTSearch(searchString, PAGELIMIT);

			doc = docs.getFirstDocument();
			
			while(null!=doc) {
				contacts.add(DataUtils.toContact(doc));

				Document nextDoc = docs.getNextDocument(doc);
				DominoUtils.recycleObject(doc);
				doc = nextDoc;
			}
			
		} catch (NotesException e) {
			throw new RuntimeException(e);
		} finally {
			DominoUtils.recycleObjects(db, docs, doc);
		}

		return contacts;
	}

	public void saveNewContact(Contact contact) {
		
		Database db = null;
		Document doc = null;
		
		try { 
			db = session.getDatabase("", CONTACTSDB);
			doc = db.createDocument();

			doc.replaceItemValue("Form", "fUserName");
			
			String number = (String) session.evaluate("@Unique").get(0);
			
			contact.setNumber(number);
			
			DataUtils.fillDoc(doc, contact);
			
			doc.save();
			
			
		} catch (NotesException e) {
			throw new RuntimeException(e);
		} finally {
			DominoUtils.recycleObjects(db, doc);
		}
		
	}
}

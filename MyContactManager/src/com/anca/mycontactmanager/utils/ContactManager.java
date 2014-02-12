package com.anca.mycontactmanager.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;



public class ContactManager {
	private static List<Contact> contactList;
	Context context;
	private static ContactManager instance;
	
	private ContactManager(Context context) {
		this.context = context;
		contactList = fetchContacts();
		
	}
	
	public static ContactManager getInstance(Context context){
		if(instance == null) {
	         instance = new ContactManager(context);
	    }
	    return instance;
	}
	


	private List<Contact> fetchContacts() {
    	List<Contact> contacts = new ArrayList<Contact>();
		String phoneNumber = null;
		String email = null;

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		String STARRED = ContactsContract.Contacts.STARRED;



		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;
		
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null );	

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Contact contact = null;

				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
				
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));
				int isStarred = Integer.parseInt(cursor.getString(cursor.getColumnIndex( STARRED )));
				
				if (hasPhoneNumber > 0) {
					contact = new Contact(contact_id, name);
					System.out.println(name + " " + isStarred);
					if(isStarred>0)
						contact.addToFavorites();

					// for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);
					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						contact.addPhoneNumber(phoneNumber);
					}
					phoneCursor.close();

					// for every email of the contact
					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
					while (emailCursor.moveToNext()) {
						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
						contact.addEmail(email);
					}
					emailCursor.close();
			
				}
				contacts.add(contact);
			}

		}
		cursor.close();
		return contacts;
	}
	
	private List<String> getRecentCalls() { 
		List<String> recentCalls = new LinkedList<String>();
		ContentResolver contentResolver = context.getContentResolver();

		
		Cursor callLogCursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null,null, null, null );	
		
		int id = callLogCursor.getColumnIndex(CallLog.Calls._ID);
		int name = callLogCursor.getColumnIndex(CallLog.Calls.CACHED_NAME); 
		int number = callLogCursor.getColumnIndex(CallLog.Calls.NUMBER); 
		int type = callLogCursor.getColumnIndex(CallLog.Calls.TYPE); 
		int date = callLogCursor.getColumnIndex(CallLog.Calls.DATE);
 
		//sb.append("Call Log :"); 
		while (callLogCursor.moveToNext()) { 
			String contactId = callLogCursor.getString(id);
			String contactName = callLogCursor.getString(name); 
			String phNumber = callLogCursor.getString(number); 
			String callType = callLogCursor.getString(type); 
			String callDate = callLogCursor.getString(date); 
			Date callDayTime = new Date(Long.valueOf(callDate)); 
			
			String dir = null; 
			int dircode = Integer.parseInt(callType); 
			if(dircode == CallLog.Calls.OUTGOING_TYPE) { 
				recentCalls.add(contactId);
			} 

		} 
		callLogCursor.close(); 
		return recentCalls;

		
	}





	public List<Contact> getContactList(String condition) {
		List<Contact> filteredContacts = new ArrayList<Contact>();
		
		if(condition.equals("Recent")){
			for (Contact contact : contactList){
				List<String> recentCalls = getRecentCalls();
				if (recentCalls.contains(contact.getContact_id()))
					filteredContacts.add(contact);
			}
		}
		else if(condition.equals("Favorite")){
			for (Contact contact : contactList){
				if (contact.getFavorited())
					filteredContacts.add(contact);
			}
		}
		else
			filteredContacts = contactList;
		return filteredContacts;
	}
	
	
	
}

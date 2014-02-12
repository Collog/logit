package com.anca.mycontactmanager.utils;

import java.util.ArrayList;

public class Contact implements Comparable<Contact>{
	private String contact_id;
	private String name;
	private ArrayList<String> phoneNumbers;
	private ArrayList<String> emails;
	private boolean favorited;
	
	
	
	public Contact(String contact_id, String name) {
		this.contact_id = contact_id;
		this.name = name;
		phoneNumbers = new ArrayList<String>();
		emails = new ArrayList<String>();
		favorited = false;
	}
	

	public void addPhoneNumber(String phoneNr){
		phoneNumbers.add(phoneNr);
	}
	
	public void addEmail(String email) {
		emails.add(email);
	}
	
	
	public void addToFavorites(){
		favorited = true;
	}

	public String getContact_id(){
		return contact_id;
	}

	public String getName(){
		return name;
	}

	public String getPhoneNumber(){
		if(!phoneNumbers.isEmpty())
			return phoneNumbers.get(0);
		return null;
	}

	public String getEmail(){
		if(!emails.isEmpty())
			return emails.get(0);
		return null;
	}
	
	public boolean getFavorited(){
		return favorited;
	}



	@Override
	public String toString() {
		StringBuilder contactinfo = new StringBuilder();
		contactinfo.append(name);
		if(phoneNumbers != null)
			contactinfo.append("\n Phone number: " + phoneNumbers);
		if(emails != null)
			contactinfo.append("\n Email: " + emails);
		return contactinfo.toString();
	}


	@Override
	public int compareTo(Contact other) {
		return name.compareTo(other.name);
	}


}


  




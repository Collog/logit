package com.anca.mycontactmanager;

import java.util.ArrayList;

public class Contact implements Comparable<Contact>{
	private String contact_id;
	private String name;
	private ArrayList<String> phoneNumber;
	private String email;
	
	public Contact(String contact_id, String name, String phoneNr, String email) {
		this.contact_id = contact_id;
		this.name = name;
		this.phoneNumber = new ArrayList<String>();
		phoneNumber.add(phoneNr);
		this.email = email;
	}
	
	
	public Contact(String contact_id, String name) {
		this.contact_id = contact_id;
		this.name = name;
		phoneNumber = new ArrayList<String>();
	}
	
	
	public Contact(String contact_id, String name, String phoneNr) {
		this.contact_id = contact_id;
		this.name = name;
		this.phoneNumber = new ArrayList<String>();
		phoneNumber.add(phoneNr);
	}
	
	public Contact(String name) {
		this.name = name;
		phoneNumber = new ArrayList<String>();
	}



	public void addPhoneNumber(String phoneNr){
		phoneNumber.add(phoneNr);
	}
	

	public void setContact_id(String contact_id) {
		this.contact_id = contact_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact_id() {
		return contact_id;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber.get(0);
	}

	public String getEmail() {
		return email;
	}



	@Override
	public String toString() {
		StringBuilder contactinfo = new StringBuilder();
		contactinfo.append(name);
		if(phoneNumber != null)
			contactinfo.append("\n Phone number: " + phoneNumber.get(0));
		if(email != null)
			contactinfo.append("\n Email: " + email);
		return contactinfo.toString();
	}


	@Override
	public int compareTo(Contact other) {
		return name.compareTo(other.name);
	}


}


  




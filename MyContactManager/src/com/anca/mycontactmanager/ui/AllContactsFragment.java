package com.anca.mycontactmanager.ui;

import com.anca.mycontactmanager.utils.ContactManager;

public class AllContactsFragment extends ContactsFragment{

	@Override
	void setContactList() {
		contactList = ContactManager.getInstance(getActivity().getBaseContext()).getContactList("All");
	}
	
    
    
}
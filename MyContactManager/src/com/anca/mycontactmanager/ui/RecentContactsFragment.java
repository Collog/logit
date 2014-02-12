package com.anca.mycontactmanager.ui;

import com.anca.mycontactmanager.utils.ContactManager;

public class RecentContactsFragment extends ContactsFragment{

	@Override
	void setContactList() {
		contactList = ContactManager.getInstance(getActivity().getBaseContext()).getContactList("Recent");
		
	}
   
	
	

    
}
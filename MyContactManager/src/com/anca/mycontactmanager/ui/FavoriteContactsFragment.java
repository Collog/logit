package com.anca.mycontactmanager.ui;

import com.anca.mycontactmanager.utils.ContactManager;

public class FavoriteContactsFragment extends ContactsFragment{
	
	
	@Override
	void setContactList() {
		contactList = ContactManager.getInstance(getActivity().getBaseContext()).getContactList("Favorite");
	}
	

	
}

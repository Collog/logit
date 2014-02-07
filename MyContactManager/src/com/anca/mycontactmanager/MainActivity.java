package com.anca.mycontactmanager;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;






public class MainActivity extends SherlockFragmentActivity implements OnItemClickListener  {

	ListView contactListView;
    ContactListAdapter contactListAdapter;
    List<Contact> contacts = new ArrayList<Contact>();
 
    Activity activity = this;
    private ActionMode actionMode;
    

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contactListView = (ListView) findViewById(R.id.contactList);
		
		fetchContacts();
		
		contactListAdapter = new ContactListAdapter(this, R.layout.contact_entry, contacts);
	    contactListView.setAdapter(contactListAdapter);
	    contactListView.setOnItemClickListener(this);	
	}



	public void fetchContacts() {

		String phoneNumber = null;
		String email = null;

		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;


		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
		String DATA = ContactsContract.CommonDataKinds.Email.DATA;

		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null );	

		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {
				Contact contact = null;

				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));

				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

				if (hasPhoneNumber > 0) {
					int nrOfPhoneNumbers = 0;
					contact = new Contact(contact_id, name);

					// for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						if(nrOfPhoneNumbers++ == 0)
						contact.addPhoneNumber(phoneNumber);

					}
					phoneCursor.close();

					// for every email of the contact
					Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
					while (emailCursor.moveToNext()) {
						email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
						contact.setEmail(email);
					}
					emailCursor.close();
				}
				contacts.add(contact);
			}

		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}	
 
	
	@Override
    public void onItemClick(AdapterView<?> adapterView, View view,
            int position, long id) {
		contactListAdapter.selectContact(position);
        actionMode = startActionMode(new ActionModeCallback());
        if (actionMode != null)
    		actionMode.setTitle(String.valueOf(contacts.get(position).getName()));
        
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.ascending: 
			Collections.sort(contacts);
			contactListView.setAdapter(contactListAdapter);
			return true;
		case R.id.descending:
			Collections.sort(contacts,Collections.reverseOrder() );
			contactListView.setAdapter(contactListAdapter);
			return true;	
		}
		return super.onOptionsItemSelected(item);
	}
	

	private class ActionModeCallback implements ActionMode.Callback {
		 
	        @Override
	        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	            mode.getMenuInflater().inflate(R.menu.context_menu, menu);
	            return true;
	        }
	 
	        @Override
	        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	            return false;
	        }
	 
	        @Override
	        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	 
	            switch (item.getItemId()) {
	            case R.id.menu_delete:
	                contactListAdapter.removeContact();                
	                mode.finish();
	                return true;
	            default:
	                return false;
	            }
	        }
	 
	        @Override
	        public void onDestroyActionMode(ActionMode mode) {
	            actionMode = null;
	        }
	    }
}



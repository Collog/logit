package com.anca.mycontactmanager.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anca.mycontactmanager.R;
import com.anca.mycontactmanager.utils.Contact;


public class ContactListAdapter extends BaseAdapter {
	List<Contact> contactList;
    Context context;
    private Contact selectedContact;


    public ContactListAdapter(Context context, List<Contact> contactList) {
    	this.context = context;
    	this.contactList = contactList;
    }
    
    public int getCount() {
        return contactList.size();
    }

    public Object getItem(int position) {
        return contactList.get(position);
    }

    public long getItemId(int position) {
    	String id = contactList.get(position).getContact_id();
        return Long.parseLong(id);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	TextView name, phonenr, email;
        ImageView star;
        View row;
    	
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.contact_entry, parent, false);
        
        name = (TextView) row.findViewById(R.id.name);
        phonenr = (TextView) row.findViewById(R.id.phonenr);
        email = (TextView) row.findViewById(R.id.email);
        star = (ImageView)row.findViewById(R.id.star);
        
        name.setText(contactList.get(position).getName());
        if(contactList.get(position).getPhoneNumber() != null)
        	phonenr.setText(contactList.get(position).getPhoneNumber());
        if(contactList.get(position).getEmail() != null)
        	email.setText(contactList.get(position).getEmail());
        
        if(contactList.get(position).getFavorited())
        	star.setImageResource(android.R.drawable.btn_star_big_on);
        else
        	star.setImageResource(android.R.drawable.btn_star_big_off);

        //((ViewGroup) row).setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);

        return (row);
    }
     
    public List<Contact> getContactList() {
		return contactList;
	}

	public void selectContact(int position){
    	selectedContact = contactList.get(position);
    }
    
    public void removeContact() {
        contactList.remove(selectedContact);
        notifyDataSetChanged();
    }
}

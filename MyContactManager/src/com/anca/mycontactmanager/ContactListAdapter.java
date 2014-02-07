package com.anca.mycontactmanager;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ContactListAdapter extends ArrayAdapter<Contact> {

    Context context;
    int layoutResourceId;
    List<Contact> contacts;
    private Contact selectedContact;

    public ContactListAdapter(Activity context, int resId, List<Contact> contacts) {
        super(context, resId, contacts);
        this.context = context;
        this.contacts = contacts;
    }

 
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.contact_entry, parent, false);
        }

        TextView contactEntry = (TextView) convertView
                    .findViewById(R.id.contactName);
        Contact contact = getItem(position);
        contactEntry.setText(contact.toString()); 
        contactEntry.setTag(contact.getContact_id()); 

        return convertView;
    }
    
    public void selectContact(int position){
    	selectedContact = contacts.get(position);
    }
    
    public void removeContact() {
        contacts.remove(selectedContact);
        notifyDataSetChanged();
    }

}


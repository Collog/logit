package com.anca.mycontactmanager.ui;


import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockListFragment;
import com.anca.mycontactmanager.adapters.ContactListAdapter;
import com.anca.mycontactmanager.utils.Contact;


public abstract class ContactsFragment extends SherlockListFragment implements ActionBar.TabListener{
	List<Contact> contactList;
	ContactListAdapter adapter;
 
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	setContactList();
		adapter = new ContactListAdapter ( getActivity().getBaseContext(), contactList);
		
        setListAdapter(adapter);
 
        return super.onCreateView(inflater, container, savedInstanceState);
 
    }
	
	abstract void setContactList();
	
	public ContactListAdapter getContactListAdapter(){
		return adapter;
	}
 
    @Override
    public void onStart() {
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        /*ListView mListView = (ListView) getListView().findViewById(R.id.list);
        mListView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);*/
        
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        ft.add(android.R.id.content, this,"contacts");
        ft.attach(this);
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
         ft.detach(this);
    }
 
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }
    
    
    
    
}
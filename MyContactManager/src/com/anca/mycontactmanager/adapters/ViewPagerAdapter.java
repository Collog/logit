package com.anca.mycontactmanager.adapters;

import com.anca.mycontactmanager.ui.AllContactsFragment;
import com.anca.mycontactmanager.ui.ContactsFragment;
import com.anca.mycontactmanager.ui.FavoriteContactsFragment;
import com.anca.mycontactmanager.ui.RecentContactsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ViewPagerAdapter extends FragmentPagerAdapter {
 
    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 3;
 
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public ContactsFragment getItem(int arg0) {
    	ContactsFragment contactList = null;
        switch (arg0) {
 
        case 0:
            contactList = new AllContactsFragment();
            break;
        case 1:
        	contactList = new RecentContactsFragment();;
            break;
        case 2:
        	contactList = new FavoriteContactsFragment();
        	break;
        }
        //setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        return contactList;
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return PAGE_COUNT;
    }
    
 
}

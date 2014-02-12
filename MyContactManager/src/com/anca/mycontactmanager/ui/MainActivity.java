package com.anca.mycontactmanager.ui;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.anca.mycontactmanager.R;
import com.anca.mycontactmanager.adapters.ContactListAdapter;
import com.anca.mycontactmanager.adapters.ViewPagerAdapter;
import com.anca.mycontactmanager.utils.Contact;

 
public class MainActivity extends SherlockFragmentActivity implements OnItemClickListener{
	ActionBar mActionBar;
    ViewPager mPager;
    Tab tab;
    ContactListAdapter contactListAdapter;
    List<Contact> contactList;
    
    Activity activity = this;
    private ActionMode actionMode;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        mActionBar = getSupportActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle("Address Book");

        FragmentManager fm = getSupportFragmentManager();
 
        mPager = (ViewPager) findViewById(R.id.pager);
        ViewPager.SimpleOnPageChangeListener viewPagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mActionBar.setSelectedNavigationItem(position);
            }
        };
 
        final ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
        mPager.setOnPageChangeListener(viewPagerListener);
        mPager.setAdapter(viewpageradapter);
        //mPager.requestDisallowInterceptTouchEvent(false);
        
        
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
        	 
            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                mPager.setCurrentItem(tab.getPosition());
                contactListAdapter = (ContactListAdapter)( viewpageradapter.getItem(tab.getPosition())).getContactListAdapter();
                //??setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            }
 
            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }

		
        };
 
        tab = mActionBar.newTab().setText("All Contacts").setTabListener(tabListener);
        mActionBar.addTab(tab);
 
        tab = mActionBar.newTab().setText("Recent Contacts").setTabListener(tabListener);
        mActionBar.addTab(tab);
        
        tab = mActionBar.newTab().setText("Favorite Contacts").setTabListener(tabListener);
        mActionBar.addTab(tab);
        
        /*ListView listview = new ListView(activity);
        listview.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
        listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            	(Toast.makeText(getBaseContext(),"item clicked", Toast.LENGTH_SHORT)).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //never had to implement personally
            }
        });*/
 
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	
	
	/*public void onItemShortClick(View view){
		(Toast.makeText(this,"item clicked " + view.getPivotY(),Toast.LENGTH_SHORT)).show();
	}*/


	@Override
    public void onItemClick(AdapterView<?> adapterView, View view,
            int position, long id) {
		//(Toast.makeText(this,"item clicked",Toast.LENGTH_SHORT)).show();
		//mValue = (ViewPagerAdapter)adapterView.getAdapter().getItem(position);
		
		/*contactListAdapter.selectContact(position);
        actionMode = startActionMode(new ActionModeCallback());
        if (actionMode != null)
    		actionMode.setTitle(String.valueOf(contactList.get(position).getName()));*/
        
	}

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.ascending: 
			Collections.sort(contactList);
			contactListView.setAdapter(contactListAdapter);
			return true;
		case R.id.descending:
			Collections.sort(contactList,Collections.reverseOrder() );
			contactListView.setAdapter(contactListAdapter);
			return true;	
		}
		return super.onOptionsItemSelected(item);
	}*/
	
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

package com.lovecust.app.explore.todo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 6/5/2016 at 16:45
 * By Fisher
 */
public class AdapterExploreTodoFragments extends FragmentStatePagerAdapter {

	Map< Integer, Fragment > fragments = new HashMap<>( 3 );

	public AdapterExploreTodoFragments ( FragmentManager fm ) {
		super( fm );
		init();
	}

	private void init () {
		fragments.put( 0, new FragmentExploreTodoOkay() );
		fragments.put( 1, new FragmentExploreTodoDone() );
		fragments.put( 2, new FragmentExploreTodoRemoved() );
	}

	@Override
	public Fragment getItem ( int position ) {
		return fragments.get( position );
	}

	@Override
	public int getCount () {
		return 3;
	}
}

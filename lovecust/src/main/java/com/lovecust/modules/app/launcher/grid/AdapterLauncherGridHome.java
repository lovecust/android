package com.lovecust.modules.app.launcher.grid;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

public class AdapterLauncherGridHome extends FragmentStatePagerAdapter {

	Map< Integer, Fragment > fragments = new HashMap<>( 2 );

	public AdapterLauncherGridHome ( FragmentManager fm ) {
		super( fm );
		init();
	}

	private void init () {
		fragments.put( 0, new FragmentLauncherPageNav() );
		fragments.put( 1, new FragmentLauncherPageHome() );
	}

	@Override
	public Fragment getItem ( int position ) {
		return fragments.get( position );
	}

	@Override
	public int getCount () {
		return 2;
	}
}

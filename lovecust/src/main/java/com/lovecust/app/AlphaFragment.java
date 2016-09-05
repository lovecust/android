package com.lovecust.app;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fisher.utils.ToastUtil;

import butterknife.ButterKnife;

public abstract class AlphaFragment extends Fragment {

	public abstract int getLayoutID ( );

	public abstract void init ( );

	@Nullable
	@Override
	public View onCreateView ( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
		View view = inflater.inflate( getLayoutID(), container, false );
		ButterKnife.bind( this, view );
		setHasOptionsMenu( true );
		init();
		return view;
	}

	@Override
	public void onDestroyView ( ) {
		super.onDestroyView();
		ButterKnife.unbind( this );
	}

	protected String toast ( int stringId ) {
		return toast( getString( stringId ) );
	}

	protected String toast ( String msg ) {
		return ToastUtil.toastLong( getContext(), msg );
	}
}

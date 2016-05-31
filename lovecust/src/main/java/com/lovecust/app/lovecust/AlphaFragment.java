package com.lovecust.app.lovecust;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lovecust.app.utils.ToastUtil;

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

	public void toast ( String msg ) {
		ToastUtil.toast( getActivity(), msg );
	}

	public void toast ( Object msg ) {
		ToastUtil.toast( getActivity(), "" + msg );
	}


}

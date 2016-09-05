package com.lovecust.surfaces;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lovecust.app.R;


public class DialogSelectorIcon extends AlertDialog implements View.OnClickListener {


	public interface OnActionResultListener {
		void onActionCommit ( int res );
	}

	private OnActionResultListener listener;
	private TextView tvTitle;

	public static DialogSelectorIcon newDialog ( Context context ) {
		return new DialogSelectorIcon( context );
	}

	private DialogSelectorIcon ( Context context ) {
		super( context );
	}

	public DialogSelectorIcon init ( String title ) {
		View view = View.inflate( getContext(), R.layout.dialog_selector_icons, null );
		tvTitle = ( TextView ) view.findViewById( R.id.textTitle );

		int[] ids = new int[]{
				R.id.emoji_happy, R.id.emoji_angry, R.id.emoji_baseball, R.id.emoji_doggy, R.id.emoji_feeling_bad
				, R.id.emoji_football, R.id.emoji_ghost, R.id.emoji_glory, R.id.emoji_look_down_on, R.id.emoji_monkey
				, R.id.emoji_omg, R.id.emoji_umm, R.id.emoji_thinking, R.id.emoji_rocket, R.id.emoji_reversed_face };

		for ( int i = 0; i < ids.length; i++ ) {
			view.findViewById( ids[i] ).setOnClickListener( this );
		}


		if ( null != title )
			tvTitle.setText( title );
		setView( view );
		return this;
	}

	public DialogSelectorIcon setTitle ( String title ) {
		if ( null != tvTitle )
			tvTitle.setText( title );
		return this;
	}

	public DialogSelectorIcon setOnActionResultListener ( OnActionResultListener listener ) {
		this.listener = listener;
		return this;
	}

	@Override
	public void onClick ( View v ) {
		switch ( v.getId() ) {
			case R.id.emoji_happy:
				listener.onActionCommit( R.mipmap.emoji_happy );
				break;
			case R.id.emoji_angry:
				listener.onActionCommit( R.mipmap.emoji_angry );
				break;
			case R.id.emoji_baseball:
				listener.onActionCommit( R.mipmap.emoji_baseball );
				break;
			case R.id.emoji_doggy:
				listener.onActionCommit( R.mipmap.emoji_doggy );
				break;
			case R.id.emoji_feeling_bad:
				listener.onActionCommit( R.mipmap.emoji_feeling_bad );
				break;
			case R.id.emoji_football:
				listener.onActionCommit( R.mipmap.emoji_football );
				break;
			case R.id.emoji_ghost:
				listener.onActionCommit( R.mipmap.emoji_ghost );
				break;
			case R.id.emoji_glory:
				listener.onActionCommit( R.mipmap.emoji_glory );
				break;
			case R.id.emoji_look_down_on:
				listener.onActionCommit( R.mipmap.emoji_look_down_on );
				break;
			case R.id.emoji_monkey:
				listener.onActionCommit( R.mipmap.emoji_monkey );
				break;
			case R.id.emoji_omg:
				listener.onActionCommit( R.mipmap.emoji_omg );
				break;
			case R.id.emoji_umm:
				listener.onActionCommit( R.mipmap.emoji_umm );
				break;
			case R.id.emoji_thinking:
				listener.onActionCommit( R.mipmap.emoji_thinking );
				break;
			case R.id.emoji_rocket:
				listener.onActionCommit( R.mipmap.emoji_rocket );
				break;
			case R.id.emoji_reversed_face:
				listener.onActionCommit( R.mipmap.emoji_reversed_face );
				break;
		}
		dismiss();
	}
}

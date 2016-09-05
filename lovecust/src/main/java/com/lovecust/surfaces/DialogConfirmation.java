package com.lovecust.surfaces;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lovecust.app.R;


public class DialogConfirmation extends AlertDialog implements View.OnClickListener {
	private View view;

	public interface OnActionResultListener {
		void onActionCommit ( );

		void onActionCancel ( );
	}

	private OnActionResultListener listener;
	private TextView tTitle;

	public static DialogConfirmation newDialog ( Context context ) {
		return new DialogConfirmation( context );
	}

	private DialogConfirmation ( Context context ) {
		super( context );
	}

	public DialogConfirmation init ( String title ) {
		view = View.inflate( getContext(), R.layout.dialog_confirmation, null );
		tTitle = ( TextView ) view.findViewById( R.id.textTitle );
		view.findViewById( R.id.mActionCommit ).setOnClickListener( this );
		view.findViewById( R.id.mActionCancel ).setOnClickListener( this );
		if ( null != title )
			tTitle.setText( title );
		setView( view );
		return this;
	}

	public DialogConfirmation setTitle ( String title ) {
		if ( null != tTitle )
			tTitle.setText( title );
		return this;
	}

	public DialogConfirmation toYesNoChoice ( ) {
		( ( TextView ) view.findViewById( R.id.mActionCommit ) ).setText( R.string.okay );
		( ( TextView ) view.findViewById( R.id.mActionCancel ) ).setText( R.string.no );
		return this;
	}

	public DialogConfirmation setOnActionResultListener ( OnActionResultListener listener ) {
		this.listener = listener;
		return this;
	}

	@Override
	public void onClick ( View v ) {
		dismiss();
		switch ( v.getId() ) {
			case R.id.mActionCommit:
				if ( null != listener )
					listener.onActionCommit();
				break;
			case R.id.mActionCancel:
				if ( null != listener )
					listener.onActionCancel();
				break;
		}
	}
}

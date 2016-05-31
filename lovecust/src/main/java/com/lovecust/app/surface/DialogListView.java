package com.lovecust.app.surface;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lovecust.app.R;


public class DialogListView extends AlertDialog implements AdapterView.OnItemClickListener {


	public interface OnActionResultListener {
		void onActionCommit ( int index );
		void onActionCancel ();
	}

	public static DialogListView newDialog( Context context ) {
		return new DialogListView( context );
	}


	private OnActionResultListener listener;
	private Context context;

	public DialogListView( Context context ) {
		super( context );
		this.context = context;
	}

	public DialogListView init( String[] strOptions ) {
		View view = View.inflate( getContext(), R.layout.dialog_listview, null );
		ListView listOptions = (ListView) view.findViewById( R.id.listOptions );
		listOptions.setAdapter( new ArrayAdapter<>( context, R.layout.item_listview_option, R.id.text, strOptions ) );
		listOptions.setOnItemClickListener( this );
		setView( view );
		return this;
	}
	public DialogListView setOnActionResultListener( OnActionResultListener listener ) {
		this.listener = listener;
		return this;
	}
	@Override
	public void onItemClick( AdapterView< ? > parent, View view, int position, long id ) {
		if ( null != listener )
			listener.onActionCommit( position );
		dismiss();
	}
}

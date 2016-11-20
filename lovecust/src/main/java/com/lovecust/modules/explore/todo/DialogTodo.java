package com.lovecust.modules.explore.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.lovecust.app.R;
import com.fisher.utils.app.BaseRecyclerViewAdapter;
import com.lovecust.app.AppContext;
import com.fisher.utils.EdittextUtil;


public class DialogTodo extends AlertDialog implements View.OnClickListener {


	public static String mLastText = "";
	private EditText mEdittext;
	private RecyclerView mRecyclerView;
	private String tag;

	private BaseRecyclerViewAdapter< DataTodo > adapter;

	public static DialogTodo newDialog ( ) {
		Context context = AppContext.getContext();
		return new DialogTodo( context );
	}

	private DialogTodo ( Context context ) {
		super( context );
		getWindow().setType( WindowManager.LayoutParams.TYPE_SYSTEM_ALERT );
	}

	public DialogTodo init ( ) {
		return init( DataTodo.TAG_DEFAULT );
	}

	public DialogTodo init ( String tag ) {
		this.tag = tag;
		View view = View.inflate( getContext(), R.layout.dialog_things_to_do, null );
		mEdittext = ( EditText ) view.findViewById( R.id.et_text );
		EdittextUtil.setText( mEdittext, mLastText );

		( ( TextView ) view.findViewById( R.id.mTag ) ).setText( "-" + tag + "-" );
		TextView mCount = ( TextView ) view.findViewById( R.id.mCount );
		int total = DataTodo.amount( tag, DataTodo.STATE_OKAY );
		mCount.setText( Math.min( total, AdapterTodo.limit ) + "/" + ( total ) );
		TextView mAdd = ( TextView ) view.findViewById( R.id.tv_add );
		mAdd.setOnClickListener( this );


		mRecyclerView = ( RecyclerView ) view.findViewById( R.id.mRecyclerView );
		// use a linear layout manager
		LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
		mRecyclerView.setLayoutManager( mLayoutManager );
		mRecyclerView.setAdapter( adapter = new AdapterTodo( tag, DataTodo.STATE_OKAY ) );
		setView( view );
		return this;
	}


	public DialogTodo setEdittext ( String content ) {
		if ( null != mEdittext )
			mEdittext.setText( content );
		return this;
	}

	@Override
	public void onClick ( View v ) {
		switch ( v.getId() ) {
			case R.id.tv_add:
				String msg = mEdittext.getText().toString().trim();
				if ( "".equals( msg ) ) {
					return;
				}
				mEdittext.setText( "" );
				DataTodo todo = new DataTodo( tag, msg );
				todo.saveThrows();
				dismiss();
				break;
		}
	}

	@Override
	public void dismiss ( ) {
		String msg = mEdittext.getText().toString().trim();
		if ( !"".equals( msg ) )
			mLastText = msg;
		else
			mLastText = "";
		super.dismiss();
	}
}

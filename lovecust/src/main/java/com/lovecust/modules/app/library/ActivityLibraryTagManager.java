package com.lovecust.modules.app.library;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lovecust.app.BaseActivity;
import com.lovecust.app.Setting;
import com.lovecust.app.R;

import java.util.List;

import butterknife.Bind;


public class ActivityLibraryTagManager extends BaseActivity {

	@Bind(R.id.listLibraryTags)
	RecyclerView listLibraryTags;

	private AdapterListTags adapterListTags;

	@Override
	public int getLayout() {
		return  R.layout.activity_library_tag_manager;
	}

	@Override
	public void init () {
		setOnBackListener().setOptionText( "New Tag" ).setTitle( getString( R.string.library_setting_tags ) );

		findViewById( R.id.titleBarOptionLeft ).setOnClickListener( this );
		findViewById( R.id.titleBarOptionRight ).setOnClickListener( this );


		LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );

		List< Tag > tags = TagsManager.getTags();
//		tags.add( 0,  );
//		if( Setting.getSetting().)
		adapterListTags = new AdapterListTags().init( tags ).setListener( onTagItemClick );
		listLibraryTags.setLayoutManager( linearLayoutManager );
		listLibraryTags.setAdapter( adapterListTags );

	}

	private ViewHolderTags.OnTagItemClick onTagItemClick = new ViewHolderTags.OnTagItemClick() {
		@Override
		public void onClick ( long tagId ) {
			Intent intent = new Intent( ActivityLibraryTagManager.this, ActivityLibraryTagEditor.class )
					.putExtra( Setting.INTENT_KEY_DEFAULT, tagId );
			startActivity( intent );
		}
	};

	private void brnNewTag () {
		Intent intent = new Intent( ActivityLibraryTagManager.this, ActivityLibraryTagEditor.class )
				.putExtra( Setting.INTENT_KEY_DEFAULT, ActivityLibraryTagEditor.CODE_CREATE_TAG );
		startActivity( intent );
	}

	@Override
	public void onClick ( View v ) {
		switch ( v.getId() ) {
			case R.id.titleBarOptionLeft:
				finish();
				break;
			case R.id.titleBarOptionRight:
				brnNewTag();
				break;
		}
	}

}

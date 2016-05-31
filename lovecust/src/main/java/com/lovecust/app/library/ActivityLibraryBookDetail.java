package com.lovecust.app.library;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.Setting;
import com.lovecust.app.R;
import com.lovecust.app.surface.DialogListView;
import com.lovecust.app.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;


public class ActivityLibraryBookDetail extends AlphaActivity {

	@Bind(R.id.tag)
	TextView tag;

	private List< Tag > tags;
	private String[] tagStrings;
	private Book book;

	@Override
	public int getLayout() {
		return R.layout.activity_library_book_detail;
	}

	@Override
	public void init () {
		setOnBackListener();

		Intent intent = getIntent();
		long id = intent.getLongExtra( Setting.INTENT_KEY_DEFAULT, -1 );
		if ( id == -1 ) {
			ToastUtil.toast( "fatal error in the intent got!" );
			return;
		}

		book = BooksManager.getBook( id );
		if ( null == book ) {
			ToastUtil.toast( "fatal error in the book got!" );
			return;
		}

		findViewById( R.id.titleBarOptionLeft ).setOnClickListener( this );
		ImageView image = (ImageView) findViewById( R.id.image );
		TextView title = (TextView) findViewById( R.id.title );
		TextView author = (TextView) findViewById( R.id.author );
		TextView pages = (TextView) findViewById( R.id.pages );
		TextView date = (TextView) findViewById( R.id.date );
		TextView publisher = (TextView) findViewById( R.id.publisher );
		TextView isbn = (TextView) findViewById( R.id.isbn );

		ToggleButton fav = (ToggleButton) findViewById( R.id.titleBarOptionRight );
		fav.setChecked( book.isFavorite() );
		fav.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged ( CompoundButton buttonView, boolean isChecked ) {
				if ( isChecked ) {
					book.setFavorite( true );
					book.save();
				} else {
					book.setFavorite( false );
					book.save();
				}
			}
		} );

		tag.setText( book.getTagString() );

		tagStrings = TagsManager.getTagStrings();
		tags = TagsManager.getTags();

		tag.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick ( View v ) {
				DialogListView.newDialog( ActivityLibraryBookDetail.this ).init( tagStrings )
						.setOnActionResultListener( new DialogListView.OnActionResultListener() {
							@Override
							public void onActionCommit ( int index ) {
								book.setTag( tags.get( index ).getId() );
								tag.setText( tags.get( index ).getTag() );
								book.save();
							}

							@Override
							public void onActionCancel () {

							}
						} ).show();
			}
		} );


		TextView authorInfo = (TextView) findViewById( R.id.authorInfo );
		TextView summary = (TextView) findViewById( R.id.summary );
		TextView catalog = (TextView) findViewById( R.id.catalog );


		setTitle( book.getTitle() );
		title.setText( book.getTitle() );
		author.setText( Arrays.toString( book.getAuthor() ) );
		pages.setText( book.getPages() );
		date.setText( book.getPubdate() );
		publisher.setText( book.getPublisher() );
		authorInfo.setText( book.getAuthor_intro() );
		summary.setText( book.getSummary() );
		catalog.setText( book.getCatalog() );
		isbn.setText( book.getIsbn() );

		if ( null == book.getImages() || null == book.getImages().getLarge() )
			Picasso.with( this ).load( book.getImage() ).into( image );
		else
			Picasso.with( this ).load( book.getImages().getLarge() ).into( image );


		TextView text = (TextView) findViewById( R.id.text );
		text.setText( book.toString() );
	}


	public void btnTestText ( View view ) {
		view.setVisibility( View.GONE );
	}
}

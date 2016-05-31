package com.lovecust.app.library;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.Setting;
import com.lovecust.app.R;

import java.util.List;

import butterknife.Bind;

public class ActivityLibraryHome extends AlphaActivity {

	@Bind(R.id.listLibraryBooks)
	RecyclerView listLibraryBooks;
	@Bind(R.id.listLibrarySlider)
	RecyclerView listLibrarySlider;

	private AdapterListBooks adapterListBooks;
	private AdapterListTags adapterListTags;
	private List<Tag> tags;


	@Override
	public int getLayout() {
		return R.layout.activity_library_home;
	}

	@Override
	public void init() {
		setTitle(R.string.library_title);
		findViewById(R.id.titleBarOptionRight).setOnClickListener(this);
		findViewById(R.id.recent).setOnClickListener(this);
		findViewById(R.id.starred).setOnClickListener(this);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);

		adapterListBooks = new AdapterListBooks().init(BooksManager.getRecentBooks(), onBookItemClick);
		listLibraryBooks.setLayoutManager(linearLayoutManager);
		listLibraryBooks.setAdapter(adapterListBooks);


		tags = TagsManager.getTags();

		adapterListTags = new AdapterListTags().init(tags).setListener(onTagItemClick);
		listLibrarySlider.setLayoutManager(linearLayoutManager2);
		listLibrarySlider.setAdapter(adapterListTags);
	}

	private ViewHolderBooks.OnBookItemClick onBookItemClick = new ViewHolderBooks.OnBookItemClick() {
		@Override
		public void onClick(long id) {
			startActivity(new Intent(ActivityLibraryHome.this, ActivityLibraryBookDetail.class).putExtra(Setting.INTENT_KEY_DEFAULT, id));
		}
	};
	private ViewHolderTags.OnTagItemClick onTagItemClick = new ViewHolderTags.OnTagItemClick() {
		@Override
		public void onClick(long tagId) {
			flushView(BooksManager.getBooksByTag(tagId));
		}
	};

	@Override
	protected void onResume() {
		flushView(BooksManager.getRecentBooks());
		super.onResume();
	}

	private void flushView(List<Book> books) {
		adapterListBooks.init(books, onBookItemClick);
		adapterListBooks.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.titleBarOptionRight:
				startActivity(new Intent(this, ActivityLibraryNewBook.class));
				break;
			case R.id.recent:
				flushView(BooksManager.getRecentBooks());
				break;
			case R.id.starred:
				flushView(BooksManager.getFavoriteBooks());
				break;
		}
	}

}

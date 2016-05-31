package com.lovecust.app.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lovecust.app.lovecust.AlphaActivity;
import com.lovecust.app.lovecust.Setting;
import com.lovecust.app.R;
import com.lovecust.app.utils.BugsUtil;
import com.lovecust.app.utils.ConsoleUtil;
import com.lovecust.app.utils.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.List;
//
//import retrofit.Callback;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;


public class ActivityLibraryNewBook extends AlphaActivity {

	private EditText edittext;
	private InterfaceBookAPI service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_library_new_book);
		init();
	}

	@Override
	public int getLayout() {
		return R.layout.activity_library_new_book;
	}

	@Override
	public void init() {
		setOnBackListener();

		edittext = (EditText) findViewById(R.id.edittext);

//		RestAdapter adapter = new RestAdapter.Builder()
//				.setEndpoint(Setting.URL_DOUBAN_API_SERVER)
//				.build();
//		service = adapter.create(InterfaceBookAPI.class);

	}


	private void queryBooks() {
		String isbn = edittext.getText().toString().trim();
		if (isbn.length() != 13) {
			ToastUtil.toast("Please Input 13 ISBN Numbers Correctly!");
			return;
		}
		List<Book> books = DataSupport.where("isbn=?", isbn).find(Book.class);
		if (null != books && books.size() >= 1) {
			if (books.size() > 1) {
				BugsUtil.onFatalError("ActivityLibraryNewBook.onCLick()-> Got books size is more than 1!");
			}
			ConsoleUtil.console(books.get(0));
			Intent intent = new Intent(ActivityLibraryNewBook.this, ActivityLibraryBookDetail.class).putExtra(Setting.INTENT_KEY_DEFAULT, books.get(0).getId());
			startActivity(intent);
			ActivityLibraryNewBook.this.finish();
			return;
		}
//		service.getBookInfo(isbn, new Callback<Book>() {
//			@Override
//			public void success(Book book, Response response) {
//				try {
//					book.saveThrows();
//					ConsoleUtil.console(book);
//					Intent intent = new Intent(ActivityLibraryNewBook.this, ActivityLibraryBookDetail.class).putExtra(Setting.INTENT_KEY_DEFAULT, book.getId());
//					startActivity(intent);
//					ActivityLibraryNewBook.this.finish();
//				} catch (Exception e) {
//					e.printStackTrace();
//					ToastUtil.toast("Failed to store book info: " + e.toString());
//				}
//			}
//
//			@Override
//			public void failure(RetrofitError error) {
//				error.printStackTrace();
//				ToastUtil.toast("failed to login book message!" + error.toString());
//			}
//		});

	}


	public void btnScanBarcode(View view) {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, Setting.INTENT_REQUEST_CODE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == Setting.INTENT_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				ConsoleUtil.console("Got Intent: " + new Gson().toJson(intent.getExtras()));
				ConsoleUtil.console("Content: " + contents + ";  format: " + format);
				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				ToastUtil.toast("Scan Barcode Canceled!");
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.titleBarOptionRight:
				queryBooks();
				break;
		}

	}

}

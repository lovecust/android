package com.lovecust.modules.explore.todo;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovecust.app.BaseActivity;
import com.lovecust.app.R;
import com.lovecust.modules.explore.popWindow.ServicePopWindowFisher;
import com.lovecust.app.Setting;
import com.lovecust.surfaces.DialogConfirmation;
import com.lovecust.surfaces.DialogListView;
import com.fisher.utils.AppUtil;
import com.fisher.utils.DensityUtils;
import com.fisher.utils.FileUtil;
import com.fisher.utils.TimeUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Fisher on 5/9/2016 at 23:06
 */
public class ActivityExploreTodoHome extends BaseActivity {

	@Bind(R.id.tv_okay)
	TextView tvOkay;
	@Bind(R.id.tv_done)
	TextView tvDone;
	@Bind(R.id.tv_removed)
	TextView tvRemoved;
	@Bind(R.id.tv_underline)
	TextView tvUnderline;

	@Bind(R.id.viewPager)
	ViewPager mPager;


	private String[] strOptions;
	private TextView[] tvNavs;
	private AdapterExploreTodoFragments adapter;
	private int colorApp;
	private int colorBlack;
	private LinearLayout.LayoutParams params;

	@Override
	public int getLayout() {
		return R.layout.activity_explore_todo_home;
	}

	@Override
	public void init() {
		setTitle(R.string.title_explore_todo);
		colorApp = AppUtil.getColor(R.color.app);
		colorBlack = AppUtil.getColor(R.color.black);
		tvNavs = new TextView[]{tvOkay, tvDone, tvRemoved};
//		tvUnderline.setWidth(  );
		int navWidth = DensityUtils.getScreenWidth(this) / 3;
		params = new LinearLayout.LayoutParams(navWidth, 4);
		params.setMargins(0, 0, 0, 0);
		tvUnderline.setLayoutParams(params);

		mPager.setAdapter(adapter = new AdapterExploreTodoFragments(getSupportFragmentManager()));
		mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				params.setMargins((int) ((positionOffset + position) * navWidth), 0, 0, 0);
				tvUnderline.setLayoutParams(params);
			}

			@Override
			public void onPageSelected(int position) {
				setColor(position);

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		setColor(0);
	}

	private void setColor(int index) {
		for (int i = 0; i < tvNavs.length; i++) {
			tvNavs[i].setTextColor(colorBlack);
		}
		tvNavs[index].setTextColor(colorApp);
	}


	@OnClick({R.id.tv_okay, R.id.tv_done, R.id.tv_removed})
	void btnNavs(View view) {
		switch (view.getId()) {
			case R.id.tv_okay:
				mPager.setCurrentItem(0, true);
				break;
			case R.id.tv_done:
				mPager.setCurrentItem(1, true);
				break;
			case R.id.tv_removed:
				mPager.setCurrentItem(2, true);
				break;
		}
	}

	private void btnOptions() {
		if (null == strOptions)
			strOptions = AppUtil.getStringArray(R.array.dialog_explore_todo_options);
		DialogListView.newDialog(this).init(strOptions)
				.setOnActionResultListener(new DialogListView.OnActionResultListener() {
					@Override
					public void onActionCommit(int index) {
						switch (index) {
							case 0:
								btnStartTodoService();
								break;
							case 1:
								btnStopTodoService();
								break;
							case 2:
								btnExportData();
								break;
							case 3:
								DialogConfirmation.newDialog(ActivityExploreTodoHome.this).init(strOptions[3] + "?").toYesNoChoice()
										.setOnActionResultListener(new DialogConfirmation.OnActionResultListener() {
											@Override
											public void onActionCommit() {
												int item = DataTodo.fnDelete(DataTodo.TAG_TODO, DataTodo.STATE_REMOVED);
												toast(getString(R.string.toast_explore_todo_deleted) + item + getString(R.string.toast_explore_todo_items));
											}

											@Override
											public void onActionCancel() {

											}
										}).show();
								break;
							case 4:
								DialogConfirmation.newDialog(ActivityExploreTodoHome.this).init(strOptions[4] + "?").toYesNoChoice()
										.setOnActionResultListener(new DialogConfirmation.OnActionResultListener() {
											@Override
											public void onActionCommit() {
												int item = DataTodo.fnDelete(DataTodo.TAG_TODO, DataTodo.STATE_DONE);
												toast(getString(R.string.toast_explore_todo_deleted) + item + getString(R.string.toast_explore_todo_items));
											}

											@Override
											public void onActionCancel() {

											}
										}).show();
								break;
							case 5:
								DialogConfirmation.newDialog(ActivityExploreTodoHome.this).init(strOptions[5] + "?").toYesNoChoice()
										.setOnActionResultListener(new DialogConfirmation.OnActionResultListener() {
											@Override
											public void onActionCommit() {
												int item = DataTodo.fnDelete(DataTodo.TAG_TODO);
												toast(getString(R.string.toast_explore_todo_deleted) + item + getString(R.string.toast_explore_todo_items));
											}

											@Override
											public void onActionCancel() {

											}
										}).show();
								break;
						}
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}


	@OnClick({R.id.titleBarOptionLeft, R.id.titleBarOptionRight})
	void btnClick(View view) {
		switch (view.getId()) {
			case R.id.titleBarOptionLeft:
				finish();
				break;
			case R.id.titleBarOptionRight:
				btnOptions();
				break;
		}
	}

	public void btnStartTodoService() {
		Intent intent = new Intent(this, ServicePopWindowFisher.class);
		startService(intent);
	}

	public void btnStopTodoService() {
		Intent intent = new Intent(this, ServicePopWindowFisher.class);
		stopService(intent);
	}

	private void btnExportData() {
		File file = FileUtil.getExternalFile(Setting.FILE_EXTERNAL_DIR_APP_DATA + "todo-" + TimeUtil.fnFormatTime(TimeUtil.FORMAT_yyyy_MM_dd) + "_" + System.currentTimeMillis() + ".txt");
		try {
			DataTodo.fnExport(file);
			toast(getString(R.string.toast_explore_todo_export_successfully) + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			toast("Failed to export todo-list data!");
		}
	}

}

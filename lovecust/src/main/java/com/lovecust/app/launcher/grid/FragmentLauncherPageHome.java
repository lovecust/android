package com.lovecust.app.launcher.grid;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.app.ecust.morning.ActivityEcustMorningExercise;
import com.lovecust.app.entity.AppProfile;
import com.lovecust.app.explore.todo.ActivityExploreTodoHome;
import com.lovecust.app.lovecust.AlphaFragment;
import com.lovecust.app.lovecust.AppSetting;
import com.lovecust.app.utils.AppUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;


public class FragmentLauncherPageHome extends AlphaFragment {

	@Bind(R.id.nick)
	TextView nick;
	@Bind(R.id.avatar)
	ImageView avatar;

	@Bind(R.id.btn_todo)
	View layoutTodo;
	@Bind(R.id.span_todo)
	View spanTodo;

	@Bind(R.id.btn_morning_exercise)
	View layoutMorningExercise;
	@Bind(R.id.span_morning_exercise)
	View spanMorningExercise;

	private AppSetting setting;

	@Override
	public int getLayoutID() {
		return R.layout.fragment_launcher_page_home;
	}

	@Override
	public void init() {
		setting = AppSetting.getInstance();
	}

	@OnClick(R.id.btn_morning_exercise)
	void btnEcustMorningExercise(View view) {
		btnStartActivity(ActivityEcustMorningExercise.class);
	}

	@OnClick(R.id.btn_todo)
	void btnTodo() {
		btnStartActivity(ActivityExploreTodoHome.class);
	}

	private void btnStartActivity(Class activity) {
		getActivity().startActivity(new Intent(getActivity(), activity));
		getActivity().overridePendingTransition(R.anim.activity_push_in_from_right, R.anim.activity_push_out_from_left);
	}

	@Override
	public void onResume() {
		String name = AppProfile.getInstance().getNick();
		if (null != name && !"".equals(name))
			nick.setText(name);
		File temp = AppProfile.getInstance().getAvatarFile();
		if (null != temp && temp.exists()) {
			avatar.setImageURI(Uri.fromFile(temp));
		} else {
//			avatar.setColorFilter(AppUtil.getColor(R.color.app), PorterDuff.Mode.SRC_IN);
		}

		if (setting.isDeveloperMode()) {
			layoutTodo.setVisibility(View.VISIBLE);
			spanTodo.setVisibility(View.VISIBLE);

			layoutMorningExercise.setVisibility(View.VISIBLE);
			spanMorningExercise.setVisibility(View.VISIBLE);
		} else {
			layoutTodo.setVisibility(View.GONE);
			spanTodo.setVisibility(View.GONE);

			layoutMorningExercise.setVisibility(View.GONE);
			spanMorningExercise.setVisibility(View.GONE);
		}
		super.onResume();
	}
}

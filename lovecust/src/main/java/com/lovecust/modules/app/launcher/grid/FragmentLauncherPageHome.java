package com.lovecust.modules.app.launcher.grid;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovecust.app.R;
import com.lovecust.modules.ecust.morning.ActivityEcustMorningExercise;
import com.lovecust.entities.AppProfile;
import com.lovecust.modules.explore.todo.ActivityExploreTodoHome;
import com.lovecust.app.BaseFragment;
import com.lovecust.app.AppSetting;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;


public class FragmentLauncherPageHome extends BaseFragment {

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
		getActivity().overridePendingTransition(R.anim.activity_push_in_from_right, R.anim.activity_push_out_to_left );
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
			layoutMorningExercise.setVisibility(View.VISIBLE);
			spanMorningExercise.setVisibility(View.VISIBLE);
		} else {
			layoutMorningExercise.setVisibility(View.GONE);
			spanMorningExercise.setVisibility(View.GONE);
		}
		super.onResume();
	}
}

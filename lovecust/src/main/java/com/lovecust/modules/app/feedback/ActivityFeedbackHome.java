package com.lovecust.modules.app.feedback;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lovecust.network.entities.AppProfile;
import com.lovecust.app.BaseActivity;
import com.lovecust.app.R;
import com.lovecust.network.LovecustNetworkManager;
import com.lovecust.surfaces.DialogEdittext;
import com.lovecust.tests.test.TestRouter;
import com.fisher.utils.InstantStoreUtil;
import com.fisher.utils.NetUtil;
import com.lovecust.utils.AppFeedbackHelper;
import com.lovecust.utils.AppProfileHelper;
import com.lovecust.utils.NetworkManager;

import butterknife.Bind;
import butterknife.OnClick;

public class ActivityFeedbackHome extends BaseActivity {

	@Bind(R.id.tv_messages)
	TextView mMessagesHolder;
	@Bind(R.id.et_text)
	EditText mEditText;
	@Bind(R.id.tv_submit)
	View mSubmit;
	@Bind(R.id.scrollView)
	ScrollView scrollView;

	private AppProfile appProfile;
	private InstantStoreUtil instantStore;
	private TestRouter router;
	private LovecustNetworkManager mNetworkManager;

	@Override
	public int getLayout() {
		return R.layout.activity_feedback_home;
	}

	@Override
	public void init() {
		setOnBackListener().setTitle(R.string.title_feedback);
		mNetworkManager = NetworkManager.getLovecustNetworkManager();

		appProfile = AppProfileHelper.getInstance();
		instantStore = InstantStoreUtil.getInstance();
		router = new TestRouter(this);

		if ("".equals(appProfile.getNick())) {
			toast(getString(R.string.toast_feedback_set_nick_before_post));
			setNick();
		}

		mEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.toString().length() == 0) {
					mSubmit.setBackgroundColor(getResources().getColor(R.color.bg_text_button_disabled_color));
				} else {
					mSubmit.setBackgroundColor(getResources().getColor(R.color.bg_text_button_normal_color));
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		mEditText.setText(instantStore.getString(getClass().getName()));
		mMessagesHolder.setText(AppFeedbackHelper.getSystemSpannable("Great to see you here!"));
		loadHistory();
	}

	private void loadHistory() {
		AppFeedbackHelper.renderHistory(mMessagesHolder);
		scrollView.fullScroll(View.FOCUS_DOWN);
		if (NetUtil.isOfflineAndToast())
			return;
		mNetworkManager.getUnreadFeedback()
				.subscribe(feedbacks -> {
					for (int i = 0; i < feedbacks.length; i++) {
						mMessagesHolder.append(AppFeedbackHelper.getSpannableString(feedbacks[i]));
//						feedbacks[i].saveThrows();
						// TODO SAVE THROWS.
					}
				}, err -> {
					err.printStackTrace();
					toast(R.string.toast_server_error);
				});
	}

	private void feedbackSend() {
		String msg = mEditText.getText().toString().trim();
		if ("".equals(msg))
			return;
		if ("".equals(appProfile.getNick())) {
			toast(getString(R.string.toast_feedback_set_nick_before_post));
			setNick();
			return;
		}
		router.check(msg);
		if (NetUtil.isOfflineAndToast())
			return;
		mEditText.setText("");
		mNetworkManager.postFeedback(appProfile.getNick(), msg)
				.subscribe(res -> {
					long ctime = res.getCtime();
//					res.saveThrows();
					mMessagesHolder.append(AppFeedbackHelper.getSpannableString(res));
					scrollView.fullScroll(View.FOCUS_DOWN);
				}, err -> {
					mEditText.setText(msg);
					log("ActivityFeedbackHome.feedbackSend().onError()-> ");
//					err.toast();
					toast("ActivityFeedbackHome.feedbackSend().onError()-> Unknown Error");
				});
	}

	public void setNick() {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_nickname))
				.setEdittext(appProfile.getNick()).setHintText(getString(R.string.hint_profile_nick))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						input = input.replace("\n\r\b", "").trim();
						appProfile.setNick(input);
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	@Override
	protected void onDestroy() {
		instantStore.putString(getClass().getName(), mEditText.getText().toString());
		super.onDestroy();
	}

	@OnClick(R.id.tv_submit)
	public void btnClick(View v) {
		switch (v.getId()) {
			case R.id.tv_submit:
				feedbackSend();
				break;
		}
	}


}

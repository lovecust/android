package com.lovecust.modules.ecust.jwc;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import com.lovecust.app.BaseActivity;
import com.lovecust.app.R;
import com.lovecust.network.entities.EcustJwcNews;
import com.lovecust.app.Setting;
import com.lovecust.network.EcustNetworkManager;
import com.lovecust.surfaces.DialogConfirmation;
import com.lovecust.surfaces.DialogEdittext;
import com.fisher.utils.NetUtil;
import com.lovecust.utils.AppProfileHelper;
import com.lovecust.utils.NetworkManager;

import butterknife.Bind;
import butterknife.OnClick;

public class ActivityEcustJwcDetail extends BaseActivity {
	public static final String JWC_URL_SERVER = "http://jwc.ecust.edu.cn";
	/**
	 * Used as key to transfer value via bundle.
	 */
	public static final String KEY_NEWS_ID = "news-id";

	@Bind(R.id.title)
	TextView title;
	@Bind(R.id.date)
	TextView date;
	@Bind(R.id.content)
	TextView content;
	@Bind(R.id.et_text)
	EditText input;
	@Bind(R.id.tv_submit)
	TextView submit;

	private String md5;
	private EcustJwcNews news;
	private EcustJwcNews temp = new EcustJwcNews();

	private FragmentJwcComments fragmentJwcComments;
	private EcustNetworkManager mNetworkManager;

	@Override
	public int getLayout() {
		return R.layout.activity_ecust_jwc_detail;
	}

	@Override
	public void init() {
		setOnBackListener().setTitle(R.string.title_ecust_jwc);
		mNetworkManager = NetworkManager.getEcustNetworkManager();

		md5 = getIntent().getStringExtra(Setting.INTENT_KEY_DEFAULT);
		temp.setMd5(md5);
		temp.setName(getIntent().getStringExtra(Setting.INTENT_KEY_TITLE));
		temp.setDate(getIntent().getStringExtra(Setting.INTENT_KEY_DATE));
		title.setText(temp.getName());
		date.setText(temp.getDate());

		submit.setOnClickListener(v -> submit());

		if (null == md5) {
			toast("got null expected a news id!");
			finish();
			return;
		}
		if (NetUtil.isOfflineAndToast())
			return;

		mNetworkManager.getJwcNewsDetail(md5)
				.subscribe(news -> {
					this.news = news;
					title.setText(news.getName());
					date.setText(news.getDate());
					content.setText(Html.fromHtml(news.getValue()));
				}, err -> {
					err.printStackTrace();
					toast(R.string.toast_server_error_get_unexpected_response);
				});
		Bundle bundle = new Bundle();
		bundle.putString(KEY_NEWS_ID, md5);
		fragmentJwcComments = new FragmentJwcComments();
		fragmentJwcComments.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().add(R.id.commentsHolder, fragmentJwcComments).commit();
	}


	private void submit() {
		String text = input.getText().toString();
		if ("".equals(text)) {
			toast("Your input is null!");
			return;
		}
		if ("".equals(AppProfileHelper.getInstance().getNick())) {
			toast("Please set your nick before post!");
			setNick();
			return;
		}
		fragmentJwcComments.postComment(text);
		input.setText("");
	}


	public void setNick() {
		DialogEdittext.newDialog(this)
				.init(getString(R.string.dialog_profile_set_nickname))
				.setEdittext(AppProfileHelper.getInstance().getNick()).setHintText(getString(R.string.hint_profile_nick))
				.setOnActionResultListener(new DialogEdittext.OnActionResultListener() {
					@Override
					public void onActionCommit(String input) {
						input = input.replace("\n\r\b", "").trim();
						AppProfileHelper.getInstance().setNick(input);
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

	@OnClick(R.id.titleBarOptionRight)
	void btnMore() {
		DialogConfirmation.newDialog(this).init(getString(R.string.dialog_open_url_use_browser)).toYesNoChoice()
				.setOnActionResultListener(new DialogConfirmation.OnActionResultListener() {
					@Override
					public void onActionCommit() {
						try {
							String url = news.getUrl();
							Intent i = new Intent(Intent.ACTION_VIEW);
							i.setData(Uri.parse(JWC_URL_SERVER + url));
							startActivity(i);
						} catch (Exception e) {
							e.printStackTrace();
							toast("Error happened when open browser!");
						}
					}

					@Override
					public void onActionCancel() {

					}
				}).show();
	}

}

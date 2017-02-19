package com.lovecust.modules.ecust.library;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovecust.network.entities.EcustLibraryStatus;
import com.lovecust.app.BaseActivity;
import com.lovecust.app.R;
import com.fisher.utils.NetUtil;
import com.lovecust.utils.NetworkManager;
import com.lovecust.network.EcustNetworkManager;

import butterknife.Bind;


public class ActivityEcustLibraryHome extends BaseActivity {

	private int AMOUNT_FLOORS = 6;

	@Bind(R.id.today)
	TextView today;

	private LibraryFloorHolder[] holders = new LibraryFloorHolder[AMOUNT_FLOORS];
	private EcustNetworkManager mNetworkManager;


	@Override
	public int getLayout() {
		return R.layout.activity_ecust_library_home;
	}

	@Override
	public void init() {
		setOnBackListener().setTitle(R.string.title_ecust_library);
		mNetworkManager = NetworkManager.getEcustNetworkManager();


		LinearLayout parent = (LinearLayout) findViewById(R.id.parent);
		for (int i = 0; i < AMOUNT_FLOORS; i++) {
			holders[i] = new LibraryFloorHolder(this);
			parent.addView(holders[i].getView());
		}
		flush();
	}

	private void flush() {
		if (NetUtil.isOfflineAndToast())
			return;
		mNetworkManager.getLibraryStatus()
				.subscribe(this::render, err -> {
					toast(R.string.toast_server_error);
				});
	}

	private void render(EcustLibraryStatus status) {
		if (null == status) {
			return;
		}
		if (null == today) {
			// occur when activities is exited
			return;
		}
		today.setText(getString(R.string.ecust_library_text_students_amount_today_before) + status.getToday());
		for (int i = 0; i < AMOUNT_FLOORS; i++) {
			holders[i].render(i
					, status.getOccupied()[i]
					, status.getAvailable()[i]);
		}
	}


}

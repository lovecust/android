package com.lovecust.utils;

import com.fisher.utils.AppUtil;
import com.fisher.utils.ConsoleUtil;
import com.fisher.utils.NetUtil;
import com.fisher.utils.TextUtil;
import com.lovecust.app.AppContext;
import com.lovecust.app.R;
import com.lovecust.modules.ecust.wifi.SettingWifi;
import com.lovecust.network.DevelopmentNetworkManager;
import com.lovecust.network.EcustNetworkManager;
import com.lovecust.network.LovecustNetworkManager;

/**
 * Created by Fisher on 5/22/2016 at 1:03
 */
public class NetworkManager {

	public static NetworkManager mManager = null;

	public static NetworkManager getInstance() {
		if (null == mManager) {mManager = new NetworkManager();}
		return mManager;
	}

	/**
	 * Get EcustNetworkManager instance.
	 *
	 * @return EcustNetworkManager instance.
	 */
	public static EcustNetworkManager getEcustNetworkManager() {
		return new EcustNetworkManager(getAgent(), getHeader());
	}

	/**
	 * Get LovecustNetworkManager instance.
	 *
	 * @return EcustNetworkManager instance.
	 */
	public static LovecustNetworkManager getLovecustNetworkManager() {
		return new LovecustNetworkManager(getAgent(), getHeader());
	}

	/**
	 * Get DevelopmentNetworkManager instance.
	 *
	 * @return EcustNetworkManager instance.
	 */
	public static DevelopmentNetworkManager getDevelopmentNetworkManager() {
		return new DevelopmentNetworkManager(getAgent(), getHeader());
	}


	public static String getAgent() {
		String header = AppUtil.getAndroidInfo();
		ConsoleUtil.log("header.agent: " + header);
		return header;
	}

	public static String getHeader() {
		String uid = AppProfileHelper.getInstance().getUid();
		if ("".equals(uid)) {
			uid = SettingWifi.getInstance().getUsername();
			if ("".equals(uid))
				uid = AppContext.getContext().getString(R.string.hint_profile_uid);
		}
		String header = AppContext.mAppVersion + "&" + AppContext.mApiVersion + "&ap." + TextUtil.md5(NetUtil.mac) + "&" + uid;
		ConsoleUtil.log("header.lovecust: " + header);
		return header;
	}


}

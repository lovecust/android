package com.lovecust.utils;

import com.fisher.utils.FileUtil;
import com.lovecust.app.Setting;
import com.lovecust.network.entities.EcustJwcNews;

import java.io.File;

/**
 * Created by fisher at 3:49 AM on 12/19/16.
 * <p>
 * Ecust Jwc News Helper.
 */

public class EcustJwcHelper {

	public static String FILENAME = Setting.FILE_INTERNAL_CACHE_JWC_NEWS;

	public static EcustJwcNews[] getCachedNews() {
		File file = FileUtil.getInternalFile(FILENAME);

		return null;
	}

}
